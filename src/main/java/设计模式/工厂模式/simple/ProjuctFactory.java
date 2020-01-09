package 设计模式.工厂模式.simple;

import 设计模式.工厂模式.Product;
import 设计模式.工厂模式.ProductA;
import 设计模式.工厂模式.ProductB;

//具体产品工厂
public class ProjuctFactory {

    public Product createProduct(String type){
        if(type.equals("A")){
            return new ProductA();
        }
        if(type.equals("B")){
            return new ProductB();
        }
        return null;
    }

}
