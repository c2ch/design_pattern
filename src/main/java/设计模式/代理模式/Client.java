package 设计模式.代理模式;

public class Client {

    public static void main(String[] args) {
        Cafe cafe = new ProxyCafe();
        cafe.getCafe();
    }
}
