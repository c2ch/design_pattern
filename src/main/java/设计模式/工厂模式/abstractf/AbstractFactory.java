package 设计模式.工厂模式.abstractf;

import 设计模式.工厂模式.Product;

//抽象工厂
public interface AbstractFactory {
    Product createProduct();
    Gift createGift();
}
