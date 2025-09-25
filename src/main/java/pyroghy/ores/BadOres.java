package pyroghy.ores;

import pyroghy.ores.block.ModBlocks;
import pyroghy.ores.item.ModItemGroups;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class BadOres implements ModInitializer {
	public static final String MOD_ID = "ores";
	public static final Logger LOGGER = LoggerFactory.getLogger("BadOres");

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
	}
}