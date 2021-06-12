package org.bookmc.tweaker.utils;

import java.io.File;
import java.lang.reflect.Field;

public class SRGUtils {
    private static File srgDir;

    public static File getSrgDir() {
        if (srgDir != null) {
            return srgDir;
        }

        try {
            Class<?> gradleStartCommon = Class.forName("GradleStart");

            Field srgDirField = gradleStartCommon.getSuperclass().getDeclaredField("SRG_DIR");
            srgDirField.setAccessible(true);

            srgDir = (File) srgDirField.get(null);
            return srgDir;
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
