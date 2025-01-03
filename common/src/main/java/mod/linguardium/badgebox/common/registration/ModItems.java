package mod.linguardium.badgebox.common.registration;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.registries.RegistrySupplier;
import mod.linguardium.badgebox.common.Util;
import mod.linguardium.badgebox.common.item.BadgeBoxItem;
import mod.linguardium.badgebox.common.item.BadgeItem;
import mod.linguardium.badgebox.common.item.components.BadgeInventoryComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static mod.linguardium.badgebox.common.registration.BadgeBoxRegistrar.ITEMS;

public class ModItems {

    public static final RegistrySupplier<Item> BADGE_BOX_ITEM = register(
            "badgesbox",
            settings->new BadgeBoxItem(
                    settings
                    .maxCount(1)
                    .component(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(),
                            BadgeInventoryComponent.EMPTY)
            )
    );

    public static final RegistrySupplier<Item> BADGE_BOX_ITEM_RIBBON = register(
            "badgesbox_ribbon",
            settings->new BadgeBoxItem(
                    settings
                    .maxCount(1)
                    .rarity(Rarity.UNCOMMON)
                    .component(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(),
                            BadgeInventoryComponent.EMPTY.setRibbonSlot(Optional.of(ItemStack.EMPTY)))
            )
    );

    public static void init() {
        LifecycleEvent.SETUP.register(() -> {

        });

            List.of(
                    "fairy",
                    "ghost",
                    "grass",
                    "ice",
                    "mountain",
                    "poison",
                    "steel",
                    "thunder",
                    "wave",
                    "wyvern"
            ).forEach(name -> createBadge(name + "_badge"));

    }

    private static RegistrySupplier<Item> register(String name, Function<Item.Settings, Item> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Util.id(name));
        // pending 1.21.2 changes
        // return Registry.register(Registries.ITEM, key, factory.apply(settings.registryKey(key)));
        return ITEMS.register(key.getValue(), ()->factory.apply(new Item.Settings()));
    }
    private static RegistrySupplier<Item> createBadge(String name) {
        return register(name, settings->new BadgeItem(settings.rarity(Rarity.EPIC).maxCount(1)));
    }
}
