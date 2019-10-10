package kz.epam.iotservice.enumeration;

public enum Role {
    GUEST(3),
    USER(2),
    ADMIN(1);

    private int id;

    Role(int id) {
        this.id = id;
    }

    Role() {
    }

    public int getId() {
        return id;
    }

}