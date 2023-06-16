package safro.saflib.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public class RegistryManager {
    @Deprecated
    protected static String ID = "saflib";
    protected static List<ItemStack> ITEMS = new ArrayList<>();

    public static void registerAll(RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addAll(ITEMS));
        ITEMS.clear();
    }

    @Deprecated
    // Use built-in mod id variable instead
    public static void setId(String name) {
        ID = name;
    }
}
