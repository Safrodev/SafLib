package safro.saflib.network;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

// As of 1.21, this makes use of the builtin ParticleS2CPacket instead of implementing a custom one
public class ParticlePacket {

    // Sends particles to players tracking the Entity
    public static void send(Entity entity, ParticleEffect effect, boolean force, double x, double y, double z, double speed) {
        PlayerLookup.tracking(entity).forEach(player -> send(player, effect, force, x, y, z, speed));
    }

    // Sends particles to players tracking the BlockEntity
    public static void send(BlockEntity blockEntity, ParticleEffect effect, boolean force, double x, double y, double z, double speed) {
        PlayerLookup.tracking(blockEntity).forEach(player -> send(player, effect, force, x, y, z, speed));
    }

    // Sends particles to players tracking the BlockPos
    public static void send(ServerWorld world, BlockPos pos, ParticleEffect effect, boolean force, double x, double y, double z, double speed) {
        PlayerLookup.tracking(world, pos).forEach(player -> send(player, effect, force, x, y, z, speed));
    }

    /**
     * @param player - the player who will see the particles
     * @param effect - the particle effect
     * @param force - if true, the max distance the particles can be seen is 512 blocks; if false, max is 32 blocks
     * @param x - x coord
     * @param y - y coord
     * @param z - z coord
     * @param speed - particle velocity; set to 0.0F for default
     */
    public static void send(ServerPlayerEntity player, ParticleEffect effect, boolean force, double x, double y, double z, double speed) {
        Packet<?> packet = new ParticleS2CPacket(effect, force, x, y, z, 1.0F, 1.0F, 1.0F, (float)speed, 0);
        BlockPos blockPos = player.getBlockPos();
        if (blockPos.isWithinDistance(new Vec3d(x, y, z), force ? 512.0D : 32.0D)) {
            player.networkHandler.sendPacket(packet);
        }
    }
}
