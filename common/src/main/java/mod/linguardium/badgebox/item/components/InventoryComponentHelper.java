package mod.linguardium.badgebox.item.components;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.collection.DefaultedList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryComponentHelper {
    public static final Codec<DefaultedList<ItemStack>> NON_EMPTY_SLOTS_INVENTORY_CODEC = RecordCodecBuilder.create(instance->instance.group(
            Codec.INT.fieldOf("Size").forGetter(List::size),
            Codec.unboundedMap(Codec.STRING, ItemStack.CODEC).fieldOf("Items").forGetter(InventoryComponentHelper::mapOfNonEmptySlots)
    ).apply(instance, InventoryComponentHelper::defaultedListFromSizeAndMap));
    public static final PacketCodec<RegistryByteBuf, DefaultedList<ItemStack>> NON_EMPTY_SLOTS_INVENTORY_PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            DefaultedList::size,
            PacketCodecs.map(Maps::newHashMapWithExpectedSize,PacketCodecs.STRING, ItemStack.PACKET_CODEC),
            InventoryComponentHelper::mapOfNonEmptySlots,
            InventoryComponentHelper::defaultedListFromSizeAndMap
    );
    private static DefaultedList<ItemStack> defaultedListFromSizeAndMap(int size, Map<String, ItemStack> map) {
        DefaultedList<ItemStack> items = DefaultedList.ofSize(size, ItemStack.EMPTY);
        map.forEach((key, value)->
            items.set(Integer.parseInt(key), value)
        );
        return items;
    }
    private static Map<String, ItemStack> mapOfNonEmptySlots(DefaultedList<ItemStack> list) {
        HashMap<String, ItemStack> map = new HashMap<>();
        for (int idx=0;idx<list.size();idx++) {
            ItemStack stack = list.get(idx);
            if (stack.isEmpty()) continue;
            map.put(String.valueOf(idx), stack);
        }
        return map;
    }
    public static DefaultedList<ItemStack> copyDefaultedList(DefaultedList<ItemStack> list) {
        DefaultedList<ItemStack> copy = DefaultedList.ofSize(list.size());
        for (int idx=0;idx<list.size();idx++) {
            copy.set(idx, list.get(idx));
        }
        return copy;
    }
    public static DefaultedList<ItemStack> copyDefaultedSubList(DefaultedList<ItemStack> list, int from, int to) {
        DefaultedList<ItemStack> copy = DefaultedList.ofSize(list.size(), ItemStack.EMPTY);
        for (int idx=from;idx<list.size()&&idx<to;idx++) {
            int targetIndex = idx - from;
            copy.set(targetIndex, list.get(idx));
        }
        return copy;
    }
}
