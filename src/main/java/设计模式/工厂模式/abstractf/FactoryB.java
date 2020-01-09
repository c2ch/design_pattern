package 设计模式.工厂模式.abstractf;

import 设计模式.工厂模式.Product;
import 设计模式.工厂模式.ProductB;

public class FactoryB implements AbstractFactory {
    @Override
    public Product createProduct() {
        return new ProductB();
    }

    @Override
    public Gift createGift() {
        return new GiftB();
    }
}
