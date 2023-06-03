package safro.saflib.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BaseEntityRegistry {

    protected static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(RegistryManager.ID, name), type);
    }

    public static void addAttributes(EntityType<? extends LivingEntity> type, DefaultAttributeContainer.Builder builder) {
        FabricDefaultAttributeRegistry.register(type, builder);
    }

    // Default player dimensions
    protected static EntityDimensions player() {
        return EntityDimensions.fixed(0.6F, 1.96F);
    }
}
