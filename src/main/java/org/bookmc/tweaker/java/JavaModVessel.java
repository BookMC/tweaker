package org.bookmc.tweaker.java;

import org.bookmc.loader.vessel.ModVessel;

import java.io.File;

public class JavaModVessel implements ModVessel {
    @Override
    public String getName() {
        return "Java";
    }

    @Override
    public String getId() {
        return "java";
    }

    @Override
    public String getDescription() {
        return "Java is a set of computer software " +
            "and specifications developed by James Gosling at Sun Microsystems, " +
            "which was later acquired by the Oracle Corporation, " +
            "that provides a system for developing application software " +
            "and deploying it in a cross-platform computing environment.";
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
        return System.getProperty("java.version");
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
