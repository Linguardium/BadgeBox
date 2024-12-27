package mod.linguardium.badgebox.fabric.datagen;

import mod.linguardium.badgebox.Util;
import mod.linguardium.badgebox.registration.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BADGE_BOX_ITEM_RIBBON.get())
                .input('a', Items.GOLD_INGOT)
                .input('b', ModItems.BADGE_BOX_ITEM.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .criterion("has_badge_box", conditionsFromItem(ModItems.BADGE_BOX_ITEM.get()))
                .offerTo(exporter, Util.id("fancy_badge_box"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BADGE_BOX_ITEM_RIBBON.get())
                .input('a', Items.RED_WOOL)
                .input('b', Items.WHITE_WOOL)
                .input('c', ConventionalItemTags.CHESTS)
                .pattern(" a ")
                .pattern("bcb")
                .pattern(" b ")
                .criterion("has_badge_box", conditionsFromTag(ConventionalItemTags.CHESTS))
                .offerTo(exporter, Util.id("badge_box"));


    }
}
