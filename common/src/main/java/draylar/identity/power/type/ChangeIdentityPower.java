package draylar.identity.power.type;

import draylar.identity.Identity;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class ChangeIdentityPower extends Power {

	public ChangeIdentityPower(PowerType<?> type, LivingEntity entity) {
		super(type, entity);
		// TODO Auto-generated constructor stub
	}

	public static PowerFactory<Power> getFactory() {
		return new PowerFactory<>(Identity.id("changeidentity"), 
				new SerializableData(),
				data -> 
					(powerType, livingEntity) -> new ChangeIdentityPower(powerType,livingEntity))
				.allowCondition();
	}
	
}
