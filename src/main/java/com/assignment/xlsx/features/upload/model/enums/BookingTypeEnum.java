package com.assignment.xlsx.features.upload.model.enums;

public enum BookingTypeEnum {
    NEW("NEW"),
    RENEWAL("RENEWAL"),
    EXPANSION("EXPANSION");

    private final String name;

    BookingTypeEnum(String name) {
        this.name = name;
    }

}
