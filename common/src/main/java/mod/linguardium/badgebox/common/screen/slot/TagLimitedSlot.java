package mod.linguardium.badgebox.common.screen.slot;

import mod.linguardium.badgebox.common.tags.ModTags;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;

public class TagLimitedSlot extends Slot {

    TagKey<Item> tag;

    public TagLimitedSlot(Inventory inventory, int index, int x, int y, TagKey<Item> tag) {
        super(inventory, index, x, y);
        this.tag = tag;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return super.canInsert(stack) && stack.isIn(tag);
    }

    public static TagLimitedSlot badge(Inventory inventory, int index, int x, int y) {
        return new TagLimitedSlot(inventory, index, x, y, ModTags.BADGE_TAG);
    }
    public static TagLimitedSlot ribbon(Inventory inventory, int index, int x, int y) {
        return new TagLimitedSlot(inventory, index, x, y, ModTags.RIBBON_TAG);
    }

}
