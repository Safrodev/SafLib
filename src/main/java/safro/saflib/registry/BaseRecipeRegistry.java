package safro.saflib.registry;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BaseRecipeRegistry {
    // Set this to your modid in the registry init function
    protected static String MODID = "";

    protected static <T extends Recipe<?>> RecipeType<T> register(String id) {
        return Registry.register(Registries.RECIPE_TYPE, Identifier.of(MODID, id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }

    protected static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(MODID, id), serializer);
    }
}
