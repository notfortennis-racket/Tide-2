package com.li64.tide.data.fishing;

import com.li64.tide.util.TideUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record ShinyData(Optional<Double> chance, Optional<ResourceLocation> sprite) {
    public static final Codec<ShinyData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.optionalFieldOf("chance").forGetter(ShinyData::chance),
            ResourceLocation.CODEC.optionalFieldOf("sprite").forGetter(ShinyData::sprite)
    ).apply(instance, ShinyData::new));

    public ShinyData() {
        this(Optional.empty(), Optional.empty());
    }

    public ShinyData(ResourceLocation sprite) {
        this(Optional.empty(), Optional.ofNullable(sprite));
    }

    public boolean sample(FishData data) {
        double value = Math.random();
        double chance = chance().orElse(TideUtils.getBaseShinyChance(data.profile().rarity()));
//        Tide.LOG.info("Rolling for shiny with odds {}, rolled {}: {}",
//                chance, value, value <= chance ? "Success" : "Failure");
        return value <= chance;
    }
}
