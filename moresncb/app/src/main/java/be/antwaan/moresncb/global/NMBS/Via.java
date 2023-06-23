package be.antwaan.moresncb.global.NMBS;

import be.antwaan.moresncb.global.Main.TimeOnly;

import java.util.ArrayList;
import java.util.Objects;

public class Via {

    private Arrival arrival;
    private Departure departure;
    private TimeOnly timeBetween;
    private String stationName;
    private Station station;
    private String vehicleName;
    private String directions;

    public Via(Arrival arrival, Departure departure, TimeOnly timeBetween, String stationName, Station station, String vehicleName, String directions) {
        this.arrival = arrival;
        this.departure = departure;
        this.timeBetween = timeBetween;
        this.stationName = stationName;
        this.station = station;
        this.vehicleName = vehicleName;
        this.directions = directions;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public TimeOnly getTimeBetween() {
        return timeBetween;
    }

    public void setTimeBetween(TimeOnly timeBetween) {
        this.timeBetween = timeBetween;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Via)) return false;
        Via via = (Via) o;
        return Objects.equals(getArrival(), via.getArrival()) && Objects.equals(getDeparture(), via.getDeparture()) && Objects.equals(getTimeBetween(), via.getTimeBetween()) && Objects.equals(getStationName(), via.getStationName()) && Objects.equals(getStation(), via.getStation()) && Objects.equals(getDirections(), via.getDirections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArrival(), getDeparture(), getTimeBetween(), getStationName(), getStation(), getVehicleName(), getDirections());
    }

    @Override
    public String toString() {
        return "Via{" +
                "arrival=" + arrival +
                ", departure=" + departure +
                ", timeBetween=" + timeBetween +
                ", stationName='" + stationName + '\'' +
                ", station=" + station +
                ", vehicleName='" + vehicleName + '\'' +
                ", directions='" + directions + '\'' +
                '}';
    }
}
