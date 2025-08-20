package draylar.identity.network;

import draylar.identity.Identity;
import draylar.identity.network.impl.IdentityCooldownC2SPacket;
import draylar.identity.network.impl.IdentityDurationC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class DmcNetworkHandler {

	public static Identifier IDENTITY_COOLDOWN = new Identifier(Identity.MOD_ID, "identity_cooldown");
	public static Identifier IDENTITY_DURATION = new Identifier(Identity.MOD_ID, "identity_duration");
	
	public static void registerC2SPackets() {
		
		ServerPlayNetworking.registerGlobalReceiver(IDENTITY_COOLDOWN, IdentityCooldownC2SPacket::receive);
		ServerPlayNetworking.registerGlobalReceiver(IDENTITY_DURATION, IdentityDurationC2SPacket::receive);
		
	}
	
}
