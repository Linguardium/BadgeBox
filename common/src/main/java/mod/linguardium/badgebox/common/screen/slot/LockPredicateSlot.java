package mod.linguardium.badgebox.common.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.Optional;
import java.util.function.Predicate;

public class LockPredicateSlot extends Slot {
    Predicate<Integer> predicate;
    public LockPredicateSlot(Inventory inventory, int index, int x, int y, Predicate<Integer> predicate) {
        super(inventory, index, x, y);
        this.predicate = predicate;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return !predicate.test(this.getIndex()) && super.canInsert(stack);
    }

    @Override
    public ItemStack takeStack(int amount) {
        if (predicate.test(this.getIndex())) return ItemStack.EMPTY;
        return super.takeStack(amount);
    }

    @Override
    public ItemStack takeStackRange(int min, int max, PlayerEntity player) {
        if (predicate.test(this.getIndex())) return ItemStack.EMPTY;
        return super.takeStackRange(min, max, player);
    }

    @Override
    public Optional<ItemStack> tryTakeStackRange(int min, int max, PlayerEntity player) {
        if (predicate.test(this.getIndex())) return Optional.empty();
        return super.tryTakeStackRange(min, max, player);
    }

    @Override
    public ItemStack insertStack(ItemStack stack, int count) {
        if (predicate.test(this.getIndex())) return ItemStack.EMPTY;
        return super.insertStack(stack, count);
    }

    @Override
    public ItemStack insertStack(ItemStack stack) {
        if (predicate.test(this.getIndex())) return ItemStack.EMPTY;
        return super.insertStack(stack);
    }

    @Override
    public boolean canTakePartial(PlayerEntity player) {
        if (predicate.test(this.getIndex())) return false;
        return super.canTakePartial(player);
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        if (predicate.test(this.getIndex())) return false;
        return super.canTakeItems(playerEntity);
    }
}
