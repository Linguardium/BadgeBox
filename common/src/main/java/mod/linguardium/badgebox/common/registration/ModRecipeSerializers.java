package mod.linguardium.badgebox.common.registration;

import dev.architectury.registry.registries.RegistrySupplier;
import mod.linguardium.badgebox.common.Util;
import mod.linguardium.badgebox.common.recipe.AddRibbonRecipe;
import net.minecraft.recipe.RecipeSerializer;

import static mod.linguardium.badgebox.common.registration.BadgeBoxRegistrar.RECIPE_SERIALIZERS;

public class ModRecipeSerializers {
    public static final RegistrySupplier<RecipeSerializer<AddRibbonRecipe>> ADD_RIBBON_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(Util.id("upgrade_box_recipe"), AddRibbonRecipe.AddRibbonSerializer::new);

    public static void init() {

    }
}
