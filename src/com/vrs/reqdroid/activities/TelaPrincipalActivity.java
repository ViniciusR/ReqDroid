/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import com.vrs.reqdroid.R;


/**
 * Implementa a tela principal do sistema.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaPrincipalActivity extends ActionBarActivity {

    private static String projetoSelecionado;
    private static int idProjetoSelecionado;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        AbreTelaNovoProjeto();
        AbreTelaAbrirProjeto();
        //mostraProjetosRecentes();
    }

    /**
     * Abre a tela de criacao de um novo projeto ao clicar no botao "Novo projeto".
     */
    private void AbreTelaNovoProjeto()
    {
        final Intent i = new Intent(TelaPrincipalActivity.this, TelaNovoProjetoActivity.class);
        final LinearLayout botaoNovo = (LinearLayout) findViewById(R.id.layoutNovoProjeto);

        botaoNovo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
                startActivity(i);
            }  
        });
    }
    
    /**
     * Abre a tela para abrir um projeto existente ao clicar no botao "Abrir projeto".
     */
    private void AbreTelaAbrirProjeto()
    {
      final Intent i = new Intent(TelaPrincipalActivity.this, TelaAbrirProjetoActivity.class);
      final LinearLayout botaoAbrir = (LinearLayout) findViewById(R.id.layoutAbrirProjeto);

        botaoAbrir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
                startActivity(i);
            }  
        });  
    }

    /*private void mostraProjetosRecentes()
    {
        //Possivel nova funcionalidade.
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
      super.onConfigurationChanged(newConfig);
      setContentView(R.layout.main); 
      AbreTelaNovoProjeto();
      AbreTelaAbrirProjeto();
   } 
    
   @Override
    public void onBackPressed() {
  	     //finish();
  	     Intent intent = new Intent(Intent.ACTION_MAIN);
  	     intent.addCategory(Intent.CATEGORY_HOME);
  	     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  	     startActivity(intent);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu_aplicativo, menu);
       return true;
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
           case R.id.menusobre:     
           					Intent i = new Intent(TelaPrincipalActivity.this, TelaSobreActivity.class);  
           					startActivity(i);  
                             break;
       }
       return true;
   }
}
