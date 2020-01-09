package 设计模式.构建者模式;


/**
 * 假设现在要设计一个类,但是这个类里面好几个的成员变量,不光要创建,还要赋值，还有构造方法（包括所有成员变量，和极个别的成员变量）。
 * 那这样像传统的写法就是写好几个构造方法，或者新建一个对象，然后根据需要不断的去set成员。如果有新的需求，构造方法可能又要发生改变，
 * 很不利于维护，所以使用建造者模式
 */
public class MyHome {
    String mDoor;
    String mGate;
    String mCourtyard;
    String mBackyard;
    String mSwimmingPond;
    String mBasement;
    String mKitchen;
    String mToilet;
    String mBathroom;
    String mStudy;

    public String getmDoor() {
        return mDoor;
    }

    public void setmDoor(String mDoor) {
        this.mDoor = mDoor;
    }

    public String getmGate() {
        return mGate;
    }

    public void setmGate(String mGate) {
        this.mGate = mGate;
    }

    public String getmCourtyard() {
        return mCourtyard;
    }

    public void setmCourtyard(String mCourtyard) {
        this.mCourtyard = mCourtyard;
    }

    public String getmBackyard() {
        return mBackyard;
    }

    public void setmBackyard(String mBackyard) {
        this.mBackyard = mBackyard;
    }

    public String getmSwimmingPond() {
        return mSwimmingPond;
    }

    public void setmSwimmingPond(String mSwimmingPond) {
        this.mSwimmingPond = mSwimmingPond;
    }

    public String getmBasement() {
        return mBasement;
    }

    public void setmBasement(String mBasement) {
        this.mBasement = mBasement;
    }

    public String getmKitchen() {
        return mKitchen;
    }

    public void setmKitchen(String mKitchen) {
        this.mKitchen = mKitchen;
    }

    public String getmToilet() {
        return mToilet;
    }

    public void setmToilet(String mToilet) {
        this.mToilet = mToilet;
    }

    public String getmBathroom() {
        return mBathroom;
    }

    public void setmBathroom(String mBathroom) {
        this.mBathroom = mBathroom;
    }

    public String getmStudy() {
        return mStudy;
    }

    public void setmStudy(String mStudy) {
        this.mStudy = mStudy;
    }
}
