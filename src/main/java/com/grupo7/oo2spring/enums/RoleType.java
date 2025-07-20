package com.grupo7.oo2spring.enums;

public enum RoleType {

    EMPLEADO,
    USER,
    MANAGER;

    public String getPrefixedName() {
        return "ROLE_" + this.name();
    }
}