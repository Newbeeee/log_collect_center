package com.fault.collect.center.util;

import java.util.UUID;

public class TokenUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}