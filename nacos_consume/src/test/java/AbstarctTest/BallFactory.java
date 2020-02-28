package AbstarctTest;

import java.lang.reflect.Constructor;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-25
 **/
public class BallFactory {
    public static <T>Object makeBall(String name) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class<?> aClass = Class.forName(name);
        return aClass.newInstance();
    }

}
