package com.dplayend.justpotionrings.common.item;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.handler.HandlerRing;
import dev.emi.trinkets.api.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ColorHelper;

import java.awt.*;

public class Ring extends TrinketItem {
    public Ring() {
        super(new Settings().maxCount(1));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return getMobEffect(stack) != null;
    }

    public RegistryEntry<StatusEffect> getMobEffect(ItemStack itemStack) {
        return HandlerRing.getEffect(itemStack);
    }

    public int getColorEffect(ItemStack itemStack) {
        int result = -1;
        if (getMobEffect(itemStack) != null) {
            Color color = new Color(getMobEffect(itemStack).value().getColor());
            result = ColorHelper.Argb.getArgb(color.getRed(), color.getGreen(), color.getBlue());
        }
        return result;
    }

    @Override
    public Text getName(ItemStack stack) {
        return getMobEffect(stack) != null ? Text.translatable("item." + JustPotionRings.MOD_ID + ".ring").append(Text.literal(" ")).append(getMobEffect(stack).value().getName().getString()).formatted(getMobEffect(stack).value().getCategory().getFormatting()) : Text.translatable("item." + JustPotionRings.MOD_ID + ".potion_ring");
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);

        if (getMobEffect(stack) != null) {
            TrinketsApi.getTrinketComponent(entity).ifPresent(trinketComponent -> {
                int amount = -1;
                for (Pair<SlotReference, ItemStack> pair : trinketComponent.getAllEquipped()) {
                    if (pair.getLeft() != null) {
                        RegistryEntry<StatusEffect> otherStackEffect = getMobEffect(pair.getRight());
                        if (otherStackEffect != null) {
                            if (getMobEffect(stack).equals(otherStackEffect)) amount++;
                        }
                    }
                }
                StatusEffectInstance instance = new StatusEffectInstance(getMobEffect(stack), -1, amount, false, false, false);
                entity.addStatusEffect(instance);
            });
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (getMobEffect(stack) != null) {
            if (entity.hasStatusEffect(getMobEffect(stack))) {
                entity.removeStatusEffect(getMobEffect(stack));
            }
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return true;
    }
}
