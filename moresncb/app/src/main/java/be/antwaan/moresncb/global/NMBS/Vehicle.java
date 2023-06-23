package be.antwaan.moresncb.global.NMBS;

import java.util.ArrayList;
import java.util.Objects;

public class Vehicle {
    private String name;
    private String shortName;
    private String number;
    private String type;
    private double locationX;
    private double locationY;
    private String id;

    private ArrayList<Stop> stops = new ArrayList<>();

    public Vehicle(String name, String shortName, double locationX, double locationY, String id) {
        this.name = name;
        this.shortName = shortName;
        this.locationX = locationX;
        this.locationY = locationY;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(getName(), vehicle.getName()) && Objects.equals(getShortName(), vehicle.getShortName()) && Objects.equals(getNumber(), vehicle.getNumber()) && Objects.equals(getType(), vehicle.getType()) && Objects.equals(getId(), vehicle.getId()) && Objects.equals(getStops(), vehicle.getStops());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getShortName(), getNumber(), getType(), getLocationX(), getLocationY(), getId(), getStops());
    }
}
