package defaultInteface;

/**
 * @description:
 * @author: liyang
 * @date: 2020-03-03
 **/
public interface ServiceA {

    default void a(){
        System.out.println("默认实现了A 方法......");
    };

    void b();
}
