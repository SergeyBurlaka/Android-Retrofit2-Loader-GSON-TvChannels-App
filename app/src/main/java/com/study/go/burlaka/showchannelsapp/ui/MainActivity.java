package com.study.go.burlaka.showchannelsapp.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.study.go.burlaka.showchannelsapp.R;
import com.study.go.burlaka.showchannelsapp.data.model.Category;
import com.study.go.burlaka.showchannelsapp.loaders.server.request.CategoryLoader;
import com.study.go.burlaka.showchannelsapp.loaders.server.request.ChannelLoader;
import com.study.go.burlaka.showchannelsapp.loaders.server.request.ProgramLoader;
import com.study.go.burlaka.showchannelsapp.ui.fragments.ShowCategory;
import com.study.go.burlaka.showchannelsapp.ui.fragments.ShowPrograms;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String CH_TAG = "GetChannel";
    private static final String PR_TAG = "GetProgram";
    private static final String TAG = "GetCategory";

    private Button getCannel;
    private Cursor cursor;
    Toolbar toolbar;

    private Menu optionsMenu;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maim_tv);
        //  getLoaderManager().initLoader(R.id.channel_loader, Bundle.EMPTY,  MainActivity.this);
        initDrawer();
        initToolBar();
        initFragmentOnCreate();


    }

    private void initFragmentOnCreate() {
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = ShowPrograms.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        setFragment (fragment);
    }

    private void initDrawer() {
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.navigation_view);
        // Inflate the header view at runtime
        //View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
// We can now look up items within the header if needed
        //ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);

       drawerToggle = setupDrawerToggle();
        drawerToggle.syncState();
        // Tie DrawerLayout events to the ActionBarToggle
        //mDrawer.addDrawerListener(drawerToggle);
        // Setup drawer view
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
                //mViewPager.setVisibility(View.INVISIBLE);
                fragmentClass = ShowCategory.class;
                break;
            case R.id.show_pager_channels_fragment:
                fragmentClass = ShowPrograms.class;
                // mViewPager.setVisibility(View.VISIBLE);
                // fragmentClass = SecondFragment.class;
                break;
            /*case R.id.nav_third_fragment:
              //  fragmentClass = ThirdFragment.class;
                break;*/
            default:
                //  fragmentClass = FirstFragment.class;
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

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setNavigationIcon(R.drawable); // just setNavigationIcon
       // toolbar.setTitle(R.string.toolbarTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.tv_icon);
        getSupportActionBar().setTitle("  TV Channels ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_hamburger_small);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.optionsMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        stopLoaders();


        Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();
        getLoaderManager().initLoader(R.id.channel_loader, Bundle.EMPTY,  MainActivity.this);

        setRefreshActionButtonState(true);

    }

    private void stopLoaders() {
        getLoaderManager().destroyLoader(R.id.channel_loader);
        getLoaderManager().destroyLoader(R.id.tvshow_loader);
        getLoaderManager().destroyLoader(R.id.category_loader);
    }


    public void setRefreshActionButtonState(final boolean refreshing) {

        //loading.show();
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



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case R.id.channel_loader:
               // Log.v(TAG, "onCreateLoader"+"MainActivity");
                return new ChannelLoader(this);
            case R.id.tvshow_loader:
                return new ProgramLoader(this);
            case R.id.category_loader:
                return new CategoryLoader(this);
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
                onFinishCategoryLoader(data);

                break;

        }
    }

    private void onFinishChannelLoader() {
        // readChannelData(data);
        //getLoaderManager().initLoader(R.id.tvshow_loader, Bundle.EMPTY,  MainActivity.this);
        getLoaderManager().initLoader(R.id.category_loader, Bundle.EMPTY,  MainActivity.this);
        //stopLoader(R.id.channel_loader);
    }


    private void onFinishCategoryLoader(Cursor cursor) {
        // readProgramData (/*data*/);
        Toast.makeText(MainActivity.this, "begin read", Toast.LENGTH_SHORT).show();
        readCategory (cursor);
        getLoaderManager().initLoader(R.id.tvshow_loader, Bundle.EMPTY,  MainActivity.this);
       // stopLoader(R.id.category_loader);
    }


    private void onFinishTVShowLoader() {
        // readProgramData (/*data*/);
        // stopLoader(R.id.tvshow_loader);
        //loading.dismiss();
        setRefreshActionButtonState(true);
        finish();
        startActivity(getIntent());
    }

    private void readCategory(Cursor data) {
        if (data.isClosed())return;

        if (data.moveToFirst()) {
            int i = 0;
            do {
                i++;
                String programName = data.getString(data.getColumnIndex(Category.KEY_CATEGORY));
                String channel = data.getString(data.getColumnIndex(Category.KEY_CHANNEL));
                Toast.makeText(MainActivity.this, "Category: "+ programName+" Channel "+channel , Toast.LENGTH_SHORT).show();
                Log.d(TAG, "#=->Category: " + programName+" Count: "+i);
                // do what ever you want here
            } while (data.moveToNext());
        //  DatabaseManager.getInstance().closeDatabase();
        // data.close();


    }
    }




    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        finish();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


}
