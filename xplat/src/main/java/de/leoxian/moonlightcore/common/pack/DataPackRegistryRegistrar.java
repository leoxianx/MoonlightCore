package de.leoxian.moonlightcore.common.pack;

import com.mojang.serialization.Codec;
import de.leoxian.moonlightcore.common.platform.XplatAbstraction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public interface DataPackRegistryRegistrar {
    static void init(String namespace, Consumer<DataPackRegistryRegistrar> initializer) {
        XplatAbstraction.INSTANCE.datapackRegistries(namespace, initializer);
    }

    <T> void register(ResourceKey<Registry<T>> registryKey, Codec<T> codec, @Nullable Codec<T> networkCodec);

    default <T> void register(ResourceKey<Registry<T>> registryKey, Codec<T> codec) {
        register(registryKey, codec, null);
    }
}
