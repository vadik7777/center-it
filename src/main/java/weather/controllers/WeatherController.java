package weather.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import weather.impl.ServiceOpenWeatherMap;
import weather.impl.ServiceYandex;
import weather.objects.WeatherData;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class WeatherController {

    public static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    private static final List<String> cities = new ArrayList<>(Arrays.asList("Москва", "Санкт-Петербург", "Екатеринбург"));

    private static final List<String> services = new ArrayList<>(Arrays.asList("Yandex", "OpenWeatherMap"));

    //Сервис OpenWeatherMap
    @Autowired
    private ServiceOpenWeatherMap serviceOpenWeatherMap;

    //Сервис Yandex
    @Autowired
    private ServiceYandex serviceYandex;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        List<WeatherData> weatherData = null;
        ModelAndView model = new ModelAndView();
        model.addObject("listService", getServices());
        model.addObject("listCities", getCities());
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView main(@RequestParam("service") String service, @RequestParam("city") String city, ModelAndView model) {
        WeatherData weatherData = null;
        model.setViewName("about");
        if (service.equals("OpenWeatherMap"))
            weatherData = serviceOpenWeatherMap.getWeatherData(city, service);
        else if (service.equals("Yandex"))
            weatherData = serviceYandex.getWeatherData(city, service);
        model.addObject("weatherData", weatherData);
        return model;
    }

    //Города
    private static List<String> getCities() {
        return cities;
    }

    //Сервисы
    private static List<String> getServices() {
        return services;
    }
}