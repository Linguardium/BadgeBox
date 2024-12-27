package mod.linguardium.badgebox.registration;

import dev.architectury.registry.registries.RegistrySupplier;
import mod.linguardium.badgebox.Util;
import mod.linguardium.badgebox.screen.BadgeBoxContainerScreenHandler;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

import static mod.linguardium.badgebox.item.components.InventoryComponentHelper.NON_EMPTY_SLOTS_INVENTORY_CODEC;
import static mod.linguardium.badgebox.item.components.InventoryComponentHelper.NON_EMPTY_SLOTS_INVENTORY_PACKET_CODEC;
import static mod.linguardium.badgebox.registration.BadgeBoxRegistrar.COMPONENTS;

public class ModDataComponentType {



    public static final DefaultedList<ItemStack> DEFAULT_ITEM_LIST = DefaultedList.ofSize(BadgeBoxContainerScreenHandler.getInventorySlotCount(), ItemStack.EMPTY);
    public static final RegistrySupplier<ComponentType<DefaultedList<ItemStack>>> BADGEBOX_INVENTORY_COMPONENT = COMPONENTS.register(Util.id("badgebox"), ()->ComponentType.<DefaultedList<ItemStack>>builder().packetCodec(NON_EMPTY_SLOTS_INVENTORY_PACKET_CODEC).codec(NON_EMPTY_SLOTS_INVENTORY_CODEC).build());
    public static final RegistrySupplier<ComponentType<ItemStack>> BADGEBOX_INVENTORY_RIBBON_COMPONENT = COMPONENTS.register(Util.id("badgebox_ribbon_enabled"), ()->ComponentType.<ItemStack>builder().packetCodec(PacketCodecs.optional(ItemStack.PACKET_CODEC).xmap(o->o.orElse(ItemStack.EMPTY),i->i.isEmpty()? Optional.empty():Optional.of(i))).codec(Codecs.optional(ItemStack.CODEC).xmap(o->o.orElse(ItemStack.EMPTY),i->i.isEmpty()? Optional.empty():Optional.of(i))).build());
    public static void init() { }


}
