public class Location {
    
    //information of a location
    double latitudes;
    double longitudes;

    //constructor
    public Location(double latitudes, double longitudes) {
        this.latitudes = latitudes;
        this.longitudes = longitudes;
    }

    /* to string method */
    @Override
    public String toString() {
        return "Location: " + longitudes + " , " + latitudes;
    }
}

