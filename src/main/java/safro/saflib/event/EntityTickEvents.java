package safro.saflib.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityTickEvents {
    public static final Event<Living> LIVING_ENTITY = EventFactory.createArrayBacked(Living.class, callbacks -> (entity) -> {
        for (Living callback : callbacks) {
            callback.tick(entity);
        }
    });

    public static final Event<Player> PLAYER = EventFactory.createArrayBacked(Player.class, callbacks -> (player) -> {
        for (Player callback : callbacks) {
            callback.tick(player);
        }
    });

    @FunctionalInterface
    public interface Living {
        void tick(LivingEntity entity);
    }

    @FunctionalInterface
    public interface Player {
        void tick(PlayerEntity entity);
    }
}
