package safro.saflib.registry;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import safro.saflib.SafLib;

public abstract class BaseBlockItemRegistry {
    // Set this to your modid at the top of your class in a 'static' body
    protected static String MODID = "";

    protected static Item.Settings settings() {
        return new Item.Settings();
    }

    protected static <T extends Item> T register(String name, T item) {
        T registered = Registry.register(Registries.ITEM, Identifier.of(MODID, name), item);
        SafLib.ITEMS.add(new ItemStack(registered));
        return registered;
    }

    protected static <T extends Block> T register(String name, T block) {
        return register(name, block, true);
    }

    protected static <T extends Block> T register(String name, T block, boolean createItem) {
        Registry.register(Registries.BLOCK, Identifier.of(MODID, name), block);
        if (createItem) {
            register(name, new BlockItem(block, settings()));
        }
        return block;
    }

    protected static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntityFactory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MODID, name), BlockEntityType.Builder.create(factory, blocks).build());
    }
}
