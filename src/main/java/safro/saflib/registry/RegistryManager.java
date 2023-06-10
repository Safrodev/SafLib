package safro.saflib.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public class RegistryManager {
    // Fallback/default modid
    protected static String ID = "saflib";
    // Fallback/default items list
    protected static List<ItemStack> ITEMS = new ArrayList<>();

    public static void registerAll(RegistryKey<ItemGroup> group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addAll(ITEMS));
    }

    public static void setId(String name) {
        ID = name;
    }
}
