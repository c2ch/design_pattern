package 设计模式.单例模式;

/**
 * 饿汉式（线程安全的）
 */
public class Hungry {

    private static Hungry hungry = new Hungry();

    private Hungry(){}

    public static Hungry getInstance(){
        return hungry;
    }

    public void say(){
        System.out.println("我饿了！");
    }
}
