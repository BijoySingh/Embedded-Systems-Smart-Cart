package com.bijoyskochar.smartcart.utils;

import android.content.Context;

import com.github.bijoysingh.starter.util.PreferenceManager;

/**
 * Created by BijoySingh on 3/23/2016.
 */
public class Preferences extends PreferenceManager {

    public Preferences(Context context) {
        super(context);
    }

    @Override
    public String getPreferencesFolder() {
        return "SMART_CART";
    }

    public class Keys {
        public static final String NAME = "NAME";
        public static final String PICTURE = "PICTURE";
        public static final String ID = "ID";
        public static final String EMAIL = "EMAIL";
        public static final String LOGGED_IN = "LOGGED_IN";
    }

    public void setProfile(String id, String name, String picture, String email) {
        save(Keys.ID, id);
        save(Keys.PICTURE, picture);
        save(Keys.NAME, name);
        save(Keys.EMAIL, email);
        save(Keys.LOGGED_IN, true);
    }

    public void logout() {
        save(Keys.LOGGED_IN, false);
    }

    public Boolean isLoggedIn() {
        return load(Keys.LOGGED_IN, false);
    }

    public String getName() {
        return load(Keys.NAME, "");
    }

    public String getPicture() {
        return load(Keys.PICTURE, "");
    }
}
