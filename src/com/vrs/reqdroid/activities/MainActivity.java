/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


/**
 * Implementa a activity principal do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vvinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class MainActivity extends ActionBarActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(MainActivity.this, TelaPrincipalActivity.class);
        startActivity(i);
    }
}
