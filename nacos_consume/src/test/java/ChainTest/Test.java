package ChainTest;

public class Test {

    @org.junit.Test
    public void demo(){
        FilterChain fc = new FilterChain();
        fc.add(new AFilter()).add(new BFilter());

//        FilterChain fc2 = new FilterChain();
//        fc2.add(new CFilter()).add(new DFilter());
//
//        fc.add(fc2);

        fc.doChain(fc);

    }
}
