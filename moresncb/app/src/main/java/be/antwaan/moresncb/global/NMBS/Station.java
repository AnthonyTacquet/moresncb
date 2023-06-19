package be.antwaan.moresncb.global.NMBS;

import java.util.Objects;

public class Station implements Comparable<Station> {
    private double locationY;
    private double locationX;
    private String standardName;
    private String name;
    private String id;
    private String apiId;

    public Station(){}

    public Station(double locationY, double locationX, String standardName, String name, String id, String apiId){
        this.locationX = locationX;
        this.locationY = locationY;
        this.standardName = standardName;
        this.name = name;
        this.id = id;
        this.apiId = apiId;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    @Override
    public String toString() {
        return standardName;
    }

    @Override
    public int compareTo(Station o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return Objects.equals(getStandardName(), station.getStandardName()) && Objects.equals(getName(), station.getName()) && Objects.equals(getId(), station.getId()) && Objects.equals(getApiId(), station.getApiId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocationY(), getLocationX(), getStandardName(), getName(), getId(), getApiId());
    }
}
