package 设计模式.代理模式;

/**
 * 目标类
 */
public class ConcreteCafe implements Cafe {

    @Override
    public void getCafe() {
        System.out.println("上一杯原味咖啡！");
    }
}
