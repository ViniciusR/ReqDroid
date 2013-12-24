/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.fragments;



import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.activities.TelaPrincipalActivity;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.util.ProjetoUtils;


/**
 * Implementa a tela de caracteristicas do usuario do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class CaracteristicasUsuarioFragment extends Fragment {
	
	private int idProjeto;
	private RadioGroup radioGroupExperiencia;
	private RadioGroup radioGroupPericia;
	private CheckBox checkBoxTreinamento;
    private View rootView;

    public CaracteristicasUsuarioFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.caracteristicas_usuario, container, false);
        getActivity().setTitle(getResources().getStringArray(R.array.titulos_funcionalidades_array)[1]);
        init();   
        carregaCaracteristicasUsuario();
        atualizaCaracteristicas();
        return rootView;
    }
    
    /**
     * Carrega as caracteristicas do usuario do projeto.
     */
    private void carregaCaracteristicasUsuario()
    {
       carregaCaracteristicasUsuarioBD(idProjeto);      
    }
    
    /**
     * Carrega a lista de caracteristicas do usuario a partir do banco de dados do aplicativo.
     * 
     * @param idProjeto O id do projeto a ter suas caracteristicas do usuario carregadas.
     */
    private void carregaCaracteristicasUsuarioBD(int idProjeto) 
    {
		int experiencia = BDGerenciador.getInstance(getActivity()).selectExperienciaUsuario(idProjeto);
		int pericia = BDGerenciador.getInstance(getActivity()).selectPericiaUsuario(idProjeto);
		int treinamento = BDGerenciador.getInstance(getActivity()).selectTreinamentoUsuario(idProjeto);
		
		switch(experiencia) {
		   case 1: radioGroupExperiencia.check(R.id.radioExperienciaBaixo);
		           break;
		   case 2: radioGroupExperiencia.check(R.id.radioExperienciaMedio);
		   		   break;		
		   case 3: radioGroupExperiencia.check(R.id.radioExperienciaAlto);
		   		   break;	
		}
		
		switch(pericia) {
		   case 1: radioGroupPericia.check(R.id.radioPericiaBaixo);
		   		   break;	 
		   case 2: radioGroupPericia.check(R.id.radioPericiaMedio);
		           break;	
		   case 3: radioGroupPericia.check(R.id.radioPericiaAlto);
		           break;	
		}
		
		switch(treinamento) {
		   case 0: checkBoxTreinamento.setChecked(false);
		           break;	
		   case 1: checkBoxTreinamento.setChecked(true);
		           break;	
		}
	}

    /**
     * Atualiza as caracteristicas do usuario no banco de dados do aplicativo quando uma das opcoes 
     * da interface grafica e selecionada.
     */
	private void atualizaCaracteristicas()
    {	
		radioGroupExperiencia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                 BDGerenciador.getInstance(getActivity()).updateExperiencia(idProjeto, getExperienciaSelecionada());
            }
       });
    	
        radioGroupPericia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                 BDGerenciador.getInstance(getActivity()).updatePericia(idProjeto, getPericiaSelecionada());
            }
       });
    	
    	checkBoxTreinamento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
            	BDGerenciador.getInstance(getActivity()).updateTreinamento(idProjeto, getTreinamentoSelecionado());
            }
        });
    }
	
	/**
	 * Retorna o indice que representa a experiencia selecionada na interface grafica.
	 * 
	 * @return O indice da experiencia do usuario
	 */
	private int getExperienciaSelecionada()
	{		 		 
		int experienciaSelecionada = radioGroupExperiencia.getCheckedRadioButtonId();
		 
		switch (experienciaSelecionada) {
		  case R.id.radioExperienciaBaixo : return 1;
		  case R.id.radioExperienciaMedio : return 2;				                      
		  case R.id.radioExperienciaAlto  : return 3;				                      
		}
		return 2;
	}
	
	/**
	 * Retorna o indice que representa a pericia selecionada na interface grafica.
	 * 
	 * @return O indice da pericia do usuario
	 */
	private int getPericiaSelecionada()
	{		 		 
		int periciaSelecionada = radioGroupPericia.getCheckedRadioButtonId();
		 
		switch (periciaSelecionada) {
		  case R.id.radioPericiaBaixo : return 1;
		  case R.id.radioPericiaMedio : return 2;				                      
		  case R.id.radioPericiaAlto  : return 3;				                      
		}
		return 2;
	}
	
	/**
	 * Retorna o indice que representa o item "Treinamento do usuario" selecionado na interface grafica.
	 * 
	 * @return O indice do item "Treinamento do usuario"
	 */
	private int getTreinamentoSelecionado()
	{
		if (checkBoxTreinamento.isChecked())
		{	
			return 1;
		}
		return 0;			
	}

	/**
     * Inicializa variaveis da classe.
     */
	private void init()
	{
		idProjeto = ProjetoUtils.getIdProjeto();
		radioGroupExperiencia = (RadioGroup) rootView.findViewById(R.id.radioGroupExperiencia);
		radioGroupPericia = (RadioGroup) rootView.findViewById(R.id.radioGroupPericia);
		checkBoxTreinamento = (CheckBox) rootView.findViewById(R.id.checkBoxTreinamentoUsuario);
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
