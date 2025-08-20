package draylar.identity.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;

public interface PlayerTimer extends ComponentV3 {
	void setCooldown(int ticks);
    int getCooldown();
    void writeToNbt(NbtCompound nbt);
    void readFromNbt(NbtCompound nbt);
}
