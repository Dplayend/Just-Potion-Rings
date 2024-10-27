package com.dplayend.justpotionrings.handler;

import com.dplayend.justpotionrings.registry.RegistryDataComponents;
import com.dplayend.justpotionrings.registry.RegistryItems;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class HandlerRing {
    public static ItemStack createRing(StatusEffect effect) {
        ItemStack stack = new ItemStack(RegistryItems.RING);
        if (effect != null) {
            Identifier location = Registries.STATUS_EFFECT.getId(effect);
            stack.set(RegistryDataComponents.RING_EFFECT, (location.getNamespace() + ":" + location.getPath()));
        }
        return stack;
    }

    public static RegistryEntry<StatusEffect> getEffect(ItemStack itemStack) {
        if (itemStack.contains(RegistryDataComponents.RING_EFFECT)) {
            String value = itemStack.get(RegistryDataComponents.RING_EFFECT);
            if (value != null && Registries.STATUS_EFFECT.getEntry(Identifier.of(value)).isPresent()) {
                return Registries.STATUS_EFFECT.getEntry(Identifier.of(value)).get();
            }
        }
        return null;
    }
}
