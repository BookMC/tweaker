package org.bookmc.tweaker.remapper;

import org.bookmc.srg.output.MappedClass;
import org.bookmc.srg.output.MappedField;
import org.bookmc.srg.output.MappedMethod;
import org.bookmc.srg.output.SrgOutput;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public class MixinRemapper implements IRemapper {
    private final SrgOutput output;

    public MixinRemapper(SrgOutput output) {
        this.output = output;
    }

    @Override
    public String mapMethodName(String owner, String name, String desc) {
        for (MappedMethod method : output.getMethods()) {
            if (method.getUnmappedOwner().equals(owner) && method.getUnmappedName().equals(name) && method.getUnmappedDesc().equals(desc)) {
                return method.getMappedName();
            }
        }

        return name; // Return unmapped name.
    }

    @Override
    public String mapFieldName(String owner, String name, String desc) {
        for (MappedField field : output.getFields()) {
            if (field.getUnmappedOwner().equals(owner) && field.getUnmappedName().equals(name)) {
                return field.getMappedName();
            }
        }

        return name; // Return unmapped name.
    }

    @Override
    public String map(String typeName) {
        for (MappedClass clazz : output.getClasses()) {
            if (clazz.getUnmappedName().equals(typeName)) {
                return clazz.getMappedName();
            }
        }

        return typeName;
    }

    @Override
    public String unmap(String typeName) {
        for (MappedClass clazz : output.getClasses()) {
            if (clazz.getMappedName().equals(typeName)) {
                return clazz.getUnmappedName();
            }
        }

        return typeName;
    }

    @Override
    public String mapDesc(String desc) {
        return desc;
    }

    @Override
    public String unmapDesc(String desc) {
        return desc;
    }
}
