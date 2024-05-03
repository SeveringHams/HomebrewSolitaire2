package com.severinghams.homebrewsolitaire;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ComponentActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;

import com.severinghams.homebrewsolitaire.core.BaseSingleDeckGameObject;
import com.severinghams.homebrewsolitaire.core.KlondikeGameObject;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                //.penaltyDeath()
                .build());
        super.onCreate(bundle);
        this.setContentView(R.layout.game_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}