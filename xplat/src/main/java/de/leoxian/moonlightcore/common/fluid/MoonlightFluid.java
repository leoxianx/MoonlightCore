package de.leoxian.moonlightcore.common.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public abstract class MoonlightFluid {
    public final int slopeFindDistance;
    public final int levelDecreasePerBlock;
    public final float explosionResistance;
    public final int tickRate;

    MoonlightFluid(Properties properties) {
        this.slopeFindDistance = properties.slopeFindDistance;
        this.levelDecreasePerBlock = properties.levelDecreasePerBlock;
        this.explosionResistance = properties.explosionResistance;
        this.tickRate = properties.tickRate;
    }

    public void tick(ServerLevel level, BlockPos pos, BlockState blockState, FluidState fluidState) {
    }

    public void randomTick(ServerLevel level, BlockPos pos, FluidState fluidState, RandomSource random) {

    }

    public boolean isRandomlyTicking() {
        return false;
    }

    public void animateTick(Level level, BlockPos pos, FluidState fluidState, RandomSource random) {
    }

    public void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
        BlockEntity blockEntity = blockState.hasBlockEntity() ? levelAccessor.getBlockEntity(blockPos) : null;
        Block.dropResources(blockState, levelAccessor, blockPos, blockEntity);
    }

    public static class Flowing extends MoonlightFluid {
        public Flowing(Properties properties) {
            super(properties);
        }
    }

    public static class Source extends MoonlightFluid {
        public Source(Properties properties) {
            super(properties);
        }
    }

    public static class Properties {
        private int slopeFindDistance = 4;
        private int levelDecreasePerBlock = 1;
        private float explosionResistance = 1;
        private int tickRate = 5;

        public Properties slopeFindDistance(int slopeFindDistance) {
            this.slopeFindDistance = slopeFindDistance;
            return this;
        }

        public Properties levelDecreasePerBlock(int levelDecreasePerBlock) {
            this.levelDecreasePerBlock = levelDecreasePerBlock;
            return this;
        }

        public Properties explosionResistance(float explosionResistance) {
            this.explosionResistance = explosionResistance;
            return this;
        }

        public Properties tickRate(int tickRate) {
            this.tickRate = tickRate;
            return this;
        }
    }
}
