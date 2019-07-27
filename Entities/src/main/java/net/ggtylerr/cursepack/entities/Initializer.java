package net.ggtylerr.cursepack.entities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.ggtylerr.cursepack.entities.renderers.CreepigRenderer;
import net.ggtylerr.cursepack.entities.renderers.PigperRenderer;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * [CursePack] Entities
 *
 * Adds several mobs (Like Creepig, Pigper, and more)
 *
 * (At least, more coming soon :P)
 *
 * ~~~developed by ggtylerr~~~
 */

public class Initializer implements ModInitializer {
    // Entities
    private static final EntityType<Creepig> CREEPIG =
        Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier("cursepack-entities","creepig"),
                FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, Creepig::new).size(EntityDimensions.fixed(1,1)).build()
        );
    private static final EntityType<Pigper> PIGPER =
        Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier("cursepack-entities","pigper"),
                FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, Pigper::new).size(EntityDimensions.fixed(1,1)).build()
        );
    // Spawn Eggs
    private static final Item CREEPIG_SPAWN_EGG = new SpawnEggItem(CREEPIG,15771042,894731,new Item.Settings());
    private static final Item PIGPER_SPAWN_EGG = new SpawnEggItem(PIGPER,894731,14377823,new Item.Settings());
    // Spawn Egg Group
    private static final ItemGroup GROUP = FabricItemGroupBuilder.create(
        new Identifier("cursepack-entities","group"))
        .icon(() -> new ItemStack(CREEPIG_SPAWN_EGG))
        .appendItems(stacks -> {
            stacks.add(new ItemStack(CREEPIG_SPAWN_EGG));
            stacks.add(new ItemStack(PIGPER_SPAWN_EGG));
        })
        .build();
    @Override
    public void onInitialize() {
        // Renderers
        EntityRendererRegistry.INSTANCE.register(Creepig.class, (entityRenderDispatcher, context) -> new CreepigRenderer(entityRenderDispatcher));
        EntityRendererRegistry.INSTANCE.register(Pigper.class, (entityRenderDispatcher, context) -> new PigperRenderer(entityRenderDispatcher));
        // Items
        Registry.register(Registry.ITEM, new Identifier("cursepack-entities","creepig_spawn_egg"), CREEPIG_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier("cursepack-entities","pigper_spawn_egg"), PIGPER_SPAWN_EGG);
    }
}
