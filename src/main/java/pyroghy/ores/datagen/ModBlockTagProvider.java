package pyroghy.ores.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;
import pyroghy.ores.block.ModBlocks;

public class ModBlockTagProvider extends BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .add(ModBlocks.ENDER_ORE)
            .add(ModBlocks.DEEPSLATE_ENDER_ORE)
            .add(ModBlocks.MEAT_ORE)
            .add(ModBlocks.DEEPSLATE_MEAT_ORE)
            .add(ModBlocks.BEEF_ORE)
            .add(ModBlocks.DEEPSLATE_BEEF_ORE)
            .add(ModBlocks.NETHER_NETHERITE_ORE);
                
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
            .add(ModBlocks.MEAT_ORE)
            .add(ModBlocks.DEEPSLATE_MEAT_ORE)
            .add(ModBlocks.BEEF_ORE)
            .add(ModBlocks.DEEPSLATE_BEEF_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
            .add(ModBlocks.ENDER_ORE)
            .add(ModBlocks.DEEPSLATE_ENDER_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
            .add(ModBlocks.NETHER_NETHERITE_ORE);
    }
}
