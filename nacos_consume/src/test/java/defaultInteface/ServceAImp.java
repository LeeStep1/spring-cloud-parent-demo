package defaultInteface;

/**
 * @description:
 * @author: liyang
 * @date: 2020-03-03
 **/
public class ServceAImp implements ServiceA{

    @Override
    public void b() {
        System.out.println("实现类实现了B 方法....");
    }

    public void a(){
        System.out.println("子类重写 方法......");
    }
}
