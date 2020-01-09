package 设计模式.装饰模式;

/**
 * 被装饰的类
 */
public class ConcreteCafe implements Cafe {

    @Override
    public void getCafe() {
        System.out.println("上一杯原味咖啡！");
    }
}
