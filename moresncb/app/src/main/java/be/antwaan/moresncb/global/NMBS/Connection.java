package be.antwaan.moresncb.global.NMBS;

import be.antwaan.moresncb.global.Main.TimeOnly;
import java.sql.Time;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Connection {
    private int id;
    private Departure departure;
    private Arrival arrival;
    private Duration duration;
    private ArrayList<Alert> alerts;
    private ArrayList<Via> vias;

    public Connection(int id, Departure departure, Arrival arrival, Duration duration, ArrayList<Alert> alerts) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        this.alerts = alerts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }

    public ArrayList<Via> getVias() {
        return vias;
    }

    public void setVias(ArrayList<Via> vias) {
        this.vias = vias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return getDeparture().getStationName().equals(that.getDeparture().getStationName()) &&
                getDeparture().getDateTime().equals(that.getDeparture().getDateTime()) &&
                getArrival().getStationName().equals(that.getArrival().getStationName()) &&
                getArrival().getDateTime().equals(that.getArrival().getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDeparture(), getArrival(), getDuration(), getAlerts(), getVias());
    }

    @Override
    public String toString() {
        return (departure.isCanceled() ? "CANCELED " : "") +
                "FROM: " + departure.getStationName() +
                ", platform: " + departure.getPlatformNumber() +
                ", " + departure.getDateTime().getHours() +
                ":" + departure.getDateTime().getMinutes() +
                (departure.getDelay()!=0? "+" + departure.getDelay():"") +
                "     TO: " + arrival.getStationName() +
                ", platform: " + arrival.getPlatformNumber() +
                ", " + arrival.getDateTime().getHours() +
                ":" + arrival.getDateTime().getMinutes() +
                (arrival.getDelay()!=0? "+" + arrival.getDelay():"") +
                "     duration: " + duration.toMinutes();
    }
}
