package net.ggtylerr.cursepack.separable_doors;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * For CursePack
 *
 * Separable Door Blocks - all doors can now be separated
 *
 * ~~~developed by ggtylerr~~~
 */

public class Initializer implements ModInitializer {
	// Block
	private static final Block OAK = new CustomDoorBlock(Block.Settings.of(Material.WOOD));
	private static final Block SPRUCE = new CustomDoorBlock(Block.Settings.of(Material.WOOD));
	private static final Block BIRCH = new CustomDoorBlock(Block.Settings.of(Material.WOOD));
	private static final Block JUNGLE = new CustomDoorBlock(Block.Settings.of(Material.WOOD));
	private static final Block ACACIA = new CustomDoorBlock(Block.Settings.of(Material.WOOD));
	private static final Block DARK_OAK = new CustomDoorBlock(Block.Settings.of(Material.WOOD));
	private static final Block IRON = new CustomDoorBlock(Block.Settings.of(Material.METAL));
	// Item group
	public static final ItemGroup group = FabricItemGroupBuilder.create(
			new Identifier("cursepack-separable-doors","group"))
			.icon(() -> new ItemStack(OAK))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(OAK));
				stacks.add(new ItemStack(SPRUCE));
				stacks.add(new ItemStack(BIRCH));
				stacks.add(new ItemStack(JUNGLE));
				stacks.add(new ItemStack(ACACIA));
				stacks.add(new ItemStack(DARK_OAK));
				stacks.add(new ItemStack(IRON));
			})
	.build();
	@Override
	public void onInitialize() {
		// Blocks
		Registry.register(Registry.BLOCK, new Identifier("cursepack-separable-doors","oak"), OAK);
		Registry.register(Registry.BLOCK, new Identifier("cursepack-separable-doors","spruce"), SPRUCE);
		Registry.register(Registry.BLOCK, new Identifier("cursepack-separable-doors","birch"), BIRCH);
		Registry.register(Registry.BLOCK, new Identifier("cursepack-separable-doors","jungle"), JUNGLE);
		Registry.register(Registry.BLOCK, new Identifier("cursepack-separable-doors","acacia"), ACACIA);
		Registry.register(Registry.BLOCK, new Identifier("cursepack-separable-doors","dark_oak"), DARK_OAK);
		Registry.register(Registry.BLOCK, new Identifier("cursepack-separable-doors","iron"), IRON);
		// Block items
		Registry.register(Registry.ITEM, new Identifier("cursepack-separable-doors","oak"), new BlockItem(OAK, new Item.Settings()));
		Registry.register(Registry.ITEM, new Identifier("cursepack-separable-doors","spruce"), new BlockItem(SPRUCE, new Item.Settings()));
		Registry.register(Registry.ITEM, new Identifier("cursepack-separable-doors","birch"), new BlockItem(BIRCH, new Item.Settings()));
		Registry.register(Registry.ITEM, new Identifier("cursepack-separable-doors","jungle"), new BlockItem(JUNGLE, new Item.Settings()));
		Registry.register(Registry.ITEM, new Identifier("cursepack-separable-doors","acacia"), new BlockItem(ACACIA, new Item.Settings()));
		Registry.register(Registry.ITEM, new Identifier("cursepack-separable-doors","dark_oak"), new BlockItem(DARK_OAK, new Item.Settings()));
		Registry.register(Registry.ITEM, new Identifier("cursepack-separable-doors","iron"), new BlockItem(IRON, new Item.Settings()));
	}
}
