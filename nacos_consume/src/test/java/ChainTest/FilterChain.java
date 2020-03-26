package ChainTest;

import java.util.ArrayList;

public class FilterChain implements Filter{
    int index = 0;
    int c = 0;
    ArrayList<Filter> arrayList = new ArrayList();
    FilterChain add(Filter filter){
        arrayList.add(filter);
        return this;
    };

    void doChain(FilterChain fc){

        if(index <arrayList.size()){
            c = index;
            index++;
            arrayList.get(c).doFilter(fc);


        }
    }

    @Override
    public void doFilter(FilterChain fc) {
        for(Filter f : arrayList){
            f.doFilter(fc);
        }
    }
}
