package com.li64.tide.mixin;

import com.li64.tide.client.TideCoreShaders;
import com.li64.tide.client.TideRenderTypes;
import com.li64.tide.data.fishing.FishData;
import com.li64.tide.data.item.TideItemData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.MatrixUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow private static boolean hasAnimatedTexture(ItemStack stack) { return false; }
    @Shadow public static VertexConsumer getCompassFoilBuffer(MultiBufferSource buffer, RenderType renderType, PoseStack.Pose matrixEntry) { return null; }
    @Shadow public static VertexConsumer getFoilBufferDirect(MultiBufferSource buffer, RenderType renderType, boolean noEntity, boolean withGlint) { return null; }
    @Shadow public abstract void renderModelLists(BakedModel model, ItemStack stack, int combinedLight, int combinedOverlay, PoseStack poseStack, VertexConsumer buffer);

    @SuppressWarnings("deprecation")
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void renderShiny(ItemStack stack, ItemDisplayContext ctx, boolean left,
                             PoseStack poseStack, MultiBufferSource bufferSource,
                             int combinedLight, int combinedOverlay,
                             BakedModel model, CallbackInfo ci) {
        if (stack.isEmpty() || !TideItemData.IS_SHINY.getOrDefault(stack, false)) return;
        FishData data = FishData.get(stack).orElse(null);
        if (data == null) return;

        poseStack.pushPose();

        model.getTransforms().getTransform(ctx).apply(left, poseStack);
        poseStack.translate(-0.5f, -0.5f, -0.5f);

        RenderType renderType;
        VertexConsumer buffer;

        if (data.shinyData().sprite().isPresent()) {
            TextureAtlasSprite baseSprite = model.getParticleIcon();
            TideCoreShaders.CUSTOM_SHINY_ITEM.safeGetUniform("BaseSpriteUV").set(
                    baseSprite.getU0(), baseSprite.getV0(),
                    baseSprite.getU1(), baseSprite.getV1()
            );

            TextureAtlasSprite shinySprite = Minecraft.getInstance()
                    .getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                    .apply(data.shinyData().sprite().get());
            TideCoreShaders.CUSTOM_SHINY_ITEM.safeGetUniform("ShinySpriteUV").set(
                    shinySprite.getU0(), shinySprite.getV0(),
                    shinySprite.getU1(), shinySprite.getV1()
            );

            renderType = TideRenderTypes.customShinyItem();
        }
        else renderType = TideRenderTypes.shinyItem();

        if (hasAnimatedTexture(stack) && stack.hasFoil()) {
            PoseStack.Pose pose = poseStack.last().copy();
            if (ctx == ItemDisplayContext.GUI) MatrixUtil.mulComponentWise(pose.pose(), 0.5f);
            else if (ctx.firstPerson()) MatrixUtil.mulComponentWise(pose.pose(), 0.75f);
            buffer = getCompassFoilBuffer(bufferSource, renderType, pose);
        }
        else buffer = getFoilBufferDirect(bufferSource, renderType, true, stack.hasFoil());

        this.renderModelLists(model, stack, combinedLight, combinedOverlay, poseStack, buffer);

        poseStack.popPose();
        ci.cancel();
    }
}
