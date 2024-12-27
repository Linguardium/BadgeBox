package mod.linguardium.badgebox.registration;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.registries.RegistrySupplier;
import mod.linguardium.badgebox.Util;
import mod.linguardium.badgebox.item.BadgeBoxItem;
import mod.linguardium.badgebox.item.BadgeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.function.Function;

import static mod.linguardium.badgebox.registration.BadgeBoxRegistrar.ITEMS;

public class ModItems {

    public static final RegistrySupplier<Item> BADGE_BOX_ITEM = register("badgesbox", BadgeBoxItem::new, new Item.Settings());
    public static final RegistrySupplier<Item> BADGE_BOX_ITEM_RIBBON = register("badgesbox_ribbon", settings->new BadgeBoxItem(settings.component(ModDataComponentType.BADGEBOX_INVENTORY_RIBBON_COMPONENT.get(),ItemStack.EMPTY)), new Item.Settings());
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

    private static RegistrySupplier<Item> register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Util.id(name));
        // pending 1.21.2 changes
        // return Registry.register(Registries.ITEM, key, factory.apply(settings.registryKey(key)));
        return ITEMS.register(key.getValue(), ()->factory.apply(settings));
    }
    private static RegistrySupplier<Item> createBadge(String name) {
        return register(name, BadgeItem::new, new Item.Settings().rarity(Rarity.EPIC).maxCount(1));
    }
}
