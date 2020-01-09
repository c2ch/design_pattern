package 设计模式.工厂模式.abstractf;


public class FactoryClient {

    public static void main(String[] args) {

        AbstractFactory factory = new FactoryA();
        factory.createProduct().product();
        factory.createGift().say();

        factory = new FactoryB();
        factory.createProduct().product();
        factory.createGift().say();
    }
}
