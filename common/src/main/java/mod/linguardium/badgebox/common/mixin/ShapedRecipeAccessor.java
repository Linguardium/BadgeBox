package mod.linguardium.badgebox.common.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ShapedRecipe.class)
public interface ShapedRecipeAccessor {
    @Accessor
    RawShapedRecipe getRaw();
    @Accessor
    ItemStack getResult();
    @Accessor
    String getGroup();
    @Accessor
    CraftingRecipeCategory getCategory();
    @Accessor
    boolean getShowNotification();


}
