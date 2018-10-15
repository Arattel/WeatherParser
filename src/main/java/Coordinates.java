import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.select.Elements;

import javax.xml.bind.Element;

@Getter
@ToString
//TODO Create class that represents coordinates
public class Coordinates {
    private double lat;
    private double lng;


    public Coordinates(Double[] coord) {
        lat = (double) coord[0];
        lng = (double) coord[1];
    }
}
