package 设计模式.工厂模式;

//具体产品
public class ProductA implements Product {
    @Override
    public void product() {
        System.out.println("产品A生产出来了。。。。");
    }
}
