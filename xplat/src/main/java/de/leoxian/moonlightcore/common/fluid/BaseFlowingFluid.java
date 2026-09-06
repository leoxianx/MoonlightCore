package de.leoxian.moonlightcore.common.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jspecify.annotations.Nullable;

/// # FluidHandler
/// This interface serves as a fluid layer to manage most methods of the Vanilla [net.minecraft.world.level.material.Fluid] class
/// since Neoforge injects their `getFluidType` method we cannot directly extend [net.minecraft.world.level.material.Fluid] without
/// to let MoonlightCore manage all fluids registration
/// having problems. Use this interface and register your fluids with [de.leoxian.moonlightcore.common.platform.XplatAbstraction#registerFluid(Identifier, Source, Flowing, FluidPropertiesHandler)]
public interface BaseFlowingFluid {
    boolean isSource();

    boolean canConvertToSource(ServerLevel level);

    void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state);

    int getSlopeFindDistance(LevelReader reader);

    boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction);

    boolean isSame(Fluid other);

    int getDropOff(LevelReader level);

    int getTickDelay(LevelReader level);

    float getExplosionResistance();

    int getAmount(FluidState fluidState);

    default void animateTick(Level level, BlockPos pos, FluidState fluidState, net.minecraft.util.RandomSource random) {
    }

    default void tick(ServerLevel level, BlockPos pos, BlockState blockState, FluidState fluidState) {
    }

    default void randomTick(ServerLevel level, BlockPos pos, FluidState fluidState, net.minecraft.util.RandomSource random) {
    }

    default void entityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
    }

    default @Nullable ParticleOptions getDripParticle() {
        return null;
    }

    interface Source extends BaseFlowingFluid {
        @Override
        default int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        default boolean isSource() {
            return true;
        }
    }

    interface Flowing extends BaseFlowingFluid {
        @Override
        default int getAmount(FluidState fluidState) {
            return fluidState.getValue(FlowingFluid.LEVEL);
        }

        @Override
        default boolean isSource() {
            return false;
        }
    }
}
