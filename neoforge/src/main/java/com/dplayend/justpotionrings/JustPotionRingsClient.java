package com.dplayend.justpotionrings;

import com.dplayend.justpotionrings.common.items.Ring;
import com.dplayend.justpotionrings.registry.RegistryItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;


@EventBusSubscriber(modid = JustPotionRings.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class JustPotionRingsClient {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((itemStack, i) -> {
            if (i == 1 && itemStack.getItem() instanceof Ring ring) return ring.getColorEffect(itemStack);
            return -1;
        }, RegistryItems.RING.get());
    }
}
