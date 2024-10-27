package com.dplayend.justpotionrings.handler;

import com.dplayend.justpotionrings.JustPotionRings;
import com.dplayend.justpotionrings.registry.RegistryDataComponents;
import com.dplayend.justpotionrings.registry.RegistryItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

@JeiPlugin
public class HandlerJei implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(JustPotionRings.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(RegistryItems.RING.get(), (stack, uidContext) -> Objects.requireNonNull(stack.get(RegistryDataComponents.RING_EFFECT.get())));
    }
}
