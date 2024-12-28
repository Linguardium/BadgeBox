package mod.linguardium.badgebox.datagen;

import mod.linguardium.badgebox.common.item.BadgeItem;
import mod.linguardium.badgebox.common.registration.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import static mod.linguardium.badgebox.common.registration.BadgeBoxRegistrar.ITEMS;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BADGE_BOX_ITEM.get(), Models.GENERATED);
        itemModelGenerator.register(ModItems.BADGE_BOX_ITEM_RIBBON.get(), ModItems.BADGE_BOX_ITEM.get(), Models.GENERATED);
        ITEMS.forEach(item-> {
            if (item instanceof BadgeItem) itemModelGenerator.register(item, Models.GENERATED);
        });
    }

    private void register(Item item, Identifier textureId, ItemModelGenerator generator) {
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(textureId.withPrefixedPath("item/")), generator.writer);
    }
}
