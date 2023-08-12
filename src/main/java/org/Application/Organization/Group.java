package org.Application.Organization;
public enum Group {
    BUMBLEBEES("Bumblebees", 5),
    SUPERHEROES( "Superheroes", 6);

    private String name;
    private int id;
    Group(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public static Group convertToGroupEnum(int groupId) {
        for (Group group : Group.values()) {
            if (group.getId() == groupId) {
                return group;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return name;
    }
}

