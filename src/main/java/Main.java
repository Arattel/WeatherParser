import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Victor on 03.10.2018.
 */
public class Main {


    @SneakyThrows
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter a city name(In ukrainian):");
        String city_name = scn.nextLine();
        System.out.println("Enter a number of day(not bigger than 7):");
        int num_days = scn.nextInt();
        String url = "https://uk.wikipedia.org/wiki/%D0%9C%D1%96%D1%81%D1%82%D0%B0_%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B8_(%D0%B7%D0%B0_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D1%96%D1%82%D0%BE%D0%BC)";
        Document doc = Jsoup.connect(url).get();
        Elements cities = doc.select("table tr");
        City[] parsedCities = new City[cities.size()]; // You can use List`s or other java Collections
        int counter = 0;
        for (Element city : cities) {
            City myCity = City.parse(city);
            if (myCity != null) {
                parsedCities[counter] = myCity;
                counter++;
                if(myCity.getName().equals(city_name)){
                    WeatherForecaster forecast = new WeatherForecaster();
                    System.out.println(forecast.forecast(myCity, num_days));
                    break;
                }
            }
        }

        }
    }

