package com.elmahalwy.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.elmahalwy.bakingapp.Activties.DetailsActivity;
import com.elmahalwy.bakingapp.Utils.Constants;


public class IngredientDetailsListService extends IntentService {

    public IngredientDetailsListService() {
        super("IngredientDetailsListService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.INGREDIENT_SERVICE_ACTION.equals(action)) {
                handleActionChangeIngredientList();
            }
        }
    }

    public static boolean startActionChangeIngredientList(Context context) {
        Intent intent = new Intent(context, IngredientDetailsListService.class);
        intent.setAction(Constants.INGREDIENT_SERVICE_ACTION);

        // a temporary solution for Android 8.0
        try {
            context.startService(intent);
            return true;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void handleActionChangeIngredientList() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateIngredientWidgets(this, appWidgetManager, appWidgetIds, DetailsActivity.title, DetailsActivity.INGREDIENTS);
    }
}
