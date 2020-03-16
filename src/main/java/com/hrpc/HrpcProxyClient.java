package com.hrpc;

import com.hrpc.discovery.IServiceDiscovery;
import com.hrpc.discovery.ServiceDiscoveryWithzk;

import java.lang.reflect.Proxy;

public class HrpcProxyClient {

    private IServiceDiscovery serviceDiscovery = new ServiceDiscoveryWithzk();


    public <T> T clientProxy(final Class<T> interfaceCls, String version){

        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},new RemoteInvocationHandler(serviceDiscovery,version));
    }
}
