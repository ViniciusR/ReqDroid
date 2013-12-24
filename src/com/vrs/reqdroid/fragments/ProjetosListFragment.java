/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.activities.DrawerPrincipalActivity;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.models.Projeto;
import com.vrs.reqdroid.util.ListViewProjetosAdapter;
import com.vrs.reqdroid.util.ProjetoUtils;


import java.util.ArrayList;

/**
 * Implementa a tela de abrir projetos do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class ProjetosListFragment extends ListFragment {

    private String projetoSelecionado;
    private int idProjetoSelecionado;
    private View rootView;

    public ProjetosListFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.listview_projetos, container, false);
        getActivity().setTitle(getResources().getString(R.string.tela_abrir_projeto_abrir));
        setListAdapter(new ListViewProjetosAdapter(getActivity(), carregaProjetos()));
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        final Intent i = new Intent(getActivity(), DrawerPrincipalActivity.class);
        Projeto projeto = (Projeto) l.getItemAtPosition(position);
        projetoSelecionado = projeto.getTitulo();
        idProjetoSelecionado = BDGerenciador.getInstance(getActivity()).selectIdProjeto(projetoSelecionado);
        ProjetoUtils.setIdProjeto(idProjetoSelecionado);
        ProjetoUtils.setTituloProjeto(projetoSelecionado);
        startActivity(i);
        getActivity().finish();
    }

    /**
     * Retorna a lista de todos os projetos contidos no banco de dados do aplicativo.
     * 
     * @return A  lista de projetos
     */
    private ArrayList<Projeto> carregaProjetos()
    {
    	return (ArrayList<Projeto>) BDGerenciador.getInstance(getActivity()).selectAllProjetosComData();
    }
}
