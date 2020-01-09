package 设计模式.工厂模式.abstractf;

import 设计模式.工厂模式.Product;
import 设计模式.工厂模式.ProductA;

public class FactoryA implements AbstractFactory {
    @Override
    public Product createProduct() {
        return new ProductA();
    }

    @Override
    public Gift createGift() {
        return new GiftA();
    }
}
