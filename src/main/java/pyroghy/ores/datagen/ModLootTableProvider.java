package pyroghy.ores.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;
import pyroghy.ores.block.ModBlocks;

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
    }

    private void addEnderPearlDrop(net.minecraft.block.Block block) {
        LootTable.Builder table = LootTable.builder().pool(LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1))
            .conditionally(SurvivesExplosionLootCondition.builder())
            .with(weightedCountEntry(1, 30))
            .with(weightedCountEntry(2, 20))
            .with(weightedCountEntry(3, 15))
            .with(weightedCountEntry(4, 12))
            .with(weightedCountEntry(5, 10))
            .with(weightedCountEntry(6, 7))
            .with(weightedCountEntry(7, 4))
            .with(weightedCountEntry(8, 2))
        );

        addDrop(block, table);
    }

    private ItemEntry.Builder<?> weightedCountEntry(int count, int weight) {
        return ItemEntry.builder(Items.ENDER_PEARL)
            .weight(weight)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)));
    }
}
