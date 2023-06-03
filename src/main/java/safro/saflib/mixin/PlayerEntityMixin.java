package safro.saflib.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.saflib.event.EntityTickEvents;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void safLibPlayerTick(CallbackInfo ci) {
        EntityTickEvents.PLAYER.invoker().tick((PlayerEntity) (Object) this);
    }
}
