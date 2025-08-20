package draylar.identity.util;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public class ApoliRegistryHelper {

	public static void registerPowerFactory(PowerFactory<?> serializer) {
        Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }

    public static void registerPowerFactorySupplier(PowerFactorySupplier<?> supplier) {
        registerPowerFactory(supplier.createFactory());
    }
	
}

