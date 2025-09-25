package pyroghy.ores.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TeleportingBlock extends Block {
    private static final int TELEPORT_RANGE = 5;
    private static final int TELEPORT_ATTEMPTS = 32;
    private static final float TELEPORT_CHANCE = 0.60F;
    private static final ThreadLocal<Boolean> SHOULD_DROP = ThreadLocal.withInitial(() -> Boolean.FALSE);

    public TeleportingBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.isClient) {
            return super.onBreak(world, pos, state, player);
        }

        ServerWorld serverWorld = (ServerWorld) world;
        Random random = serverWorld.getRandom();

        if (random.nextFloat() < TELEPORT_CHANCE && teleportBlock(serverWorld, pos, state)) {
            SHOULD_DROP.set(Boolean.FALSE);
            return world.getBlockState(pos);
        }

        SHOULD_DROP.set(Boolean.TRUE);
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
        if (!world.isClient && Boolean.TRUE.equals(SHOULD_DROP.get())) {
            super.afterBreak(world, player, pos, state, blockEntity, stack);
        }

        SHOULD_DROP.remove();
    }

    @SuppressWarnings("deprecation")
    private boolean teleportBlock(ServerWorld world, BlockPos origin, BlockState state) {
        Random random = world.getRandom();

        for (int attempt = 0; attempt < TELEPORT_ATTEMPTS; attempt++) {
            int offsetX = random.nextInt(TELEPORT_RANGE * 2 + 1) - TELEPORT_RANGE;
            int offsetY = random.nextInt(TELEPORT_RANGE * 2 + 1) - TELEPORT_RANGE;
            int offsetZ = random.nextInt(TELEPORT_RANGE * 2 + 1) - TELEPORT_RANGE;
            BlockPos target = origin.add(offsetX, offsetY, offsetZ);

            if (target.equals(origin)) {
                continue;
            }

            if (!world.isChunkLoaded(target) || !isValidTeleport(world, target)) {
                continue;
            }

            world.setBlockState(target, state, Block.NOTIFY_ALL);
            world.setBlockState(origin, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);

            world.playSound(null, origin, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.playSound(null, target, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 1.0F, 1.0F);

            spawnTeleportParticles(world, origin);
            spawnTeleportParticles(world, target);

            return true;
        }

        return false;
    }

    private boolean isValidTeleport(WorldAccess world, BlockPos pos) {
        return world.getBlockState(pos).isAir() && world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
    }

    private void spawnTeleportParticles(ServerWorld world, BlockPos pos) {
        double centerX = pos.getX() + 0.5D;
        double centerY = pos.getY() + 0.5D;
        double centerZ = pos.getZ() + 0.5D;

        world.spawnParticles(ParticleTypes.PORTAL, centerX, centerY, centerZ, 32, 0.4D, 0.4D, 0.4D, 0.2D);
    }

}
