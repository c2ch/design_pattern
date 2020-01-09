package 设计模式.享元模式;

/**
 * 位置类，提供一个辅助功能，只是为了业务用的。。在享元模式中没有意义
 */
public class Local {
    private int x;
    private int y;
    public Local(int x, int y){
        this.x = x;
        this.y = y;
    }
    // 省略get和set方法

    @Override
    public String toString() {
        return "横：" + x + ", 纵：" + y ;
    }
}
