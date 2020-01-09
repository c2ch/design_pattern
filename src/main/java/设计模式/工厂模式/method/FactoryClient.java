package 设计模式.工厂模式.method;

public class FactoryClient {

    public static void main(String[] args) {

        AbstractFactory factory = new FactoryA();
        factory.createProduct().product();
        factory = new FactoryB();
        factory.createProduct().product();
    }
}
