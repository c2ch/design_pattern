package 设计模式.策略模式.use;

import 设计模式.策略模式.RechargeTypeEnum;

import java.util.HashMap;

public class StrategyFactory {

    private static StrategyFactory factory = new StrategyFactory();
    private StrategyFactory(){
    }
    private static HashMap<Integer,Strategy> strategyMap = new HashMap<Integer,Strategy>();
    static{
        strategyMap.put(RechargeTypeEnum.E_BANK.value(), new EBankStrategy());
        strategyMap.put(RechargeTypeEnum.BUSI_ACCOUNTS.value(), new BusiAcctStrategy());
        strategyMap.put(RechargeTypeEnum.MOBILE.value(), new MobileStrategy());
        strategyMap.put(RechargeTypeEnum.CARD_RECHARGE.value(), new CardStrategy());
    }
    public Strategy creator(Integer type){
        return strategyMap.get(type);
    }
    public static StrategyFactory getInstance(){
        return factory;
    }
}
