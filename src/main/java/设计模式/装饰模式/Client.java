package 设计模式.装饰模式;

public class Client {

    public static void main(String[] args) {
        Cafe cafe = new ConcreteCafe();

        MilkDecorator milkDecorator = new MilkDecorator(cafe);
        milkDecorator.getCafe();

        SugarDecorator sugarDecorator = new SugarDecorator(milkDecorator);
        sugarDecorator.getCafe();


    }
}
