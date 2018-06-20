package com.melonltd.naberc.model.type;

import com.google.common.collect.Lists;

import java.util.List;

public enum Identity {
    ELEMENTARY("小學生"),
    SENOR("國中生"),
    JUNOR("高中生"),
    UNIVERSITY("大學/大專院校生"),
    NON_STUDENT("社會人士"),
    SELLERS("SELLERS");

    private String name;

    Identity(String name) {
        this.name = name;
    }


    public static Identity of(String name) {
        for (Identity type : getEnumValues()) {
            if (type.name.equals(name.toUpperCase())) {
                return type;
            }
        }
        return null;
    }

    public static List<Identity> getEnumValues() {
        return Lists.newArrayList(Identity.values());
    }

}
