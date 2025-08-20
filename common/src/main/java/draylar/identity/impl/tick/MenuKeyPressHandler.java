package draylar.identity.impl.tick;

import dev.architectury.event.events.client.ClientTickEvent;
import draylar.identity.api.platform.IdentityConfig;
import draylar.identity.power.type.ChangeIdentityPower;
import draylar.identity.screen.IdentityScreen;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.origins.OriginsClient;
import net.minecraft.client.MinecraftClient;

public class MenuKeyPressHandler implements ClientTickEvent.Client {

    @Override
    public void tick(MinecraftClient client) {
        assert client.player != null;
       
        
        if(OriginsClient.usePrimaryActivePowerKeybind.isPressed()) {
            if((IdentityConfig.getInstance().enableClientSwapMenu() || client.player.hasPermissionLevel(3))
                    && PowerHolderComponent.getPowers(client.player, ChangeIdentityPower.class).stream().anyMatch(Power::isActive)) {
                MinecraftClient.getInstance().setScreen(new IdentityScreen());
            }
        }
    }
}
