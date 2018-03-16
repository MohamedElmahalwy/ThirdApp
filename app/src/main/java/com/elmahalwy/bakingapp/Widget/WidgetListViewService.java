package com.elmahalwy.bakingapp.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;



public class WidgetListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetListViewFactory(this.getApplicationContext());
    }
}
