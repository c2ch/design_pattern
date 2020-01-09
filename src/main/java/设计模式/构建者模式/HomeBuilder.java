package 设计模式.构建者模式;

public class HomeBuilder implements Builder {

    private MyHome mMyHome = new MyHome();


    public MyHome build() {
        return mMyHome;
    }

    public HomeBuilder planningDoor(String door) {
        mMyHome.setmDoor(door);
        return this;
    }

    public HomeBuilder planningKitchen(String kitchen) {
        mMyHome.setmKitchen(kitchen);
        return this;
    }

    public HomeBuilder planningToilet(String toilet) {
        mMyHome.setmToilet(toilet);
        return this;
    }

    public HomeBuilder planningBathroom(String bathroom) {
        mMyHome.setmBathroom(bathroom);
        return this;
    }

    public HomeBuilder planningStudy(String study) {
        mMyHome.setmStudy(study);
        return this;
    }
}
