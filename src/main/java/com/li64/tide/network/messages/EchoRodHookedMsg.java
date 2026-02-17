package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.overlays.SonarOverlay;
import com.li64.tide.data.item.TideItemData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record EchoRodHookedMsg(ItemStack stack, boolean shiny) implements TidePacketPayload {
    public static final ResourceLocation ID = Tide.resource("echo_rod_hooked");
    @Override public ResourceLocation id() { return ID; }

    public EchoRodHookedMsg(FriendlyByteBuf buf) {
        this(
            BuiltInRegistries.ITEM
                .getOptional(ResourceLocation.tryParse(buf.readUtf()))
                .orElseThrow().getDefaultInstance(),
            buf.readBoolean()
        );
    }

    public static void encode(EchoRodHookedMsg message, FriendlyByteBuf buf) {
        buf.writeUtf(BuiltInRegistries.ITEM.getKey(message.stack.getItem()).toString());
        buf.writeBoolean(message.shiny);
    }

    public static void handle(EchoRodHookedMsg message, Player player) {
        ItemStack stack = message.stack;
        if (message.shiny) TideItemData.IS_SHINY.set(stack, true);
        SonarOverlay.show(stack);
    }
}