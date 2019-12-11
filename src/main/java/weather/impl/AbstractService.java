package weather.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import weather.interfaces.Weather;
import weather.objects.Coords;
import weather.objects.WeatherData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

abstract public class AbstractService implements Weather {

    //Время обновления погодного сервиса
    static final long REFRESH = 10000l;

    //Память
    static List<WeatherData> weatherDataList = new ArrayList<>();

    //Координаты городов
    Map<String, Coords> cityMap = createMap();
    private static Map<String, Coords> createMap() {
        Map cityMap = new HashMap<String, Coords>();
        cityMap.put("Москва", new Coords("55.755826", "37.6173"));
        cityMap.put("Санкт-Петербург", new Coords("59.9342802", "30.3350986"));
        cityMap.put("Екатеринбург", new Coords("56.83892609999999", "60.60570250000001"));
        return cityMap;
    }

    //Реализация в сервисах
    abstract public WeatherData getServiceData(String city, String service);

    //Дата конвертер
    abstract public WeatherData getNotNullWeatherData(Map main, String service, String city);

    //Получение данных с сервисов
    public WeatherData getWeatherData(String city, String service){
        WeatherData weatherData = null;
        if (weatherDataList.size() > 0)
            weatherData = weatherDataList.stream().filter(data -> data.getCity().equals(city) && data.getService().equals(service)).findFirst().orElse(null);
        if(weatherData == null || new Date().getTime() - REFRESH > weatherData.getLastDate().getTime()){
            //получение реальных сведений о погоде
            WeatherData old = null;
            synchronized (this) {
                if(weatherData != null)
                    old = weatherData;
                weatherData = getServiceData(city, service);
                if(!weatherData.getCity().equals("Сервис не доступен"))
                    if(old != null)
                        weatherDataList.set(weatherDataList.indexOf(old), weatherData);
                    else
                        weatherDataList.add(weatherData);
            }
        }
        return weatherData;
    }

    //Получение данных с сервиса
    public JSONArray getJSONFromUrl(String url, Map<String, String> param) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;
        JSONArray jsonArray = null;
        String line = null;
        String jsonString = null;
        try {
            java.net.URL u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if(param != null){
                for(Map.Entry<String, String> entry : param.entrySet()) {
                    httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            jsonString = stringBuilder.toString();
        } catch (MalformedURLException e) {
            return null;
        } catch (ProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            httpURLConnection.disconnect();
        }
        try {
            jsonString = "[" + jsonString + "]";
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            return null;
        }
        return jsonArray;
    }

    public static WeatherData getNullWeatherData(String service){
        WeatherData weatherData = new WeatherData();
        weatherData.setCity("Сервис не доступен");
        weatherData.setTemp("Сервис не доступен");
        weatherData.setHumidity("Сервис не доступен");
        weatherData.setPressure("Сервис не доступен");
        Date date = new Date();
        weatherData.setLastDate(date);
        weatherData.setDate(formateDate(date));
        weatherData.setService(service);
        return weatherData;
    }

    public static String formateDate(Date date){
        SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
        return dt.format(date);
    }
}
