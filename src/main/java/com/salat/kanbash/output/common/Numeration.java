package com.salat.kanbash.output.common;

public enum Numeration {
    HYPHEN("Hyphen"),
    NUMBER("Number");

    public final String displayName;

    Numeration(String displayName) {
        this.displayName = displayName;
    }
}
