package 设计模式.构建者模式;

public interface Builder {
    MyHome build();

    HomeBuilder planningDoor(String door);

    HomeBuilder planningKitchen(String kitchen);

    HomeBuilder planningToilet(String toilet);

    HomeBuilder planningBathroom(String bathroom);

    HomeBuilder planningStudy(String study);

}
