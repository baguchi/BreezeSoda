package baguchan.breeze_soda.recipe;

import baguchan.breeze_soda.BreezeSoda;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, BreezeSoda.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, BreezeSoda.MODID);


    public static final Supplier<RecipeType<SodaRecipe>> RECIPETYPE_SODA = RECIPE_TYPES.register("soda", () -> register(ResourceLocation.fromNamespaceAndPath(BreezeSoda.MODID, "soda")));

    public static final Supplier<RecipeSerializer<SodaRecipe>> SODA = RECIPE_SERIALIZER_TYPES.register(
            "soda", () -> new SodaRecipeSerializer<>(SodaRecipe::new)
    );

    static <T extends Recipe<?>> RecipeType<T> register(final ResourceLocation p_44120_) {
        return new RecipeType<T>() {
            public String toString() {
                return p_44120_.toString();
            }
        };
    }
}