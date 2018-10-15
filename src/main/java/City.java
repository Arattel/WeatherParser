/**
 * Created by Victor on 03.10.2018.
 */
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;


@Getter
@Setter
@ToString
public class City {
    private String name;
    private String url;
    private String administrativeArea;
    private int numberOfCitizens;
    private String yearOfFound;
    private Coordinates coordinates; // Set this
    private double area;

    private static final int INFO_SIZE = 6;




    public static City parse(Element city) throws IOException {
        Elements info = city.select("td");
        if (info.size() == INFO_SIZE) {
            Element anchor = info.get(1).select("a").get(0);
            City myCity = new City();
            myCity.setName(anchor.attr("title"));
            String url = String.format("https://uk.wikipedia.org%s", anchor.attr("href"));
            myCity.setUrl(url);
            Element admArea = info.get(2).select("a").get(0);
            myCity.setAdministrativeArea(admArea.attr("title"));
            Element td = info.get(3).select("td").get(0);
            Elements td_children = td.children();
            if(td_children.size() == 0){
                myCity.setNumberOfCitizens((int) Integer.parseInt(td.text().replace(" ", "")));
            }
            else{
                myCity.setNumberOfCitizens((int) Integer.parseInt(td_children.get(1).text().replace(" ", "")));
            }
            //TODO  set all other attributes
            String yearOfFound = info.get(4).select("a").get(0).attr("title");
            myCity.setYearOfFound(yearOfFound);
            Double area = Double.parseDouble(info.get(5).select("td").get(0).text().toString());
            myCity.setArea((double) area);
            Document doc = Jsoup.connect(url).get();
            Elements coord_arr= doc.select("span[class=geo]");
            if(coord_arr.isEmpty()){
                myCity.setCoordinates(null);
            }
            else{
                String[] coord = coord_arr.get(0).text().split("; ");
                Double[] coord_numeric = new Double[2];
                for (int i = 0; i < 2; i++){
                    coord_numeric[i] = Double.parseDouble(coord[i]);
                }
                Coordinates coord_class = new Coordinates(coord_numeric);
                myCity.setCoordinates(coord_class);
            }
            return myCity;
        }
        return null;
    }

}