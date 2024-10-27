package com.dplayend.justpotionrings.common.items;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.handler.HandlerRing;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Ring extends Item {
    public Ring() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack itemStack) {
        return getMobEffect(itemStack) != null;
    }

    public Holder<MobEffect> getMobEffect(ItemStack itemStack) {
        return HandlerRing.getEffect(itemStack);
    }

    public int getColorEffect(ItemStack itemStack) {
        int result = -1;
        if (getMobEffect(itemStack) != null) {
            Color color = new Color(getMobEffect(itemStack).value().getColor());
            result = FastColor.ARGB32.color(color.getRed(), color.getGreen(), color.getBlue());
        }
        return result;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack itemStack) {
        return getMobEffect(itemStack) != null ? Component.translatable("item." + JustPotionRings.MOD_ID + ".ring").append(Component.literal(" ")).append(getMobEffect(itemStack).value().getDisplayName().getString()).withStyle(getMobEffect(itemStack).value().getCategory().getTooltipFormatting()) : Component.translatable("item." + JustPotionRings.MOD_ID + ".potion_ring");
    }
}
