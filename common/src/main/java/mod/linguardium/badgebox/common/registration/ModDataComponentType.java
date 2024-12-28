package mod.linguardium.badgebox.common.registration;

import dev.architectury.registry.registries.RegistrySupplier;
import mod.linguardium.badgebox.common.Util;
import mod.linguardium.badgebox.common.item.components.BadgeInventoryComponent;
import net.minecraft.component.ComponentType;

import static mod.linguardium.badgebox.common.registration.BadgeBoxRegistrar.COMPONENTS;

public class ModDataComponentType {



    public static final RegistrySupplier<ComponentType<BadgeInventoryComponent>> BADGEBOX_INVENTORY_COMPONENT = COMPONENTS.register(Util.id("badgebox"), ()->ComponentType.<BadgeInventoryComponent>builder().packetCodec(BadgeInventoryComponent.PACKET_CODEC).codec(BadgeInventoryComponent.CODEC.codec()).cache().build());
    public static void init() { }


}
