package safro.saflib.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.saflib.event.EntityTickEvents;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void safLibLivingTick(CallbackInfo ci) {
        EntityTickEvents.LIVING_ENTITY.invoker().tick((LivingEntity) (Object) this);
    }
}
