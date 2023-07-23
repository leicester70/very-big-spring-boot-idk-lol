package com.verybigspringbootidklol.models.ForFun;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;

public class World {
    public String name;
    public DateTime currentTime;
    private ArrayList<Entity> entities;
    public World(String worldName) {
        super();
        this.name = worldName;
        this.currentTime = new DateTime();
    }

    public void newEntity(Entity newEntity){
        entities.add(newEntity);
    }
}
