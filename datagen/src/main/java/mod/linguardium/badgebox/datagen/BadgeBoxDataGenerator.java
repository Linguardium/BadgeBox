package mod.linguardium.badgebox.datagen;

import mod.linguardium.badgebox.common.BadgeBoxCommonInitializer;
import mod.linguardium.badgebox.common.client.BadgeBoxCommonClientInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BadgeBoxDataGenerator implements DataGeneratorEntrypoint, ModInitializer {
	@Override
	public void onInitialize() {
		BadgeBoxCommonInitializer.init();
		BadgeBoxCommonClientInitializer.initNow();
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModTagProvider::new);
		pack.addProvider(ModLangProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
