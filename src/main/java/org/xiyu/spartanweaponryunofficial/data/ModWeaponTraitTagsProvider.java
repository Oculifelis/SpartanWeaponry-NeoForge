package org.xiyu.spartanweaponryunofficial.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanweaponryunofficial.ModSpartanWeaponry;
import org.xiyu.spartanweaponryunofficial.api.WeaponTraits;
import org.xiyu.spartanweaponryunofficial.api.tags.ModWeaponTraitTags;
import org.xiyu.spartanweaponryunofficial.api.trait.WeaponTrait;

import java.util.concurrent.CompletableFuture;

public class ModWeaponTraitTagsProvider extends TagsProvider<WeaponTrait> {

    public ModWeaponTraitTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registry) {
        super(output, WeaponTraits.REGISTRY_KEY, registry);
    }

    @Override
    public @NotNull String getName() {
        return ModSpartanWeaponry.NAME + " Weapon Trait Tags";
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider registry) {
        this.vtag(ModWeaponTraitTags.DAGGER).add(WeaponTraits.THROWABLE.get(), WeaponTraits.DAMAGE_BONUS_BACKSTAB.get());
        this.vtag(ModWeaponTraitTags.PARRYING_DAGGER).add(WeaponTraits.BLOCK_MELEE.get());
        this.vtag(ModWeaponTraitTags.LONGSWORD).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.SWEEP_1.get());
        this.vtag(ModWeaponTraitTags.KATANA).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.DAMAGE_BONUS_CHEST.get(), WeaponTraits.SWEEP_1.get());
        this.vtag(ModWeaponTraitTags.SABER).add(WeaponTraits.DAMAGE_ABSORB.get(), WeaponTraits.DAMAGE_BONUS_CHEST.get(), WeaponTraits.SWEEP_1.get());
        this.vtag(ModWeaponTraitTags.RAPIER).add(WeaponTraits.DAMAGE_ABSORB.get(), WeaponTraits.DAMAGE_BONUS_UNARMORED.get());
        this.vtag(ModWeaponTraitTags.GREATSWORD).add(WeaponTraits.TWO_HANDED_2.get(), WeaponTraits.REACH_1.get(), WeaponTraits.SWEEP_3.get());
        this.vtag(ModWeaponTraitTags.CLUB).add(WeaponTraits.NAUSEA.get());
        this.vtag(ModWeaponTraitTags.CESTUS).add(WeaponTraits.QUICK_STRIKE.get());
        this.vtag(ModWeaponTraitTags.BATTLE_HAMMER).add(WeaponTraits.KNOCKBACK.get(), WeaponTraits.NAUSEA.get(), WeaponTraits.HAMMER_SLAM.get());
        this.vtag(ModWeaponTraitTags.WARHAMMER).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.ARMOR_PIERCING.get());
        this.vtag(ModWeaponTraitTags.SPEAR).add(WeaponTraits.REACH_2.get());
        this.vtag(ModWeaponTraitTags.HALBERD).add(WeaponTraits.TWO_HANDED_2.get(), WeaponTraits.REACH_1_5.get(), WeaponTraits.SHIELD_BREACH.get());
        this.vtag(ModWeaponTraitTags.PIKE).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.REACH_2_5.get());
        this.vtag(ModWeaponTraitTags.LANCE).add(WeaponTraits.REACH_2.get(), WeaponTraits.DAMAGE_BONUS_RIDING.get(), WeaponTraits.SWEEP_1.get());
        this.vtag(ModWeaponTraitTags.THROWING_KNIFE).add(WeaponTraits.DAMAGE_BONUS_THROWN_1.get());
        this.vtag(ModWeaponTraitTags.TOMAHAWK).add(WeaponTraits.DAMAGE_BONUS_THROWN_1.get());
        this.vtag(ModWeaponTraitTags.JAVELIN).add(WeaponTraits.DAMAGE_BONUS_THROWN_2.get());
        this.vtag(ModWeaponTraitTags.BOOMERANG);
        this.vtag(ModWeaponTraitTags.BATTLEAXE).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.VERSATILE_AXE.get());
        this.vtag(ModWeaponTraitTags.FLANGED_MACE).add(WeaponTraits.DAMAGE_BONUS_UNDEAD.get());
        this.vtag(ModWeaponTraitTags.GLAIVE).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.REACH_1_5.get(), WeaponTraits.SWEEP_2.get());
        this.vtag(ModWeaponTraitTags.QUARTERSTAFF).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.SWEEP_2.get(), WeaponTraits.REACH_1.get());
        this.vtag(ModWeaponTraitTags.SCYTHE).add(WeaponTraits.DAMAGE_BONUS_HEAD.get(), WeaponTraits.DECAPITATE.get(), WeaponTraits.REACH_1.get());

        this.vtag(ModWeaponTraitTags.WOOD);
        this.vtag(ModWeaponTraitTags.STONE);
        this.vtag(ModWeaponTraitTags.LEATHER);
        this.vtag(ModWeaponTraitTags.COPPER);
        this.vtag(ModWeaponTraitTags.IRON);
        this.vtag(ModWeaponTraitTags.GOLD);
        this.vtag(ModWeaponTraitTags.DIAMOND);
        this.vtag(ModWeaponTraitTags.NETHERITE).add(WeaponTraits.FIREPROOF.get());

        this.vtag(ModWeaponTraitTags.TIN);
        this.vtag(ModWeaponTraitTags.BRONZE);
        this.vtag(ModWeaponTraitTags.STEEL);
        this.vtag(ModWeaponTraitTags.SILVER).add(WeaponTraits.DAMAGE_BONUS_UNDEAD.get());
        this.vtag(ModWeaponTraitTags.LEAD).add(WeaponTraits.HEAVY_2.get());
        this.vtag(ModWeaponTraitTags.ELECTRUM);
        this.vtag(ModWeaponTraitTags.NICKEL);
        this.vtag(ModWeaponTraitTags.INVAR);
        this.vtag(ModWeaponTraitTags.CONSTANTAN);
        this.vtag(ModWeaponTraitTags.PLATINUM);
        this.vtag(ModWeaponTraitTags.ALUMINUM).add(WeaponTraits.LIGHTWEIGHT_2.get());
    }

    private ValueTagAppender<WeaponTrait> vtag(net.minecraft.tags.TagKey<WeaponTrait> key) {
        return new ValueTagAppender<>(this.tag(key), value -> WeaponTraits.REGISTRY.getRegistry().get().getResourceKey(value).orElseThrow());
    }
}
