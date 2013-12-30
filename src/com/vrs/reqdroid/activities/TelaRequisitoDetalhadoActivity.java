/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import com.vrs.reqdroid.R;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.fragments.RequisitosFragment;

import com.vrs.reqdroid.util.ProjetoUtils;
import com.vrs.reqdroid.util.RequisitosUtils;

/**
 * Implementa a tela de requisito detalhado.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaRequisitoDetalhadoActivity extends ActionBarActivity {
     
    private TextView descricaoRequisitoTV;
    private TextView dataTV;
    private TextView versaoTV;
    private TextView autorTV;
    private TextView tituloTV;
    private RatingBar prioridadeTV;
    private int versaoRequisito;
    private int subversaoRequisito;
        
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.requisito_detalhado);
        recebeRequisito();
        editaRequisito();
        removeRequisito();
        moveRequisito();
        atualizaPrioridade();
        editaAutorRequisito();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Carrega o descricaoRequisitoTV selecionado na lista de requisitos atraves do banco de dados do aplicativo.
     */
    private void recebeRequisito()
    {
      String textoRequisito = RequisitosFragment.getRequisitoSelecionado();
      int idProjeto = ProjetoUtils.getIdProjeto();
      String dataRequisito;
      int prioridadeRequisito;
      String autorRequisito;
      int tituloRequisito;
      
      descricaoRequisitoTV = (TextView)findViewById(R.id.textoRequisitoDetalhado);
      descricaoRequisitoTV.setText(textoRequisito);

      dataTV = (TextView)findViewById(R.id.campoDataRequisito);
      dataRequisito = BDGerenciador.getInstance(this).selectDataRequisito(textoRequisito, idProjeto);
      dataTV.setText(dataRequisito);
      
      versaoTV = (TextView)findViewById(R.id.campoVersaoRequisito);
      versaoRequisito = BDGerenciador.getInstance(this).selectVersaoRequisito(textoRequisito, idProjeto);
      subversaoRequisito = BDGerenciador.getInstance(this).selectSubversaoRequisito(textoRequisito, idProjeto);
      versaoTV.setText(versaoRequisito + "." + subversaoRequisito);
      
      prioridadeTV = (RatingBar)findViewById(R.id.ratingBarPrioridadeRequisito);
      prioridadeRequisito = BDGerenciador.getInstance(this).selectPrioridadeRequisito(textoRequisito, idProjeto);
      prioridadeTV.setRating(prioridadeRequisito);
      
      autorTV = (EditText)findViewById(R.id.campoAutorRequisito);
      autorRequisito = BDGerenciador.getInstance(this).selectAutorRequisito(textoRequisito, idProjeto);
      autorTV.setText(autorRequisito);
      
      tituloTV = (TextView)findViewById(R.id.campoTituloRequisito);
      tituloRequisito = BDGerenciador.getInstance(this).selectNumeroRequisito(textoRequisito, idProjeto);
      tituloTV.setText(getResources().getString(R.string.tela_detalhes_requisito_nome_requisito) + tituloRequisito);
    }

    /**
     * Atualiza o RatingBar de prioridadeTV do descricaoRequisitoTV ao clicar em uma das estrelas.
     * A atualizacao tambem e feita no banco de dados do aplicativo.
     */
    private void atualizaPrioridade()
    {
    	 
    	prioridadeTV = (RatingBar)findViewById(R.id.ratingBarPrioridadeRequisito);
     
    	//Listener para a RatingBar da prioridadeTV.
    	prioridadeTV.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                int idRequisito;
                idRequisito = BDGerenciador.getInstance(TelaRequisitoDetalhadoActivity.this).
                        selectRequisitoPorDescricao(descricaoRequisitoTV.getText().toString(),
                                ProjetoUtils.getIdProjeto());
                BDGerenciador.getInstance(TelaRequisitoDetalhadoActivity.this).updatePrioridadeRequisito(idRequisito,
                        (int) prioridadeTV.getRating());
            }
        });
      }
    
    /**
     * Edita o descricaoRequisitoTV ao clicar no botao "Editar".
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
     * Edita o autorTV do descricaoRequisitoTV.
     */
    private void editaAutorRequisito()
    {
        autorTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                editaAutorRequisitoBD(descricaoRequisitoTV.getText().toString(), autorTV.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    } 
    
    /**
     * Remove o descricaoRequisitoTV da lista e do banco de dados do aplicativo.
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
                          String requisito = RequisitosFragment.getRequisitoSelecionado();
                          RequisitosFragment.atualizaListaRemovido(RequisitosFragment.getPosicaoRequisitoSelecionado());
                          RequisitosUtils.removeRequisitoBD(TelaRequisitoDetalhadoActivity.this, requisito,
                                  ProjetoUtils.getIdProjeto());
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
     * Move o descricaoRequisitoTV para a lista de requisitos atrasados do aplicativo.
     */
    private void moveRequisito()
    {
       Button bMoverRequisito = (Button)findViewById(R.id.botaoMoverRequisito);
       final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
      
       bMoverRequisito.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {            
               alertbox.setTitle(R.string.alert_atrasar_requisito_titulo);
               alertbox.setMessage(R.string.alert_atrasar_requisito_msg);
               alertbox.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                         String requisito = RequisitosFragment.getRequisitoSelecionado();
                         RequisitosFragment.atualizaListaRemovido(RequisitosFragment.getPosicaoRequisitoSelecionado());
                         RequisitosUtils.removeRequisitoBD(TelaRequisitoDetalhadoActivity.this, requisito,
                                 ProjetoUtils.getIdProjeto());
                         RequisitosUtils.moveRequisitoBD(TelaRequisitoDetalhadoActivity.this, requisito, dataTV.getText().toString(),
                                 (int) prioridadeTV.getRating(), versaoRequisito, subversaoRequisito, autorTV.getText().toString(),
                                 ProjetoUtils.getIdProjeto());
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
     * Exibe a janela para editar a descricao do descricaoRequisitoTV.
     */
    private void exibeJanelaEditarRequisito()
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.alert_editar_requisito_titulo);
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout layoutEditar = (LinearLayout)inflater.inflate(R.layout.alert_editar, null);

        final EditText entrada = (EditText) layoutEditar.findViewById(R.id.descricao_item);
        final NumberPicker npVersao = (NumberPicker) layoutEditar.findViewById(R.id.versao_item);
        final NumberPicker npSubversao = (NumberPicker) layoutEditar.findViewById(R.id.subversao_item);
        npVersao.setMinValue(1);
        npVersao.setMaxValue(10);
        npSubversao.setMinValue(0);
        npSubversao.setMaxValue(9);

        entrada.setText(descricaoRequisitoTV.getText().toString());
        npVersao.setValue(versaoRequisito);
        npSubversao.setValue(subversaoRequisito);
        final String descricaoAtual = entrada.getText().toString();

        alert.setView(layoutEditar);
        alert.create();

        alert.setPositiveButton(R.string.alert_salvar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!entrada.getText().toString().equals(""))
                {
                     RequisitosUtils.editaRequisito(TelaRequisitoDetalhadoActivity.this, descricaoAtual, entrada.getText().toString(),
                                                    npVersao.getValue(), npSubversao.getValue(),
                                                    RequisitosFragment.getPosicaoRequisitoSelecionado(), ProjetoUtils.getIdProjeto());
                    descricaoRequisitoTV.setText(entrada.getText());
                    versaoTV.setText(npVersao.getValue() + "." + npSubversao.getValue());
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
     * Atualiza o autorTV do descricaoRequisitoTV no banco de dados do aplicativo.
     *
     * @param descricaoAtual A descricao atual do descricaoRequisitoTV
     * @param autorNovo O novo autorTV do descricaoRequisitoTV
     */
    private void editaAutorRequisitoBD(String descricaoAtual, String autorNovo)
    {
        int idRequisito = BDGerenciador.getInstance(this).selectRequisitoPorDescricao(descricaoAtual,
                      ProjetoUtils.getIdProjeto());
        BDGerenciador.getInstance(this).updateAutorRequisito(idRequisito, autorNovo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_aplicativo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusobre:
                Intent i = new Intent(TelaRequisitoDetalhadoActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
