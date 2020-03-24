package ChainTest;

import java.util.ArrayList;

public class FilterChain implements Filter{
    ArrayList<Filter> arrayList = new ArrayList();
    FilterChain add(Filter filter){
        arrayList.add(filter);
        return this;
    };

    void doChain(){
        for(Filter f : arrayList){
            f.doFilter();
        }
    }

    @Override
    public void doFilter() {
        for(Filter f : arrayList){
            f.doFilter();
        }
    }
}
