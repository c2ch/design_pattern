package 设计模式.模板模式;

/**
 * 抽象做菜类
 */
public abstract class DodishTemplate {

    /**
     * 具体的整个过程（模板方法）
     */
    protected void dodish(){
        this.preparation();
        this.doing();
        this.carriedDishes();
    }
    /**
     * 备料
     */
    abstract void preparation();
    /**
     * 做菜
     */
    abstract void doing();
    /**
     * 上菜
     */
    abstract void carriedDishes();
}
