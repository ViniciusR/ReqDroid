/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import com.actionbarsherlock.app.SherlockActivity;

/**
 * Implementa a tela da secao de visao geral do sistema.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaVisaoGeralActivity extends SherlockActivity {

    private static int idProjeto;
    private static String tituloProjeto;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.descricaogeral); 
        AbreTelaRequisitos();
        AbreTelaEscopo();
        AbreTelaRequisitosAtrasados();
        AbreTelaRequisitosCaracteristicasUsuario();
        AbreTelaHipotesesEDependencias();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    /**
     * Abre a tela de lista de requisitos do aplicativo.
     */
    private void AbreTelaRequisitos()
    {
      final Intent i = new Intent(TelaVisaoGeralActivity.this, TelaRequisitosActivity.class);
      final Button button = (Button) findViewById(R.id.botaoRequisitos);
      
        button.setOnClickListener(new View.OnClickListener() {  
            public void onClick(View v) 
            {  
                startActivity(i);
            }  
        });  
    }
    
    /**
     * Abre a tela de escopo do aplicativo.
     */
    private void AbreTelaEscopo()
    {
      final Intent i = new Intent(TelaVisaoGeralActivity.this, TelaEscopoActivity.class);       
      final Button button = (Button) findViewById(R.id.botaoEscopo);  
      
        button.setOnClickListener(new View.OnClickListener() {  
            public void onClick(View v) 
            {  
                startActivity(i); 
            }  
        });  
    }
    
    /**
     * Abre a tela de lista de requisitos atrasados do aplicativo.
     */
    private void AbreTelaRequisitosAtrasados()
    {
      final Intent i = new Intent(TelaVisaoGeralActivity.this, TelaRequisitosAtrasadosActivity.class);       
      final Button button = (Button) findViewById(R.id.botaoRequisitosAtrasados);  
      
        button.setOnClickListener(new View.OnClickListener() {  
            public void onClick(View v) 
            {  
                startActivity(i); 
            }  
        });  
    }
    
    /**
     * Abre a tela de caracteristicas do usuario do aplicativo.
     */
    private void AbreTelaRequisitosCaracteristicasUsuario()
    {
      final Intent i = new Intent(TelaVisaoGeralActivity.this, TelaCaracteristicasUsuarioActivity.class);       
      final Button button = (Button) findViewById(R.id.botaoCaracteristicasUsuario);  
      
        button.setOnClickListener(new View.OnClickListener() {  
            public void onClick(View v) 
            {  
                startActivity(i); 
            }  
        });  
    }
    
    /**
     * Abre a tela de hipoteses e dependencias do aplicativo.
     */
    private void AbreTelaHipotesesEDependencias()
    {
      final Intent i = new Intent(TelaVisaoGeralActivity.this, TabsHipotesesEDependenciasActivity.class);       
      final Button button = (Button) findViewById(R.id.botaoHipoteses);  
      
        button.setOnClickListener(new View.OnClickListener() {  
            public void onClick(View v) 
            {  
                startActivity(i); 
            }  
        });  
    }
    
     @Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
      super.onConfigurationChanged(newConfig);
      setContentView(R.layout.descricaogeral); 
      AbreTelaRequisitos();
      AbreTelaEscopo();
      AbreTelaRequisitosAtrasados();
      AbreTelaRequisitosCaracteristicasUsuario();
      AbreTelaHipotesesEDependencias();
   }
     
   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
        super.onStop();
        this.finish();
        final Intent i = new Intent(TelaVisaoGeralActivity.this, TelaPrincipalActivity.class);  
        startActivity(i);
        return false; // this avoids passing to super
        }
        return super.onKeyDown(keyCode, event);
    }

    public static int getIdProjeto() {
        return idProjeto;
    }

    public static void setIdProjeto(int id) {
        idProjeto = id;
    }

    public static String getTituloProjeto() {
        return tituloProjeto;
    }

    public static void setTituloProjeto(String tituloProjeto) {
        TelaVisaoGeralActivity.tituloProjeto = tituloProjeto;
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menusobre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusobre:
                Intent i = new Intent(TelaVisaoGeralActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpTo(this,new Intent(this, TelaPrincipalActivity.class));
        }
        return true;
    }
}
