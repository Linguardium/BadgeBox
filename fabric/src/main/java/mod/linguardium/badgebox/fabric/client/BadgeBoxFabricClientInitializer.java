package mod.linguardium.badgebox.fabric.client;

import mod.linguardium.badgebox.common.client.BadgeBoxCommonClientInitializer;
import net.fabricmc.api.ClientModInitializer;

public class BadgeBoxFabricClientInitializer implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// TODO: Check if arch is fixed yet
		BadgeBoxCommonClientInitializer.initNow();
	}

}