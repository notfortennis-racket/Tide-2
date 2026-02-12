//? if fabric {
package com.li64.tide.datagen.fabric.providers.fishing;

import com.li64.tide.data.rods.BaitData;
import com.li64.tide.datagen.fabric.providers.SimpleDataOutput;
import com.li64.tide.datagen.fabric.providers.SimpleDataProvider;
import com.li64.tide.registries.TideItems;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TideBaitDataProvider extends SimpleDataProvider<BaitData> {
    public TideBaitDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super("bait", output, registries);
    }

    @Override
    protected Codec<BaitData> dataCodec() {
        return BaitData.CODEC;
    }

    @Override
    protected void generate(SimpleDataOutput<BaitData> output) {
        output.accept("bait", new BaitData(TideItems.BAIT, 2, 0));
        output.accept("lucky_bait", new BaitData(TideItems.LUCKY_BAIT, 0, 2));
        output.accept("magnetic_bait", new BaitData(TideItems.MAGNETIC_BAIT, 0, 0));
        output.accept("incandescent_bait", new BaitData(TideItems.INCANDESCENT_BAIT, 1, 0));
        output.accept("abyss_bait", new BaitData(TideItems.ABYSS_BAIT, 2, 0));
        output.accept("shiny_bait", new BaitData(TideItems.SHINY_BAIT, 0, 0));

        output.accept("fishofthieves/earthworms", new BaitData("fishofthieves:earthworms", 1, 0));
        output.accept("fishofthieves/grubs", new BaitData("fishofthieves:grubs", 1, 0));
        output.accept("fishofthieves/leeches", new BaitData("fishofthieves:leeches", 1, 0));

        output.accept("naturalist/butterfly", new BaitData("naturalist:butterfly", 1, 0));
        output.accept("naturalist/caterpillar", new BaitData("naturalist:caterpillar", 1, 0));
    }

    @Override
    public @NotNull String getName() {
        return "Bait Data";
    }
}
//?}