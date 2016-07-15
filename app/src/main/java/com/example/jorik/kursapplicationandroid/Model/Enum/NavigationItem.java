package com.example.jorik.kursapplicationandroid.Model.Enum;

/**
 * Created by jorik on 12.07.16.
 */

public enum NavigationItem {
    NONE(0),
    WORK_LIST(1),
    BUS_LIST(2),
    DRIVER_LIST(3),
    GAS_LIST(4),
    REPAIR_LIST(5),
    CONVERSATION_LIST(6);

    private int value;

    NavigationItem(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NavigationItem fromValue(int value){
        for(NavigationItem item : values()){
            if(item.getValue() == value) return item;
        }
        return NONE;
    }
}
