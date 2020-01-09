package 设计模式.策略模式.use;


public class MobileStrategy implements Strategy {

    @Override
    public Double calRecharge(Double charge) {
        return charge;
    }
}