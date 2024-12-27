package mod.linguardium.badgebox.fabric.datagen;

import mod.linguardium.badgebox.Util;
import mod.linguardium.badgebox.registration.ModItems;
import mod.linguardium.badgebox.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static mod.linguardium.badgebox.registration.BadgeBoxRegistrar.ITEMS;

public class ModLangProvider extends FabricLanguageProvider {

    public ModLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        addTagTranslations(translationBuilder);
        // ITEM GROUP
        addItemGroupTranslations(translationBuilder);
        // ITEMS
        addItemTranslations(translationBuilder);
        // GUI
        addGuiTranslations(translationBuilder);
    }
    private void addGuiTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("gui.badgebox.badge_box_gui.label","League Badges");
    }
    private void addTagTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModTags.BADGE_TAG, "Badges");
        translationBuilder.add(ModTags.RIBBON_TAG, "Ribbons");
    }
    private void addItemGroupTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(getItemGroup("badge_box_tab"), "Badges");

    }
    private void addItemTranslations(TranslationBuilder translationBuilder) {
        // BOXES
        translationBuilder.add(ModItems.BADGE_BOX_ITEM.get(), "Badge Box");
        translationBuilder.add(ModItems.BADGE_BOX_ITEM_RIBBON.get(), "Fancy Badge Box");

        // BADGES
        translationBuilder.add(getBadge("fairy"), "Aether Badge");
        translationBuilder.add(getBadge("ghost"), "Soul Badge");
        translationBuilder.add(getBadge("grass"), "Botanical Badge");
        translationBuilder.add(getBadge("ice"), "Hoarfrost Badge");
        translationBuilder.add(getBadge("mountain"), "Ridge Badge");
        translationBuilder.add(getBadge("poison"), "Pestilence Badge");
        translationBuilder.add(getBadge("steel"), "Engrenage Badge");
        translationBuilder.add(getBadge("thunder"), "Surge Badge");
        translationBuilder.add(getBadge("wave"), "Torrent Badge");
        translationBuilder.add(getBadge("wyvern"), "Wyvern Badge");

    }

    private static Item getBadge(String internalName) {
        return ITEMS.get(Util.id(internalName+"_badge"));
    }
    private static RegistryKey<ItemGroup> getItemGroup(String name) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Util.id(name));
    }
}
