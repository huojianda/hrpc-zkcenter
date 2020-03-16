package com.hrpc;

import com.hrpc.api.IHelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {


    public static void main( String[] args ) throws InterruptedException {
        ApplicationContext context=new
                AnnotationConfigApplicationContext(SpringConfig.class);
        HrpcProxyClient rpcProxyClient=context.getBean(HrpcProxyClient.class);

        IHelloService iHelloService=rpcProxyClient.clientProxy
                (IHelloService.class,"v1.0");
        for(int i=0;i<1;i++) {
            Thread.sleep(2000);
            System.out.println(iHelloService.sayHello("danielHuo"));
        }
    }
}
