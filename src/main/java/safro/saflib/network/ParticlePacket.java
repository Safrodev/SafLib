package safro.saflib.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import safro.saflib.SafLib;

public class ParticlePacket {
    public static final Identifier ID = new Identifier(SafLib.MODID, "spawn_particles");

    // Sends particles to players tracking the Entity
    public static void send(Entity entity, ParticleEffect effect, double x, double y, double z, double vX, double vY, double vZ) {
        PlayerLookup.tracking(entity).forEach(player -> send(player, effect, x, y, z, vX, vY, vZ));
    }

    // Sends particles to players tracking the BlockEntity
    public static void send(BlockEntity blockEntity, ParticleEffect effect, double x, double y, double z, double vX, double vY, double vZ) {
        PlayerLookup.tracking(blockEntity).forEach(player -> send(player, effect, x, y, z, vX, vY, vZ));
    }

    // Sends particles to players tracking the BlockPos
    public static void send(ServerWorld world, BlockPos pos, ParticleEffect effect, double x, double y, double z, double vX, double vY, double vZ) {
        PlayerLookup.tracking(world, pos).forEach(player -> send(player, effect, x, y, z, vX, vY, vZ));
    }

    // Sends particles to the player
    public static void send(ServerPlayerEntity player, ParticleEffect effect, double x, double y, double z, double vX, double vY, double vZ) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeRegistryValue(Registries.PARTICLE_TYPE, effect.getType());
        effect.write(buf);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(vX);
        buf.writeDouble(vY);
        buf.writeDouble(vZ);
        ServerPlayNetworking.send(player, ID, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void receive(MinecraftClient client, PacketByteBuf buf) {
        if (client.world != null) {
            ParticleEffect effect = readEffect(buf, buf.readRegistryValue(Registries.PARTICLE_TYPE));
            client.world.addParticle(effect, buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
        }
    }

    private static <T extends ParticleEffect> T readEffect(PacketByteBuf buf, ParticleType<T> type) {
        return type.getParametersFactory().read(type, buf);
    }
}
