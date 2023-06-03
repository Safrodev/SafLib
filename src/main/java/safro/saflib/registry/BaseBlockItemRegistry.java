package safro.saflib.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public abstract class BaseBlockItemRegistry {

    protected static FabricItemSettings settings() {
        return new FabricItemSettings();
    }

    protected static <T extends Item> T register(String name, T item) {
        T registered = Registry.register(Registries.ITEM, new Identifier(RegistryManager.ID, name), item);
        RegistryManager.ITEMS.add(new ItemStack(registered));
        return registered;
    }

    private static <T extends Block> T register(String name, T block) {
        return register(name, block, true);
    }

    private static <T extends Block> T register(String name, T block, boolean createItem) {
        Registry.register(Registries.BLOCK, new Identifier(RegistryManager.ID, name), block);
        if (createItem) {
            register(name, new BlockItem(block, settings()));
        }
        return block;
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(RegistryManager.ID, name), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }
}