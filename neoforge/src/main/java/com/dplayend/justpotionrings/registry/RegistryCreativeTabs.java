package com.dplayend.justpotionrings.registry;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.handler.HandlerRing;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class RegistryCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JustPotionRings.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RING_TAB = REGISTER.register("potion_rings", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.potion_rings"))
            .icon(() -> RegistryItems.RING.get().getDefaultInstance())
            .displayItems((params, output) -> {
                output.accept(RegistryItems.POTION_RING.get());
                BuiltInRegistries.MOB_EFFECT.forEach(mobEffect -> output.accept(HandlerRing.createRing(mobEffect)));
            })
            .build()
    );

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
