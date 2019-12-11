package weather.objects;

public class Coords {

    public Coords(String latitude, String longitude) {
        this.latitude = latitude;
        Longitude = longitude;
    }

    private String latitude;
    private String Longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return Longitude;
    }
}
