package entity;


import java.util.ArrayList;
import java.util.List;

public class Home {
    private List<Device> homeInstalledDevices = new ArrayList<>();
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
}
