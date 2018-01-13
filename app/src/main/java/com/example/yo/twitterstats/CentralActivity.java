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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        new AsyncCaller().execute();


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
        if(id == R.id.refresh_setting){
            //TODO
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Lanza la actividad del perfil y
     */
    private void lanzarPerfilActivity(){
        Intent intent= new Intent(this, UserProfileActivity.class);
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

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    FansTab tab1 = new FansTab();
                    return tab1;
                case 1:
                    FollowersTab tab2 = new FollowersTab();
                    return tab2;
                case 2:
                    MutualsTab tab3 = new MutualsTab();
                    return tab3;
                case 3:
                    RecentUnfollowersTab tab4 = new RecentUnfollowersTab();
                    return tab4;
                case 4:
                    UnfollowersTab tab5 = new UnfollowersTab();
                    return tab5;
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
            switch (position){
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
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Boolean>
    {
        ProgressDialog pdLoading = new ProgressDialog(CentralActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tObteniendo datos, podría tardar unos segundos...");
            pdLoading.show();
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            return GetData.getInstance().fetchData();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
            if(!result) {
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
            }
        }

    }
}

