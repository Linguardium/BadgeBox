package mod.linguardium.badgebox.neoforge;

import mod.linguardium.badgebox.common.BadgeBoxCommonInitializer;
import mod.linguardium.badgebox.common.client.screen.BadgeBoxContainerScreen;
import mod.linguardium.badgebox.common.registration.ModScreenHandlerTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(dist = {Dist.CLIENT},value= BadgeBoxCommonInitializer.MOD_ID)
@EventBusSubscriber(value = Dist.CLIENT,modid = BadgeBoxCommonInitializer.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class BadgeBoxClientNeoForge {
    public BadgeBoxClientNeoForge() {
        // Arch API bugs but wont let us use its client setup events
        //BadgeBoxCommonClientInitializer.init();

    }

    // TODO: Check if arch is fixed yet
    // Temporary until arch fixes client setup event bug and menu registry bug
    @SubscribeEvent
    private static void screenRegistration(RegisterMenuScreensEvent event) {
            event.register(ModScreenHandlerTypes.BADGE_BOX_SCREEN_HANDLER_TYPE.get(), BadgeBoxContainerScreen::new);
    }

}
