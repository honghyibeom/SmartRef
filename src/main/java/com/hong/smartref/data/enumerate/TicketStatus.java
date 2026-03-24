package com.hong.smartref.data.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TicketStatus implements DisplayEnum{

    inProgress("inProgress"),
    failed("failed"),
    success("success");

    private final String value;

    private TicketStatus(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TicketStatus from(String value) {
        for (TicketStatus type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown RecipeVisibility: " + value);
    }
}
