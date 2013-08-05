/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

//import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * Implementa a activity principal do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vvinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class MainActivity extends SherlockActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(MainActivity.this, TelaPrincipalActivity.class);  
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusobre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusobre:
                Intent i = new Intent(MainActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }
}
