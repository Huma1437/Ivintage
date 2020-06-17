package com.ivintagesolutions.i_vintage;

public class State {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String id;
    String name;

    public State(String id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }


}
