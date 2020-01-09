package 设计模式.策略模式.use;


public class CardStrategy implements Strategy{

    @Override
    public Double calRecharge(Double charge) {
        return charge+charge*0.01;
    }
}
