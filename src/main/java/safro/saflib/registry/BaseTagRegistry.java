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
        return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(id, tag));
    }

    protected static TagKey<Item> item(String id, String tag) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(id, tag));
    }

    protected static TagKey<Block> block(String id, String tag) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(id, tag));
    }

    protected static TagKey<Biome> biome(String id, String tag) {
        return TagKey.of(RegistryKeys.BIOME, new Identifier(id, tag));
    }

    protected static TagKey<Fluid> fluid(String id, String tag) {
        return TagKey.of(RegistryKeys.FLUID, new Identifier(id, tag));
    }
}
