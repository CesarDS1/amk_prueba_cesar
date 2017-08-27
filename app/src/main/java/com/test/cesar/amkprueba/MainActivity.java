package com.test.cesar.amkprueba;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.test.cesar.amkprueba.model.Category;
import com.test.cesar.amkprueba.model.Constants;
import com.test.cesar.amkprueba.model.Result;
import com.test.cesar.amkprueba.model.SharedPreferencesManager;
import com.test.cesar.amkprueba.utils.ICommunication;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ICommunication {

    SharedPreferencesManager sharedPreferences;
    boolean isPLAYING;
    MediaPlayer mp;
    private TextView toolbarTitle;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        sharedPreferences = new SharedPreferencesManager(getBaseContext());

        if (findViewById(R.id.fragment_container) != null) {

            if (sharedPreferences.getAccessToken().isEmpty()) {
                replaceFragment(new LoginFragment());
                toolbarTitle.setText("Login");
            } else {
                replaceFragment(new CategoryFragment());
                toolbarTitle.setText("Categorias");
            }
        }
    }

    @Override
    public void onResponse(String callId, @Nullable Object data) {
        Bundle bundle = new Bundle();
        switch (callId) {
            case Constants.LOGIN_SUCCESS:
                replaceFragment(new CategoryFragment());
                MenuItem item = menu.findItem(R.id.logout);
                item.setVisible(true);
                toolbarTitle.setText("Categorias");
                break;
            case Constants.CATEGORY_ITEM:

                bundle.putString(Constants.CATEGORY, ((Category) data).getName());

                ArtistsFragment artistFragment = new ArtistsFragment();

                artistFragment.setArguments(bundle);

                addFragment(artistFragment, Constants.CATEGORY);
                toolbarTitle.setText(((Category) data).getName());
                break;
            case Constants.ARTIST_ITEM:

                bundle.putString(Constants.ARTIST, ((Result) data).getArtistName());

                ArtistSongsFragment artistSongFragment = new ArtistSongsFragment();

                artistSongFragment.setArguments(bundle);

                addFragment(artistSongFragment, Constants.ARTIST);

                toolbarTitle.setText(((Result) data).getArtistName());
                break;
            case Constants.SONG_ITEM:
                onRadioClick(((Result) data).getPreviewUrl());

                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);

        if (sharedPreferences.getAccessToken().isEmpty()) {
            MenuItem item = menu.findItem(R.id.logout);
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        stopPlaying();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                toolbarTitle.setText("Login");
                item.setVisible(false);

                sharedPreferences.setValueString(SharedPreferencesManager.ACCESS_TOKEN, "");
                replaceFragment(new LoginFragment());

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(tag);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    public void onRadioClick(String url) {

        if (!isPLAYING) {
            isPLAYING = true;
            mp = new MediaPlayer();
            try {
                mp.setDataSource(url);
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                Log.e("Main", "prepare() failed");
            }
        } else {
            isPLAYING = false;
            stopPlaying();
        }
    }

    private void stopPlaying() {
        if (mp != null) {
            mp.release();
        }
        mp = null;
    }

    public void updateToolbarTitle(String title) {

        toolbarTitle.setText(title);
    }

}
