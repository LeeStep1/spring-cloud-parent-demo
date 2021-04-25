package ProxyTest.JDKDY;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MovieInvocationHandle implements InvocationHandler {

    Object movieFactory;

    public MovieInvocationHandle (MovieFactory movieFactory){
        this.movieFactory = movieFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("啤酒饮料矿泉水...");
        System.out.println("花生瓜子准备好..........");
        HomeMovieFactory h = (HomeMovieFactory) method.invoke(movieFactory);

        return h;
    }
}
