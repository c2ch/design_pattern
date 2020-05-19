package cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 利用AtomicStampedReference解决CAS的ABA问题
 */
public class CasABADemo02 {
    public static AtomicStampedReference<Integer> a = new AtomicStampedReference(new Integer(1),1);

    public static void main(String[] args) {
       Thread main =  new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程名："+Thread.currentThread().getName()+",初始值："+a.getReference());
                try {
                    Integer expectReference = a.getReference();//期望引用预期值
                    Integer newReference = expectReference + 1;//新的引用值
                    Integer expectStamp = a.getStamp();//期望版本
                    Integer newStamp = expectStamp+1;//新版本
                    Thread.sleep(1000);//主线程睡眠1秒，让出cpu,好让其他线程修改这个值
                    boolean isCasSucc = a.compareAndSet(expectReference, newReference, expectStamp, newStamp);
                    System.out.println("线程名："+Thread.currentThread().getName()+",修改后的值,值="+a.getReference()+",CAS操作是否成功："+isCasSucc);
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
                    a.compareAndSet(a.getReference(), (a.getReference() + 1), a.getStamp(), (a.getStamp() + 1));//a+1 ,a=2
                    System.out.println("线程名："+Thread.currentThread().getName()+",【Increment】,值="+a.getReference());
                    a.compareAndSet(a.getReference(), (a.getReference() - 1), a.getStamp(), (a.getStamp() + 1));//a+1 ,a=2
                    System.out.println("线程名："+Thread.currentThread().getName()+",【decrement】,值="+a.getReference());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"干扰线程");
        main.start();
        other.start();
    }
}
