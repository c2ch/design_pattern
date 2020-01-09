package 设计模式.构建者模式;

public class HomeClient {

    public static void main(String[] args) {

        //创建我们想要的对象。
        Builder homeBuilder = new HomeBuilder();
        homeBuilder.planningBathroom("浴室").planningDoor("门").planningKitchen("厨房").build();
    }

}
