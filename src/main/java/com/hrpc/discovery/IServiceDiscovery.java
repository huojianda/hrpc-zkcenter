package com.hrpc.discovery;

public interface IServiceDiscovery {
    /**
     * 根据服务名称返回 服务地址
     */
    String  discovery(String serviceName);
}
