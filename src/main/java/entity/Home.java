package entity;


import java.util.List;
import java.util.Objects;

public class Home {
    private List<Device> homeInstalledDevices;
    private Long homeID;
    private String homeName;
    private String homeAddress;

    public List<Device> getHomeInstalledDevices() {
        return homeInstalledDevices;
    }

    public void setHomeInstalledDevices(List<Device> homeInstalledDevices) {
        this.homeInstalledDevices = homeInstalledDevices;
    }

    public Long getHomeID() {
        return homeID;
    }

    public void setHomeID(Long homeID) {
        this.homeID = homeID;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return homeName + " " + homeAddress;
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeInstalledDevices, homeID, homeName, homeAddress);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Home home = (Home) o;
        return homeID.equals(home.homeID);
    }
}
