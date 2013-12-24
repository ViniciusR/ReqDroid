/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.activities.TelaPrincipalActivity;
import com.vrs.reqdroid.models.Dependencia;
import com.vrs.reqdroid.util.DependenciasUtils;
import com.vrs.reqdroid.util.ListViewDependenciasAdapter;
import com.vrs.reqdroid.util.ProjetoUtils;

/**
 * Implementa a tela de dependencias.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
public class DependenciasFragment extends Fragment
{
    private static ArrayList<Dependencia> dependencias;
    private static ListViewDependenciasAdapter lvDependenciasAdapter;
    private ListView lvDependencias;
    private static int idProjeto;
    private int posicao;
    private static Spinner spinnerPrimeiroRequisito;
    private static Spinner spinnerSegundoRequisito;
    private static Button botaoAlternarRequisitos;
    private int primeiroRequisitoSelecionado;
    private int segundoRequisitoSelecionado;

    private View rootView;

    public DependenciasFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.dependencias, container, false);
        getActivity().setTitle(getResources().getStringArray(R.array.titulos_funcionalidades_array)[2]);
        init();
        carregaRequisitos();
        carregaDependencias();
        selecionaRequisitosSpinners();
        alternaSpinners();
        adicionaDependencia();
        return rootView;
    }

    /**
     * Carrega os requisitos existentes e preenche os spinners com eles.
     */
    private void carregaRequisitos()
    {
        DependenciasUtils.preencheSpinners(getActivity(), spinnerPrimeiroRequisito, spinnerSegundoRequisito, idProjeto);
    }

    /**
     * Carrega as dependencias existentes no banco de dados e as adiciona a lista.
     */
    private void carregaDependencias()
    {
        dependencias = DependenciasUtils.carregaDependenciasBD(getActivity(), idProjeto);
        lvDependencias.setCacheColorHint(Color.TRANSPARENT);
        lvDependencias.setSelectionAfterHeaderView();
        lvDependenciasAdapter = new ListViewDependenciasAdapter(getActivity(), dependencias, botaoRemoverListener);
        lvDependencias.setAdapter(lvDependenciasAdapter);
    }

    /**
     * Listener do botao de opcoes da dependencia.
     */
    private final View.OnClickListener botaoRemoverListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LinearLayout caixaDependencia = (LinearLayout)view.getParent();
            posicao = lvDependencias.getPositionForView(caixaDependencia);
            Dependencia dependencia = (Dependencia) lvDependenciasAdapter.getItem(posicao);
            DependenciasUtils.removeDependencia(getActivity(), dependencias, dependencia,
                    posicao, idProjeto, lvDependenciasAdapter);
        }
    };
    
    /**
     * Adiciona uma nova dependencia ao clicar no botao "Adicionar dependencia".
     */
    private void adicionaDependencia()
    {   
        Button botaoAdicionarDependencia = (Button) rootView.findViewById(R.id.botaoAdicionarDependencia);

        botaoAdicionarDependencia.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (DependenciasUtils.possuiItens(spinnerPrimeiroRequisito, spinnerSegundoRequisito)){
            	    if (primeiroRequisitoSelecionado != segundoRequisitoSelecionado)
            	    {
                      Dependencia novaDependencia = new Dependencia(primeiroRequisitoSelecionado, segundoRequisitoSelecionado);
                      salvaDependencia(novaDependencia);
                      dependencias.add(lvDependencias.getChildCount(), novaDependencia);
                      lvDependenciasAdapter.notifyDataSetChanged();
            	    }
            	    else
            	    {
            		  Toast toast = Toast.makeText(getActivity().getApplicationContext(), R.string.toast_dependencia_igual, Toast.LENGTH_SHORT);
                      toast.show();
            	    }
                }
            }
        });
    }

    /**
     * Salva a dependencia no banco de dados do aplicativo.
     */
    private void salvaDependencia(Dependencia dependencia)
    {
        DependenciasUtils.salvaDependenciaBD(getActivity(), dependencia, idProjeto);
    }

    /**
     * Configura o texto dos dois requisitos selecionados nas listas de selecao.
     */
    private void selecionaRequisitosSpinners()
    {
    	final TextView tvRequisitoSelecionado1 = (TextView) rootView.findViewById(R.id.requisitoSelecionado1);
    	final TextView tvRequisitoSelecionado2 = (TextView) rootView.findViewById(R.id.requisitoSelecionado2);
        final String titulo = getResources().getString(R.string.tela_detalhes_requisito_nome_requisito);

        spinnerPrimeiroRequisito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				//pega nome pela posicao
				primeiroRequisitoSelecionado = (Integer)parent.getItemAtPosition(posicao);
				tvRequisitoSelecionado1.setText(titulo + String.valueOf(primeiroRequisitoSelecionado));
			}
			public void onNothingSelected(AdapterView<?> arg0) {				
			}
		});
    	
    	spinnerSegundoRequisito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   		 
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				segundoRequisitoSelecionado = (Integer)parent.getItemAtPosition(posicao);
		    	tvRequisitoSelecionado2.setText(titulo + String.valueOf(segundoRequisitoSelecionado));
			}
			public void onNothingSelected(AdapterView<?> arg0) {				
			}
		});    	  	
    }
    
    /**
     * Alterna o conteudo das duas listas de selecao ao clicar no botao de alternar requisitos.
     */
    private void alternaSpinners()
    { 	
    	botaoAlternarRequisitos = (Button) rootView.findViewById(R.id.BotaoAlternarRequisitos);
    	
    	botaoAlternarRequisitos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            	 int r1 = spinnerPrimeiroRequisito.getSelectedItemPosition();
            	 int r2 = spinnerSegundoRequisito.getSelectedItemPosition();
                 spinnerPrimeiroRequisito.setSelection(r2);  
                 spinnerSegundoRequisito.setSelection(r1);   
             }
         });
    }

    /**
     * Inicializa variaveis da classe.
     */
    void init()
	{
		getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		idProjeto = ProjetoUtils.getIdProjeto();
        lvDependencias = (ListView) rootView.findViewById(R.id.lvDependencias);
        dependencias = new ArrayList<Dependencia>();
		spinnerPrimeiroRequisito =  (Spinner) rootView.findViewById(R.id.spinnerRequisito1);
		spinnerSegundoRequisito =  (Spinner) rootView.findViewById(R.id.spinnerRequisito2);
	}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(), TelaPrincipalActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
