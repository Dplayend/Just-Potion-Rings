package com.dplayend.justpotionrings.registry;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.handler.HandlerRing;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RegistryItemGroup {
    public static ItemGroup REGISTER(String name, ItemGroup itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, Identifier.of(JustPotionRings.MOD_ID, name), itemGroup);
    }

    public static ItemGroup RING_TAB = REGISTER("potion_rings", FabricItemGroup.builder()
            .icon(() -> new ItemStack(RegistryItems.RING))
            .displayName(Text.translatable("itemGroup.potion_rings"))
            .entries((displayContext, entries) -> {
                entries.add(RegistryItems.POTION_RING);
                Registries.STATUS_EFFECT.forEach(effect -> entries.add(HandlerRing.createRing(effect)));
            }).build());

    public static void load() {}
}
