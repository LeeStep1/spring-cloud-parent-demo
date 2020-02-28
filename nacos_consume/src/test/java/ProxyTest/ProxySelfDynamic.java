package ProxyTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-27
 **/
public class ProxySelfDynamic implements InvocationHandler{

    public Object factory;

    public Object proxyFactory(){
        return Proxy.newProxyInstance(factory.getClass().getClassLoader(),factory.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态    联系厂家....");
        Object b = method.invoke(factory,args);
        System.out.println("动态    提供售后....");
        return b;
    }

    public ProxySelfDynamic(Object factory){
        this.factory = factory;
    }
}
