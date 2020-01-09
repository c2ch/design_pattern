package 设计模式.模板模式;

public class Client {

    public static void main(String[] args) {
        DodishTemplate eggsWithTomato = new EggsWithTomato();
        eggsWithTomato.dodish();//调用模板方法

        System.out.println("-----------------------------");

        DodishTemplate bouilli = new Bouilli();
        bouilli.dodish();//调用模板方法
    }
}
