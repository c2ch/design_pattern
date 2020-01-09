package 设计模式.享元模式;

public class Client {
    public static void main(String[] args) {
        String black = "black";
        String white = "white";
        ChessFactory chessFactory = ChessFactory.getChessFactory(); // 共享池
        Chess chess1, chess2, chess3, chess4;  // 棋子

        chess1 = chessFactory.getChess(black); // 黑棋
        chess1.operation(new Local(5, 5));
        chess2 = chessFactory.getChess(black); // 黑棋
        chess2.operation(new Local(5, 6));
        System.out.println("池中大小："+chessFactory.flyWeightMap.size());

        chess3 = chessFactory.getChess(white); // 白棋
        chess3.operation(new Local(6, 6));
        chess4 = chessFactory.getChess(white); // 白棋
        chess4.operation(new Local(7, 7));
        System.out.println("池中大小："+ chessFactory.flyWeightMap.size());
    }
}

