package 设计模式.单例模式;

/**
 * 懒汉式(线程不安全)
 */
public class Lazy {

    private static Lazy lazy = null;

    private Lazy(){}

    public static Lazy getInstance(){
        if(lazy == null){
            lazy = new Lazy();
            return lazy;
        }
        return lazy;
    }

    public void say(){
        System.out.println("我很懒！");
    }

}
