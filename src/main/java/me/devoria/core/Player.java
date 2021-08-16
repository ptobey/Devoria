package me.devoria.core;

import java.util.UUID;

public class Player {
    private final UUID uuid;
    private final String name;
    private String type;

    public Player(UUID uuid, String name, String type) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
    }
    public UUID getUuid() {
        return uuid;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
