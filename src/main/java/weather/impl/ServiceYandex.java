package weather.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import weather.objects.Coords;
import weather.objects.WeatherData;

import java.util.*;

@Service
public class ServiceYandex extends AbstractService {

    //Реализация подключения к сервисам
    public WeatherData getServiceData(String city, String service) {
        Coords coords = cityMap.get(city);

        String getUrl = String.format("https://api.weather.yandex.ru/v1/forecast?lat=%s&lon=%s", coords.getLatitude(), coords.getLongitude());
        Map param = new HashMap<String, String>();
        param.put("X-Yandex-API-Key", "a2fb9572-1767-477b-966b-8a4cc9a19ccc");
        JSONArray jsonArray = getJSONFromUrl(getUrl, param);
        if(jsonArray == null){
            return getNullWeatherData(service);
        }

        final JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        Map fact = jsonObject.getJSONObject("fact").toMap();

        return getNotNullWeatherData(fact, service, city);
    }

    //Реализация конвертера
    public WeatherData getNotNullWeatherData(Map fact, String service, String city){
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setTemp(fact.get("temp").toString());
        weatherData.setHumidity(fact.get("humidity").toString());
        weatherData.setPressure(fact.get("pressure_mm").toString());
        Date date = new Date();
        weatherData.setLastDate(date);
        weatherData.setDate(formateDate(date));
        weatherData.setService(service);
        return weatherData;
    }

}
