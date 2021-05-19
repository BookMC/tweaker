package org.bookmc.tweaker.utils;

import org.bookmc.loader.Loader;
import org.bookmc.loader.MinecraftModDiscoverer;
import org.bookmc.loader.book.DevelopmentModDiscoverer;

import java.io.File;

public class DiscoveryUtils {
    public static void discover(File modsDirectory) {
        for (MinecraftModDiscoverer discoverer : Loader.getModDiscoverers()) {
            File[] files = modsDirectory.listFiles();

            if (files != null || discoverer instanceof DevelopmentModDiscoverer) {
                discoverer.discover(files);
            }
        }
    }
}
