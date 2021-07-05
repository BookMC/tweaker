package org.bookmc.tweaker.game;

import org.bookmc.loader.vessel.ModVessel;

import java.io.File;

public class MinecraftModVessel implements ModVessel {
    private final String version;

    public MinecraftModVessel(String version) {
        this.version = version;
    }

    @Override
    public String getName() {
        return "Minecraft";
    }

    @Override
    public String getId() {
        return "minecraft";
    }

    @Override
    public String getDescription() {
        return "Minecraft is a sandbox video game developed by the Swedish video game developer Mojang Studios. " +
            "The game was created by Markus \"Notch\" Persson in the Java programming language.";
    }

    @Override
    public String getConfig() {
        return null;
    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public String[] getAuthors() {
        return new String[]{"Oracle Corporation", "Sun Microsystems", "James Gosling"};
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getEntrypoint() {
        return "org.bookmc.tweaker.entrypoint.FakeEntrypoint::fakeItToMakeIt";
    }

    @Override
    public File getFile() {
        return new File("there_is_no_mod_to_see");
    }

    @Override
    public String getMixinEntrypoint() {
        return null;
    }

    @Override
    public String[] getDependencies() {
        return new String[0];
    }
}
