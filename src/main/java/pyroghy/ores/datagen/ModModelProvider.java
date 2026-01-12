package pyroghy.ores.datagen;

import pyroghy.ores.block.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENDER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_ENDER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MEAT_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_MEAT_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BEEF_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_BEEF_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_NETHERITE_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) { }
}
