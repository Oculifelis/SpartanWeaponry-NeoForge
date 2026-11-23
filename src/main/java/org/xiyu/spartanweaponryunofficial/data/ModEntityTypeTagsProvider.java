package org.xiyu.spartanweaponryunofficial.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanweaponryunofficial.ModSpartanWeaponry;
import org.xiyu.spartanweaponryunofficial.api.tags.ModEntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registry) {
        super(output, registry);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider registry) {
        this.vtag(ModEntityTypeTags.CREEPERS).add(EntityTypes.CREEPER);
        this.vtag(ModEntityTypeTags.HUMANOIDS).addTag(EntityTypeTags.RAIDERS).add(EntityTypes.VILLAGER).add(EntityTypes.PLAYER);
        this.vtag(ModEntityTypeTags.ENDER).add(EntityTypes.ENDERMAN, EntityTypes.ENDER_DRAGON, EntityTypes.ENDERMITE, EntityTypes.SHULKER);
    }

    @Override
    public @NotNull String getName() {
        return ModSpartanWeaponry.NAME + ": Entity Type Tags";
    }

    private ValueTagAppender<EntityType<?>> vtag(net.minecraft.tags.TagKey<EntityType<?>> key) {
        return new ValueTagAppender<>(this.tag(key), value -> net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getResourceKey(value).orElseThrow());
    }
}
