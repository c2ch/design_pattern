package 设计模式.工厂模式.simple;

public class ProductClient {
    public static void main(String[] args) {
        ProjuctFactory projuctFactory = new ProjuctFactory();
        projuctFactory.createProduct("A").product();
        projuctFactory.createProduct("B").product();

    }
}
