package com.dplayend.justpotionrings.registry;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.handler.HandlerRing;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegistryItems {
    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(JustPotionRings.MOD_ID);

    public static final DeferredItem<Item> POTION_RING = REGISTER.register("potion_ring", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> RING = REGISTER.register("ring", HandlerRing::registryItem);

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
