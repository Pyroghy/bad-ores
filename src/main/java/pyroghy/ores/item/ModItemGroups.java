package pyroghy.ores.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pyroghy.ores.BadOres;
import pyroghy.ores.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup BAD_ORES_GROUP = Registry.register(Registries.ITEM_GROUP,
        Identifier.of(BadOres.MOD_ID, "ores"),
        FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.ENDER_ORE))
            .displayName(Text.translatable("itemgroup.ores.bad_ores"))
            .entries((displayContext, entries) -> {
                entries.add(ModBlocks.ENDER_ORE);
                entries.add(ModBlocks.DEEPSLATE_ENDER_ORE);
                entries.add(ModBlocks.MEAT_ORE);
                entries.add(ModBlocks.DEEPSLATE_MEAT_ORE);
                entries.add(ModBlocks.BEEF_ORE);
                entries.add(ModBlocks.DEEPSLATE_BEEF_ORE);
                entries.add(ModBlocks.NETHER_NETHERITE_ORE);
            }).build());

    public static void registerItemGroups() {
        BadOres.LOGGER.info("Registering Item Groups for " + BadOres.MOD_ID);
    }
}
