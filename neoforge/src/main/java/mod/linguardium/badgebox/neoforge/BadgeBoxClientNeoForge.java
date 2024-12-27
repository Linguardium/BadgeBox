package mod.linguardium.badgebox.neoforge;

import mod.linguardium.badgebox.BadgeBoxCommon;
import mod.linguardium.badgebox.BadgeBoxCommonClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(dist = {Dist.CLIENT},value= BadgeBoxCommon.MOD_ID)
public class BadgeBoxClientNeoForge {
    public BadgeBoxClientNeoForge() {
        // Run our common setup.
        BadgeBoxCommonClient.init();
    }
}
