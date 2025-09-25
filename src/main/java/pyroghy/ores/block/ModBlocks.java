package pyroghy.ores.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import pyroghy.ores.BadOres;
import pyroghy.ores.block.custom.TeleportingBlock;

public class ModBlocks {
    public static final Block ENDER_ORE = registerBlock("ender_ore",
        new TeleportingBlock(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE)));

    public static final Block DEEPSLATE_ENDER_ORE = registerBlock("deepslate_ender_ore",
            new TeleportingBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_DIAMOND_ORE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block, new Item.Settings());
        return Registry.register(Registries.BLOCK, Identifier.of(BadOres.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block, Item.Settings settings) {
        Registry.register(Registries.ITEM, Identifier.of(BadOres.MOD_ID, name), new BlockItem(block, settings));
    }

    public static void registerModBlocks() {
        BadOres.LOGGER.info("Registering Mod Blocks for " + BadOres.MOD_ID);
    }
}
