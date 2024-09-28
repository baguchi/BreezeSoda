package baguchan.breeze_soda.compat;

import baguchan.breeze_soda.recipe.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

import java.util.ArrayList;
import java.util.List;

public class BreezeSodaRecipeMaker {
    public static final BreezeSodaRecipeMaker INSTANCE = new BreezeSodaRecipeMaker();

    public static List<RecipeHolder<CraftingRecipe>> createRecipes() {
        String group = "jei.wind_charge";
        Ingredient bottle = Ingredient.of(Items.GLASS_BOTTLE);
        Ingredient potion = Ingredient.of(Items.POTION);
        Ingredient breeze = Ingredient.of(Items.WIND_CHARGE);

        return getRegistry(Registries.POTION)
                .stream()
                .map(effect -> {
                    List<MobEffectInstance> mobEffect = effect.getEffects();
                    List<MobEffectInstance> newMobEffect = new ArrayList<>();
                    NonNullList<Ingredient> inputs = NonNullList.of(potion, bottle, breeze);
                    ItemStack output = new ItemStack(Items.POTION, 1);

                    mobEffect.forEach(mobEffectInstance -> {
                        newMobEffect.add(new MobEffectInstance(mobEffectInstance.getEffect(), (int) (mobEffectInstance.getDuration() * 0.6F), mobEffectInstance.getAmplifier() + 1));
                    });
                    output.set(ModDataComponents.SODA, true);
                    ResourceLocation resourceLocation = getRegistry(Registries.POTION).getKey(effect);

                    ResourceLocation id = ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "jei.potion." + resourceLocation.getPath());
                    CraftingRecipe recipe = new ShapelessRecipe(group, CraftingBookCategory.MISC, output, inputs);
                    return new RecipeHolder<>(id, recipe);
                })
                .toList();
    }

    private BreezeSodaRecipeMaker() {

    }

    public static <T> Registry<T> getRegistry(ResourceKey<? extends Registry<T>> key) {
        RegistryAccess registryAccess = getRegistryAccess();
        return registryAccess.registryOrThrow(key);
    }

    public static RegistryAccess getRegistryAccess() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new IllegalStateException("Could not get registry, registry access is unavailable because the level is currently null");
        }
        return level.registryAccess();
    }
}
