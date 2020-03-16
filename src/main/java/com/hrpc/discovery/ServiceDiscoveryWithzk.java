package com.hrpc.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

public class ServiceDiscoveryWithzk implements IServiceDiscovery {

    CuratorFramework curatorFramework = null;

    List<String> serviceRepos=new ArrayList<String>(); //服务地址的本地缓存
    {
        //初始化zookeeper的连接， 会话超时时间是5s，衰减重试
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZookeeperConfig.URL).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("registry")
                .build();
        curatorFramework.start();

    }

    public String discovery(String serviceName) {
        String path = "/"+serviceName;
        if(serviceRepos.isEmpty()){
            try {
                serviceRepos = curatorFramework.getChildren().forPath(path);
                registryWatch(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoadBalanceStrategy loadBalanceStrategy = new RandomLoadBalance();

        return loadBalanceStrategy.selectHost(serviceRepos);
    }

    private void registryWatch(String path) throws Exception {

        PathChildrenCache nodeCache = new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener nodeCacheListener= (curatorFramework1, pathChildrenCacheEvent) -> {
            System.out.println("客户端收到节点变更的事件");
            serviceRepos=curatorFramework1.getChildren().forPath(path);// 再次更新本地的缓存地址
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }
}
