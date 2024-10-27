package com.dplayend.justpotionrings.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class RegistryDataComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTER = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "mob");

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> RING_EFFECT = register("effect", stringBuilder -> stringBuilder.persistent(Codec.STRING));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return REGISTER.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
