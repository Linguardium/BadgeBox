package mod.linguardium.badgebox.common.item;

import com.google.common.collect.ImmutableList;
import mod.linguardium.badgebox.common.item.components.BadgeInventoryComponent;
import mod.linguardium.badgebox.common.registration.ModDataComponentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ComponentStorageInventory extends SimpleInventory {
    ItemStack holder;
    boolean hasRibbonSlot = false;
    int listSize;
    BadgeInventoryComponent backingComponent;

    private ComponentStorageInventory(ItemStack holder, int size, @NotNull BadgeInventoryComponent backingComponent) {
        super(size);
        this.holder = holder;
        this.backingComponent = backingComponent;
        listSize = this.backingComponent.getSize();
        for (int idx=0; idx<listSize; idx++) {
            this.getHeldStacks().set(idx, this.backingComponent.get(idx));
        }
        this.hasRibbonSlot = this.backingComponent.hasRibbonSlot();
        if (this.backingComponent.hasRibbonSlot()) {
            this.getHeldStacks().set(listSize, this.backingComponent.getRibbonSlot());
        }
    }
    public static ComponentStorageInventory fromBadgeInventoryComponent(ItemStack holder, @NotNull BadgeInventoryComponent backingComponent) {
        int size = backingComponent.getSize();
        if (backingComponent.hasRibbonSlot()) {
            size++;
        }
        return new ComponentStorageInventory(holder, size, backingComponent);
    }
    public void saveInventory() {
        BadgeInventoryComponent component = backingComponent.setInventory(ImmutableList.copyOf(this.getHeldStacks()).subList(0, listSize));
        if (this.hasRibbonSlot) {
            component = component.setRibbonSlot(Optional.of(this.getHeldStacks().get(listSize)));
        }
        updateBackingStackComponent(component);
    }
    public void updateBackingStackComponent(BadgeInventoryComponent component) {
        this.holder.set(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(), component);
        this.backingComponent = component;
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
