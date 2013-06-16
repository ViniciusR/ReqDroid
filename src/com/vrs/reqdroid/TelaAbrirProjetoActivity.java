/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

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
public class TelaAbrirProjetoActivity extends ListActivity  {

    private String projetoSelecionado;
    private int idProjetoSelecionado;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle){
        super.onCreate(icicle);
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
        TextView tvHeader = new TextView(this);
        tvHeader.setText(R.string.tela_abrir_projeto_abrir);
        tvHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP,22);
        int l = 20;
        int t = 30;
        int b = 20;
        float d = this.getResources().getDisplayMetrics().density;
        int lmargin = (int)(l * d);
        int tmargin = (int)(t * d);
        int bmargin = (int)(b * d);
        tvHeader.setPadding(lmargin, tmargin, 0, bmargin);
        tvHeader.setTextColor(getResources().getColor(R.color.titulo));

        lv.addHeaderView(tvHeader,null,false);

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
}
