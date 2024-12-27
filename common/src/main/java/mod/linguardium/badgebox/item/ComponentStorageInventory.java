package mod.linguardium.badgebox.item;

import mod.linguardium.badgebox.registration.ModDataComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mod.linguardium.badgebox.item.components.InventoryComponentHelper.copyDefaultedSubList;

public class ComponentStorageInventory extends SimpleInventory {
    ItemStack holder;
    boolean hasRibbonSlot = false;
    int listSize;

    public ComponentStorageInventory(ItemStack holder, List<ItemStack> stacks, @Nullable ItemStack ribbonStack) {
        super(stacks.size() + (ribbonStack == null ? 0 : 1));
        this.holder = holder;
        listSize = stacks.size();
        for (int idx=0; idx<stacks.size(); idx++) {
            this.getHeldStacks().set(idx, stacks.get(idx));
        }
        if (ribbonStack != null) {
            this.hasRibbonSlot = true;
            this.getHeldStacks().set(stacks.size(), ribbonStack);
        }
    }
    public void saveInventory() {
        DefaultedList<ItemStack> stacks = copyDefaultedSubList(this.getHeldStacks(),0, listSize);
        holder.set(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(), stacks);
        if (hasRibbonSlot) {
            holder.set(ModDataComponentType.BADGEBOX_INVENTORY_RIBBON_COMPONENT.get(), this.getHeldStacks().get(listSize));
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        saveInventory();
    }

    @Override
    public void onClose(PlayerEntity player) {
        super.onClose(player);
        saveInventory();
    }
}
