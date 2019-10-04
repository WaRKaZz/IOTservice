package kz.epam.IOTService.enumeration;

public enum  Role {
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

    public static Role getUserRole(int value) {
        for (Role v : values())
            if (v.getId() == value) return v;
        throw new IllegalArgumentException();
    }

}