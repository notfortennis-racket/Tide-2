//? if neoforge {
/*package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.compat.starcatcher.TideStarcatcherMinigameScreen;
import com.wdiscute.starcatcher.registry.FishProperties;
import com.wdiscute.starcatcher.registry.minigamemodifiers.AbstractMinigameModifier;
import com.wdiscute.starcatcher.registry.minigamemodifiers.SCMinigameModifiers;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record StarcatcherStartMinigameMsg(FishProperties properties, ItemStack rod, List<ResourceLocation> modifiers) implements TidePacketPayload {
    public static final ResourceLocation ID = Tide.resource("start_starcatcher_minigame");
    @Override public ResourceLocation id() { return ID; }

    public StarcatcherStartMinigameMsg(RegistryFriendlyByteBuf buf) {
        this(
            ByteBufCodecs.fromCodec(FishProperties.CODEC).decode(buf),
            ItemStack.STREAM_CODEC.decode(buf),
            ByteBufCodecs.fromCodec(ResourceLocation.CODEC.listOf()).decode(buf)
        );
    }

    public static void encode(StarcatcherStartMinigameMsg message, RegistryFriendlyByteBuf buf) {
        ByteBufCodecs.fromCodec(FishProperties.CODEC).encode(buf, message.properties);
        ItemStack.STREAM_CODEC.encode(buf, message.rod);
        ByteBufCodecs.fromCodec(ResourceLocation.CODEC.listOf()).encode(buf, message.modifiers);
    }

    public static void handle(StarcatcherStartMinigameMsg message, Player player) {
        TideStarcatcherMinigameScreen screen = new TideStarcatcherMinigameScreen(message.properties, message.rod);
        message.modifiers.forEach(key -> screen.addModifier(
                SCMinigameModifiers.getMinigameModifierSupplier(player.level(), key).get()));
        Minecraft.getInstance().setScreen(screen);
    }
}
*///?} else if forge {

//?}