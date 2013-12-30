/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.activities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.vrs.reqdroid.R;
import com.vrs.reqdroid.fragments.CaracteristicasUsuarioFragment;
import com.vrs.reqdroid.fragments.HipotesesEDependenciasFragment;
import com.vrs.reqdroid.fragments.RequisitosAtrasadosFragment;
import com.vrs.reqdroid.fragments.RequisitosFragment;
import com.vrs.reqdroid.util.DrawerPrincipalAdapter;
import com.vrs.reqdroid.util.ProjetoUtils;

/**
 * Implementa o drawer principal do aplicativo, que exibe o menu com cada uma das funcionalidades.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class DrawerPrincipalActivity extends ActionBarActivity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private LinearLayout drawerLinear;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence tituloDrawer;
    private CharSequence tituloActivity;
    private String[] titulosFuncionalidades;
    private DrawerPrincipalAdapter drawerAdapter;
    private TextView tituloProjeto;
    private LinearLayout funcionalidadeEscopoLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_principal);
        init();
        exibeEscopo();


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                0,  /* "open drawer" description for accessibility */
                0 /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(tituloActivity);
                supportInvalidateOptionsMenu();  // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getSupportActionBar().setTitle(tituloDrawer);
                supportInvalidateOptionsMenu();  // creates call to onPrepareOptionsMenu()
        }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_aplicativo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
           case R.id.menusobre:
               Intent i = new Intent(this, TelaSobreActivity.class);
               startActivity(i);
               return true;

            /*case R.id.action_websearch: //BOTAO DE BUSCA
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getSupportActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

       Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new RequisitosFragment();
                break;
            case 1:
                fragment = new CaracteristicasUsuarioFragment();
                break;
            case 2:
                fragment = new HipotesesEDependenciasFragment();
                break;
            case 3:
                fragment = new RequisitosAtrasadosFragment();
                break;
            default:
               break;
        }
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        drawerList.setItemChecked(position, true);
        setTitle(titulosFuncionalidades[position]);
        drawerLayout.closeDrawer(drawerLinear);
    }

    @Override
    public void setTitle(CharSequence title) {
        tituloActivity = title;
        getSupportActionBar().setTitle(tituloActivity);
    }

    public void exibeEscopo()
    {
        funcionalidadeEscopoLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(DrawerPrincipalActivity.this, TelaEscopoActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Inicia as variaveis da classe.
     */
    private void init()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLinear = (LinearLayout) findViewById(R.id.left_drawer);
        drawerList = (ListView) findViewById(R.id.left_drawer_list);
        tituloProjeto = (TextView) findViewById(R.id.tituloProjetoDrawer);
        funcionalidadeEscopoLayout = (LinearLayout) findViewById(R.id.llEscopoDrawer);
        titulosFuncionalidades = getResources().getStringArray(R.array.titulos_funcionalidades_array);
        drawerAdapter = new DrawerPrincipalAdapter(this, titulosFuncionalidades);

        tituloActivity = tituloDrawer = getTitle();

        // set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        drawerList.setAdapter(drawerAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        tituloProjeto.setText(ProjetoUtils.getTituloProjeto());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
