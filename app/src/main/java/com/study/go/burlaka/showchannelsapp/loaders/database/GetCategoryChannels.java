package com.study.go.burlaka.showchannelsapp.loaders.database;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.study.go.burlaka.showchannelsapp.data.repo.CategoryRepo;
import com.study.go.burlaka.showchannelsapp.ui.show.category.recyclerview.Ingredient;
import com.study.go.burlaka.showchannelsapp.ui.show.category.recyclerview.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Operator on 25.09.2016.
 */
public class GetCategoryChannels extends AsyncTaskLoader <List<Recipe>> {


    private static final String TAG ="GetCategoryDB" ;
    ArrayList <String> categoryList ;
    //List <Recipe> recipe;

    public GetCategoryChannels(Context context, ArrayList <String> categoryList) {
        super(context);
        this.categoryList = categoryList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }


    @Override
    public List<Recipe> loadInBackground() {
        Log.d(TAG, "in Begin category loader");
        for (String category: categoryList){
            Log.d(TAG, "My category:"+category);
        }
        //recipe = new ArrayList<>();
       /* Ingredient beef = new Ingredient("matrix_neo");
        Ingredient cheese = new Ingredient("matrix_neo");
        Ingredient salsa = new Ingredient("matrix_neo");
        Ingredient tortilla = new Ingredient("matrix_neo");
        Ingredient ketchup = new Ingredient("matrix_neo");
        Ingredient bun = new Ingredient("matrix_neo");

        Recipe taco = new Recipe("matrix", Arrays.asList(beef, cheese, salsa, tortilla));
        Recipe quesadilla = new Recipe("matrix", Arrays.asList(cheese, tortilla));
        Recipe burger = new Recipe("matrix", Arrays.asList(beef, cheese, ketchup, bun));
        final List<Recipe> recipes = Arrays.asList(taco, quesadilla, burger);*/

        // TODO: 26.09.2016 ---/**--- create list of Ingredient

        Recipe recipe;
        List<Recipe> recipeList =new ArrayList<>();
        final List<Recipe> finalRecipes;
                //final List<Recipe> finalrecipes

        if (categoryList==null)return  new ArrayList<>();

        //ArrayList<Ingredient> categoryChannels = new ArrayList<>();
        for (String category: categoryList){
            //ingredient = new Ingredient(category);
            Log.d(TAG, "get from list: "+category);

            recipe = new Recipe( category,  getChannelsFromDB (category));
            recipeList.add(recipe);
        }



        //TODO: 26.09.2016 create request to get channels of category
        //TODO: 26.09.2016 crete list of recipe

       return recipeList;

    }


    public ArrayList<Ingredient> getChannelsFromDB(String category) {
        CategoryRepo cr = new CategoryRepo();
        ArrayList<Ingredient> categoryChannels = new ArrayList<>();

        ArrayList <String> channels;
        Ingredient ingredient;
        channels = cr.getChannels4mCategory(category);

        for (String channel : channels ){
            ingredient = new Ingredient(channel);
            categoryChannels.add(ingredient);
        }

        return categoryChannels;
    }
}
