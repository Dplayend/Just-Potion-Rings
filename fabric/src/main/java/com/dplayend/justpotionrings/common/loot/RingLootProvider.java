package com.dplayend.justpotionrings.common.loot;

import com.dplayend.justpotionrings.registry.RegistryDataComponents;
import com.dplayend.justpotionrings.registry.RegistryItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.entry.LootPoolEntryTypes;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class RingLootProvider {
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((registryKey, builder, lootTableSource, wrapperLookup) -> {
            createRingLootChance(builder, registryKey.getValue(), "chests/end_city_treasure", StatusEffects.HEALTH_BOOST.value(), 0.2f);
            createRingLootChance(builder, registryKey.getValue(), "chests/desert_pyramid", StatusEffects.NIGHT_VISION.value(), 0.2f);
            createRingLootChance(builder, registryKey.getValue(), "chests/woodland_mansion", StatusEffects.LUCK.value(), 1.0f);
        });
    }

    public static void createRingLootChance(LootTable.Builder builder, Identifier modifier, String location, StatusEffect effect, float randomChance) {
        if (Identifier.of(location).equals(modifier)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemStackEntry.builder(Registries.STATUS_EFFECT.getId(effect).toString(), randomChance))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
            builder.pool(poolBuilder);
        }
    }

    public static class ItemStackEntry extends LeafEntry {
        private final String effect;
        private final float randomChance;

        ItemStackEntry(String effect, float randomChance, int weight, int quality, List<LootCondition> conditions, List<LootFunction> functions) {
            super(weight, quality, conditions, functions);
            this.effect = effect;
            this.randomChance = randomChance;
        }

        public LootPoolEntryType getType() {
            return LootPoolEntryTypes.ITEM;
        }

        public void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
            ItemStack stack = new ItemStack(RegistryItems.RING);
            stack.set(RegistryDataComponents.RING_EFFECT, this.effect);
            if ((int) Math.floor(Math.random() * (1.0f / this.randomChance)) == 0) {
                lootConsumer.accept(stack);
            }
        }

        public static LeafEntry.Builder<?> builder(String effect, float randomChance) {
            return builder((weight, quality, conditions, functions) -> new ItemStackEntry(effect, randomChance, weight, quality, conditions, functions));
        }
    }
}
