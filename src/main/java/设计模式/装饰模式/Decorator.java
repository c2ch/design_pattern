package 设计模式.装饰模式;

/**
 * 装饰类
 */
public class Decorator implements Cafe {

    public Cafe cafe;

    public Decorator(Cafe cafe) {
        this.cafe = cafe;
    }

    @Override
    public void getCafe() {
        cafe.getCafe();
    }
}
