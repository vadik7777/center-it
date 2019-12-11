package weather.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import weather.objects.Coords;
import weather.objects.WeatherData;

import java.util.Date;
import java.util.Map;

@Service
public class ServiceOpenWeatherMap extends AbstractService {

    //Реализация подключения к сервисам
    public WeatherData getServiceData(String city, String service) {
        Coords coords = cityMap.get(city);

        String getUrl = String.format("http://api.openweathermap.org/data/2.5/weather?units=metric&APPID=5108f37ee604fcc659ad24ac350153c0&lat=%s&lon=%s", coords.getLatitude(), coords.getLongitude());
        JSONArray jsonArray = getJSONFromUrl(getUrl, null);
        if(jsonArray == null){
            return getNullWeatherData(service);
        }

        final JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        Map main = jsonObject.getJSONObject("main").toMap();

        return getNotNullWeatherData(main, service, city);
    }

    //Реализация конвертера
    public WeatherData getNotNullWeatherData(Map main, String service, String city){
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setTemp(main.get("temp").toString());
        weatherData.setHumidity(main.get("humidity").toString());
        weatherData.setPressure(main.get("pressure").toString());
        Date date = new Date();
        weatherData.setLastDate(date);
        weatherData.setDate(formateDate(date));
        weatherData.setService(service);
        return weatherData;
    }

}
