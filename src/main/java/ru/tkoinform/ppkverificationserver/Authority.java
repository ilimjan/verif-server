package ru.tkoinform.ppkverificationserver;

import com.google.common.base.Joiner;

public class Authority {

    public static final String ADMIN = hasAnyRole(new String[]{Role.ADMIN, Role.ANALYTIC});
    public static final String USER = hasAnyRole(new String[]{Role.OPERATOR, Role.USER});
    public static final String MOBILE = hasAnyRole(new String[]{Role.MOBILE});

    private static String hasAnyRole(String[] roles) {
        return  "hasAnyRole('" + Joiner.on("','").join(roles) + "')";
    }
}
