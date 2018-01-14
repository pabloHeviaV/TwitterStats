package com.example.yo.twitterstats.activities;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yo.twitterstats.R;
import com.example.yo.twitterstats.bd.DataSource;
import com.example.yo.twitterstats.tabs.FansTab;
import com.example.yo.twitterstats.tabs.FollowersTab;
import com.example.yo.twitterstats.tabs.MutualsTab;
import com.example.yo.twitterstats.tabs.RecentUnfollowersTab;
import com.example.yo.twitterstats.tabs.UnfollowersTab;
import com.example.yo.twitterstats.util.GetData;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.GET;

/**
 * Activity principal de la app que tiene todas las pestañas
 * con las distintas listas de usuarios dependiendo de la pestaña.
 */
public class CentralActivity extends AppCompatActivity {

    /**
     * El {@link android.support.v4.view.PagerAdapter}que proporcionará
     * fragments para cada una de las secciones. Se usa un derivado de
     * {@link FragmentPagerAdapter}, que mantendrá cada fragment
     * cargado en memoria.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * El {@link ViewPager} que albergará los contenidos de las secciones.
     */
    private ViewPager mViewPager;

    /**
     * Un {@link BroadcastReceiver} que se encargará de recibir el mensaje
     * de finalizar esta Activity que se lanza cuando se cierra sesión desde
     * {@link UserProfileActivity}
     */
    private BroadcastReceiver broadcast_reciever;


    /**
     * Crea los elementos necesarios para lanzar la activity,
     * registra el BroadcastReceiver y se encarga de llamar
     * al método que obtendrá los datos necesarios para llenar
     * las listas de pestañas.
     *
     * @param savedInstanceState
     */
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

        boolean login = getIntent().getBooleanExtra("Login",false);
        if(login){
            new AsyncCaller().execute();

        }else{
            GetData getData = GetData.getInstance();
            getData.fetchData(true, this);
            getData.calculateLists();
        }
    }

    /**
     * Se encargar de que se cancele el registro del {@link BroadcastReceiver}
     * antes de que se finalice la activity.
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcast_reciever);
        mSectionsPagerAdapter.clearAllFragments();

        super.onDestroy();

    }

    /**
     * Añade los items al menú.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_central, menu);
        return true;
    }

    /**
     * Maneja lo que ocurre al presionar los botones del menú.
     * Al presionar el perfil, se lanza la activity del
     * perfil({@link UserProfileActivity}.
     * Al presionar sobre el botón de actualizar, se llama a la
     * tarea asíncrona que obtiene los datos de las listas de usuarios.
     *
     * @param item el item del menú seleccionado
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile_settings) {
            lanzarPerfilActivity();
        }
        if (id == R.id.refresh_setting) {
            new AsyncCaller().execute();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Lanza la actividad del perfil de usuario {@link UserProfileActivity}
     */
    private void lanzarPerfilActivity() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    /**
     * Prepara un BroadcastReceiver para que en el
     * caso de que se cierre sesión desde el perfil se le mande un mensaje a la actividad central
     * para que termine.
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
     * Un {@link FragmentPagerAdapter} que devuelve un fragment correspondiente
     * a una de las pestañas.
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

        /**
         * Devuelve el número de pestañas.
         *
         * @return int el número de pestañas
         */
        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        /**
         * Devuelve el título para la pestaña de la posición seleccionada.
         *
         * @param position int la posición de la pestaña
         * @return CharSequence el título de la pestaña
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.fansTabTitle);
                case 1:
                    return getString(R.string.followersTabTitle);
                case 2:
                    return getString(R.string.mutualsTabTitle);
                case 3:
                    return getString(R.string.recentUnfollowersTabTitle);
                case 4:
                    return getString(R.string.unfollowersTabTitle);
            }
            return null;
        }

        /**
         * Actualiza la lista de todas las pestañas.
         */
        public void updateAllFragments(){
            fansTab.updateList();
            followersTab.updateList();
            mutualsTab.updateList();
            recentUnfollowersTab.updateList();
            unfollowersTab.updateList();
        }

        /**
         * Limpia la lista de todas las pestañas.
         */
        public void clearAllFragments(){
            GetData.getInstance().clear();
            fansTab.clearList();
            followersTab.clearList();
            mutualsTab.clearList();
            recentUnfollowersTab.clearList();
            unfollowersTab.clearList();
        }


    }

    /**
     * Ejecuta una llamada asíncrona a la API de Twitter para obtener la lista de
     * seguidores y seguidos.
     */
    private class AsyncCaller extends AsyncTask<Void, Void, Integer> {
        ProgressDialog pdLoading = new ProgressDialog(CentralActivity.this,R.style.AppCompatAlertDialogStyle);


        /**
         * Muestra un {@link ProgressDialog} mientras se ejecuta la llamada.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage(getString(R.string.centralAsyncLoadingMessage));
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        /**
         * Ejecuta la llamada a la API de Twitter para obtener las listas de
         * seguidores y seguidos del User
         * y coloca los flags necesarios para el postExecute.
         *
         * @return un int que indica si ha ocurrido alguna excepción a la hora
         * de obtener los datos.
         */
        @Override
        protected Integer doInBackground(Void... params) {

            int result = GetData.getInstance().fetchData(false, CentralActivity.this);
            return result;
        }

        /**
         * Si ha habido un error en la obtención de datos de la API, muestra
         * un cuadro de diálogo en el que es posible volver a iniciar la
         * llamada o salir de la aplicación.
         * Si se han obtenido los datos, comprueba de dónde viene la llamada
         * y actualiza la lista de las pestañas necesarias.
         *
         * @param flags un map con los flags necesarios
         */
        @Override
        protected void onPostExecute(Integer flags) {
            super.onPostExecute(flags);
            pdLoading.dismiss();

            if (!flags.equals(GetData.NO_ERROR)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CentralActivity.this);
                builder.setTitle(R.string.errorObtainDataMessageTitle);
                if(flags.equals(GetData.ERROR_NO_INTERNET))
                    builder.setMessage(R.string.errorMsgCheckInternet);
                else
                    builder.setMessage(R.string.errorMsgRateLimit);

                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new CentralActivity.AsyncCaller().execute();
                    }
                });
                builder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();

            } else {
                GetData.getInstance().calculateLists();
                mSectionsPagerAdapter.updateAllFragments();
            }
        }

    }
}


