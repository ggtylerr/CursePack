package net.ggtylerr.cursepack.entities.renderers;

import net.ggtylerr.cursepack.entities.Creepig;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;

public class CreepigRenderer extends MobEntityRenderer<Creepig, PigEntityModel<Creepig>> {
    public CreepigRenderer(EntityRenderDispatcher entityRenderDispatcher_1) {
        super(entityRenderDispatcher_1, new PigEntityModel<>(), 1);
        this.addFeature(new CreepigChargeRenderer(this));
    }
    @Override
    protected Identifier getTexture(Creepig creepig){
        return new Identifier("cursepack-entities:textures/entity/creepig/creepig.png");
    }
}
