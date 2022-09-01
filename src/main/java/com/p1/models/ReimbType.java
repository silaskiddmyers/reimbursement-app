package com.p1.models;

public class ReimbType {
    int id;
    String type;

    public ReimbType() {
    }

    public ReimbType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
    
}
