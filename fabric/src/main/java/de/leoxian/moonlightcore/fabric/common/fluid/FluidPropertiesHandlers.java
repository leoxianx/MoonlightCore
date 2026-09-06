package de.leoxian.moonlightcore.fabric.common.fluid;

import de.leoxian.moonlightcore.common.fluid.FluidPropertiesHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class FluidPropertiesHandlers {
    private static final HashSet<Fluid> VANILLA_FLUIDS = new HashSet<>(){{
       add(Fluids.EMPTY);
        add(Fluids.WATER); add(Fluids.FLOWING_WATER);
        add(Fluids.LAVA); add(Fluids.FLOWING_LAVA);
    }};

    private static final Map<Fluid, FluidPropertiesHandler> HANDLERS = new HashMap<>();

    public static void register(Fluid fluid, FluidPropertiesHandler handler) {
        if (VANILLA_FLUIDS.contains(fluid)) {
            throw new IllegalArgumentException("May not register a fluid properties handler to a vanilla ('minecraft' namespace) fluid");
        }

        if (HANDLERS.putIfAbsent(fluid, handler) != null) {
            throw new IllegalArgumentException("May not register duplicated properties handler to fluid '" + BuiltInRegistries.FLUID.getKey(fluid) + "'");
        }
    }

    public static FluidPropertiesHandler get(Fluid fluid) {
        return HANDLERS.get(fluid);
    }
}
