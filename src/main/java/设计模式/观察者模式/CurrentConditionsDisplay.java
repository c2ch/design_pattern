package 设计模式.观察者模式;


//具体观察者2 显示当前的温度、湿度
public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;//温度
    private float humidity;//湿度
    private Subject weatherData;

    public CurrentConditionsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    //推数据，这里只使用到了前两个参数
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("Current conditions: " + temperature
                + "F degrees and " + humidity + "% humidity");
    }

}
