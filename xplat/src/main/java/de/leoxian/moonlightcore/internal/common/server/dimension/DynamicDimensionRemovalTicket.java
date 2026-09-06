package de.leoxian.moonlightcore.internal.common.server.dimension;

import de.leoxian.moonlightcore.common.server.dimension.DimensionPlayerRemover;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record DynamicDimensionRemovalTicket(ResourceKey<Level> key, DimensionPlayerRemover playerRemover, boolean removeFiles) {
}
