package net.ggtylerr.cursepack.entities.renderers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.ggtylerr.cursepack.entities.Creepig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

public class CreepigChargeRenderer extends FeatureRenderer<Creepig, PigEntityModel<Creepig>> {
    private static final Identifier SKIN = new Identifier("cursepack-entities:textures/entity/creepig/creepig_armor.png");
    private final PigEntityModel<Creepig> model = new PigEntityModel(2.0F);

    public CreepigChargeRenderer(FeatureRendererContext<Creepig, PigEntityModel<Creepig>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(Creepig creepig, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, float float_7) {
        if (creepig.isCharged()) {
            boolean boolean_1 = creepig.isInvisible();
            GlStateManager.depthMask(!boolean_1);
            this.bindTexture(SKIN);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float float_8 = (float)creepig.age + float_3;
            GlStateManager.translatef(float_8 * 0.01F, float_8 * 0.01F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float float_9 = 0.5F;
            GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            ((PigEntityModel)this.getModel()).copyStateTo(this.model);
            GameRenderer gameRenderer_1 = MinecraftClient.getInstance().gameRenderer;
            gameRenderer_1.setFogBlack(true);
            this.model.render(creepig, float_1, float_2, float_4, float_5, float_6, float_7);
            gameRenderer_1.setFogBlack(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
        }
    }

    @Override
    public boolean hasHurtOverlay() {
        return false;
    }
}
