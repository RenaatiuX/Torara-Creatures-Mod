package rena.toraracreatures.entities.enums;

import java.util.Arrays;
import java.util.Comparator;

public enum LoveBirdVariant {

    GREEN(0),
    DARK_BLUE(1),
    SKY_BLUE(2),
    BROWN(3),
    BLUE(4),
    LIGHT_BLUE(5),
    GOLDEN(6),
    PURPLE(7),
    GRAY(8);


    private static final LoveBirdVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(LoveBirdVariant::getId)).toArray(LoveBirdVariant[]::new);

    private final int id;

    LoveBirdVariant(int id){

        this.id = id;

    }

    public int getId() {
        return this.id;
    }


    public static LoveBirdVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

}
