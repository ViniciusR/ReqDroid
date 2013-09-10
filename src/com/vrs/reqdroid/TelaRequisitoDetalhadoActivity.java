/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.util.OperacoesRequisitos;

/**
 * Implementa a tela de requisito detalhado.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaRequisitoDetalhadoActivity extends SherlockActivity {
     
    private TextView requisito;
    private TextView data;
    private TextView versao;
    private TextView autor;
    private TextView titulo;
    private RatingBar prioridade;
    private int versaoRequisito;
        
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.requisitodetalhado);
        recebeRequisito();
        editaRequisito();
        removeRequisito();
        moveRequisito();
        atualizaPrioridade();
        editaAutorRequisito();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    /**
     * Carrega o requisito selecionado na lista de requisitos atraves do banco de dados do aplicativo.
     */
    private void recebeRequisito()
    {
      String texto = TelaRequisitosActivity.getRequisitoSelecionado();
      int idProj = TelaVisaoGeralActivity.getIdProjeto();
      String dataRequisito;      
      int prioridadeRequisito;
      String autorRequisito;
      int tituloRequisito;
      
      requisito = (TextView)findViewById(R.id.textorequisitodetalhado);   
      requisito.setText(texto);

      data = (TextView)findViewById(R.id.campoDataRequisito);
      dataRequisito = BDGerenciador.getInstance(this).selectDataRequisito(texto, idProj);
      data.setText(dataRequisito);
      
      versao = (TextView)findViewById(R.id.campoVersaoRequisito);  
      versaoRequisito = BDGerenciador.getInstance(this).selectVersaoRequisito(texto, idProj);
      versao.setText(versaoRequisito + ".0");
      
      prioridade = (RatingBar)findViewById(R.id.ratingBarPrioridadeRequisito); 
      prioridadeRequisito = BDGerenciador.getInstance(this).selectPrioridadeRequisito(texto, idProj);
      prioridade.setRating(prioridadeRequisito);
      
      autor = (EditText)findViewById(R.id.campoAutorRequisito);
      autorRequisito = BDGerenciador.getInstance(this).selectAutorRequisito(texto, idProj);
      autor.setText(autorRequisito);
      
      titulo = (TextView)findViewById(R.id.campoTituloRequisito); 
      tituloRequisito = BDGerenciador.getInstance(this).selectNumeroRequisito(texto, idProj);
      titulo.setText(getResources().getString(R.string.tela_detalhes_requisito_nome_requisito) + tituloRequisito);
      
    }
    
    /**
     * Atualiza o RatingBar de prioridade do requisito ao clicar em uma das estrelas.
     * A atualizacao tambem e feita no banco de dados do aplicativo.
     */
    private void atualizaPrioridade()
    {
    	 
    	prioridade = (RatingBar)findViewById(R.id.ratingBarPrioridadeRequisito); 
     
    	//Listener para a RatingBar da prioridade.
    	prioridade.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    			boolean fromUser) {
    			int idRequisito;
    		       idRequisito = BDGerenciador.getInstance(TelaRequisitoDetalhadoActivity.this).
    		    		   		 selectRequisitoPorDescricao(requisito.getText().toString(),
                                                             TelaVisaoGeralActivity.getIdProjeto());
    		       BDGerenciador.getInstance(TelaRequisitoDetalhadoActivity.this).updatePrioridadeRequisito(idRequisito,
                                            (int)prioridade.getRating());
    		}
    	});
      }
    
    /**
     * Edita o requisito ao clicar no botao "Editar".
     */
    private void editaRequisito()
    {
       Button bEditarRequisito = (Button)findViewById(R.id.botaoEditarRequisito);   
       
       bEditarRequisito.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            exibeJanelaEditarRequisito();            
           }
       });
    }
   
    /**
     * Edita o autor do requisito.
     */
    private void editaAutorRequisito()
    {
        autor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                editaAutorRequisitoBD(requisito.getText().toString(),autor.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    } 
    
    /**
     * Remove o requisito da lista e do banco de dados do aplicativo.
     */
    private void removeRequisito()
    {
        Button bRemoverRequisito = (Button)findViewById(R.id.botaoRemoverRequisito);   
        final AlertDialog.Builder alertBoxConfirmaExclusao = new AlertDialog.Builder(this);
       
       bRemoverRequisito.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {            
            	alertBoxConfirmaExclusao.setTitle(R.string.alert_remover_requisito_titulo);
            	alertBoxConfirmaExclusao.setMessage(R.string.alert_remover_requisito_msg);
            	alertBoxConfirmaExclusao.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                          String requisito = TelaRequisitosActivity.getRequisitoSelecionado();
                          TelaRequisitosActivity.atualizaListaRemovido(TelaRequisitosActivity.getPosicaoRequisitoSelecionado());
                          OperacoesRequisitos.removeRequisitoBD(TelaRequisitoDetalhadoActivity.this, requisito,
                                                                TelaVisaoGeralActivity.getIdProjeto());
                          finish();
                    }
                });
            	alertBoxConfirmaExclusao.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
            	alertBoxConfirmaExclusao.show();           
           }
       });        
    }

    /**
     * Move o requisito para a lista de requisitos atrasados do aplicativo.
     */
    private void moveRequisito()
    {
       Button bMoverRequisito = (Button)findViewById(R.id.botaoAtrasarRequisito);   
       final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
      
       bMoverRequisito.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {            
               alertbox.setTitle(R.string.alert_atrasar_requisito_titulo);
               alertbox.setMessage(R.string.alert_atrasar_requisito_msg);
               alertbox.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                         String requisito = TelaRequisitosActivity.getRequisitoSelecionado();
                         TelaRequisitosActivity.atualizaListaRemovido(TelaRequisitosActivity.getPosicaoRequisitoSelecionado());
                         OperacoesRequisitos.removeRequisitoBD(TelaRequisitoDetalhadoActivity.this, requisito,
                                                               TelaVisaoGeralActivity.getIdProjeto());
                         OperacoesRequisitos.moveRequisitoBD(TelaRequisitoDetalhadoActivity.this, requisito, data.getText().toString(),
                                                             (int)prioridade.getRating(), versaoRequisito, autor.getText().toString(),
                                                             TelaVisaoGeralActivity.getIdProjeto());
                         finish();
                   }
               });
              alertbox.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int whichButton) {
                   }
               });
              alertbox.show();           
          }
      });        
    }

    /**
     * Exibe a janela para editar a descricao do requisito.
     */
    private void exibeJanelaEditarRequisito()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.alert_editar_requisito_titulo);

        final EditText entrada = new EditText(this);
        entrada.setText(requisito.getText().toString());
        alert.setView(entrada);

        alert.setPositiveButton(R.string.alert_salvar, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
        	if (!entrada.getText().toString().equals(""))
        	{
	        	versaoRequisito++;
	            String descricaoAtual = requisito.getText().toString();
	            requisito.setText(entrada.getText().toString());
	            versao.setText(versaoRequisito + ".0");
                TelaRequisitosActivity.atualizaLista(TelaRequisitosActivity.getPosicaoRequisitoSelecionado(),
                        entrada.getText().toString());

	            OperacoesRequisitos.editaRequisitoBD(TelaRequisitoDetalhadoActivity.this, descricaoAtual, entrada.getText().toString(),
                                                     versaoRequisito, TelaVisaoGeralActivity.getIdProjeto());
        	}        	
          }
       });

        alert.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
          }
        });
        alert.show();
    }

    /**
     * Atualiza o autor do requisito no banco de dados do aplicativo.
     *
     * @param descricaoAtual A descricao atual do requisito
     * @param autorNovo O novo autor do requisito
     */
    private void editaAutorRequisitoBD(String descricaoAtual, String autorNovo)
    {
        int idRequisito = BDGerenciador.getInstance(this).selectRequisitoPorDescricao(descricaoAtual,
                      TelaVisaoGeralActivity.getIdProjeto());
        BDGerenciador.getInstance(this).updateAutorRequisito(idRequisito, autorNovo);
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
                Intent i = new Intent(TelaRequisitoDetalhadoActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, TelaRequisitosActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
