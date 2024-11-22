package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;

import java.util.ArrayList;
import java.util.List;

abstract public class Level0ItemRoom extends Level0Room{

    private List<Item> items;

    public Level0ItemRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        items = new ArrayList<>();
    }

    protected void addItem(Item item){
        items.add(item);
    }

    // registers in the area all items on the list
    @Override
    protected void initRoom() {
        super.initRoom();
        for (int i = 0 ; i < items.size() ; ++i){
            registerActor(items.get(i));
        }
    }

    // an item room is solved when all items are collected
    @Override
    public boolean isOn() {
        for (int i = 0 ; i < items.size() ; ++i){
            if (!items.get(i).isCollected()) {return false;}
        }
        return true;
    }

    @Override
    public boolean isOff() {
        return !isOn();
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        09.12.2022
 */

