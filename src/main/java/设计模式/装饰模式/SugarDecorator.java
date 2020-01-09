package 设计模式.装饰模式;

/**
 * 具体装饰类
 */
public class SugarDecorator extends Decorator {

    public SugarDecorator(Cafe cafe) {
        super(cafe);
    }

    @Override
    public void getCafe() {
        super.getCafe();
        addSugar();
    }

    private void addSugar(){
        System.out.println("加点糖！");
    }
}
