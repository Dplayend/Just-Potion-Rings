package com.dplayend.justpotionrings.registry;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.common.loots.RingLootProvider;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class RegistryLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, JustPotionRings.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<? extends IGlobalLootModifier>> RING = REGISTER.register("ring_loot", RingLootProvider.CreateLoot.CODEC);

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
