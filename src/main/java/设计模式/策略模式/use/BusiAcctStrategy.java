package 设计模式.策略模式.use;


public class BusiAcctStrategy implements Strategy {
    @Override
    public Double calRecharge(Double charge) {
        return charge*0.90;
    }
}
