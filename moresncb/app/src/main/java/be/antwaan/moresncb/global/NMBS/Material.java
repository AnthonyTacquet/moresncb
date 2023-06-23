package be.antwaan.moresncb.global.NMBS;

import java.util.Objects;

import be.antwaan.moresncb.global.Enum.Orientation;

public class Material {
    private String parentType;
    private String subType;
    private Orientation orientation;

    public Material(String parentType, String subType, Orientation orientation) {
        this.parentType = parentType;
        this.subType = subType;
        this.orientation = orientation;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;
        Material material = (Material) o;
        return Objects.equals(getParentType(), material.getParentType()) && Objects.equals(getSubType(), material.getSubType()) && getOrientation() == material.getOrientation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParentType(), getSubType(), getOrientation());
    }
}
