package draylar.identity.impl;

import java.util.Set;

import org.jetbrains.annotations.Nullable;

import draylar.identity.api.variant.IdentityType;
import net.minecraft.entity.LivingEntity;

public interface PlayerDataProvider {

    Set<IdentityType<?>> getUnlocked();
    void setUnlocked(Set<IdentityType<?>> unlocked);

    Set<IdentityType<?>> getFavorites();
    void setFavorites(Set<IdentityType<?>> favorites);

    int getRemainingHostilityTime();
    void setRemainingHostilityTime(int max);

    int getAbilityCooldown();
    void setAbilityCooldown(int cooldown);

    LivingEntity getIdentity();
    void setIdentity(@Nullable LivingEntity identity);
    boolean updateIdentity(@Nullable LivingEntity identity);

    IdentityType<?> getIdentityType();
}
