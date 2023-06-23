package be.antwaan.moresncb.global.NMBS;

import java.util.ArrayList;
import java.util.Objects;

public class Composition {

    private ArrayList<Segment> segments = new ArrayList<>();

    public Composition(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Composition)) return false;
        Composition that = (Composition) o;
        return Objects.equals(getSegments(), that.getSegments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSegments());
    }
}
