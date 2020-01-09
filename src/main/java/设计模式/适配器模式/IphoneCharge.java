package 设计模式.适配器模式;

public class IphoneCharge implements LightningInterface {
    @Override
    public void chargeWithLightning() {

        System.out.println("给Iphone充电。");
    }
}
