package cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 做一个访问量统计，
 * 模拟100个用户，每个用户10次请求，统计最后访问量，应该为1000次访问
 */
public class Demo01 {

    //总访问量
    static int count = 0;


    public static void request() throws InterruptedException {
        //模拟请求延时
        TimeUnit.MILLISECONDS.sleep(5);
        /**
         * Q: 问题出在哪？
         * A: count ++ 操作实际上是由3步来完成！
         *      1. 获取count的值，记做A: A=count
         *      2. 将A值+1，得到B: B=A+1
         *      3. 将B值赋值给count
         *
         *      如果有A,B两个线程同时执行count++，他们同时执行到上面步骤的第二步，得到的count
         *      是一样的，3步操作之后，count只加了1，导致count结果不正确！
         *
         *  Q:怎么解决结果不正确问题？
         *  A: 对count++操作的时候，我们让多个线程排队处理，多个线程同时到达request()方法的时候，
         *  只能允许一个线程可以操作，其他的线程在外面等着，等里面的处理完毕出来之后，外面等着的线程再
         *  进去一个，这样操作的count++就是排队进行的，结果一定是正确的。
         *
         *  Q:怎么实现排队效果？
         *  A: java中synchronized关键字和ReentranLock都可以实现对资源加锁，保证并发正确性，
         *  多线程的环境下可以保证被锁住的资源被“串行”访问。
         */
        count++;
    }

    public static void main(String[] args) throws InterruptedException {

        //开始时间
        long startTime = System.currentTimeMillis();

        int threadSize = 100;

        CountDownLatch countDownLatch = new CountDownLatch(threadSize);


        for (int i = 0; i < threadSize; i++) {
           Thread thread =  new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //模拟用户行为，每个用户访问10次网站
                        for (int j = 0; j < 10; j++) {
                            request();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                }
            });
            thread .start();
        }

        //等待100个请求全部结束
        countDownLatch.await();
        //结束时间
        long endTime = System.currentTimeMillis();

        System.out.println(Thread.currentThread().getName()+",耗时： "+(endTime-startTime)+",count = "+count);

    }
}
