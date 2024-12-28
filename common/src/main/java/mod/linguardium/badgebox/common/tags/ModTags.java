package mod.linguardium.badgebox.common.tags;

import mod.linguardium.badgebox.common.Util;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static final TagKey<Item> BADGE_TAG = TagKey.of(RegistryKeys.ITEM, Util.id("badges"));
    public static final TagKey<Item> RIBBON_TAG = TagKey.of(RegistryKeys.ITEM, Util.id("ribbons"));
}
