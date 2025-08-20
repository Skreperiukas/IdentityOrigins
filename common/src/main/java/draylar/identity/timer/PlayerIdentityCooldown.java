package draylar.identity.timer;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import draylar.identity.ModComponents;
import draylar.identity.impl.PlayerTimer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerIdentityCooldown implements PlayerTimer, AutoSyncedComponent, ServerTickingComponent {
    private int cooldown = -1;
    private final PlayerEntity player;
    
    public PlayerIdentityCooldown(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void setCooldown(int ticks) {
        this.cooldown = ticks;
        ModComponents.IDENTITY_COOLDOWN.sync(player);
        // System.out.println("[PlayerIdentityCooldown] Timer set to: " + cooldown + "ticks"); // debug
    }
    
    @Override
    public int getCooldown() {
        return cooldown;
    }

    @Override
	public void serverTick() {
    	
    	if(cooldown % 20 == 0 && cooldown >= 0) {
    		ModComponents.IDENTITY_COOLDOWN.sync(player);
    		//player.sendMessage(Text.literal("Cooldown: " + cooldown + "ticks")); // debug
    	}
    	
    	if (cooldown >= 0) cooldown--; // Decrease cooldown each tick
    	
	}
    
    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity player) {
        buf.writeVarInt(this.cooldown); // only synchronize the information you need!
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        this.cooldown = buf.readVarInt();
    }
    
    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return player == this.player; // only sync with the provider itself
    }
    
    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt("identityCooldown", cooldown);
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        if (nbt.contains("identityCooldown")) {
            this.cooldown = nbt.getInt("identityCooldown");
        }
    }
}