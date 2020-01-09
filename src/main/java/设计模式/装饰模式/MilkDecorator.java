package 设计模式.装饰模式;

/**
 * 具体装饰类
 */
public class MilkDecorator extends Decorator {

    public MilkDecorator(Cafe cafe) {
        super(cafe);
    }

    @Override
    public void getCafe() {
        super.getCafe();
        addMilk();
    }

    private void addMilk(){
        System.out.println("加点牛奶！");
    }
}
