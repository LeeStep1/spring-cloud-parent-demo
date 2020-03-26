package ChainTest;

public class BFilter implements Filter {
    @Override
    public void doFilter(FilterChain fc) {
        System.out.println("B request 2 的过滤");
        fc.doChain(fc);
        System.out.println("B response 2 的过滤");
    }
}
