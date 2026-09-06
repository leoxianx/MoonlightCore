package de.leoxian.moonlightcore.common.fluid;

import de.leoxian.moonlightcore.common.transfer.fluid.FluidResource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public interface FluidPropertiesHandler {
    FluidPropertiesHandler DEFAULT = new FluidPropertiesHandler() {};

    default String getDescriptionId(FluidResource resource) {
        Block fluidBlock = resource.fluid().defaultFluidState().createLegacyBlock().getBlock();

        if (!resource.isEmpty() && fluidBlock == Blocks.AIR) {
            return Util.makeDescriptionId("block", BuiltInRegistries.FLUID.getKey(resource.fluid()));
        }
        return fluidBlock.getDescriptionId();
    }

    default int getAssociatedColor(FluidResource resource) {
        return -1;
    }

    default Optional<SoundEvent> getFillSound(FluidResource resource) {
        return Optional.empty();
    }

    default Optional<SoundEvent> getEmptySound(FluidResource resource) {
        return Optional.empty();
    }

    default int getLightEmission(FluidResource resource) {
        return resource.fluid().defaultFluidState().createLegacyBlock().getLightEmission();
    }

    default int getTemperature(FluidResource resource) {
        return 300;
    }

    default int getViscosity(FluidResource resource) {
        return 1000;
    }

    default boolean isLighterThanAir(FluidResource resource) {
        return false;
    }
}
