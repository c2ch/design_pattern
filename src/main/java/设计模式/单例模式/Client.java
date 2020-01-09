package 设计模式.单例模式;

public class Client {

    public static void main(String[] args) {

        Lazy instance = Lazy.getInstance();

        instance.say();

        Hungry instance1 = Hungry.getInstance();

        instance1.say();

//        SynchronizedLazy instance2 = SynchronizedLazy.getInstance();
//
//        instance2.say();

        SynchronizedLazy instance21 = SynchronizedLazy.getInstance2();

        instance21.say();
    }
}
