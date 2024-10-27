package com.dplayend.justpotionrings.handler;

import com.dplayend.justpotionrings.common.items.Ring;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class HandlerCurios {
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("curios");
    }

    public static Item getRingItem() {
        if (isModLoaded()) {
            try {
                return CuriosAPIContinuationRing.class.newInstance();
            } catch (NoClassDefFoundError | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return new Ring();
    }

    public static class CuriosAPIContinuationRing extends Ring implements ICurioItem {
        public Holder<MobEffect> getMobEffect(ItemStack itemStack) {
            return HandlerRing.getEffect(itemStack);
        }

        @Override
        public void curioTick(SlotContext slotContext, ItemStack itemStack) {
            if (getMobEffect(itemStack) != null) {
                CuriosApi.getCuriosHelper().getCuriosHandler(slotContext.entity()).ifPresent(handler -> {
                    ICurioStacksHandler stacksHandler = handler.getCurios().get("ring");
                    if (stacksHandler != null) {
                        int amount = -1;
                        for (int i = 0; i < stacksHandler.getStacks().getSlots(); i++) {
                            Holder<MobEffect> otherStack = getMobEffect(stacksHandler.getStacks().getStackInSlot(i));
                            if (otherStack != null) {
                                if (getMobEffect(itemStack).equals(otherStack)) amount++;
                            }
                        }
                        MobEffectInstance instance = new MobEffectInstance(getMobEffect(itemStack), -1, amount, false, false, false);
                        slotContext.entity().addEffect(instance);
                    }
                });
            }
        }

        @Override
        public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
            if (getMobEffect(stack) != null) {
                if (slotContext.entity().hasEffect(getMobEffect(stack))) {
                    slotContext.entity().removeEffect(getMobEffect(stack));
                }
            }
        }

        @Override
        public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
            return true;
        }
    }
}
