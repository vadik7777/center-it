package weather.interfaces;

import weather.objects.WeatherData;
import java.util.List;

public interface Weather {

    //Интерфейс получения данных о погоде с сервисов
    WeatherData getServiceData(String city, String service);

}
