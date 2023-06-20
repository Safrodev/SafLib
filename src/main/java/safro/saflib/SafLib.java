package safro.saflib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import safro.saflib.group.SearchItemGroup;
import safro.saflib.network.PacketRegistry;

import java.util.ArrayList;
import java.util.List;

public class SafLib implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("saflib");
	public static final String MODID = "saflib";
	public static List<ItemStack> ITEMS = new ArrayList<>();

	@Override
	public void onInitialize() {
		PacketRegistry.initServer();
	}

	public static RegistryKey<ItemGroup> createGroup(String modid) {
		return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(modid, "item_group"));
	}

	// Call this after registering all objects in your onInitialize method
	public static void registerAll(RegistryKey<ItemGroup> key, ItemConvertible groupIcon) {
		String modid = key.getValue().getNamespace();
		Registry.register(Registries.ITEM_GROUP, key, FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + modid + "." + modid))
				.icon(() -> new ItemStack(groupIcon)).entries((displayContext, entries) -> entries.addAll(ITEMS)).build());
	}

	// It is not recommended to use search groups as they are still buggy
	// Use at your own risk, these will be completed soon
	@ApiStatus.Experimental
	public static RegistryKey<ItemGroup> createSearchGroup(String modid, ItemConvertible icon) {
		RegistryKey<ItemGroup> key = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(modid, "item_group"));
		SearchItemGroup group = SearchItemGroup.builder().displayName(Text.translatable("itemGroup." + modid + "." + modid)).icon(() -> new ItemStack(icon)).build();
		Registry.register(Registries.ITEM_GROUP, key, group);
		return key;
	}
}