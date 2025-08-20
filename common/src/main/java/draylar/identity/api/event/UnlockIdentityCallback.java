package draylar.identity.api.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import draylar.identity.api.variant.IdentityType;
import net.minecraft.server.network.ServerPlayerEntity;

public interface UnlockIdentityCallback {
    Event<UnlockIdentityCallback> EVENT = EventFactory.createEventResult(UnlockIdentityCallback.class);

    @SuppressWarnings("rawtypes")
	EventResult unlock(ServerPlayerEntity player, IdentityType type);
}
