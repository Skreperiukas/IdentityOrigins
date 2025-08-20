package draylar.identity;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import draylar.identity.impl.PlayerTimer;
import draylar.identity.timer.PlayerIdentityCooldown;
import draylar.identity.timer.PlayerIdentityDurationTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {

	public static final ComponentKey<PlayerTimer> IDENTITY_COOLDOWN = 
	        ComponentRegistry.getOrCreate(new Identifier(Identity.MOD_ID+":identity_cooldown"), PlayerTimer.class);
	
	public static final ComponentKey<PlayerTimer> IDENTITY_DURATION = 
	        ComponentRegistry.getOrCreate(new Identifier(Identity.MOD_ID+":identity_duration"), PlayerTimer.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
//		registry.registerForPlayers(IDENTITY_COOLDOWN, player -> new PlayerIdentityCooldown(player), RespawnCopyStrategy.ALWAYS_COPY); // debug (test)
		registry.beginRegistration(PlayerEntity.class, ModComponents.IDENTITY_COOLDOWN).impl(PlayerIdentityCooldown.class).end(PlayerIdentityCooldown::new);
		registry.beginRegistration(PlayerEntity.class, ModComponents.IDENTITY_DURATION).impl(PlayerIdentityDurationTimer.class).end(PlayerIdentityDurationTimer::new);
	}
	
	
	// IDENTITY COOLDOWN
	public static int getIdentityCooldown(Entity provider) {
		return IDENTITY_COOLDOWN.get(provider).getCooldown();
	}
	
	public static void setIdentityCooldown(Entity provider, int ticks) {
		IDENTITY_COOLDOWN.get(provider).setCooldown(ticks);
	}
	
	// IDENTITY DURATION
	public static int getIdentityDuration(Entity provider) {
		return IDENTITY_DURATION.get(provider).getCooldown();
	}
	
	public static void setIdentityDuration(Entity provider, int ticks) {
		IDENTITY_DURATION.get(provider).setCooldown(ticks);
	}
	
	
	
	
	
}