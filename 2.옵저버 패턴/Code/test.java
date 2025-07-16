package Code;

public class test {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionDisplay currentDisplay = new CurrentConditionDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherData);

        weatherData.setMeasurements(80.0f, 65.0f, 30.4f); System.out.println();
        weatherData.setMeasurements(82.0f, 70.0f, 29.2f); System.out.println();
        weatherData.setMeasurements(78.0f, 90.0f, 29.2f); System.out.println();
    }
}
