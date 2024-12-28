package mod.linguardium.badgebox.common.client;


import dev.architectury.event.events.client.ClientLifecycleEvent;
import mod.linguardium.badgebox.common.client.registration.ModHandledScreens;

import java.util.function.Consumer;

public class BadgeBoxCommonClientInitializer {
    // TODO: Check if arch is fixed yet
    public static void init() {
        init(ClientLifecycleEvent.CLIENT_SETUP::register);
    }
    public static void initNow() {
        ModHandledScreens.init();
    }
    public static void init(Consumer<ClientLifecycleEvent.ClientState> clientSetupEvent) {
        clientSetupEvent.accept(client-> {
            initNow();
        });
    }
}
