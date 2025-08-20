package draylar.identity.sound;

import draylar.identity.Identity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModSounds {
	
	public static final SoundEvent CHANGE_IDENTITY = registerSoundEvent("change_identity");
	
	private static SoundEvent registerSoundEvent(String name) {
		Identifier id = Identity.id(name);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
		
	}
	
	public static void registerSounds() {
		
		Identity.LOGGER.info("Registering Sounds for " + Identity.MOD_ID);
		
	}
	
	public static void playSoundForPlayerAndNearbyPlayers(World world, BlockPos position, SoundEvent sound, SoundCategory category, float volume, float pitch, float range) {

		Vec3d soundPosition = Vec3d.ofCenter(position); // Converts BlockPos to Vec3d at center

        world.getPlayers().forEach(player -> {
            if (player.squaredDistanceTo(soundPosition) < range * range) player.playSound(sound, category, volume, pitch);
            //world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.MASTER, volume, pitch);
        });
    }
	
}
