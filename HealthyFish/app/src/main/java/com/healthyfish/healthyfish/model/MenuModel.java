package com.healthyfish.healthyfish.model;

import android.view.Menu;

/**
 * Created by User on 01-10-2016.
 */
public class MenuModel {
     Menu menu;
public  MenuModel(){}
    public MenuModel(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
