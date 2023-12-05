package ru.tkoinform.ppkverificationserver;

public class Role {
    public static final String rolePrefix = "ROLE_";

    // Эти нам нужны
    public static final String OPERATOR = rolePrefix + "fs_operator";
    public static final String ADMIN = rolePrefix + "fs_admin";
    public static final String ANALYTIC = rolePrefix + "fs_object_analyst";
    public static final String MOBILE = rolePrefix + "fs_mobile";

    // Эти не знаю
    public static final String MONITORING = rolePrefix + "fs_monitoring";
    public static final String USER = rolePrefix + "fs_user";
}
