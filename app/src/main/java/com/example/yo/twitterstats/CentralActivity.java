package com.example.yo.twitterstats;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yo.twitterstats.tabs.FansTab;
import com.example.yo.twitterstats.tabs.FollowersTab;
import com.example.yo.twitterstats.tabs.MutualsTab;
import com.example.yo.twitterstats.tabs.RecentUnfollowersTab;
import com.example.yo.twitterstats.tabs.UnfollowersTab;

import java.util.HashMap;
import java.util.Map;

public class CentralActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private BroadcastReceiver broadcast_reciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_central);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        registrarReceiver();

        new AsyncCaller().execute(false);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcast_reciever);
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_central, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_settings) {
            lanzarPerfilActivity();
        }
        if (id == R.id.refresh_setting) {
            new AsyncCaller().execute(true);
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Lanza la actividad del perfil y
     */
    private void lanzarPerfilActivity() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    /*
    Prepara un BroadcastReceiver para que en el
    caso de que se cierre sesión desde el perfil se le mande un mensaje a la actividad central
    para que termine.
     */
    private void registrarReceiver() {
        broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_activity"));
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        FansTab fansTab;
        FollowersTab followersTab;
        MutualsTab mutualsTab;
        RecentUnfollowersTab recentUnfollowersTab;
        UnfollowersTab unfollowersTab;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fansTab = new FansTab();
            followersTab = new FollowersTab();
            mutualsTab = new MutualsTab();
            recentUnfollowersTab = new RecentUnfollowersTab();
            unfollowersTab = new UnfollowersTab();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return fansTab;
                case 1:
                    return followersTab;
                case 2:
                    return mutualsTab;
                case 3:
                    return recentUnfollowersTab;
                case 4:
                    return unfollowersTab;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Fans";
                case 1:
                    return "Followers";
                case 2:
                    return "Mutuals";
                case 3:
                    return "Recent Unfollowers";
                case 4:
                    return "Unfollowers";
            }
            return null;
        }

        public void updateFirstsFragments(){
            fansTab.updateList();
            followersTab.updateList();

        }

        public void updateAllFragments(){
            fansTab.updateList();
            followersTab.updateList();
            mutualsTab.updateList();
            //TODO
//            recentUnfollowersTab;
            unfollowersTab.updateList();
        }
    }

    private class AsyncCaller extends AsyncTask<Boolean, Void, Map<String, Boolean>> {
        ProgressDialog pdLoading = new ProgressDialog(CentralActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("Obteniendo datos, podría tardar unos segundos...");
            pdLoading.show();
        }

        @Override
        protected Map<String, Boolean> doInBackground(Boolean... refresh) {
            Map<String, Boolean> flags = new HashMap<>();
            boolean result = GetData.getInstance().fetchData();
            flags.put("result",result);
            flags.put("refresh",refresh[0]);
            return flags;
        }

        @Override
        protected void onPostExecute(Map<String, Boolean> flags) {
            super.onPostExecute(flags);
            pdLoading.dismiss();
            if (!flags.get("result")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CentralActivity.this);
                builder.setTitle("Error al obtener los datos");
                builder.setMessage("Compruebe su conexión a internet");

                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new CentralActivity.AsyncCaller().execute();
                    }
                });
                builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            } else {
                GetData.getInstance().calculateLists();
                if(flags.get("refresh"))
                    mSectionsPagerAdapter.updateAllFragments();
                else
                    mSectionsPagerAdapter.updateFirstsFragments();

            }
        }

    }
}


