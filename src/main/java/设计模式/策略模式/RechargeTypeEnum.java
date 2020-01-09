package 设计模式.策略模式;

public enum RechargeTypeEnum {
    E_BANK(1),
    BUSI_ACCOUNTS(2),
    MOBILE(3),
    CARD_RECHARGE(4);

    Integer type;

    RechargeTypeEnum(Integer type){
        this.type = type;
    }


    public Integer value(){
        return type;
    }
}
