package ChainTest;

public class CFilter implements Filter {
    @Override
    public void doFilter(FilterChain fc) {
        System.out.println("C的过滤");
    }
}
