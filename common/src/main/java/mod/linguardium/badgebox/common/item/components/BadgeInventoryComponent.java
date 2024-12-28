package mod.linguardium.badgebox.common.item.components;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.linguardium.badgebox.common.item.ComponentStorageInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.collection.DefaultedList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BadgeInventoryComponent {

    public static final BadgeInventoryComponent EMPTY = new BadgeInventoryComponent(ImmutableList.copyOf(DefaultedList.ofSize(8,ItemStack.EMPTY)), Optional.empty());

    public static final MapCodec<BadgeInventoryComponent> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("Size").forGetter(BadgeInventoryComponent::getSize),
            Codec.unboundedMap(Codec.STRING, ItemStack.CODEC).fieldOf("Items").forGetter(BadgeInventoryComponent::getNonEmptySlotMap),
            Codec.BOOL.fieldOf("RibbonSlotEnabled").forGetter(BadgeInventoryComponent::hasRibbonSlot),
            ItemStack.CODEC.optionalFieldOf("RibbonSlot",ItemStack.EMPTY).forGetter(BadgeInventoryComponent::getRibbonSlot)
    ).apply(instance, BadgeInventoryComponent::decode));

    public static final PacketCodec<RegistryByteBuf, BadgeInventoryComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            BadgeInventoryComponent::getSize,
            PacketCodecs.map(Maps::newHashMapWithExpectedSize,PacketCodecs.STRING, ItemStack.PACKET_CODEC),
            BadgeInventoryComponent::getNonEmptySlotMap,
            PacketCodecs.BOOL,
            BadgeInventoryComponent::hasRibbonSlot,
            ItemStack.OPTIONAL_PACKET_CODEC,
            BadgeInventoryComponent::getRibbonSlot,
            BadgeInventoryComponent::decode);



    ImmutableList<ItemStack> badgeInventory;
    Optional<ItemStack> ribbonSlot;
    int hashCode = 0;

    private BadgeInventoryComponent(ImmutableList<ItemStack> badgeInventory, Optional<ItemStack> ribbonSlot) {
        this.badgeInventory = badgeInventory;
        this.ribbonSlot = ribbonSlot;
        this.hashCode = this.hashCode();
    }

    public ComponentStorageInventory asInventory(ItemStack stack) {
        return ComponentStorageInventory.fromBadgeInventoryComponent(stack, this);
    }
    public ImmutableList<ItemStack> getItemStackList() {
        return badgeInventory;
    }
    public ItemStack get(int slot) {
        if (slot < this.badgeInventory.size() && slot >= 0)
            return this.badgeInventory.get(slot);
        return ItemStack.EMPTY;
    }

    public BadgeInventoryComponent setInventory(ImmutableList<ItemStack> newStackList) {
        DefaultedList<ItemStack> newItemList = DefaultedList.ofSize(Math.max(newStackList.size(), this.badgeInventory.size()), ItemStack.EMPTY);
        for (int i = 0; i < newStackList.size(); i++) {
            newItemList.set(i, newStackList.get(i).copy());
        }
        return new BadgeInventoryComponent(ImmutableList.copyOf(newItemList), this.ribbonSlot);
    }

    public BadgeInventoryComponent setRibbonSlot(Optional<ItemStack> newRibbonSlot) {
        return new BadgeInventoryComponent(this.badgeInventory, newRibbonSlot);
    }

    public int getSize() {
        return this.badgeInventory.size();
    }

    public boolean hasRibbonSlot() {
        return this.ribbonSlot.isPresent();
    }

    public ItemStack getRibbonSlot() {
        return this.ribbonSlot.orElse(ItemStack.EMPTY);
    }

    private Map<String, ItemStack> getNonEmptySlotMap() {
        HashMap<String, ItemStack> map = new HashMap<>();
        for (int idx=0;idx<this.badgeInventory.size();idx++) {
            ItemStack stack = this.badgeInventory.get(idx);
            if (stack.isEmpty()) continue;
            map.put(String.valueOf(idx), stack);
        }
        return map;
    }

    @Override
    public int hashCode() {
        final int hashcode = ItemStack.listHashCode(badgeInventory);
        return ribbonSlot.map(Object::hashCode).map(hash->hashcode * 31 + hash).orElse(hashcode);
    }
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof BadgeInventoryComponent badgeInventoryComponent) {
                return badgeInventoryComponent.hashCode == this.hashCode;
        }
        return false;
    }

    private static BadgeInventoryComponent decode(int size, Map<String, ItemStack> nonEmptySlotMap, Boolean ribbonSlotEnabled, ItemStack ribbonSlot) {
        DefaultedList<ItemStack> newItemList = DefaultedList.ofSize(size, ItemStack.EMPTY);
        nonEmptySlotMap.forEach((key, value)->{
            newItemList.set(Integer.parseInt(key), value);
        });
        Optional<ItemStack> optionalRibbonSlot = Optional.empty();
        if (ribbonSlotEnabled) {
            optionalRibbonSlot = Optional.of(ribbonSlot);
        }
        return new BadgeInventoryComponent(ImmutableList.copyOf(newItemList), optionalRibbonSlot);
    }

}
