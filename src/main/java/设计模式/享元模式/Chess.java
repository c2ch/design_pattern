package 设计模式.享元模式;



/**
 * 黑白五子棋或者围棋只有两种颜色-黑白，如果我们把棋子作为一个抽象类Chess，
 * 黑棋BlackChess和白棋WhiteChess分别作为继承抽象类的具体类，那么每下一步都需要new一个新的棋子对象，
 * 如此下来会产生大量的黑白棋对象。仔细观察黑白棋，不难发现黑白棋对象其实都一样，唯一不同的是其位置的变化。
 * 享元模式：不用创建大量的黑白棋对象，但是也能准确的实现其位置的变化
 *
 * 抽象棋子类
 */
public abstract class Chess {
    private String type;  // 棋子种类，分黑两种
    public Chess(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }
    // 对棋子的操作
    public abstract void operation(Local local);
}
