package safro.saflib.group;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.Experimental
public class SearchItemGroup extends ItemGroup {

    public SearchItemGroup(ItemGroup.Row row, int column, ItemGroup.Type type, Text displayName, Supplier<ItemStack> iconSupplier, ItemGroup.EntryCollector entryCollector) {
        super(row, column, type, displayName, iconSupplier, entryCollector);
    }

    public static Builder builder() {
        return new Builder(null, -1);
    }

    @Override
    public Identifier getTexture() {
        return ItemGroup.getTabTextureId("item_search");
    }

    public static class Builder {
        private static final ItemGroup.EntryCollector EMPTY_ENTRIES = (displayContext, entries) -> {
        };
        private final ItemGroup.Row row;
        private final int column;
        private Text displayName = Text.empty();
        private Supplier<ItemStack> iconSupplier = () -> ItemStack.EMPTY;
        private ItemGroup.EntryCollector entryCollector;
        private ItemGroup.Type type;

        public Builder(ItemGroup.Row row, int column) {
            this.entryCollector = EMPTY_ENTRIES;
            this.type = ItemGroup.Type.CATEGORY;
            this.row = row;
            this.column = column;
        }

        public Builder displayName(Text displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder icon(Supplier<ItemStack> iconSupplier) {
            this.iconSupplier = iconSupplier;
            return this;
        }

        public Builder entries(EntryCollector entryCollector) {
            this.entryCollector = entryCollector;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public SearchItemGroup build() {
            if ((this.type == ItemGroup.Type.HOTBAR || this.type == ItemGroup.Type.INVENTORY) && this.entryCollector != EMPTY_ENTRIES) {
                throw new IllegalStateException("Special tabs can't have display items");
            } else {
                return new SearchItemGroup(this.row, this.column, this.type, this.displayName, this.iconSupplier, this.entryCollector);
            }
        }
    }
}
