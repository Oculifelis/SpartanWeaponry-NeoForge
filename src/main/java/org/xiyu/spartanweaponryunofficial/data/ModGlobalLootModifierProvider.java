package org.xiyu.spartanweaponryunofficial.data;

// TODO: Find a way to allow custom loot conditions to be added to datagen (as well as getting Codecs right!)
/*import org.xiyu.spartanweaponryunofficial.ModSpartanWeaponry;
import org.xiyu.spartanweaponryunofficial.init.ModItems;
import org.xiyu.spartanweaponryunofficial.loot.ConfigLootCondition;
import org.xiyu.spartanweaponryunofficial.loot.DecapitateLootModifier;

import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider
{
	public ModGlobalLootModifierProvider(DataGenerator gen)
	{
		super(gen, ModSpartanWeaponry.ID);
	}

	@Override
	protected void start() 
	{
		add("player_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.PLAYER)).build()}, Items.PLAYER_HEAD));
		add("skeleton_drop_skull", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.SKELETON)).build()}, Items.SKELETON_SKULL));
		add("wither_skeleton_drop_skull", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.WITHER_SKELETON)).build()}, Items.WITHER_SKELETON_SKULL));
		add("zombie_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.ZOMBIE)).build()}, Items.ZOMBIE_HEAD));
		add("creeper_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.CREEPER)).build()}, Items.CREEPER_HEAD));
		add("ender_dragon_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.ENDER_DRAGON)).build()}, Items.DRAGON_HEAD));
		add("blaze_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.BLAZE)).build()}, ModItems.BLAZE_HEAD.get()));
		add("enderman_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.ENDERMAN)).build()}, ModItems.ENDERMAN_HEAD.get()));
		add("spider_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.SPIDER)).build()}, ModItems.SPIDER_HEAD.get()));
		add("cave_spider_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.CAVE_SPIDER)).build()}, ModItems.CAVE_SPIDER_HEAD.get()));
		add("piglin_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.PIGLIN)).build()}, ModItems.PIGLIN_HEAD.get()));
		add("zombified_piglin_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.ZOMBIFIED_PIGLIN)).build()}, ModItems.ZOMBIFIED_PIGLIN_HEAD.get()));
		add("husk_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.HUSK)).build()}, ModItems.HUSK_HEAD.get()));
		add("stray_drop_skull", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.STRAY)).build()}, ModItems.STRAY_SKULL.get()));
		add("drowned_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.DROWNED)).build()}, ModItems.DROWNED_HEAD.get()));
		add("pillager_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.PILLAGER)).build()}, ModItems.ILLAGER_HEAD.get()));
		add("vindicator_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.VINDICATOR)).build()}, ModItems.ILLAGER_HEAD.get()));
		add("illusioner_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.ILLUSIONER)).build()}, ModItems.ILLAGER_HEAD.get()));
		add("evoker_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.EVOKER)).build()}, ModItems.ILLAGER_HEAD.get()));
		add("witch_drop_head", new DecapitateLootModifier(new LootItemCondition[] { ConfigLootCondition.builder().build(), LootItemKilledByPlayerCondition.killedByPlayer().build(), 
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS, EntityPredicate.Builder.entity().of(net.minecraft.world.entity.EntityTypes.WITCH)).build()}, ModItems.WITCH_HEAD.get()));
	}
	
	@Override
	public String getName()
	{
		return ModSpartanWeaponry.NAME + " Global Loot Modifiers";
	}

}*/
