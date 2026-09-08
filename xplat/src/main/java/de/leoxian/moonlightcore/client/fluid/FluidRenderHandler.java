package de.leoxian.moonlightcore.client.fluid;

import de.leoxian.moonlightcore.common.transfer.fluid.FluidResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface FluidRenderHandler {
    FluidRenderHandler DEFAULT = new FluidRenderHandler() {};

    default void appendTooltip(FluidResource resource, List<Component> tooltip, TooltipFlag tooltipFlag) {

    }

    default int getColor(FluidResource resource, @Nullable BlockAndTintGetter level, @Nullable BlockPos blockPos) {
        FluidState fluidState = resource.fluid().defaultFluidState();
        FluidModel fluidModel = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluidState);

        if (fluidModel.tintSource() == null) {
            return -1;
        }

        if (level != null && blockPos != null) {
            return fluidModel.tintSource().colorInWorld(Blocks.AIR.defaultBlockState(), level, blockPos);
        } else {
            return fluidModel.tintSource().color(Blocks.AIR.defaultBlockState());
        }
    }
}
