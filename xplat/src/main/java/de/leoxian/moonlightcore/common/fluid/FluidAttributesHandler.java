package de.leoxian.moonlightcore.common.fluid;

import de.leoxian.moonlightcore.common.transfer.fluid.FluidResource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public interface FluidAttributesHandler {
    FluidAttributesHandler DEFAULT = new FluidAttributesHandler() {};

    default Component getName(FluidResource resource) {
        Block fluidBlock = resource.fluid().defaultFluidState().createLegacyBlock().getBlock();

        if (!resource.isEmpty() && fluidBlock == Blocks.AIR) {
            return Component.translatable(Util.makeDescriptionId("block", BuiltInRegistries.FLUID.getKey(resource.fluid())));
        } else {
            return fluidBlock.getName();
        }
    }

    default int getAssociatedColor(FluidResource resource) {
        return -1;
    }

    default Component getColoredName(FluidResource resource) {
        return getName(resource).copy().withColor(getAssociatedColor(resource));
    }

    default Optional<SoundEvent> getFillSound(FluidResource resource) {
        return Optional.empty();
    }

    default Optional<SoundEvent> getEmptySound(FluidResource resource) {
        return Optional.empty();
    }

    default float getFallDistanceModifier(Entity entity) {
        return 0.0F;
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
}
