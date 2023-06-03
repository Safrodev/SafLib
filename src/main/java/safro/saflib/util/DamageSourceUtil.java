package safro.saflib.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DamageSourceUtil {

    public static DamageSource create(World world, RegistryKey<DamageType> type) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(type));
    }

    public static RegistryKey<DamageType> register(String modid, String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(modid, name));
    }
}
