package com.dplayend.justpotionrings;

import com.dplayend.justpotionrings.common.loots.RingLootProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = JustPotionRings.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class JustPotionRingsDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(event.includeServer(), new RingLootProvider(packOutput, event.getLookupProvider()));
    }
}
