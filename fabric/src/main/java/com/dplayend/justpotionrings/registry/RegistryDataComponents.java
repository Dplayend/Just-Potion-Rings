package com.dplayend.justpotionrings.registry;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class RegistryDataComponents {
    public static final ComponentType<String> RING_EFFECT = register("effect", stringBuilder -> stringBuilder.codec(Codec.STRING));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderUnaryOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("mob", name), builderUnaryOperator.apply(ComponentType.builder()).build());
    }

    public static void load() {}
}
