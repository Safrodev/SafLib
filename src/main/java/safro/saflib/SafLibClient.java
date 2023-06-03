package safro.saflib;

import net.fabricmc.api.ClientModInitializer;
import safro.saflib.network.PacketRegistry;

public class SafLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PacketRegistry.initClient();
    }
}
