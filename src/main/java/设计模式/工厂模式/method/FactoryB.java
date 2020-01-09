package 设计模式.工厂模式.method;

import 设计模式.工厂模式.Product;
import 设计模式.工厂模式.ProductB;

//具体工厂
public class FactoryB implements AbstractFactory {
    @Override
    public Product createProduct() {
        return new ProductB();
    }
}
