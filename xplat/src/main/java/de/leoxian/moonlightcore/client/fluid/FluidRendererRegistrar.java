package de.leoxian.moonlightcore.client.fluid;

import de.leoxian.moonlightcore.client.platform.XplatClientAbstraction;
import jdk.jfr.Experimental;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Consumer;

@Experimental
public interface FluidRendererRegistrar {
    static void init(String namespace, Consumer<FluidRendererRegistrar> initializer) {
        XplatClientAbstraction.INSTANCE.fluidRenderer(namespace, initializer);
    }

    void registerModel(Holder<Fluid> holder, FluidModel.Unbaked model);

    default void registerModel(Holder<Fluid> source, Holder<Fluid> flowing, FluidModel.Unbaked model) {
        registerModel(source, model);
        registerModel(flowing, model);
    }

    void registerRenderHandler(Holder<Fluid> holder, FluidRenderHandler renderHandler);

    default void registerRenderHandler(Holder<Fluid> source, Holder<Fluid> flowing, FluidRenderHandler renderHandler) {
        registerRenderHandler(source, renderHandler);
        registerRenderHandler(flowing, renderHandler);
    }
}
