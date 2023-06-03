package safro.saflib;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SafLib implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("saflib");
	public static final String MODID = "saflib";

	@Override
	public void onInitialize() {
	}

	public static ItemGroup createGroup(String modid, ItemConvertible icon) {
		return FabricItemGroup.builder(new Identifier(modid, modid)).icon(() -> new ItemStack(icon)).build();
	}
}