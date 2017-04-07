package com.comp4020.listproject;

/**
 * Created by brett on 07/04/17.
 */

public class Item {

    private String name;
    private int level;

    public Item(){

    }

    public Item(String name, int level){
        this.name = name;
        this.level = level;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){

        this.name = name;
    }
    public void setLevel(int level){
        this.level = level;
    }


}
