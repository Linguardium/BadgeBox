package mod.linguardium.badgebox.fabric;

import mod.linguardium.badgebox.common.BadgeBoxCommonInitializer;
import net.fabricmc.api.ModInitializer;

public class BadgeBoxFabricInitializer implements ModInitializer {

	@Override
	public void onInitialize() {
		BadgeBoxCommonInitializer.init();
	}
}