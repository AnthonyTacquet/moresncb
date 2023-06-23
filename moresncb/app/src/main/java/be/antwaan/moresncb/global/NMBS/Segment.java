package be.antwaan.moresncb.global.NMBS;

import java.util.ArrayList;
import java.util.Objects;

public class Segment {
    private int id;
    private Station origin;
    private Station desination;
    private String source;
    private ArrayList<Unit> units = new ArrayList<>();

    public Segment(int id, Station origin, Station desination, String source, ArrayList<Unit> units) {
        this.id = id;
        this.origin = origin;
        this.desination = desination;
        this.source = source;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public Station getDesination() {
        return desination;
    }

    public void setDesination(Station desination) {
        this.desination = desination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Segment)) return false;
        Segment segment = (Segment) o;
        return getId() == segment.getId() && Objects.equals(getOrigin(), segment.getOrigin()) && Objects.equals(getDesination(), segment.getDesination()) && Objects.equals(getSource(), segment.getSource()) && Objects.equals(getUnits(), segment.getUnits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrigin(), getDesination(), getSource(), getUnits());
    }
}
