package org.bookmc.tweaker.utils;

public class ClassUtils {
    public static boolean isClassAvailable(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
