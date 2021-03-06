package org.bookmc.tweaker.common;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bookmc.loader.Loader;
import org.bookmc.loader.utils.ClassUtils;
import org.bookmc.loader.utils.DiscoveryUtils;
import org.bookmc.loader.vessel.ModVessel;
import org.bookmc.tweaker.game.MinecraftModVessel;
import org.bookmc.tweaker.java.JavaModVessel;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class BookMCLoaderCommon implements ITweaker {
    private final Logger logger = LogManager.getLogger(this);

    private static File modsDirectory;

    private final List<String> args = new ArrayList<>();

    private String version;

    private static MixinEnvironment.Side side = MixinEnvironment.Side.UNKNOWN;

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args.addAll(args);

        if (gameDir != null) {
            addArg("gameDir", gameDir.getAbsolutePath());
        }

        if (assetsDir != null) {
            addArg("assetsDir", assetsDir.getAbsolutePath());
        }

        if (profile != null) {
            addArg("version", profile);
            this.version = profile;
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        // Redirect this stuff to the parent classloader
        classLoader.addClassLoaderExclusion("org.bookmc.loader.");

        MixinBootstrap.init();

        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();

        injectIntoClassLoader(classLoader, environment);

        String passedDirectory = System.getProperty("book.discovery.folder", "mods");

        modsDirectory = new File(Launch.minecraftHome, passedDirectory);

        if (!modsDirectory.exists()) {
            if (!modsDirectory.mkdir()) {
                System.err.println("Failed to create mods directory");
            }
        }

        Loader.registerVessel(new JavaModVessel());
        loadModMixins(modsDirectory);

        if (version != null) {
            loadModMixins(new File(modsDirectory, version));
            Loader.registerVessel(new MinecraftModVessel(version));
        } else {
            logger.error("Failed to detect the game version! Mods inside the game version's mod folder will not be loaded!");
        }

        if (environment.getObfuscationContext() == null) {
            environment.setObfuscationContext("notch"); // Switch's to notch mappings
        }

        // Load our transformation service only if it's available.
        if (ClassUtils.isClassAvailable("org.bookmc.services.TransformationService")) {
            classLoader.registerTransformer("org.bookmc.services.TransformationService");
        }

        side = setSide(environment);
    }

    public abstract void injectIntoClassLoader(LaunchClassLoader classLoader, MixinEnvironment environment);

    public abstract MixinEnvironment.Side setSide(MixinEnvironment environment);

    @Override
    public String[] getLaunchArguments() {
        return args.toArray(new String[0]);
    }

    private void addArg(String key, String value) {
        args.add("--" + key);
        args.add(value);
    }

    private void loadModMixins(File modsDirectory) {
        DiscoveryUtils.discover(modsDirectory);

        for (ModVessel vessel : Loader.getModVessels()) {
            String mixinEntrypoint = vessel.getMixinEntrypoint();

            // Load mixins from everywhere (All jars should now be on the LaunchClassLoader)
            if (mixinEntrypoint != null) {
                Mixins.addConfiguration(mixinEntrypoint);
            }
        }
    }

    public static File getModsDirectory() {
        return modsDirectory;
    }

    public static MixinEnvironment.Side getSide() {
        return side;
    }
}
