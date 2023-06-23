package be.antwaan.moresncb.global.NMBS;

import java.util.Objects;

public class Platform {
    private String name;
    private String normal;

    public Platform(String name, String normal) {
        this.name = name;
        this.normal = normal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "name='" + name + '\'' +
                ", normal='" + normal + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Platform)) return false;
        Platform platform = (Platform) o;
        return Objects.equals(getName(), platform.getName()) && Objects.equals(getNormal(), platform.getNormal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNormal());
    }
}
