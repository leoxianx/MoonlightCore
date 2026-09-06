package de.leoxian.moonlightcore.common.platform;

import de.leoxian.moonlightcore.common.fluid.FluidPropertiesHandler;
import de.leoxian.moonlightcore.common.fluid.MoonlightFluid;
import de.leoxian.moonlightcore.common.registry.DeferredHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.function.Supplier;

public final class Platform {
    public static SoundType createSoundType(float volume, float pitch, Supplier<SoundEvent> breakSound, Supplier<SoundEvent> stepSound, Supplier<SoundEvent> placeSound, Supplier<SoundEvent> hitSound, Supplier<SoundEvent> fallSound) {
        return XplatAbstraction.INSTANCE.createSoundType(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
    }

    @ApiStatus.Experimental
    public static <T extends FlowingFluid> DeferredHolder<Fluid, T> registerFluid(Identifier id, MoonlightFluid.Source sourceFluidHandler, MoonlightFluid.Flowing flowingFluidHandler, FluidPropertiesHandler propertiesHandler) {
        return XplatAbstraction.INSTANCE.registerFluid(id, sourceFluidHandler, flowingFluidHandler, propertiesHandler);
    }

    public static boolean isModLoaded(String modId) {
        return XplatAbstraction.INSTANCE.isModLoaded(modId);
    }

    public static MinecraftServer getCurrentServer() {
        return XplatAbstraction.INSTANCE.getCurrentServer();
    }

    public static Path getConfigDirectory() {
        return XplatAbstraction.INSTANCE.getConfigDirectory();
    }

    public static Path getGameDirectory() {
        return XplatAbstraction.INSTANCE.getGameDirectory();
    }

    public static boolean isDevelopmentWorkspace() {
        return XplatAbstraction.INSTANCE.isDevelopmentWorkspace();
    }

    public static boolean isNeoforge() {
        return XplatAbstraction.INSTANCE.isNeoforge();
    }

    public static boolean isFabric() {
        return XplatAbstraction.INSTANCE.isFabric();
    }

    private Platform() {}
}
