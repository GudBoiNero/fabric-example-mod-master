package com.gudboinero.coolmod.registry;


import com.gudboinero.coolmod.CoolMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ParrotEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderer.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderer.geo.IGeoRenderer;

@Environment(EnvType.CLIENT)
public class ShoulderBeardedDragonFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
    private final BeardedDragonModel model = new BeardedDragonModel();

    public ShoulderBeardedDragonFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T playerEntity, float f, float g, float h, float j, float k, float l) {
        this.renderShoulderDragon(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, true);
        this.renderShoulderDragon(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, false);
    }

    private void renderShoulderDragon(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T player, float limbAngle, float limbDistance, float headYaw, float headPitch, boolean leftShoulder) {
        CompoundTag compoundTag = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
        EntityType.get(compoundTag.getString("id")).filter((entityType) -> {
            return entityType == CoolMod.BEARDED_DRAGON;
        }).ifPresent((entityType) -> {
            matrices.push();
            matrices.translate(leftShoulder ? 0.4000000059604645D : -0.4000000059604645D, player.isInSneakingPose() ? -1.2999999523162842D : -1.5D, 0.0D);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(BeardedDragonRenderer.TEXTURES[compoundTag.getInt("Variant")]));
            this.model.poseOnShoulder(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, limbAngle, limbDistance, headYaw, headPitch, player.age);
            matrices.pop();
        });
    }
}
