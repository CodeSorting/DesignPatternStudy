package Code;

public class ForecastDisplay implements Observer, DisplayElement {
    private float currentPressure = 29.92f; // 초기 압력
    private float lastPressure; // 이전 압력
    private Subject weatherData;

    public ForecastDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        lastPressure = currentPressure;
        currentPressure = pressure;
        display();
    }

    @Override
    public void display() {
        String forecast;
        if (currentPressure > lastPressure) {
            forecast = "날씨가 좋아질 것 같습니다.";
        } else if (currentPressure == lastPressure) {
            forecast = "날씨가 변하지 않을 것 같습니다.";
        } else {
            forecast = "날씨가 나빠질 것 같습니다.";
        }
        System.out.println("예보: " + forecast);
    }
    
}
