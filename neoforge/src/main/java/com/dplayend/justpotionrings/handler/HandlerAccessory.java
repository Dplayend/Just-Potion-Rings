package com.dplayend.justpotionrings.handler;

import com.dplayend.justpotionrings.registry.RegistryItems;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

import java.util.concurrent.atomic.AtomicInteger;

public class HandlerAccessory {
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("accessories");
    }

    public static void init() {
        if (isModLoaded()) {
            try {
                AccessoriesAPI.registerAccessory(RegistryItems.RING.value(), AccessoryRing.class.newInstance());
            } catch (NoClassDefFoundError | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class AccessoryRing implements Accessory {
        public Holder<MobEffect> getMobEffect(ItemStack itemStack) {
            return HandlerRing.getEffect(itemStack);
        }

        @Override
        public void tick(ItemStack itemStack, SlotReference reference) {
            if (getMobEffect(itemStack) != null) {
                if (reference.entity() instanceof Player player) {
                    if (reference.slotContainer() != null) {
                        AtomicInteger amount = new AtomicInteger(-1);
                        reference.slotContainer().getAccessories().forEach(pair -> {
                            if (getMobEffect(itemStack).equals(getMobEffect(pair.getSecond()))) amount.getAndIncrement();
                        });
                        MobEffectInstance instance = new MobEffectInstance(getMobEffect(itemStack), -1, amount.get(), false, false, false);
                        player.addEffect(instance);
                    }
                }
            }
        }

        @Override
        public void onUnequip(ItemStack stack, SlotReference reference) {
            if (getMobEffect(stack) != null) {
                if (reference.entity().hasEffect(getMobEffect(stack))) {
                    reference.entity().removeEffect(getMobEffect(stack));
                }
            }
        }

        @Override
        public boolean canEquipFromUse(ItemStack stack) {
            return true;
        }
    }
}
