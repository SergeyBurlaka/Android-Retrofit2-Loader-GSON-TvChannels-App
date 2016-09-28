package com.study.go.burlaka.showchannelsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.study.go.burlaka.showchannelsapp.data.constant.MyConstants;
import com.study.go.burlaka.showchannelsapp.loaders.ServerCategoryLoader;
import com.study.go.burlaka.showchannelsapp.loaders.ServerChannelLoader;
import com.study.go.burlaka.showchannelsapp.loaders.ServerProgramLoader;
import com.study.go.burlaka.showchannelsapp.ui.ShowCategory;
import com.study.go.burlaka.showchannelsapp.ui.ShowPrograms;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "GetCategory";
    private Toolbar toolbar;
    private Menu optionsMenu;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    //ProgressDialog loading = null;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_maim_tv);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        initDrawer();
        initToolBar();
        initFragmentOnCreate();
    }


    private void beginDBWork() {
        SharedPreferences prefs = getSharedPreferences(MyConstants.MY_DB, MODE_PRIVATE);
        int isEmpty = prefs.getInt(MyConstants.IS_EMPTY, MyConstants.EMPTY);
        if (isEmpty == MyConstants.EMPTY ) {
            Toast.makeText(MainActivity.this, "Begin get data from server", Toast.LENGTH_SHORT).show();
            onRefreshClick();
        }
    }


    private void initFragmentOnCreate() {
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = ShowPrograms.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        setFragment (fragment);
    }


   /*
   *      Create and init Naw Drawer View
   * */
    private void initDrawer() {
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.navigation_view);
        drawerToggle = setupDrawerToggle();
        drawerToggle.syncState();
        setupDrawerContent(nvDrawer);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.show_category_fragment:
                fragmentClass = ShowCategory.class;
                break;
            case R.id.show_pager_channels_fragment:
                fragmentClass = ShowPrograms.class;
                break;
            default:
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        setFragment (fragment);
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }


    /*
    *   Create and init toolbar and toolbar items
    * */
    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.tv_icon);
        getSupportActionBar().setTitle("  TV Channels ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_hamburger_small);
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.optionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        beginDBWork();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.myRefresh:
               onRefreshClick ();
                return true;
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void onRefreshClick() {
        if (!isOnline ()){ showFailDialog (); return;}
        stopLoaders();
        getLoaderManager().initLoader(R.id.channel_loader, Bundle.EMPTY,  MainActivity.this);
        setRefreshActionButtonState(true);
        setProgressBar ();
    }


    private void setProgressBar() {
        spinner.setVisibility(View.VISIBLE);
    }


    private void showFailDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(" Connection failed. ");
        alertDialog.setMessage("Please check your internet connection and try again.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void stopLoaders() {
        getLoaderManager().destroyLoader(R.id.channel_loader);
        getLoaderManager().destroyLoader(R.id.tvshow_loader);
        getLoaderManager().destroyLoader(R.id.category_loader);
    }


    public void setRefreshActionButtonState(final boolean refreshing) {
        if (optionsMenu != null) {
            final MenuItem refreshItem = optionsMenu
                    .findItem(R.id.myRefresh);
            if (refreshItem != null) {
                if (refreshing) {
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                } else {
                    refreshItem.setActionView(null);
                }
            }
        }
    }


    /*
    *   Create and init Loader for asynchronous tasks
    * */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case R.id.channel_loader:
                return new ServerChannelLoader(this);
            case R.id.tvshow_loader:
                return new ServerProgramLoader(this);
            case R.id.category_loader:
                return new ServerCategoryLoader(this);
            default:
                return null;
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch ( loader.getId()) {
            case R.id.channel_loader:
                onFinishChannelLoader();
                break;
            case R.id.tvshow_loader:
                onFinishTVShowLoader ();
                break;
            case R.id.category_loader:
                onFinishCategoryLoader();
                break;
        }
    }


    private void onFinishChannelLoader() {
        getLoaderManager().initLoader(R.id.category_loader, Bundle.EMPTY,  MainActivity.this);
    }


    private void onFinishCategoryLoader() {
        getLoaderManager().initLoader(R.id.tvshow_loader, Bundle.EMPTY,  MainActivity.this);
    }


    private void onFinishTVShowLoader() {
        setRefreshActionButtonState(false);
        spinner.setVisibility(View.GONE);
        initFragmentOnCreate();
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    /*
    *   Methods of activity life circle
    * */

    @Override
    protected void onStop (){
        super.onStop();
        finish();
   }


    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();  // optional depending on your needs
        //finish();
    }
}
