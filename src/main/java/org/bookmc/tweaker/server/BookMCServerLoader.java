package org.bookmc.tweaker.server;

import net.minecraft.launchwrapper.LaunchClassLoader;
import org.bookmc.tweaker.common.BookMCLoaderCommon;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

public class BookMCServerLoader extends BookMCLoaderCommon {
    private final String target = System.getProperty("book.launch.target", "net.minecraft.server.MinecraftServer");

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader, MixinEnvironment environment) {
        Mixins.addConfiguration("bookmc-server.mixins.json");

    }

    @Override
    public void setSide(MixinEnvironment environment) {
        environment.setSide(MixinEnvironment.Side.SERVER);
    }

    @Override
    public String getLaunchTarget() {
        return target;
    }
}
