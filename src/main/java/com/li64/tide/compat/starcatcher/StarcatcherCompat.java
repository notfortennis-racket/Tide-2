//? if neoforge || forge {
/*package com.li64.tide.compat.starcatcher;

import com.li64.tide.registries.TideFish;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import com.wdiscute.starcatcher.io.network.FishingStartedPayload;
import com.wdiscute.starcatcher.storage.FishProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class StarcatcherCompat {
    public static boolean start(ServerPlayer player, HookAccessor hook, ItemStack rod, List<ItemStack> hookedItems) {
        FishProperties properties = FishProperties.builder()
                .withFish(BuiltInRegistries.ITEM.wrapAsHolder(TideFish.CRYSTAL_SHRIMP)).build();
        PacketDistributor.sendToPlayer(player, new FishingStartedPayload(properties, rod));
        return true;
    }
}
*///?}