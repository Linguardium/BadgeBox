package mod.linguardium.badgebox.registration;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import mod.linguardium.badgebox.Util;
import mod.linguardium.badgebox.tags.ModTags;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

import static mod.linguardium.badgebox.registration.BadgeBoxRegistrar.ITEM_GROUPS;
import static mod.linguardium.badgebox.registration.ModItems.BADGE_BOX_ITEM;
import static mod.linguardium.badgebox.registration.ModItems.BADGE_BOX_ITEM_RIBBON;

public class ModItemGroups {
    public static final RegistrySupplier<ItemGroup> BADGE_BOX_ITEM_GROUP = ITEM_GROUPS.register(
            Util.id("badge_box_tab"), // Tab ID
            () -> CreativeTabRegistry.create(builder-> {
                builder.displayName(Text.translatable("item_group.badgebox.badge_box_tab"))
                        .icon(() -> new ItemStack(BADGE_BOX_ITEM.get()))
                        .entries((displayContext, entries) -> {
                            entries.add(BADGE_BOX_ITEM.get());
                            entries.add(BADGE_BOX_ITEM_RIBBON.get());
                            displayContext.lookup().getOptionalWrapper(RegistryKeys.ITEM).ifPresent(registry -> {
                                registry.streamEntries()
                                        .filter(item -> item.isIn(ModTags.BADGE_TAG))
                                        .map(RegistryEntry.Reference::value)
                                        .forEach(entries::add);
                                registry.streamEntries()
                                        .filter(item -> item.isIn(ModTags.RIBBON_TAG))
                                        .map(RegistryEntry.Reference::value)
                                        .forEach(entries::add);
                            });
                        });
            })
    );

    public static void init() {}
}
