package 设计模式.代理模式.jdk;

/**
 * Client
 * client测试代码
 *
 * @author
 * @create
 **/
public class Client {
    public static void main(String[] args) {

        // jdk动态代理测试
        Subject subject = new JDKDynamicProxy(new RealSubject()).getProxy();
        subject.doSomething();
    }
}