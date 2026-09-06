package de.leoxian.moonlightcore.client.fluid;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.HashMap;
import java.util.Map;

public final class ClientFluidRenderHandlers {
    private static final Map<Fluid, ClientFluidRenderHandler> HANDLERS = new HashMap<>();

    public static void register(Fluid fluid, ClientFluidRenderHandler handler) {
        if (fluid == Fluids.EMPTY || fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER || fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA) {
            throw new IllegalArgumentException("May not register render handler to any vanilla ('minecraft' namespace) fluid");
        }

        if (HANDLERS.putIfAbsent(fluid, handler) != null) {
            throw new IllegalArgumentException("Duplicate render handler registration for fluid: '" + BuiltInRegistries.FLUID.getKey(fluid) + "'");
        }
    }

    public static ClientFluidRenderHandler get(Fluid fluid) {
        return HANDLERS.getOrDefault(fluid, ClientFluidRenderHandler.DEFAULT);
    }

    private ClientFluidRenderHandlers() {}
}
