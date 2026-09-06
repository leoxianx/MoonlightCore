package de.leoxian.moonlightcore.neoforge.common.fluid;

import de.leoxian.moonlightcore.common.fluid.FluidPropertiesHandler;
import de.leoxian.moonlightcore.common.transfer.fluid.FluidResource;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import org.jspecify.annotations.Nullable;

public class NeoforgeFluidTypeWrapper extends FluidType {
    private final FluidPropertiesHandler handler;

    public NeoforgeFluidTypeWrapper(FluidPropertiesHandler handler) {
        super(Properties.create().fallDistanceModifier(0.0F));
        this.handler = handler;
    }

    @Override
    public boolean canDrownIn(LivingEntity entity) {
        return this.handler.canDrown();
    }

    @Override
    public boolean canSwim(Entity entity) {
        return this.handler.canSwim();
    }

    @Override
    public boolean getIsWaterLike() {
        return this.handler.isWaterLike();
    }

    @Override
    public Component getDescription(FluidStack stack) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        return this.handler.getColoredName(resource);
    }

    @Override
    public @Nullable SoundEvent getSound(FluidStack stack, SoundAction action) {
        FluidResource resource = FluidResource.of(stack.getFluid(), stack.getComponentsPatch());
        SoundEvent fallback = super.getSound(stack, action);
        if (action == SoundActions.BUCKET_FILL) {
            return this.handler.getFillSound(resource).orElse(fallback);
        } else if (action == SoundActions.BUCKET_EMPTY) {
            return this.handler.getEmptySound(resource).orElse(fallback);
        }
        return fallback;
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
}
