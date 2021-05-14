package guava.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * @Description: BloomFilterTest
 * @Author LeeYoung
 * @Date: 2021/5/14
 */
public class BloomFilterTest {

    private static int size = 1000000;

    public final static BloomFilter bloomFilter = BloomFilter.create(Funnels.integerFunnel(),500,0.01);

    @Test
    public void test1(){
        int count = 0;
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }

        for (int i = 0; i < size; i++) {
            Random random = new Random();
            int number=random.nextInt(9000000)+1000000;
            if(!bloomFilter.mightContain(number)){
                System.out.println("漏了一个...." + i);
                count++;
            }
        }

        System.out.println(count);

    }
}
