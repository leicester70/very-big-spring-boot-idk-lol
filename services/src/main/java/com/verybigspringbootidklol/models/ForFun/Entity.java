package com.verybigspringbootidklol.models.ForFun;

public class Entity extends World {
    //  Entity will belong to a World.
    private World world;
    public String name;
    //  Not every entitiy is a living thing.
    private boolean isLiving = false;
    public Entity(World world, String name) {
        super(world.name);
        this.name = name;
    }
}
