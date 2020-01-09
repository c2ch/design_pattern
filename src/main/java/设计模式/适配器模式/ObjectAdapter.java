package 设计模式.适配器模式;

public class ObjectAdapter implements MicroUsbInterface {

    private LightningInterface lightningInterface;

    public ObjectAdapter(LightningInterface lightningInterface) {
        this.lightningInterface = lightningInterface;
    }

    @Override
    public void chargeWithUsb() {
        lightningInterface.chargeWithLightning();
    }
}
