package ChainTest;

public class AFilter implements Filter {
    @Override
    public void doFilter(FilterChain fc) {
        System.out.println("A  request 1 的过滤");
        fc.doChain(fc);
        System.out.println("A response 1 的过滤");
    }
}
