package baguchan.breeze_soda.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SodaRecipeSerializer<T extends SodaRecipe> implements RecipeSerializer<T> {
    private final Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public SodaRecipeSerializer(SodaRecipeSerializer.Factory<T> p_250090_) {
        this.factory = p_250090_;
        this.codec = RecordCodecBuilder.mapCodec(
                p_311736_ -> p_311736_.group(CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CraftingRecipe::category),
                                Ingredient.CODEC.fieldOf("ingredient").forGetter(SodaRecipe::getIngredient))
                        .apply(p_311736_, p_250090_::create)
        );
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    private T fromNetwork(RegistryFriendlyByteBuf p_320282_) {
        CraftingBookCategory cookingbookcategory = p_320282_.readEnum(CraftingBookCategory.class);
        Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(p_320282_);
        return this.factory.create(cookingbookcategory, ingredient);
    }

    private void toNetwork(RegistryFriendlyByteBuf p_320422_, T p_320933_) {
        p_320422_.writeEnum(p_320933_.category());
        Ingredient.CONTENTS_STREAM_CODEC.encode(p_320422_, p_320933_.getIngredient());
    }


    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    @FunctionalInterface
    public interface Factory<T extends SodaRecipe> {
        T create(CraftingBookCategory category, Ingredient ingredient);
    }
}
