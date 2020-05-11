package Lock.c_026_01_ThreadPool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class T10_ScheduledPool {
	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		service.scheduleAtFixedRate(()->{
			System.out.println("开启线程 ： " + Thread.currentThread().getName()   + "开始时间" + System.currentTimeMillis());
			try {
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(3000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("线程结束 ： " + Thread.currentThread().getName() + "结束时间" + System.currentTimeMillis());
		}, 0, 5000, TimeUnit.MILLISECONDS);
		
	}
}
