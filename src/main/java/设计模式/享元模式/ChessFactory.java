package 设计模式.享元模式;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 棋子工厂类
 */
public class ChessFactory {

    private static ChessFactory chessFactory;
    public ConcurrentMap<String, Chess> flyWeightMap; // 共享池
    private ChessFactory(){
        flyWeightMap = new ConcurrentHashMap<String, Chess>();
    }

    /**
     * 双重锁单例模式获取棋子工厂类
     */
    public static ChessFactory getChessFactory() {
        synchronized (ChessFactory.class) {
            if (chessFactory == null){
                synchronized (ChessFactory.class) {
                    chessFactory = new ChessFactory();
                }
            }
        }
        return chessFactory;
    }


    /**
     * 棋子工厂根据color构造不同的对象，并放入共享池
     */
    public Chess getChess(String color){
        if (!flyWeightMap.containsKey(color)) {
            System.out.println("没有"+color+"棋子，创建！");
            flyWeightMap.put(color, new BlackWhiteChess(color));
            return flyWeightMap.get(color);
        }else{
            System.out.println("已经有"+color+"棋子，直接取！");
            return flyWeightMap.get(color);
        }
    }
}

