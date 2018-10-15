import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import lombok.SneakyThrows;

/**
 * Created by Victor on 04.10.2018.
 */
public class WeatherForecaster {


    //TODO Apply forecast logic for city
    @SneakyThrows
    public String forecast(City city, int days){
        if(city.getCoordinates() == null){
            return "No weather available";
        }else{
            String forecast_string = new String();
            forecast_string += city.getName() + "\n";
            forecast_string += String.format("Established: %s", city.getYearOfFound()) + "\n";
            forecast_string += String.format("Population: %d", city.getNumberOfCitizens()) + "\n";
            forecast_string += String.format("Administrative area: %s", city.getAdministrativeArea()) + "\n";
            forecast_string += String.format("Area: %f", city.getArea()) + "\n";
            GetRequest request = Unirest
                    .get(String
                            .format("https://api.apixu.com/v1/forecast.json?key=5517288787344723b23141524181410&q=%f,%f&days=%d",
                                    city.getCoordinates().getLat(),
                                    city.getCoordinates().getLng(), days));
            JSONObject forecast= new JSONObject(request.asJson().getBody().toString());
            JSONObject forecast_obj = forecast.getJSONObject( "forecast");
            JSONArray forecast_array = forecast_obj.getJSONArray("forecastday");
            for(int i  = 0; i < forecast_array.length(); i++){
                forecast_string += "\nDate: ";
                JSONObject cur_day = forecast_array.getJSONObject(i);
                JSONObject day_weather = cur_day.getJSONObject("day");
                String condition = day_weather.getJSONObject("condition").getString("text");
                String date = cur_day.getString("date");
                forecast_string += date + "\n";
                forecast_string += "Condition: " + condition +"\n";
                forecast_string += "Minimal temperature: " + day_weather.getInt("mintemp_c") + " C\n";
                forecast_string += "Maximal temperature: " + day_weather.getInt("maxtemp_c") + " C\n";
                forecast_string += "Average temperature: " + day_weather.getInt("avgtemp_c") + " C\n";
                forecast_string += "Average humidity: " + day_weather.getInt("avghumidity")+ "%\n";

                // System.out.println(date);
            }
            return forecast_string;
        }
    }

}
