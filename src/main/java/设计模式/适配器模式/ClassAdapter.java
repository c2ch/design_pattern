package 设计模式.适配器模式;

//这个是类适配器
public class ClassAdapter extends IphoneCharge implements MicroUsbInterface {

    @Override
    public void chargeWithUsb() {
        //对外的接口是安卓充电口，却是给苹果充电
        super.chargeWithLightning();
    }
}
