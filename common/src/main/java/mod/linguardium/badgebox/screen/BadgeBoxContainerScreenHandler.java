package mod.linguardium.badgebox.screen;

import mod.linguardium.badgebox.registration.ModScreenHandlerTypes;
import mod.linguardium.badgebox.screen.slot.LockPredicateSlot;
import mod.linguardium.badgebox.screen.slot.TagLimitedSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BadgeBoxContainerScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public static final int INVENTORY_COLUMNS = 4;
    public static final int INVENTORY_ROWS = 2;
    private static final int PLAYER_INVENTORY_COLUMNS = 9;
    private final int playerInventoryRows = (PlayerInventory.MAIN_SIZE - PlayerInventory.getHotbarSize()) / PLAYER_INVENTORY_COLUMNS;
    private final PlayerInventory playerInventory;
    final int boxSlot;
    boolean hasRibbonSlot;
    public static int getInventorySlotCount() {
        return INVENTORY_COLUMNS * INVENTORY_ROWS;
    }
    public BadgeBoxContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, @Nullable ItemStack ribbonSlotStack, int boxSlot) {
        super(ModScreenHandlerTypes.BADGE_BOX_SCREEN_HANDLER_TYPE.get(), syncId);
        this.inventory = inventory;
        this.playerInventory = playerInventory;
        this.boxSlot = boxSlot;
        this.hasRibbonSlot = ribbonSlotStack != null;
        this.addBadgeSlots();
        this.addPlayerHotbarSlots();
        this.addPlayerInventorySlots();
    }

    // Arch specific clientside constructor
    // Using a packet codec to match fabric extended screen handler
    public static BadgeBoxContainerScreenHandler create(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        return new BadgeBoxContainerScreenHandler(syncId, playerInventory, ExtendedScreenHandlerData.PACKET_CODEC.decode(buf));
    }

    // Clientside constructor
    public BadgeBoxContainerScreenHandler(int syncId, PlayerInventory playerInventory, ExtendedScreenHandlerData data) {
        this(syncId, playerInventory, createClientsideInventory(data), data.ribbon ? ItemStack.EMPTY : null, data.boxSlot());
    }

    private static Inventory createClientsideInventory(ExtendedScreenHandlerData data) {
        int slots = INVENTORY_ROWS*INVENTORY_COLUMNS;
        if (data.ribbon()) slots++;
        return new SimpleInventory(slots);
    }

    private boolean isBoxSlot(Integer index) {
        return this.boxSlot == index;
    }

    private void addBadgeSlots() {
        int offset = 0;
        int ribbonOffsetX = 28;
        int ribbonOffsetY = 9;
        if (hasRibbonSlot()) {
            offset = -13;
        }
        for (int y = 0; y < INVENTORY_ROWS; y++) {
            for (int x = 0; x < INVENTORY_COLUMNS; x++) {
                this.addSlot(TagLimitedSlot.badge(inventory, x + (y * INVENTORY_COLUMNS),  offset+57+(x*18),28+(y*18)));
            }
        }
        if (hasRibbonSlot()) {
            this.addSlot(TagLimitedSlot.ribbon(inventory, this.inventory.size()-1, ribbonOffsetX+(57+INVENTORY_ROWS*18),ribbonOffsetY+28));
        }

    }

    private void addPlayerInventorySlots() {
        int hotbarSize = PlayerInventory.getHotbarSize();
        for (int y = 0; y < this.playerInventoryRows; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMNS; x++) {
                this.addSlot(new LockPredicateSlot(this.playerInventory, hotbarSize + x + (y * PLAYER_INVENTORY_COLUMNS),  12+(x*18),84+(y*18),this::isBoxSlot));
            }
        }
    }

    private void addPlayerHotbarSlots() {
        for (int x = 0; x < PlayerInventory.getHotbarSize(); x++) {
            this.addSlot(new LockPredicateSlot(this.playerInventory, x,  12+(x*18),142, this::isBoxSlot));
        }
    }

    private int getInventorySlots() {
        return this.inventory.size();
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return super.canInsertIntoSlot(stack, slot) && slot.canInsert(stack);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private boolean isBadgeSlot(int slotIndex) {
        return slotIndex < getInventorySlots();
    }
    private boolean isHotbarSlot(int slotIndex) {
        int playerSlot = getPlayerSlotInventoryIndex(slotIndex);
        return PlayerInventory.isValidHotbarIndex(playerSlot);
    }

    private Pair<Integer, Integer> getPlayerMainSlotRange() {
        int start = getInventorySlots() + PlayerInventory.getHotbarSize();
        int end = this.slots.size();
        return new Pair<>(start,end);
    }

    private Pair<Integer, Integer> getPlayerHotbarSlotRange() {
        int start = getInventorySlots();
        int end = start + PlayerInventory.getHotbarSize();
        return new Pair<>(start, end);
    }
    private Pair<Integer, Integer> getInventorySlotRange() {
        return new Pair<>(0,getInventorySlots());
    }
    private boolean insertIntoBadgeSlots(ItemStack stack) {
        return insertIntoRange(stack, this::getInventorySlotRange);
    }
    private boolean insertIntoPlayerSlots(ItemStack stack) {
        return insertIntoRange(stack, this::getPlayerMainSlotRange);
    }
    private boolean insertIntoHotbarSlots(ItemStack stack) {
        return insertIntoRange(stack, this::getPlayerHotbarSlotRange);
    }
    private boolean insertIntoRange(ItemStack stack, Supplier<Pair<Integer, Integer>> rangeProvider) {
        Pair<Integer, Integer> range = rangeProvider.get();
        return this.insertItem(stack, range.getLeft(), range.getRight(), false);
    }
    private boolean isValidScreenSlot(int slotIndex) {
        return slotIndex < this.slots.size() && slotIndex >= 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        if (!isValid(slotIndex)) return ItemStack.EMPTY;
        if (!isValidScreenSlot(slotIndex)) return ItemStack.EMPTY;

        Slot moveSlot = this.slots.get(slotIndex);
        if (!moveSlot.hasStack()) return ItemStack.EMPTY;

        ItemStack moveStack = moveSlot.getStack();
        ItemStack returnStack = moveStack.copy();
        if (isBadgeSlot(slotIndex)) {
            if (!insertIntoHotbarSlots(moveStack) && !insertIntoPlayerSlots(moveStack)) {
                return ItemStack.EMPTY;
            }
        } else {
            if(isHotbarSlot(slotIndex)) {
                if (!insertIntoBadgeSlots(moveStack) && !insertIntoPlayerSlots(moveStack)) {
                    return ItemStack.EMPTY;
                }
            }else{
                if (!insertIntoBadgeSlots(moveStack) && !insertIntoHotbarSlots(moveStack)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (moveStack.isEmpty()) {
            moveSlot.setStack(ItemStack.EMPTY);
        } else {
            moveSlot.markDirty();
        }

        return returnStack;
    }


    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public boolean hasRibbonSlot() {
        return this.hasRibbonSlot;
    }

    private int getPlayerSlotInventoryIndex(int slotIndex) {
        int newIdx = slotIndex - getInventorySlots();
        if (newIdx < 0) newIdx = -1;
        return newIdx;
    }

    public record ExtendedScreenHandlerData(boolean ribbon, int boxSlot) {
        public static final PacketCodec<PacketByteBuf, ExtendedScreenHandlerData> PACKET_CODEC = PacketCodec.tuple(PacketCodecs.BOOL, ExtendedScreenHandlerData::ribbon, PacketCodecs.INTEGER, ExtendedScreenHandlerData::boxSlot, ExtendedScreenHandlerData::new);
    }
}
