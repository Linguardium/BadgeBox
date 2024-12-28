package mod.linguardium.badgebox.common.recipe;

import com.mojang.serialization.MapCodec;
import mod.linguardium.badgebox.common.item.components.BadgeInventoryComponent;
import mod.linguardium.badgebox.common.mixin.ShapedRecipeAccessor;
import mod.linguardium.badgebox.common.registration.ModDataComponentType;
import mod.linguardium.badgebox.common.registration.ModRecipeSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RawShapedRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;


public class AddRibbonRecipe extends ShapedRecipe {

    public AddRibbonRecipe(ShapedRecipeAccessor recipe) {
        this(recipe.getGroup(), recipe.getCategory(), recipe.getRaw(), recipe.getResult(), recipe.getShowNotification());

    }
    public static AddRibbonRecipe create(ShapedRecipe recipe) {
        return new AddRibbonRecipe((ShapedRecipeAccessor) recipe);
    }
    public AddRibbonRecipe(String group, CraftingRecipeCategory category, RawShapedRecipe raw, ItemStack result) {
        super(group, category, raw, result);
    }

    public AddRibbonRecipe(String group, CraftingRecipeCategory category, RawShapedRecipe raw, ItemStack result, boolean showNotification) {
        super(group, category, raw, result, showNotification);
    }

    @Override
    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack result = getResult(wrapperLookup).copy();
        craftingRecipeInput.getStacks()
                .stream()
                .filter(inputStacks->inputStacks.get(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get()) != null)
                .findFirst()
                .ifPresent(old-> {
                    BadgeInventoryComponent existingComponent = old.getOrDefault(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(), BadgeInventoryComponent.EMPTY);
                    BadgeInventoryComponent newComponent = result.getOrDefault(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(), BadgeInventoryComponent.EMPTY);
                    newComponent = newComponent.setInventory(existingComponent.getItemStackList());
                    if (existingComponent.hasRibbonSlot()) {
                        newComponent.setRibbonSlot(existingComponent.getRibbonSlot());
                    }

                    result.set(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(), newComponent);
                    old.set(ModDataComponentType.BADGEBOX_INVENTORY_COMPONENT.get(), BadgeInventoryComponent.EMPTY);
                });
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ADD_RIBBON_RECIPE_SERIALIZER.get();
    }
    public static class AddRibbonSerializer implements RecipeSerializer<AddRibbonRecipe> {

        @Override
        public PacketCodec<RegistryByteBuf, AddRibbonRecipe> packetCodec() {
            return RecipeSerializer.SHAPED.packetCodec().xmap(shaped->new AddRibbonRecipe((ShapedRecipeAccessor)shaped), addon->addon);
        }

        @Override
        public MapCodec<AddRibbonRecipe> codec() {
            return RecipeSerializer.SHAPED.codec().xmap(shapedRecipe -> new AddRibbonRecipe((ShapedRecipeAccessor)shapedRecipe), ribbonRecipe->ribbonRecipe);
        }
    }
}
