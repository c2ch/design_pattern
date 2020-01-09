package 设计模式.观察者模式;


/**
 * 观察者模式的应用场景：
 * 1、 对一个对象状态的更新，需要其他对象同步更新，而且其他对象的数量动态可变。
 * 2、 对象仅需要将自己的更新通知给其他对象而不需要知道其他对象的细节。
 * 观察者模式的优点：
 * 1、 Subject和Observer之间是松偶合的，分别可以各自独立改变。
 * 2、 Subject在发送广播通知的时候，无须指定具体的Observer，Observer可以自己决定是否要订阅Subject的通知。
 * 3、 遵守大部分GRASP原则和常用设计原则，高内聚、低偶合。
 *
 */

public class ObserveClient {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }



}

