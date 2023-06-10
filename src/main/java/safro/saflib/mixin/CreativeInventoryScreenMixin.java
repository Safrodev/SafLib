package safro.saflib.mixin;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.search.SearchProvider;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import safro.saflib.group.SearchItemGroup;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {

    public CreativeInventoryScreenMixin(PlayerEntity player) {
        super(new CreativeInventoryScreen.CreativeScreenHandler(player), player.getInventory(), Text.empty());
    }

    @Shadow private boolean ignoreTypedCharacter;

    @Shadow private TextFieldWidget searchBox;

    @Shadow protected abstract void search();

    @Shadow protected abstract boolean isCreativeInventorySlot(@Nullable Slot slot);

    @Shadow private float scrollPosition;

    @Shadow protected abstract void searchForTags(String id);

    @Shadow @Final private Set<TagKey<Item>> searchResultTags;

    @Shadow private static ItemGroup selectedTab;

    @Inject(method = "charTyped", at = @At("HEAD"), cancellable = true)
    private void charTypedSearchGroup(char chr, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (!this.ignoreTypedCharacter) {
            if (selectedTab instanceof SearchItemGroup) {
                String string = this.searchBox.getText();
                if (this.searchBox.charTyped(chr, modifiers)) {
                    if (!Objects.equals(string, this.searchBox.getText())) {
                        this.search();
                    }
                    cir.setReturnValue(true);
                } else {
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen;isCreativeInventorySlot(Lnet/minecraft/screen/slot/Slot;)Z", shift = At.Shift.BEFORE), cancellable = true)
    private void keyPressedSearchGroup(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (selectedTab instanceof SearchItemGroup) {
            boolean bl = !this.isCreativeInventorySlot(this.focusedSlot) || this.focusedSlot.hasStack();
            boolean bl2 = InputUtil.fromKeyCode(keyCode, scanCode).toInt().isPresent();
            if (bl && bl2 && this.handleHotbarKeyPressed(keyCode, scanCode)) {
                this.ignoreTypedCharacter = true;
                cir.setReturnValue(true);
            } else {
                String string = this.searchBox.getText();
                if (this.searchBox.keyPressed(keyCode, scanCode, modifiers)) {
                    if (!Objects.equals(string, this.searchBox.getText())) {
                        this.search();
                    }

                    cir.setReturnValue(true);
                } else {
                    cir.setReturnValue(this.searchBox.isFocused() && this.searchBox.isVisible() && keyCode != 256 || super.keyPressed(keyCode, scanCode, modifiers));
                }
            }
        }
    }

    @Inject(method = "search", at = @At(value = "INVOKE", target = "Ljava/util/Set;clear()V", shift = At.Shift.AFTER), cancellable = true)
    private void customSearch(CallbackInfo ci) {
        if (selectedTab instanceof SearchItemGroup group) {
            this.handler.itemList.clear();
            this.searchResultTags.clear();
            String string = this.searchBox.getText();
            if (string.isEmpty()) {
                this.handler.itemList.addAll(group.getDisplayStacks());
            } else {
                SearchProvider searchProvider;
                if (string.startsWith("#")) {
                    string = string.substring(1);
                    searchProvider = this.client.getSearchProvider(SearchManager.ITEM_TAG);
                    this.searchForTags(string);
                } else {
                    searchProvider = this.client.getSearchProvider(SearchManager.ITEM_TOOLTIP);
                }

                List<ItemStack> list = ((List<ItemStack>)searchProvider.findAll(string.toLowerCase(Locale.ROOT))).stream().filter(stack -> group.getDisplayStacks().contains(stack)).toList();
                this.handler.itemList.addAll(list);
            }

            this.scrollPosition = 0.0F;
            this.handler.scrollItems(0.0F);
            ci.cancel();
        }
    }

    @Inject(method = "setSelectedTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;setText(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    private void setSearchVisible(ItemGroup group, CallbackInfo ci) {
        if (group instanceof SearchItemGroup) {
            this.searchBox.setVisible(true);
            this.searchBox.setFocusUnlocked(false);
            this.searchBox.setFocused(true);
            if (selectedTab != group) {
                this.searchBox.setText("");
            }
            this.search();
        }
    }
}
