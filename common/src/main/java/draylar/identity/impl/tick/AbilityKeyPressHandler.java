package draylar.identity.impl.tick;

import dev.architectury.event.events.client.ClientTickEvent;
import draylar.identity.ability.AbilityRegistry;
import draylar.identity.api.PlayerIdentity;
import draylar.identity.network.ClientNetworking;
import io.github.apace100.origins.OriginsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;

public class AbilityKeyPressHandler implements ClientTickEvent.Client {

    @Override
    public void tick(MinecraftClient client) {
        assert client.player != null;

        if(OriginsClient.useSecondaryActivePowerKeybind.isPressed()) {
            // TODO: maybe the check should be on the server to allow for ability extension mods?
            // Only send the ability packet if the identity equipped by the player has one
            LivingEntity identity = PlayerIdentity.getIdentity(client.player);

            if(identity != null) {
                if(AbilityRegistry.has(identity.getType())) {
                    ClientNetworking.sendAbilityRequest();
                }
            }
        }
    }
}
