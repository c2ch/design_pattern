package 设计模式.适配器模式;

public class Client {
    public static void main(String[] args) {
        //类适配器，调用安卓接口给苹果充电
        ClassAdapter adaptder = new ClassAdapter();
        adaptder.chargeWithUsb();

        //对象适配器
        ObjectAdapter objectAdapter = new ObjectAdapter(new IphoneCharge());
        objectAdapter.chargeWithUsb();
    }
}
