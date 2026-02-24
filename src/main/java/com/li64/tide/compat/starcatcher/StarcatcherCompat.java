//? if neoforge || forge {
/*package com.li64.tide.compat.starcatcher;

import com.li64.tide.Tide;
import com.li64.tide.network.messages.StarcatcherStartMinigameMsg;
import com.li64.tide.registries.TideFish;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import com.wdiscute.starcatcher.io.ModDataComponents;
import com.wdiscute.starcatcher.io.SingleStackContainer;
import com.wdiscute.starcatcher.storage.FishProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class StarcatcherCompat {
    public static boolean start(ServerPlayer player, HookAccessor hook, ItemStack rod, List<ItemStack> hookedItems) {
        if (hookedItems.isEmpty()) return false;
        FishProperties properties = FishProperties.builder()
                .withFish(hookedItems.get(0).getItemHolder()).build();

        ItemStack fakeRod = rod.copy();
        fakeRod.set(ModDataComponents.BOBBER, new SingleStackContainer(new ItemStack(Items.AIR)));
        fakeRod.set(ModDataComponents.HOOK, new SingleStackContainer(new ItemStack(Items.AIR)));
        fakeRod.set(ModDataComponents.BAIT, new SingleStackContainer(new ItemStack(Items.AIR)));

        Tide.NETWORK.sendToPlayer(new StarcatcherStartMinigameMsg(properties, fakeRod), player);
        return true;
    }
}
*///?}