package draylar.identity.timer;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import draylar.identity.ModComponents;
import draylar.identity.impl.PlayerTimer;
import draylar.identity.network.impl.SwapPackets;
import draylar.identity.util.TimeUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlayerIdentityDurationTimer implements PlayerTimer, AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {

	private int cooldown = -1;
    private final PlayerEntity player;
    
    public PlayerIdentityDurationTimer(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void setCooldown(int ticks) {
        this.cooldown = ticks;
        ModComponents.IDENTITY_DURATION.sync(player);
    }
    
    @Override
    public int getCooldown() {
        return cooldown;
    }
    
    @Override
	public void clientTick() {
		
    	if(cooldown % 20 == 0 && cooldown >= 0) player.sendMessage(Text.translatable("identity.time.left").append(Text.literal("" + TimeUtils.ticksToSeconds(ModComponents.getIdentityDuration(player)) + "s").formatted(Formatting.AQUA)), true);
    	
    	if(cooldown == 0) {
    		player.sendMessage(Text.of(""), true);
    		SwapPackets.sendSwapRequest(null);
    	}
		
	}
    
    @Override
	public void serverTick() {
    	
    	if((cooldown % 20 == 0 && cooldown >= 0) || (cooldown == -1)) ModComponents.IDENTITY_DURATION.sync(player);
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
        nbt.putInt("identityDuration", cooldown);
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        if (nbt.contains("identityDuration")) {
            this.cooldown = nbt.getInt("identityDuration");
        }
    }

}
