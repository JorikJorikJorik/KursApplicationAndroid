package com.example.jorik.kursapplicationandroid.Model.Enum;

/**
 * Created by jorik on 15.05.16.
 */
public enum StateApplication {
    NONE(0),
    SIGNIN(1),
    REGISTRATION(2),
    ENTER(3);

    private int value;

    StateApplication(int i) {
        value = i;
    }

    public int getValue(){
        return value;
    }

    public static StateApplication fromValue(int value){
        for(StateApplication item : StateApplication.values()){
            if(value == item.getValue()) return item;
        }
        return NONE;
    }
}
