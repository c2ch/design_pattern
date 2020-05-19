package cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示CAS的ABA问题
 */
public class CasABADemo01 {
    public static AtomicInteger a = new AtomicInteger(1);

    public static void main(String[] args) {
       Thread main =  new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程名："+Thread.currentThread().getName()+",初始值："+a.get());
                try {
                    int expectNum = a.get();
                    int newNum = expectNum+1;
                    Thread.sleep(1000);//主线程睡眠1秒，让出cpu,好让其他线程修改这个值
                    boolean isCasSucc = a.compareAndSet(expectNum, newNum);
                    System.out.println("线程名："+Thread.currentThread().getName()+",修改后的值,"+a.get()+",CAS操作是否成功："+isCasSucc);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"主线程");

        Thread other =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20);//保证主线程先运行
                    a.getAndIncrement();//a+1 ,a=2
                    System.out.println("线程名："+Thread.currentThread().getName()+",【Increment】,值="+a.get());
                    a.decrementAndGet();//a-1, a=1
                    System.out.println("线程名："+Thread.currentThread().getName()+",【decrement】,值="+a.get());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"干扰线程");
        main.start();
        other.start();
    }
}
