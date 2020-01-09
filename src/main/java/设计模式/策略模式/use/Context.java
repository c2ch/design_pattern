package 设计模式.策略模式.use;


public class Context {

    private Strategy strategy;

    public Double calRecharge(Double charge, Integer type) {
        strategy = StrategyFactory.getInstance().creator(type);
        return strategy.calRecharge(charge);
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

}