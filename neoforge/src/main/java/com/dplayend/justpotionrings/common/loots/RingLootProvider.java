package com.dplayend.justpotionrings.common.loots;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.registry.RegistryDataComponents;
import com.dplayend.justpotionrings.registry.RegistryItems;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;


public class RingLootProvider extends GlobalLootModifierProvider {
    public RingLootProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, JustPotionRings.MOD_ID);
    }

    @Override
    protected void start() {
        createRingLootChance("ring_of_health_boost_from_end_city_treasure", "chests/end_city_treasure", MobEffects.HEALTH_BOOST.value(), 0.2F);
        createRingLootChance("ring_of_night_vision_from_desert_pyramid", "chests/desert_pyramid", MobEffects.NIGHT_VISION.value(), 0.2F);
        createRingLootChance("ring_of_luck_from_woodland_mansion", "chests/woodland_mansion", MobEffects.LUCK.value(), 1.0F);
    }

    private void createRingLootChance(String modifier, String location, MobEffect effect, float randomChance) {
        add(modifier,new CreateLoot(new LootItemCondition[] { new LootTableIdCondition.Builder(ResourceLocation.parse(location)).build() }, BuiltInRegistries.MOB_EFFECT.getKey(effect).toString(), randomChance));
    }

    public static class CreateLoot extends LootModifier {
        public static final Supplier<MapCodec<CreateLoot>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(instance -> codecStart(instance).and(Codec.STRING.fieldOf("effect").forGetter(o -> o.effect)).and(Codec.FLOAT.fieldOf("randomChance").forGetter(f -> f.randomChance)).apply(instance, CreateLoot::new)));
        private final String effect;
        private final float randomChance;

        public CreateLoot(LootItemCondition[] conditionsIn, String effect, float randomChance) {
            super(conditionsIn);
            this.effect = effect;
            this.randomChance = randomChance;
        }

        @Override
        protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            for (LootItemCondition condition : this.conditions) if (!condition.test(context)) return generatedLoot;
            ItemStack stack = new ItemStack(RegistryItems.RING.get());
            stack.set(RegistryDataComponents.RING_EFFECT.get(), this.effect);
            if ((int) Math.floor(Math.random() * (1.0f / this.randomChance)) == 0) {
                generatedLoot.add(stack);
            }
            return generatedLoot;
        }


        @Override
        public MapCodec<? extends IGlobalLootModifier> codec() {
            return CODEC.get();
        }
    }
}
