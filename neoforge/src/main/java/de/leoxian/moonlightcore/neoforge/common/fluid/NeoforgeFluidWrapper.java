package de.leoxian.moonlightcore.neoforge.common.fluid;

import de.leoxian.moonlightcore.common.fluid.MoonlightFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Supplier;

public abstract class NeoforgeFluidWrapper extends BaseFlowingFluid {
    protected final MoonlightFluid delegate;

    public NeoforgeFluidWrapper(Supplier<FluidType> fluidType, Supplier<? extends Fluid> sourceGetter, Supplier<? extends Fluid> flowingGetter, Supplier<? extends Item> bucketItem, Supplier<? extends LiquidBlock> blockGetter, MoonlightFluid delegate) {
        super(new Properties(fluidType, sourceGetter, flowingGetter)
                .bucket(bucketItem)
                .block(blockGetter)
                .explosionResistance(delegate.explosionResistance)
                .tickRate(delegate.tickRate)
                .levelDecreasePerBlock(delegate.levelDecreasePerBlock)
                .slopeFindDistance(delegate.slopeFindDistance));
        this.delegate = delegate;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
        if (direction == Direction.DOWN) {
            return !isSame(fluid);
        }
        return fluid.isSame(this) || state.isEmpty() || level.getBlockState(pos).canBeReplaced();
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == getSource() || fluid == getFlowing();
    }

    @Override
    public void tick(ServerLevel level, BlockPos pos, BlockState blockState, FluidState fluidState) {
        super.tick(level, pos, blockState, fluidState);
        this.delegate.tick(level, pos, blockState, fluidState);
    }

    @Override
    protected void randomTick(ServerLevel level, BlockPos pos, FluidState fluidState, RandomSource random) {
        super.randomTick(level, pos, fluidState, random);
        this.delegate.randomTick(level, pos, fluidState, random);
    }

    @Override
    protected void animateTick(Level level, BlockPos pos, FluidState fluidState, RandomSource random) {
        super.animateTick(level, pos, fluidState, random);
        this.delegate.animateTick(level, pos, fluidState, random);
    }

    @Override
    protected boolean isRandomlyTicking() {
        return this.delegate.isRandomlyTicking();
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor worldIn, BlockPos pos, BlockState state) {
        this.delegate.beforeDestroyingBlock(worldIn, pos, state);
    }

    public static class Source extends NeoforgeFluidWrapper {
        public Source(Supplier<FluidType> fluidType, Supplier<? extends Fluid> sourceGetter, Supplier<? extends Fluid> flowingGetter, Supplier<? extends Item> bucketItem, Supplier<? extends LiquidBlock> blockGetter, MoonlightFluid delegate) {
            super(fluidType, sourceGetter, flowingGetter, bucketItem, blockGetter, delegate);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }
    }

    public static class Flowing extends NeoforgeFluidWrapper {
        public Flowing(Supplier<FluidType> fluidType, Supplier<? extends Fluid> sourceGetter, Supplier<? extends Fluid> flowingGetter, Supplier<? extends Item> bucketItem, Supplier<? extends LiquidBlock> blockGetter, MoonlightFluid delegate) {
            super(fluidType, sourceGetter, flowingGetter, bucketItem, blockGetter, delegate);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(FlowingFluid.LEVEL);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(FlowingFluid.LEVEL);
        }
    }
}
