package BloomFilterTest;

import com.google.common.hash.Funnels;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.google.common.hash.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Letcheng on 2016/2/23.
 */
public class Test {

    private int total = 100000; //测试元素的总数

    private List<String> existingElements = null;
    private List<String> nonExistingElements = null;

    private void printStat(long start, long end) {
        double diff = (end - start) / 1000.0;
        System.out.println(diff + "s, " + (total / diff) + " 元素/s");
    }

    @Before
    public void prepare(){

        final Random r = new Random();
        existingElements = new ArrayList(total);
        for (int i = 0; i < total; i++) {
            existingElements.add(Double.toString(r.nextDouble()));
        }

        nonExistingElements = new ArrayList(total);
        for (int i = 0; i < total; i++) {
            nonExistingElements.add(Double.toString(r.nextDouble()));
        }

    }

    @org.junit.Test
    public void test(){

        double fpp = 0.001d;

        BloomFilter<String> ruyu_bf = new BloomFilter(fpp, total);
        com.google.common.hash.BloomFilter<String> google_bf = com.google.common.hash.BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), total, fpp);

        // 添加元素
        System.out.print("Ruyu Bloom Filter添加元素: ");
        long start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            ruyu_bf.add(existingElements.get(i));
        }
        long end = System.currentTimeMillis();
        printStat(start, end);

        System.out.print("Google Bloom Filter添加元素: ");
        start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            google_bf.put(existingElements.get(i));
        }
        end = System.currentTimeMillis();
        printStat(start, end);

        //测试已经存在的元素
        System.out.print("Ruyu Bloom Filter测试已经存在的元素: ");
        start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            ruyu_bf.contains(existingElements.get(i));
        }
        end = System.currentTimeMillis();
        printStat(start, end);

        System.out.print("Google Bloom Filter测试已经存在的元素: ");
        start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            ruyu_bf.contains(existingElements.get(i));
        }
        end = System.currentTimeMillis();
        printStat(start, end);

        //测试不存在的元素
        System.out.print("Ruyu Bloom Filter 测试不存在的元素: ");
        start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            ruyu_bf.contains(nonExistingElements.get(i));
        }
        end = System.currentTimeMillis();
        printStat(start, end);

        System.out.print("Google Bloom Filter 测试不存在的元素: ");
        start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            ruyu_bf.contains(nonExistingElements.get(i));
        }
        end = System.currentTimeMillis();
        printStat(start, end);

    }

    @org.junit.Test
    public void test2(){

        int count = 0;

        CachedBloomFilter<String> cbf = new CachedBloomFilter<String>(0.01,total);
        //BloomFilter<String> bf = new BloomFilter<String>(0.01,total);

        existingElements.forEach(x->{
            cbf.add(x);
            //bf.add(x);
        });

        for (int i = 0; i < total; i++) {
           if(!cbf.contains(existingElements.get(i))){
                count++;
            };
            /*if(!bf.contains(existingElements.get(i))){
                count++;
            };*/
        }
        System.out.println(count);
    }

}