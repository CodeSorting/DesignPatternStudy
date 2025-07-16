package Code;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {
    /*
    옵저버 패턴 적용 전
    public void measurementsChanged() {
        float temp = getTemperature();
        float humidity = getHumidity();
        float pressure = getPressure();

        //각 디스플레이 갱신
        currentConditionsDisplay.update(temp, humidity, pressure);
        statisticsDisplay.update(temp, humidity, pressure);
        forecastDisplay.update(temp, humidity, pressure);
    } 
    */
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        observers = new ArrayList<Observer>();
    }
    //등록
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    //해지
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    //알림
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
    //측정값 갱신
    public void measurementsChanged() {
        notifyObservers();
    }
    //업데이트 하고 기준 변경 -> 알림으로 넘김.
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}