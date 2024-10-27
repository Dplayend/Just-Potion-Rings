package com.dplayend.justpotionrings.handler;

import com.dplayend.justpotionrings.common.items.Ring;
import com.dplayend.justpotionrings.registry.RegistryDataComponents;
import com.dplayend.justpotionrings.registry.RegistryItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HandlerRing {
    public static ItemStack createRing(MobEffect effect) {
        ItemStack stack = new ItemStack(RegistryItems.RING.get());
        if (effect != null) {
            ResourceLocation location = BuiltInRegistries.MOB_EFFECT.getKey(effect);
            stack.set(RegistryDataComponents.RING_EFFECT.get(), (location.getNamespace() + ":" + location.getPath()));
        }
        return stack;
    }

    public static Holder<MobEffect> getEffect(ItemStack itemStack) {
        if (itemStack.has(RegistryDataComponents.RING_EFFECT.get())) {
            String value = itemStack.get(RegistryDataComponents.RING_EFFECT.get());
            if (value != null && BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(value)).isPresent()) {
                return BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(value)).get();
            }
        }
        return null;
    }

    public static Item registryItem() {
        if (HandlerCurios.isModLoaded()) {
            return HandlerCurios.getRingItem();
        }
        return new Ring();
    }
}
