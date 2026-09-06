package de.leoxian.moonlightcore.neoforge.common.fluid;

import de.leoxian.moonlightcore.common.fluid.BaseFlowingFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class NeoforgeFluidWrapper extends FlowingFluid {
    protected final BaseFlowingFluid delegate;
    protected final Supplier<? extends Fluid> sourceSupplier;
    protected final Supplier<? extends Fluid> flowingSupplier;
    protected final Supplier<? extends Item> bucketItem;
    protected final Supplier<FluidType> fluidTypeSupplier;

    public NeoforgeFluidWrapper(BaseFlowingFluid delegate, Supplier<? extends Fluid> sourceSupplier, Supplier<? extends Fluid> flowingSupplier, Supplier<? extends Item> bucketItem, Supplier<FluidType> fluidTypeSupplier) {
        this.delegate = delegate;
        this.sourceSupplier = sourceSupplier;
        this.flowingSupplier = flowingSupplier;
        this.bucketItem = bucketItem;
        this.fluidTypeSupplier = fluidTypeSupplier;

        if (delegate.isSource()) {
            this.registerDefaultState(this.getStateDefinition().any().setValue(FALLING, false));
        } else {
            this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 7).setValue(FALLING, false));
        }
    }

    @Override
    public FluidType getFluidType() {
        return this.fluidTypeSupplier.get();
    }

    @Override
    protected void animateTick(Level level, BlockPos pos, FluidState fluidState, RandomSource random) {
        super.animateTick(level, pos, fluidState, random);
        this.delegate.animateTick(level, pos, fluidState, random);
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
    protected void entityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        super.entityInside(level, pos, entity, effectApplier);
        this.delegate.entityInside(level, pos, entity, effectApplier);
    }

    @Override
    protected @Nullable ParticleOptions getDripParticle() {
        return this.delegate.getDripParticle();
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
        builder.add(LEVEL, FALLING);
    }

    @Override
    public Fluid getSource() {
        return this.sourceSupplier.get();
    }

    @Override
    public boolean isSame(Fluid other) {
        return other == this || other == getSource() || other == getFlowing() || this.delegate.isSame(other);
    }

    @Override
    protected boolean canConvertToSource(ServerLevel serverLevel) {
        return this.delegate.canConvertToSource(serverLevel);
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        this.delegate.beforeDestroyingBlock(levelAccessor, blockPos, blockState);
    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelReader) {
        return this.delegate.getSlopeFindDistance(levelReader);
    }

    @Override
    protected int getDropOff(LevelReader levelReader) {
        return this.delegate.getDropOff(levelReader);
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return this.delegate.getAmount(fluidState);
    }

    @Override
    public Fluid getFlowing() {
        return this.flowingSupplier.get();
    }

    @Override
    public Item getBucket() {
        return this.bucketItem.get();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return this.delegate.canBeReplacedWith(fluidState, blockGetter, blockPos, fluid, direction);
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return this.delegate.getTickDelay(levelReader);
    }

    @Override
    protected float getExplosionResistance() {
        return this.delegate.getExplosionResistance();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        Identifier fluidId = BuiltInRegistries.FLUID.getKey(this.getSource());
        Block block = BuiltInRegistries.BLOCK.getValue(fluidId);

        if (block != Blocks.AIR) {
            return block.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSource(FluidState fluidState) {
        return this.delegate.isSource();
    }
}
