package com.sz91online.bgms.module.tracer.util;

import java.util.HashMap;
import java.util.Map;

public class ClassInfoMap {
    private static Map<Class, ClassInfo> mapWrapper = new HashMap<>();

    public static void put(Class cls, ClassInfo classInfo) {
        mapWrapper.put(cls, classInfo);
    }

    public static ClassInfo getClassInfo(Class cls) {
        return mapWrapper.get(cls);
    }

    public static String getModule(Class cls) {
        return mapWrapper.get(cls).getModule();
    }

    public static String getType(Class cls) {
        return mapWrapper.get(cls).getType();
    }
}
