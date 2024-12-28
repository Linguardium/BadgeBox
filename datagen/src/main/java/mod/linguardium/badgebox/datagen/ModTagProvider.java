package mod.linguardium.badgebox.datagen;

import mod.linguardium.badgebox.common.item.BadgeItem;
import mod.linguardium.badgebox.common.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static mod.linguardium.badgebox.common.registration.BadgeBoxRegistrar.ITEMS;

public class ModTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        FabricTagBuilder badges = this.getOrCreateTagBuilder(ModTags.BADGE_TAG);
        ITEMS.forEach(item-> {
            if (item instanceof BadgeItem) badges.add(item);
        });
    }
}
