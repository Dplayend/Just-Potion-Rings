package com.dplayend.justpotionrings;

import com.dplayend.justpotionrings.common.item.Ring;
import com.dplayend.justpotionrings.registry.RegistryItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class JustPotionRingsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex == 1 && stack.getItem() instanceof Ring ring) return ring.getColorEffect(stack);
            return -1;
        }, RegistryItems.RING);
    }
}
