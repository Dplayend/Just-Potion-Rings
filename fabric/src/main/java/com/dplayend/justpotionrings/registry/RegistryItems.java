package com.dplayend.justpotionrings.registry;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.common.item.Ring;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RegistryItems {
    public static Item REGISTER(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(JustPotionRings.MOD_ID, name), item);
    }

    public static Item POTION_RING = REGISTER("potion_ring", new Item(new Item.Settings().maxCount(1)));
    public static Item RING = REGISTER("ring", new Ring());

    public static void load() {}
}
