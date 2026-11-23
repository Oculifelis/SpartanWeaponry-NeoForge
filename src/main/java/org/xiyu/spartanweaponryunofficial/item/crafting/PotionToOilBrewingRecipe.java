package org.xiyu.spartanweaponryunofficial.item.crafting;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.BrewingInput;
import net.minecraft.world.item.crafting.BrewingRecipe;
import net.minecraft.world.item.crafting.PotionIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.xiyu.spartanweaponryunofficial.init.ModRecipeSerializers;
import org.jetbrains.annotations.NotNull;
import org.xiyu.spartanweaponryunofficial.util.Config;
import org.xiyu.spartanweaponryunofficial.util.OilHelper;

public class PotionToOilBrewingRecipe extends BrewingRecipe {
    public static final MapCodec<PotionToOilBrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                    PotionIngredient.MAP_CODEC.fieldOf("input").forGetter(BrewingRecipe::getInput),
                    PotionIngredient.MAP_CODEC.fieldOf("reagent").forGetter(BrewingRecipe::getReagent),
                    ItemStackTemplate.CODEC.fieldOf("output").forGetter(BrewingRecipe::getOutput)
            ).apply(i, PotionToOilBrewingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PotionToOilBrewingRecipe> STREAM_CODEC = StreamCodec.composite(
            PotionIngredient.STREAM_CODEC, BrewingRecipe::getInput,
            PotionIngredient.STREAM_CODEC, BrewingRecipe::getReagent,
            ItemStackTemplate.STREAM_CODEC, BrewingRecipe::getOutput,
            PotionToOilBrewingRecipe::new);

    public PotionToOilBrewingRecipe(PotionIngredient input, PotionIngredient reagent, ItemStackTemplate output) {
        super(input, reagent, output);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public @NotNull RecipeSerializer<BrewingRecipe> getSerializer() {
        return (RecipeSerializer) ModRecipeSerializers.POTION_TO_OIL_BREWING.get();
    }

    @Override
    public boolean matches(@NotNull BrewingInput brewingInput) {
        return super.matches(brewingInput) && !this.getOutput(brewingInput.input(), brewingInput.reagent()).isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull BrewingInput brewingInput) {
        return this.getOutput(brewingInput.input(), brewingInput.reagent());
    }

    public boolean isInput(ItemStack input) {
        Potion inputPotion = input.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                .potion().map(Holder::value).orElse(null);
        return inputPotion != null && !Config.INSTANCE.disableOilRecipes.get() && input.is(Items.POTION) && OilHelper.isValidPotion(inputPotion);
    }

    public boolean isIngredient(@NotNull ItemStack ingredient) {
        return !Config.INSTANCE.disableOilRecipes.get() && ingredient.is(Items.SLIME_BALL);
    }

    public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
        if (this.isInput(input) && this.isIngredient(ingredient)) {
            Potion inputPotion = input.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                    .potion().map(Holder::value).orElse(null);
            if (inputPotion != null)
                return OilHelper.makePotionOilStack(inputPotion);
        }

        return ItemStack.EMPTY;
    }
}
