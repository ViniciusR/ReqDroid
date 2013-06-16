/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Implementa a tela principal do sistema.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaPrincipalActivity extends Activity {

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
        final Intent i = new Intent(TelaPrincipalActivity.this, TelaNovaEspecificacaoActivity.class);
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
        TextView tv = new TextView(this);
        tv.setText("Projetos recentes");


        final LinearLayout llRecentes = (LinearLayout) findViewById(R.id.llRecentes);


        final ListView lvProjetosRecentes = new ListView(getApplicationContext());
        lvProjetosRecentes.addHeaderView(tv,null,false);
        lvProjetosRecentes.setSelectionAfterHeaderView();
        lvProjetosRecentes.setCacheColorHint(Color.TRANSPARENT);
        lvProjetosRecentes.setTextFilterEnabled(true);
        ColorDrawable grey = new ColorDrawable(Color.LTGRAY);
        lvProjetosRecentes.setDivider(grey);
        lvProjetosRecentes.setDividerHeight(1);

        lvProjetosRecentes.setAdapter(new ListViewProjsAdapter(this, carregaProjetosRecentes()));
        llRecentes.addView(lvProjetosRecentes);

        final Intent i = new Intent(TelaPrincipalActivity.this, TelaVisaoGeralActivity.class);

        lvProjetosRecentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Object o = lvProjetosRecentes.getItemAtPosition(position);
                Projeto projeto = (Projeto)o;
                projetoSelecionado = projeto.getTitulo();
                //projetoSelecionado = ((TextView) view).getText().toString();
                idProjetoSelecionado = BDGerenciador.getInstance(TelaPrincipalActivity.this).selectIdProjeto(projetoSelecionado);
                TelaEscopoActivity.setProjetoAtualizado(false);
                //setProjetoAberto(true);
                startActivity(i);
                finish();
            }
        });
    }

    private ArrayList<Projeto> carregaProjetosRecentes()
    {
        return (ArrayList<Projeto>) BDGerenciador.getInstance(this).selectProjetosRecentesComData();
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
       inflater.inflate(R.menu.menusobre, menu);
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
