package ProxyTest.JDKDY;

import org.junit.Test;

import java.lang.reflect.Proxy;

public class TestDemo {


    @Test
    public void demo1(){

        MovieFactory movieFactory = new MovieFactory();

        HomeMovieFactory p = (HomeMovieFactory) Proxy.newProxyInstance(MovieFactory.class.getClassLoader(),MovieFactory.class.getInterfaces(),new MovieInvocationHandle(movieFactory));
        p.doMovie();
    }
}
