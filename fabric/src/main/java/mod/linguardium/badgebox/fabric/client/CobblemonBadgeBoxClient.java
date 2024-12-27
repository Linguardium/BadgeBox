package mod.linguardium.badgebox.fabric.client;

import mod.linguardium.badgebox.BadgeBoxCommonClient;
import net.fabricmc.api.ClientModInitializer;

public class CobblemonBadgeBoxClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BadgeBoxCommonClient.init();
	}
}