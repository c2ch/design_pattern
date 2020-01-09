package 设计模式.代理模式;

/**
 * 代理类
 */
public class ProxyCafe implements Cafe {
    ConcreteCafe concreteCafe;

    public ProxyCafe() {
        this.concreteCafe = new ConcreteCafe();
    }

    @Override
    public void getCafe() {
        concreteCafe.getCafe();
        addMilk();
        addSugar();

    }

    private void addMilk(){
        System.out.println("加点牛奶！");
    }

    private void addSugar(){
        System.out.println("加点糖！");
    }
}
