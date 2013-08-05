/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.modelo.Projeto;

/**
 * Implementa a tela do escopo do projeto do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaEscopoActivity extends SherlockActivity {
    
    private static Projeto projeto;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.escopo);
        carregaProjeto();
        preencheInformacoesProjeto();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        preencheInformacoesProjeto();
    }


    /**
     * Carrega o projeto ao entrar na tela do escopo. Se o projeto foi aberto, 
     * ira exibir o escopo do determinado projeto que foi selecionado na tela
     * de abrir projetos. Senao, o escopo e de um novo projeto que esta sendo criado.
     */
    private void carregaProjeto()
    {
        String projetoSelecionado;
        projetoSelecionado = TelaVisaoGeralActivity.getTituloProjeto();
        projeto = BDGerenciador.getInstance(TelaEscopoActivity.this).selectProjeto(projetoSelecionado);
    }

    /**
     * Preenche as informacoes nao editadas do escopo nos TextViews,
     * ou seja, as informacoes atuais.
     */
    private void preencheInformacoesProjeto()
    {
    	TextView tituloAtual = (TextView)findViewById(R.id.tituloSistema);
    	TextView descricaoAtual = (TextView)findViewById(R.id.descricaoSistema);
    	TextView objetivosAtual = (TextView)findViewById(R.id.objetivosSistema);
    	TextView beneficiosAtual = (TextView)findViewById(R.id.beneficiosSistema);
    	TextView publicoAtual = (TextView)findViewById(R.id.publicoSistema);
    	TextView data = (TextView)findViewById(R.id.dataSistema);
    	
        tituloAtual.setText(projeto.getTitulo());
        descricaoAtual.setText(projeto.getDescricao());
        objetivosAtual.setText(projeto.getObjetivos());
        beneficiosAtual.setText(projeto.getBeneficios());
        publicoAtual.setText(projeto.getPublicoAlvo());    
        data.setText(projeto.getData());
    }

    /**
     * Remove o projeto e todas as suas informacoes.
     */
    private void removeProjeto()
    {
    	final int idProjeto = TelaVisaoGeralActivity.getIdProjeto();

    	final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
    	alertbox.setTitle(R.string.alert_remover_projeto_titulo);
    	alertbox.setMessage(R.string.alert_remover_projeto_msg);

    	alertbox.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int whichButton) {   	 
              removeProjetoBD(idProjeto);
              final Intent i = new Intent(TelaEscopoActivity.this, TelaPrincipalActivity.class);  
              startActivity(i);
              finish();
         }
    	});
    	alertbox.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int whichButton) {
    		}
    	});
    	alertbox.show();           
    }
    
    /**
     * Remove o projeto do banco de dados do aplicativo.
     * 
     * @param id O id do projeto
     */
    private void removeProjetoBD(int id)
    {
    	BDGerenciador.getInstance(this).deleteProjeto(id);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuopcoesprojeto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menueditarprojeto:     
            					Intent i = new Intent(TelaEscopoActivity.this, TelaEditarEscopoActivity.class);  
            					startActivity(i);
                                this.onPause();
                                break;
            case R.id.menuexcluirprojeto:     
            					removeProjeto();
                                break;
            case android.R.id.home:
                NavUtils.navigateUpTo(this,new Intent(this, TelaVisaoGeralActivity.class));
        }
        return true;
    }

    public static Projeto getProjeto()
    {
        return projeto;
    }
}
