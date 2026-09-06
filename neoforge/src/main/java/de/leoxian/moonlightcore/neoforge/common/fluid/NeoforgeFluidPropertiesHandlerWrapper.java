package de.leoxian.moonlightcore.neoforge.common.fluid;

import de.leoxian.moonlightcore.common.fluid.FluidPropertiesHandler;
import de.leoxian.moonlightcore.common.transfer.fluid.FluidResource;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import org.jspecify.annotations.Nullable;

public class NeoforgeFluidPropertiesHandlerWrapper extends FluidType {
    private final FluidPropertiesHandler handler;

    public NeoforgeFluidPropertiesHandlerWrapper(final FluidPropertiesHandler handler) {
        super(Properties.create()
                .fallDistanceModifier(0.0F)
                .canExtinguish(true)
                .canConvertToSource(true)
                .supportsBoating(true)
                .canPushEntity(true)
                .canHydrate(true)
                .isWaterLike(true)
                .canDrown(true));
        this.handler = handler;
    }

    @Override
    public String getDescriptionId(FluidStack stack) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        return handler.getDescriptionId(resource);
    }

    @Override
    public Component getDescription(FluidStack stack) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        return super.getDescription(stack).copy().withColor(this.handler.getAssociatedColor(resource));
    }

    @Override
    public @Nullable SoundEvent getSound(FluidStack stack, SoundAction action) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        if (action == SoundActions.BUCKET_FILL) {
            return this.handler.getFillSound(resource).orElse(super.getSound(stack, action));
        } else if (action == SoundActions.BUCKET_EMPTY) {
            return this.handler.getEmptySound(resource).orElse(super.getSound(stack, action));
        }
        return super.getSound(stack, action);
    }

    @Override
    public int getLightLevel(FluidStack stack) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        return this.handler.getLightEmission(resource);
    }

    @Override
    public int getTemperature(FluidStack stack) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        return this.handler.getTemperature(resource);
    }

    @Override
    public int getViscosity(FluidStack stack) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        return this.handler.getViscosity(resource);
    }

    @Override
    public int getDensity(FluidStack stack) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        // For some reason we can't overide 'isLighterThanAir'...???
        return this.handler.isLighterThanAir(resource) ? -1 : super.getDensity(stack);
    }

    @Override
    public boolean canSwim(Entity entity) {
        return true;
    }
}
