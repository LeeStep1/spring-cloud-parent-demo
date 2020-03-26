package ChainTest;

public class DFilter implements Filter {
    @Override
    public void doFilter(FilterChain fc) {
        System.out.println("D的过滤");
    }
}
