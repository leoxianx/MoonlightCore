package de.leoxian.moonlightcore.fabric.common.fluid;

import de.leoxian.moonlightcore.common.fluid.MoonlightFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import java.util.function.Supplier;

public abstract class FabricFluidImpl extends FlowingFluid {
    private final Supplier<? extends Fluid> flowing;
    private final Supplier<? extends Fluid> still;
    private final Supplier<? extends Item> bucket;
    private final Supplier<? extends LiquidBlock> block;
    private final int slopeFindDistance;
    private final int levelDecreasePerBlock;
    private final float explosionResistance;
    private final int tickRate;
    private final MoonlightFluid delegate;

    public FabricFluidImpl(Supplier<? extends Fluid> flowing, Supplier<? extends Fluid> still, Supplier<? extends Item> bucket, Supplier<? extends LiquidBlock> block, MoonlightFluid delegate) {
        this.flowing = flowing;
        this.still = still;
        this.bucket = bucket;
        this.block = block;
        this.slopeFindDistance = delegate.slopeFindDistance;
        this.levelDecreasePerBlock = delegate.levelDecreasePerBlock;
        this.explosionResistance = delegate.explosionResistance;
        this.tickRate = delegate.tickRate;
        this.delegate = delegate;
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
    protected boolean isRandomlyTicking() {
        return this.delegate.isRandomlyTicking();
    }

    @Override
    protected void animateTick(Level level, BlockPos pos, FluidState fluidState, RandomSource random) {
        super.animateTick(level, pos, fluidState, random);
        this.delegate.animateTick(level, pos, fluidState, random);
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        this.delegate.beforeDestroyingBlock(level, pos, state);
    }

    @Override
    public Fluid getFlowing() {
        return this.flowing.get();
    }

    @Override
    public Fluid getSource() {
        return this.still.get();
    }

    @Override
    protected boolean canConvertToSource(ServerLevel level) {
        return false;
    }

    @Override
    protected int getSlopeFindDistance(LevelReader level) {
        return this.slopeFindDistance;
    }

    @Override
    protected int getDropOff(LevelReader level) {
        return this.levelDecreasePerBlock;
    }

    @Override
    public Item getBucket() {
        return this.bucket.get();
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
    public int getTickDelay(LevelReader level) {
        return this.tickRate;
    }

    @Override
    protected float getExplosionResistance() {
        return this.explosionResistance;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    public static class Source extends FabricFluidImpl {
        public Source(Supplier<? extends Fluid> flowing, Supplier<? extends Fluid> still, Supplier<? extends Item> bucket, Supplier<? extends LiquidBlock> block, MoonlightFluid delegate) {
            super(flowing, still, bucket, block, delegate);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
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

    public static class Flowing extends FabricFluidImpl {
        public Flowing(Supplier<? extends Fluid> flowing, Supplier<? extends Fluid> still, Supplier<? extends Item> bucket, Supplier<? extends LiquidBlock> block, MoonlightFluid delegate) {
            super(flowing, still, bucket, block, delegate);
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
            return fluidState.getValue(LEVEL);
        }
    }
}
