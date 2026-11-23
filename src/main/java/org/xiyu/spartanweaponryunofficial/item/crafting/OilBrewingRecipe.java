package org.xiyu.spartanweaponryunofficial.item.crafting;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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
import org.xiyu.spartanweaponryunofficial.api.OilEffects;
import org.xiyu.spartanweaponryunofficial.api.oil.OilEffect;
import org.xiyu.spartanweaponryunofficial.init.ModItems;
import org.xiyu.spartanweaponryunofficial.util.OilHelper;

import java.util.ArrayList;
import java.util.List;

public class OilBrewingRecipe extends BrewingRecipe {
    private static final List<OilMix> VALID_MIXES = new ArrayList<>();

    public static final MapCodec<OilBrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                    PotionIngredient.MAP_CODEC.fieldOf("input").forGetter(BrewingRecipe::getInput),
                    PotionIngredient.MAP_CODEC.fieldOf("reagent").forGetter(BrewingRecipe::getReagent),
                    ItemStackTemplate.CODEC.fieldOf("output").forGetter(BrewingRecipe::getOutput)
            ).apply(i, OilBrewingRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, OilBrewingRecipe> STREAM_CODEC = StreamCodec.composite(
            PotionIngredient.STREAM_CODEC, BrewingRecipe::getInput,
            PotionIngredient.STREAM_CODEC, BrewingRecipe::getReagent,
            ItemStackTemplate.STREAM_CODEC, BrewingRecipe::getOutput,
            OilBrewingRecipe::new);

    public OilBrewingRecipe(PotionIngredient input, PotionIngredient reagent, ItemStackTemplate output) {
        super(input, reagent, output);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public @NotNull RecipeSerializer<BrewingRecipe> getSerializer() {
        return (RecipeSerializer) ModRecipeSerializers.OIL_BREWING.get();
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
        return input.is(ModItems.WEAPON_OIL.get());
    }

    public boolean isIngredient(@NotNull ItemStack ingredient) {
        for (OilMix mix : VALID_MIXES) {
            if (mix.brewingIngredient.test(ingredient))
                return true;
        }
        return false;
    }

    public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
        if (this.isInput(input) && this.isIngredient(ingredient)) {
            OilEffect effect = OilHelper.getOilFromStack(input);
            for (OilMix mix : VALID_MIXES) {
                if (mix.from == effect && mix.brewingIngredient.test(ingredient))
                    return OilHelper.makeOilStack(mix.to);
            }
        }

        return ItemStack.EMPTY;
    }

    public static int getBrewingSteps(OilEffect effect) {
        OilEffect currentEffect = effect;    // The effect to check
        int steps = 0;
        do {
            int currentSteps = steps;
            for (OilMix mix : VALID_MIXES) {
                if (currentEffect == mix.to) {
                    steps++;
                    currentEffect = mix.from;
                    break;
                }
            }
            // A little contingency plan in case that an oil recipe isn't derived from the base oil recipe or has no recipe. This will display as 'Steps: ???' in JEI
            if (currentSteps == steps)
                return Integer.MAX_VALUE;
        } while (currentEffect != OilEffects.NONE.get());
        return steps;
    }

    public static void clearMixes() {
        VALID_MIXES.clear();
    }

    public static void addBaseOilMix(Ingredient ingredientIn, OilEffect oilEffectOut) {
        VALID_MIXES.add(new OilMix(OilEffects.NONE.get(), ingredientIn, oilEffectOut));
    }

    public static void addOilMix(OilEffect oilEffectIn, Ingredient ingredientIn, OilEffect oilEffectOut) {
        VALID_MIXES.add(new OilMix(oilEffectIn, ingredientIn, oilEffectOut));
    }

    public static List<OilMix> getValidMixes() {
        return ImmutableList.copyOf(VALID_MIXES);
    }

    public static class OilMix {
        public final OilEffect from, to;
        public final Ingredient brewingIngredient;

        public OilMix(OilEffect oilEffectIn, Ingredient ingredientIn, OilEffect oilEffectOut) {
            this.from = oilEffectIn;
            this.brewingIngredient = ingredientIn;
            this.to = oilEffectOut;
        }
    }
}
