package net.ggtylerr.cursepack.entities.renderers;

import net.ggtylerr.cursepack.entities.Pigper;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.util.Identifier;

public class PigperRenderer extends MobEntityRenderer<Pigper, CreeperEntityModel<Pigper>> {
    public PigperRenderer(EntityRenderDispatcher entityRenderDispatcher_1) {
        super(entityRenderDispatcher_1, new CreeperEntityModel<>(),1);
    }
    @Override
    protected Identifier getTexture(Pigper pigper){
        return new Identifier("cursepack-entities:textures/entity/pigper/pigper.png");
    }
}
