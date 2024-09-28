package baguchan.breeze_soda.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SodaRecipe extends CustomRecipe {
    private final Ingredient ingredient;
    private static final Ingredient BREEZE_INGREDIENT = Ingredient.of(Items.WIND_CHARGE);

    public SodaRecipe(CraftingBookCategory p_250134_, Ingredient ingredient) {
        super(p_250134_);
        this.ingredient = ingredient;
    }

    public boolean matches(CraftingInput p_345559_, Level p_43855_) {
        boolean flag = false;
        int i = 0;

        for (int j = 0; j < p_345559_.size(); j++) {
            ItemStack itemstack = p_345559_.getItem(j);
            if (!itemstack.isEmpty()) {
                if (ingredient.test(itemstack)) {
                    if (flag) {
                        return false;
                    }

                    if (itemstack.get(DataComponents.POTION_CONTENTS) == null || itemstack.get(ModDataComponents.SODA.get()) != null) {
                        return false;
                    }

                    flag = true;
                } else if (BREEZE_INGREDIENT.test(itemstack)) {
                    if (++i > 1) {
                        return false;
                    }
                }
            }
        }

        return flag && i >= 1;
    }

    public ItemStack assemble(CraftingInput p_345921_, HolderLookup.Provider p_335560_) {
        ItemStack itemstack = ItemStack.EMPTY;
        List<MobEffectInstance> newMobEffect = new ArrayList<>();

        for (int j = 0; j < p_345921_.size(); j++) {
            ItemStack itemstack1 = p_345921_.getItem(j);
            if (!itemstack1.isEmpty()) {
                if (itemstack1.get(DataComponents.POTION_CONTENTS) != null) {

                    itemstack = itemstack1;
                }
            }
        }
        Iterable<MobEffectInstance> mobEffect = itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getAllEffects();
        mobEffect.forEach(mobEffectInstance -> {
            newMobEffect.add(new MobEffectInstance(mobEffectInstance.getEffect(), (int) (mobEffectInstance.getDuration() * 0.6F), mobEffectInstance.getAmplifier() + 1));
        });

        ItemStack copy = itemstack.copy();
        copy.set(ModDataComponents.SODA, true);

        copy.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), newMobEffect));
        return copy;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public boolean canCraftInDimensions(int p_43844_, int p_43845_) {
        return p_43844_ * p_43845_ >= 2;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_335481_) {
        return new ItemStack(Items.POTION);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SODA.get();
    }
}
