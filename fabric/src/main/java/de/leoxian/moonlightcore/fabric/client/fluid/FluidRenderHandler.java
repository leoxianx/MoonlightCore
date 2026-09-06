package de.leoxian.moonlightcore.fabric.client.fluid;

import de.leoxian.moonlightcore.client.fluid.ClientFluidRenderHandler;
import de.leoxian.moonlightcore.common.transfer.fluid.FluidResource;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record FluidRenderHandler(ClientFluidRenderHandler handler) implements FluidVariantRenderHandler {
    @Override
    public void appendTooltip(FluidVariant fluidVariant, List<Component> tooltip, TooltipFlag tooltipFlag) {
        handler.appendTooltip(FluidResource.of(fluidVariant.getFluid(), fluidVariant.getComponentsPatch()), tooltip, tooltipFlag);
    }

    @Override
    public int getColor(FluidVariant fluidVariant, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos) {
        return handler.getColor(FluidResource.of(fluidVariant.getFluid(), fluidVariant.getComponentsPatch()), level, pos);
    }
}
