package com.dplayend.justpotionrings;

import com.dplayend.justpotionrings.handler.HandlerAccessory;
import com.dplayend.justpotionrings.handler.HandlerRing;
import com.dplayend.justpotionrings.registry.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@Mod(JustPotionRings.MOD_ID)
public class JustPotionRings {
    public static final String MOD_ID = "justpotionrings";

    public JustPotionRings(IEventBus eventBus) {
        RegistryDataComponents.load(eventBus);
        RegistryItems.load(eventBus);
        RegistryCreativeTabs.load(eventBus);
        RegistryLootModifiers.load(eventBus);

        eventBus.addListener(this::onInitialize);

        NeoForge.EVENT_BUS.register(this);
    }

    public void onInitialize(FMLCommonSetupEvent event){
        HandlerAccessory.init();
    }

    @SubscribeEvent
    public void villagerEvent(WandererTradesEvent event) {
        event.getGenericTrades().add((trade, randomSource) -> new MerchantOffer(new ItemCost(Items.EMERALD, 32), HandlerRing.createRing(MobEffects.HERO_OF_THE_VILLAGE.value()), 1, 0, 1.0F));
    }
}
