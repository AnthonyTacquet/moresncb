package be.antwaan.moresncb.global.NMBS;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Alert {
    private int id;
    private String header;
    private String lead;
    private String link;
    private Date startTime;
    private Date endTime;

    public Alert(){}

    public Alert(int id, String header, String lead, String link, Date startTime, Date endTime) {
        this.id = id;
        this.header = header;
        this.lead = lead;
        this.link = link;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alert)) return false;
        Alert alert = (Alert) o;
        return getId() == alert.getId() && Objects.equals(getHeader(), alert.getHeader()) && Objects.equals(getLead(), alert.getLead()) && Objects.equals(getLink(), alert.getLink()) && Objects.equals(getStartTime(), alert.getStartTime()) && Objects.equals(getEndTime(), alert.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHeader(), getLead(), getLink(), getStartTime(), getEndTime());
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", lead='" + lead + '\'' +
                ", link='" + link + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
