package com.hrpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huoji
 */
@Configuration
public class SpringConfig {

     @Bean(name = "hrpcProxyClient")
     public HrpcProxyClient proxyClient(){

         return new HrpcProxyClient();
     }
}
