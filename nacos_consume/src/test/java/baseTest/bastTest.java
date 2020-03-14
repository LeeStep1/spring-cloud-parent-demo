package baseTest;

import org.junit.Test;

/**
 * @description:
 * @author: liyang
 * @date: 2020-03-02
 **/
public class bastTest {

    public static void main(String[] args) {
        String a = "abc";
        String b = "def";
        String c = a+b;
        System.out.println(c.intern().hashCode());
        System.out.println((a+b).hashCode());
    }

    @Test
    public void test1(){
        String a = "abc";
        String b = "abc";
        System.out.println(a==b);

        System.out.println("==================");

        String a1 = "abc";
        a1.intern();
        String b1 = "abc";
        System.out.println(a1==b1);

        System.out.println("==================");

        String a2 = new String("abc");
        String b2 = new String("abc");
        System.out.println(a2==b2);

        System.out.println("==================");

        String a3 = new String("abc");
        a3.intern();
        String b3 = new String("abc");
        System.out.println(a3==b3);
        System.out.println("==================");

        String a4 = new String("abc");
        String b4 = "abc";
        System.out.println(a4==b4);
        System.out.println("==================");

        String a5 = new String("abc");
        a5.intern();
        String b5 = "abc";
        System.out.println("no 5");
        System.out.println( a5==b5);
        System.out.println("==================");

        String a6 = new String("abc");
        String c = a6.intern();
        String b6 = "abc";
        System.out.println("no 6");
        System.out.println( c==b6);
        System.out.println("==================");

        String a7 = new String("abc");
        String c7 = a7.intern();
        String b7 = "abc";
        System.out.println("no 7");
        System.out.println( a7==c7);
        System.out.println("==================");
    }
}
