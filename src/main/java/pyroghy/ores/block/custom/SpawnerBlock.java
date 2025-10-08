package pyroghy.ores.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SpawnerBlock extends Block {
    private static final ThreadLocal<Boolean> SHOULD_DROP = ThreadLocal.withInitial(() -> Boolean.FALSE);

    public SpawnerBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.isClient) {
            return super.onBreak(world, pos, state, player);
        }

        ItemStack tool = player.getMainHandStack();
        ItemEnchantmentsComponent enchantments = tool.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
        net.minecraft.registry.Registry<net.minecraft.enchantment.Enchantment> enchantmentRegistry = world.getRegistryManager().get(net.minecraft.registry.RegistryKeys.ENCHANTMENT);
        net.minecraft.registry.entry.RegistryEntry<net.minecraft.enchantment.Enchantment> silkTouchEntry = enchantmentRegistry.getEntry(Enchantments.SILK_TOUCH).orElse(null);
        boolean hasSilkTouch = silkTouchEntry != null && enchantments.getLevel(silkTouchEntry) > 0;

        if (hasSilkTouch) {
            SHOULD_DROP.set(Boolean.TRUE);
        } else {
            SHOULD_DROP.set(Boolean.FALSE);
            spawnMobs((ServerWorld) world, pos);
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
        if (!world.isClient && Boolean.TRUE.equals(SHOULD_DROP.get())) {
            super.afterBreak(world, player, pos, state, blockEntity, stack);
        }

        SHOULD_DROP.remove();
    }

    private void spawnMobs(ServerWorld world, BlockPos pos) {
        Random random = world.getRandom();
        int count = getWeightedMobCount(random);
        EntityType<?> mobType = getWeightedMobType(random);

        for (int i = 0; i < count; i++) {
            MobEntity mob = createMob(world, mobType);
            if (mob != null) {
                mob.refreshPositionAndAngles(
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    random.nextFloat() * 360.0F,
                    0.0F
                );
                world.spawnEntity(mob);
            }
        }
    }

    private int getWeightedMobCount(Random random) {
        int roll = random.nextInt(100);
        
        if (roll < 40) {
            return 1;
        } else if (roll < 70) {
            return 2;
        } else if (roll < 90) {
            return 3;
        } else {
            return 4;
        }
    }

    private EntityType<?> getWeightedMobType(Random random) {
        int roll = random.nextInt(100);
        
        if (roll < 55) {
            return EntityType.COW;
        } else if (roll < 90) {
            return EntityType.PIG;
        } else {
            return EntityType.CHICKEN;
        }
    }

    private MobEntity createMob(ServerWorld world, EntityType<?> type) {
        if (type == EntityType.COW) {
            return new CowEntity(EntityType.COW, world);
        } else if (type == EntityType.PIG) {
            return new PigEntity(EntityType.PIG, world);
        } else if (type == EntityType.CHICKEN) {
            return new ChickenEntity(EntityType.CHICKEN, world);
        }
        return null;
    }
}
