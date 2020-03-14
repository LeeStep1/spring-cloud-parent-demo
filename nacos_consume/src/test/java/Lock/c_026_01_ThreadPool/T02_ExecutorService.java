/**
 * ��ʶExecutorService,�Ķ�API�ĵ�
 * ��ʶsubmit��������չ��execute����������һ������ֵ
 */
package Lock.c_026_01_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T02_ExecutorService  {
    public static void main(String[] args) {
        ExecutorService e = Executors.newCachedThreadPool();
    }
}
