package 设计模式.享元模式;

public class BlackWhiteChess extends Chess {

    public BlackWhiteChess(String type) {
        super(type);
    }

    @Override
    public void operation(Local local) {  // Local表示位置类
        System.out.println(getType() + " " + local);
    }

}
