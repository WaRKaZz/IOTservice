package kz.epam.IOTservice.enumeration;

public enum  Role {
    GUEST(0),
    USER(1),
    ADMIN(2);

    private int id;

    Role(int id) {
        this.id = id;
    }

    Role() {
    }

    public int getId() {
        return id;
    }

    public static Role getUserRole(int value) {
        for (Role v : values())
            if (v.getId() == value) return v;
        throw new IllegalArgumentException();
    }

}