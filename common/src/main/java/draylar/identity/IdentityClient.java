package draylar.identity;

import java.util.HashSet;
import java.util.Set;

import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import draylar.identity.ability.AbilityOverlayRenderer;
import draylar.identity.api.ApplicablePacket;
import draylar.identity.api.model.EntityArms;
import draylar.identity.api.model.EntityUpdaters;
import draylar.identity.impl.join.ClientPlayerJoinHandler;
import draylar.identity.impl.tick.AbilityKeyPressHandler;
import draylar.identity.impl.tick.MenuKeyPressHandler;
import draylar.identity.network.ClientNetworking;

public class IdentityClient {

    

    private static final Set<ApplicablePacket> SYNC_PACKET_QUEUE = new HashSet<>();

    public void initialize() {

        // Register client-side event handlers
        EntityUpdaters.init();
        AbilityOverlayRenderer.register();
        EntityArms.init();

        // Register event handlers
        ClientTickEvent.CLIENT_PRE.register(new MenuKeyPressHandler());
        ClientTickEvent.CLIENT_PRE.register(new AbilityKeyPressHandler());
        ClientNetworking.registerPacketHandlers();
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(new ClientPlayerJoinHandler());
    }

    // We do this because the Architectury "player log in" network event runs before MinecraftClient#player exists.
    public static Set<ApplicablePacket> getSyncPacketQueue() {
        return SYNC_PACKET_QUEUE;
    }
}
