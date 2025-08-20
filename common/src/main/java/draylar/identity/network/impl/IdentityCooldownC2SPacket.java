package draylar.identity.network.impl;

import draylar.identity.ModComponents;
import draylar.identity.api.platform.IdentityConfig;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class IdentityCooldownC2SPacket {

	public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		
//		EntityType.COW.spawn((ServerWorld) player.getWorld(), null, (cow) -> {
//            // Set any properties on the CowEntity, such as health, custom name, etc.
//            cow.setCustomName(Text.literal("Balls"));
//            cow.setCustomNameVisible(true);
//        }, player.getBlockPos(), SpawnReason.TRIGGERED, true, true); // debug
		
		
		if(ModComponents.getIdentityCooldown(player) <= 0) ModComponents.setIdentityCooldown(player, IdentityConfig.getInstance().identityCooldown());
		
		
		
	}
	
}
