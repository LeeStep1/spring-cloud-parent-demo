package ChainTest;

public class AFilter implements Filter {
    @Override
    public void doFilter() {
        System.out.println("A的过滤");
    }
}
