//? if neoforge {
/*package com.li64.tide.compat.starcatcher;

import com.li64.tide.Tide;
import com.li64.tide.data.rods.CustomRodManager;
import com.li64.tide.network.messages.StarcatcherStartMinigameMsg;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import com.wdiscute.starcatcher.Starcatcher;
import com.wdiscute.starcatcher.io.SCDataComponents;
import com.wdiscute.starcatcher.io.SingleStackContainer;
import com.wdiscute.starcatcher.registry.FishProperties;
import com.wdiscute.starcatcher.registry.minigamemodifiers.SCMinigameModifiers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class StarcatcherCompat {
    public static boolean start(ServerPlayer player, HookAccessor hook, ItemStack rod, List<ItemStack> hookedItems) {
        if (hookedItems.isEmpty()) return false;

        // assign data components to rod item
        ItemStack fakeRod = rod.copy();
        fakeRod.set(SCDataComponents.BOBBER, SingleStackContainer.from(new ItemStack(Items.AIR)));
        fakeRod.set(SCDataComponents.HOOK, SingleStackContainer.from(new ItemStack(Items.AIR)));
        fakeRod.set(SCDataComponents.BAIT, SingleStackContainer.from(new ItemStack(Items.AIR)));

        // try get minigame properties for current fish
        Optional<FishProperties> optional = player.level().registryAccess()
                .registryOrThrow(Starcatcher.FISH_REGISTRY_KEY)
                .getOptional(BuiltInRegistries.ITEM.getKey(hookedItems.get(0).getItem()));

        // unwrap it or use a fallback if it doesn't exist
        FishProperties properties = optional.orElseGet(() -> FishProperties.builder()
                .withFish(hookedItems.get(0).getItemHolder()).build());

        // start the minigame
        Tide.NETWORK.sendToPlayer(new StarcatcherStartMinigameMsg(properties, fakeRod, getMinigameModifiers(rod)), player);
        return true;
    }

    private static List<ResourceLocation> getMinigameModifiers(ItemStack rod) {
        ItemStack line = CustomRodManager.getLine(rod);
        HashSet<ResourceLocation> modifiers = new HashSet<>();

        if (line.is(TideItems.IRON_LINE)) {
            modifiers.add(SCMinigameModifiers.BIGGER_GREEN_SWEET_SPOTS.getId());
        }
        if (line.is(TideItems.COPPER_LINE)) {
            modifiers.add(SCMinigameModifiers.SLOWER_MOVING_SWEET_SPOTS.getId());
            modifiers.add(SCMinigameModifiers.SLOWER_VANISHING.getId());
            modifiers.add(SCMinigameModifiers.SLIGHTLY_SLOWER_POINTER_SPEED.getId());
        }
        if (line.is(TideItems.GOLDEN_LINE)) {
            modifiers.add(SCMinigameModifiers.SLOWER_VANISHING.getId());
            modifiers.add(SCMinigameModifiers.SLOWER_MOVING_SWEET_SPOTS.getId());
            modifiers.add(SCMinigameModifiers.STOP_DECAY_ON_HIT.getId());
        }
        if (line.is(TideItems.DIAMOND_LINE)) {
            modifiers.add(SCMinigameModifiers.SLOWER_VANISHING.getId());
            modifiers.add(SCMinigameModifiers.BIGGER_GREEN_SWEET_SPOTS.getId());
            modifiers.add(SCMinigameModifiers.ADD_AQUA_SWEET_SPOT.getId());
            modifiers.add(SCMinigameModifiers.STOP_DECAY_ON_HIT.getId());
        }

        return modifiers.stream().toList();
    }

    public static void openMinigameScreen(StarcatcherStartMinigameMsg message, Player player) {
        TideStarcatcherMinigameScreen screen = new TideStarcatcherMinigameScreen(message.properties(), message.rod());
        message.modifiers().forEach(key -> screen.addModifier(
                SCMinigameModifiers.getMinigameModifierSupplier(player.level(), key).get()));
        Minecraft.getInstance().setScreen(screen);
    }
}
*///?} else if forge {

//?}