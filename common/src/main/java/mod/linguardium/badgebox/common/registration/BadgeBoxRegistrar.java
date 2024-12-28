package mod.linguardium.badgebox.common.registration;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;

import java.util.function.Supplier;

import static mod.linguardium.badgebox.common.BadgeBoxCommonInitializer.MOD_ID;

public class BadgeBoxRegistrar {
    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final Registrar<ComponentType<?>> COMPONENTS = MANAGER.get().get(RegistryKeys.DATA_COMPONENT_TYPE);
    public static final Registrar<Item> ITEMS = MANAGER.get().get(RegistryKeys.ITEM);
    public static final Registrar<ItemGroup> ITEM_GROUPS = MANAGER.get().get(RegistryKeys.ITEM_GROUP);
    public static final Registrar<ScreenHandlerType<?>> SCREEN_HANDLER_TYPES = MANAGER.get().get(RegistryKeys.SCREEN_HANDLER);
    public static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = MANAGER.get().get(RegistryKeys.RECIPE_SERIALIZER);

    public static void init() { }

}
