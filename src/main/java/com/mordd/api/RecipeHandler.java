package com.mordd.api;

import gregapi.data.CS;
import gregapi.data.FL;
import gregapi.data.MD;
import gregapi.data.MT;
import gregapi.util.ST;
import gregapi.recipes.Recipe;
import minetweaker.MineTweakerAPI;
import net.minecraftforge.fluids.FluidStack;

public class RecipeHandler {
    public static void defineExtendRecipe() {
        try {
            final Recipe.RecipeMap recipeMap = Recipe.RecipeMap.RECIPE_MAPS.get("gt.recipe.fusionreactor");

            final Recipe recipe = new Recipe(
                    false,
                    true,
                    true,
                    ST.array(ST.make(MD.GAPI, "gt.integrated_circuit", 0, 2)),
                    CS.ZL_IS,
                    CS.NI,
                    new long[]{10000},
                    new FluidStack[]{FL.Helium.make(500),new FluidStack(FL.createGas(MT.He_3),500)},
                    new FluidStack[]{new FluidStack(FL.createMolten(MT.Be_7),72)},
                    1956,
                    -8192,
                    256376832L
            );

            recipeMap.addRecipe(recipe, false, false, false);

            final Recipe recipe2 = new Recipe(
                    false,
                    true,
                    true,
                    ST.array(ST.make(MD.GAPI, "gt.integrated_circuit", 0, 2)),
                    CS.ZL_IS,
                    CS.NI,
                    new long[]{10000},
                    new FluidStack[]{FL.Hydrogen.make(2000),new FluidStack(FL.createMolten(MT.Be),144)},
                    new FluidStack[]{new FluidStack(FL.createMolten(MT.Be_7),144),new FluidStack(FL.createGas(MT.D),2000)},
                    1956,
                    -8192,
                    256376832L
            );

            recipeMap.addRecipe(recipe2, false, false, false);
        }
        catch (Exception e) {
            MineTweakerAPI.logError(
                    "[VoidWayfarer] Custom Extend Recipe " + "gt.recipe.fusionreactor" + " Exploded, Reason Is:\n", e);
        }
    }
}