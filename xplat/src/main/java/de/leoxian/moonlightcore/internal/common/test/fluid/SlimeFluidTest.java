package de.leoxian.moonlightcore.internal.common.test.fluid;

import de.leoxian.moonlightcore.client.platform.ClientPlatform;
import de.leoxian.moonlightcore.common.fluid.FluidPropertiesHandler;
import de.leoxian.moonlightcore.common.fluid.MoonlightFluid;
import de.leoxian.moonlightcore.common.platform.Platform;
import de.leoxian.moonlightcore.common.registry.DeferredHolder;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public final class SlimeFluidTest {
    private static final Identifier ID = Identifier.fromNamespaceAndPath("moonlightcore", "slime");
    private static final MoonlightFluid.Properties PROPERTIES = new MoonlightFluid.Properties();

    private static final DeferredHolder<Fluid, FlowingFluid> SLIME = DeferredHolder.create(ResourceKey.create(Registries.FLUID, ID));
    private static final DeferredHolder<Fluid, FlowingFluid> SLIME_FLOWING = DeferredHolder.create(ResourceKey.create(Registries.FLUID, ID.withPath(s -> s + "_flowing")));

    public static void init() {
        Platform.registerFluid(ID, new MoonlightFluid.Source(PROPERTIES), new MoonlightFluid.Flowing(PROPERTIES), new FluidPropertiesHandler() {
            @Override
            public boolean canDrown() {
                return true;
            }

            @Override
            public boolean canSwim() {
                return true;
            }

            @Override
            public boolean isWaterLike() {
                return true;
            }
        });
        ClientPlatform.registerFluidModel(SLIME::value, SLIME_FLOWING::get, new FluidModel.Unbaked(
                new Material(Identifier.withDefaultNamespace("block/water_still")),
                new Material(Identifier.withDefaultNamespace("block/water_flow")),
                new Material(Identifier.withDefaultNamespace("block/water_overlay")),
                BlockTintSources.constant(ARGB.colorFromFloat(1.0F, 0.45F, 0.85F, 0.285F))
        ));
    }

    private SlimeFluidTest() {}
}
