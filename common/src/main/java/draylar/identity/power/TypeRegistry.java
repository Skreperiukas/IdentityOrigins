package draylar.identity.power;

import draylar.identity.power.type.ChangeIdentityPower;
import draylar.identity.util.ApoliRegistryHelper;

public class TypeRegistry {

	public static void registerTypes() {
		ApoliRegistryHelper.registerPowerFactory(ChangeIdentityPower.getFactory());
	}
	
}
