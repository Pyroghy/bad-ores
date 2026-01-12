package pyroghy.ores.datagen;

import java.util.concurrent.CompletableFuture;

import pyroghy.ores.block.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addEnderPearlDrop(ModBlocks.ENDER_ORE);
        addEnderPearlDrop(ModBlocks.DEEPSLATE_ENDER_ORE);
        
        addDrop(ModBlocks.MEAT_ORE);
        addDrop(ModBlocks.DEEPSLATE_MEAT_ORE);
        
        addBeefDrop(ModBlocks.BEEF_ORE);
        addBeefDrop(ModBlocks.DEEPSLATE_BEEF_ORE);
        
        addDrop(ModBlocks.NETHER_NETHERITE_ORE);
    }

    private void addEnderPearlDrop(Block block) {
        LootTable.Builder table = LootTable.builder().pool(LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1))
            .conditionally(SurvivesExplosionLootCondition.builder())
            .with(weightedCountEntry(Items.ENDER_PEARL, 1, 30))
            .with(weightedCountEntry(Items.ENDER_PEARL, 2, 20))
            .with(weightedCountEntry(Items.ENDER_PEARL, 3, 15))
            .with(weightedCountEntry(Items.ENDER_PEARL, 4, 12))
            .with(weightedCountEntry(Items.ENDER_PEARL, 5, 10))
            .with(weightedCountEntry(Items.ENDER_PEARL, 6, 7))
            .with(weightedCountEntry(Items.ENDER_PEARL, 7, 4))
            .with(weightedCountEntry(Items.ENDER_PEARL, 8, 2))
        );

        addDrop(block, table);
    }

    private void addBeefDrop(Block block) {
        LootTable.Builder table = LootTable.builder().pool(LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1))
            .conditionally(SurvivesExplosionLootCondition.builder())
            .with(weightedCountEntry(Items.BEEF, 1, 40))
            .with(weightedCountEntry(Items.BEEF, 2, 30))
            .with(weightedCountEntry(Items.BEEF, 3, 20))
            .with(weightedCountEntry(Items.BEEF, 4, 10))
        );

        addDrop(block, table);
    }

    private ItemEntry.Builder<?> weightedCountEntry(Item item, int count, int weight) {
        return ItemEntry.builder(item)
            .weight(weight)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)));
    }
}
