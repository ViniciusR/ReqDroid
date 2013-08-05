/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.modelo.Projeto;
import com.vrs.reqdroid.util.ListViewProjetosAdapter;


import java.util.ArrayList;

/**
 * Implementa a tela de abrir projetos do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaAbrirProjetoActivity extends SherlockListActivity {

    private String projetoSelecionado;
    private int idProjetoSelecionado;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle){
        super.onCreate(icicle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mostraProjetos();  
    }

    /**
     * Exibe os projetos existentes a serem escolhidos para serem abertos em uma lista. 
     */
    private void mostraProjetos()
    {
       final ListView lvProjetos = getListView();
       configuraListView(lvProjetos);
       lvProjetos.setAdapter(new ListViewProjetosAdapter(this, carregaProjetos()));
       final Intent i = new Intent(TelaAbrirProjetoActivity.this, TelaVisaoGeralActivity.class);

       lvProjetos.setOnItemClickListener(new OnItemClickListener() {
        		public void onItemClick(AdapterView<?> parent, View view,
        				int position, long id) {
          Object p = lvProjetos.getItemAtPosition(position);
	      Projeto projeto = (Projeto)p;
	      projetoSelecionado = projeto.getTitulo();
	      idProjetoSelecionado = BDGerenciador.getInstance(TelaAbrirProjetoActivity.this).selectIdProjeto(projetoSelecionado);
          TelaVisaoGeralActivity.setIdProjeto(idProjetoSelecionado);
          TelaVisaoGeralActivity.setTituloProjeto(projetoSelecionado);
	      startActivity(i);
	      finish();
         }
                 	});
    }

    /**
     * Configura os itens presentes no ListView dos projetos.
     *
     * @param lv A ListView a ser configurado
     */
    private void configuraListView(ListView lv)
    {
        lv.setSelectionAfterHeaderView();
        lv.setCacheColorHint(Color.TRANSPARENT);
        lv.setTextFilterEnabled(true);
        ColorDrawable grey = new ColorDrawable(Color.LTGRAY);
        lv.setDivider(grey);
        lv.setDividerHeight(1);

        TextView emptyView = new TextView(this);
        emptyView.setText(R.string.tela_abrir_projeto_lista_vazia);
        emptyView.setTextSize(18);
        emptyView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ((ViewGroup) lv.getParent()).addView(emptyView);
        lv.setEmptyView(emptyView);
    }

    /**
     * Retorna a lista de todos os projetos contidos no banco de dados do aplicativo.
     * 
     * @return A  lista de projetos
     */
    private ArrayList<Projeto> carregaProjetos()
    {
    	return (ArrayList<Projeto>) BDGerenciador.getInstance(this).selectAllProjetosComData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menusobre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusobre:
                Intent i = new Intent(TelaAbrirProjetoActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpTo(this,new Intent(this, TelaPrincipalActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
