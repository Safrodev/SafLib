package safro.saflib.registry;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class BaseTagRegistry {

    protected static TagKey<EntityType<?>> entity(String id, String tag) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(id, tag));
    }

    protected static TagKey<Item> item(String id, String tag) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(id, tag));
    }

    protected static TagKey<Block> block(String id, String tag) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(id, tag));
    }

    protected static TagKey<Biome> biome(String id, String tag) {
        return TagKey.of(RegistryKeys.BIOME, Identifier.of(id, tag));
    }

    protected static TagKey<Fluid> fluid(String id, String tag) {
        return TagKey.of(RegistryKeys.FLUID, Identifier.of(id, tag));
    }
}
