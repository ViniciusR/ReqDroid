/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.vrs.reqdroid.dao.BDGerenciador;


/**
 * Implementa a tela de caracteristicas do usuario do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaCaracteristicasUsuarioActivity extends SherlockActivity {
	
	private int idProjeto;
	private RadioGroup radioGroupExperiencia;
	private RadioGroup radioGroupPericia;
	private CheckBox checkBoxTreinamento;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caracteristicasusuario);
        init();   
        carregaCaracteristicasUsuario();
        atualizaCaracteristicas();        
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
		int experiencia = BDGerenciador.getInstance(this).selectExperienciaUsuario(idProjeto);
		int pericia = BDGerenciador.getInstance(this).selectPericiaUsuario(idProjeto);
		int treinamento = BDGerenciador.getInstance(this).selectTreinamentoUsuario(idProjeto);
		
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
                 BDGerenciador.getInstance(TelaCaracteristicasUsuarioActivity.this).updateExperiencia(idProjeto, getExperienciaSelecionada());
            }
       });
    	
        radioGroupPericia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                 BDGerenciador.getInstance(TelaCaracteristicasUsuarioActivity.this).updatePericia(idProjeto, getPericiaSelecionada());
            }
       });
    	
    	checkBoxTreinamento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
            	BDGerenciador.getInstance(TelaCaracteristicasUsuarioActivity.this).updateTreinamento(idProjeto, getTreinamentoSelecionado());
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
		idProjeto = TelaVisaoGeralActivity.getIdProjeto();
		radioGroupExperiencia = (RadioGroup) findViewById(R.id.radioGroupExperiencia);
		radioGroupPericia = (RadioGroup) findViewById(R.id.radioGroupPericia);
		checkBoxTreinamento = (CheckBox)findViewById(R.id.checkBoxTreinamentoUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Intent i = new Intent(TelaCaracteristicasUsuarioActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpTo(this,new Intent(this, TelaVisaoGeralActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
