/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import java.util.ArrayList;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.vrs.reqdroid.modelo.Dependencia;
import com.vrs.reqdroid.util.ListViewDependenciasAdapter;
import com.vrs.reqdroid.util.OperacoesDependencias;

/**
 * Implementa a tela de dependencias.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
public class DependenciasFragment extends SherlockFragment
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.dependencias, container, false);
    }

    /** Called when the activity is first created. */
    @Override
    public void onStart()
    {
        super.onStart();
        init();
        carregaRequisitos();
        carregaDependencias();
        selecionaRequisitosSpinners();
        alternaSpinners();
        adicionaDependencia();
    }

    /**
     * Carrega os requisitos existentes e preenche os spinners com eles.
     */
    private void carregaRequisitos()
    {
        OperacoesDependencias.preencheSpinners(getActivity(), spinnerPrimeiroRequisito, spinnerSegundoRequisito, idProjeto);
    }

    /**
     * Carrega as dependencias existentes no banco de dados e as adiciona a lista.
     */
    private void carregaDependencias()
    {
        dependencias = OperacoesDependencias.carregaDependenciasBD(getActivity(), idProjeto);
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
            OperacoesDependencias.removeDependencia(getActivity(), dependencias, dependencia,
                                                    posicao, idProjeto, lvDependenciasAdapter);
        }
    };
    
    /**
     * Adiciona uma nova dependencia ao clicar no botao "Adicionar dependencia".
     */
    private void adicionaDependencia()
    {   
        Button botaoAdicionarDependencia = (Button) getView().findViewById(R.id.botaoAdicionarDependencia);

        botaoAdicionarDependencia.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (OperacoesDependencias.possuiItens(spinnerPrimeiroRequisito, spinnerSegundoRequisito)){
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
        OperacoesDependencias.salvaDependenciaBD(getActivity(), dependencia, idProjeto);
    }

    /**
     * Configura o texto dos dois requisitos selecionados nas listas de selecao.
     */
    private void selecionaRequisitosSpinners()
    {
    	final TextView tvRequisitoSelecionado1 = (TextView)getView().findViewById(R.id.requisitoSelecionado1);
    	final TextView tvRequisitoSelecionado2 = (TextView)getView().findViewById(R.id.requisitoSelecionado2);
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
    	botaoAlternarRequisitos = (Button)getView().findViewById(R.id.BotaoAlternarRequisitos);
    	
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
		idProjeto = TelaVisaoGeralActivity.getIdProjeto();
        lvDependencias = (ListView) getView().findViewById(R.id.lvDependencias);
        dependencias = new ArrayList<Dependencia>();
		spinnerPrimeiroRequisito =  (Spinner)getView().findViewById(R.id.spinnerRequisito1);
		spinnerSegundoRequisito =  (Spinner)getView().findViewById(R.id.spinnerRequisito2);
	}

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusobre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusobre:
                Intent i = new Intent(DependenciasFragment.this, TelaSobreActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }*/
}
