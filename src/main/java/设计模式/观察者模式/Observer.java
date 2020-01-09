package 设计模式.观察者模式;

//抽象观察者
public interface Observer {
    public void update(float temp, float humidity, float pressure);
}
