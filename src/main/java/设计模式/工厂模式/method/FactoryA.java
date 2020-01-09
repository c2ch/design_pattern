package 设计模式.工厂模式.method;

import 设计模式.工厂模式.Product;
import 设计模式.工厂模式.ProductA;

//具体工厂
public class FactoryA implements AbstractFactory {
    @Override
    public Product createProduct() {
        return new ProductA();
    }
}
