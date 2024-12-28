package mod.linguardium.badgebox.datagen;

import mod.linguardium.badgebox.common.Util;
import mod.linguardium.badgebox.common.recipe.AddRibbonRecipe;
import mod.linguardium.badgebox.common.registration.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeConverter converter = new ShapedRecipeConverter(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BADGE_BOX_ITEM_RIBBON.get())
                .input('a', Items.GOLD_INGOT)
                .input('b', ModItems.BADGE_BOX_ITEM.get())
                .pattern(" a ")
                .pattern("aba")
                .pattern(" a ")
                .criterion("has_badge_box", conditionsFromItem(ModItems.BADGE_BOX_ITEM.get()))
                .offerTo(converter, Util.id("fancy_badge_box"));

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
    private record ShapedRecipeConverter(RecipeExporter exporter) implements RecipeExporter {

        @Override
        public void accept(Identifier recipeId, Recipe<?> recipe, @Nullable AdvancementEntry advancement) {
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                recipe = AddRibbonRecipe.create(shapedRecipe);
            }
            exporter.accept(recipeId, recipe, advancement);
        }

        @Override
        public Advancement.Builder getAdvancementBuilder() {
            return exporter.getAdvancementBuilder();
        }
    }
}
