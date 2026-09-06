package de.leoxian.moonlightcore.client.platform;

import de.leoxian.moonlightcore.client.fluid.ClientFluidRenderHandler;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public final class ClientPlatform {
    public static void registerFluidModel(Supplier<Fluid> fluid, FluidModel.Unbaked model, ClientFluidRenderHandler handler) {
        XplatClientAbstraction.INSTANCE.registerFluidModel(fluid, model, handler);
    }

    public static void registerFluidModel(Supplier<Fluid> fluid, FluidModel.Unbaked model) {
        XplatClientAbstraction.INSTANCE.registerFluidModel(fluid, model);
    }

    public static void registerFluidModel(Supplier<Fluid> source, Supplier<Fluid> flowing, FluidModel.Unbaked model) {
        XplatClientAbstraction.INSTANCE.registerFluidModel(source, flowing, model);
    }

    public static void registerFluidModel(Supplier<Fluid> source, Supplier<Fluid> flowing, FluidModel.Unbaked model, ClientFluidRenderHandler handler) {
        XplatClientAbstraction.INSTANCE.registerFluidModel(source, flowing, model, handler);
    }

    private ClientPlatform() {}
}
