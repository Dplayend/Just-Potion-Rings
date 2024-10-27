package com.dplayend.justpotionrings;

import com.dplayend.justpotionrings.common.loot.RingLootProvider;
import com.dplayend.justpotionrings.handler.HandlerRing;
import com.dplayend.justpotionrings.registry.RegistryDataComponents;
import com.dplayend.justpotionrings.registry.RegistryItemGroup;
import com.dplayend.justpotionrings.registry.RegistryItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;

public class JustPotionRings implements ModInitializer {
	public static final String MOD_ID = "justpotionrings";

	@Override
	public void onInitialize() {
		RegistryDataComponents.load();
        RegistryItems.load();
		RegistryItemGroup.load();

		RingLootProvider.modifyLootTables();
		villagerEvent();
	}

	public void villagerEvent() {
		TradeOfferHelper.registerWanderingTraderOffers(2, factories -> factories.add((entity, random) -> new TradeOffer(new TradedItem(Items.EMERALD, 32), HandlerRing.createRing(StatusEffects.HERO_OF_THE_VILLAGE.value()), 1, 0, 1.0F)));
	}
}