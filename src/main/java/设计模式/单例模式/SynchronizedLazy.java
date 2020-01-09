package 设计模式.单例模式;

/**
 * 线程安全的懒汉式
 */
public class SynchronizedLazy {

    private static SynchronizedLazy instance = null;

    private SynchronizedLazy(){}


    //同步函数
    public static synchronized SynchronizedLazy getInstance(){
        if(instance == null){
            instance = new SynchronizedLazy();
            return instance;
        }
        return instance;
    }

    //同步代码块(更高效)
    public static SynchronizedLazy getInstance2(){
        if(instance == null){
            synchronized (SynchronizedLazy.class){
                if(instance == null){
                    instance = new SynchronizedLazy();
                    return instance;
                }
            }
        }
        return instance;
    }


    public void say(){
        System.out.println("我懒的很安全！");
    }
}
