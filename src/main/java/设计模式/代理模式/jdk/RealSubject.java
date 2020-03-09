package 设计模式.代理模式.jdk;

/**
 * RealSubject
 * 真实主题类
 * @author
 * @create
 **/
public class RealSubject implements Subject {
    @Override
    public void doSomething() {
        System.out.println("RealSubject do something");
    }
}