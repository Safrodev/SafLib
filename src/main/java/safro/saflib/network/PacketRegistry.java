package safro.saflib.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class PacketRegistry {

    public static void initServer() {

    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(ParticlePacket.ID, (((client, handler, buf, responseSender) -> ParticlePacket.receive(client, buf))));
    }
}
