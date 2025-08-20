package draylar.identity.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.mob.SlimeEntity;

@Mixin(SlimeEntity.class)
public interface SlimeEntityAccessor {

    @Invoker
    void callSetSize(int size, boolean heal);
}
