package de.leoxian.moonlightcore.common.fluid;

import de.leoxian.moonlightcore.common.platform.XplatAbstraction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Consumer;

public interface FluidRegistrar {
    static void init(String namespace, Consumer<FluidRegistrar> initializer) {
        XplatAbstraction.INSTANCE.fluids(namespace, initializer);
    }

    void register(String id, TagKey<Fluid> fluidType, FluidProperties properties, FluidAttributesHandler propertiesHandler, FluidBehavior entityInteraction);
}
