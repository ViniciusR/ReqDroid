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
import android.widget.TextView;
import com.vrs.reqdroid.R;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.fragments.HipotesesFragment;
import com.vrs.reqdroid.util.HipotesesUtils;
import com.vrs.reqdroid.util.ProjetoUtils;

/**
 * Implementa a tela de hipotese detalhada.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaHipoteseDetalhadaActivity extends ActionBarActivity {

    private TextView descricaoHipoteseTV;
    private TextView dataTV;
    private TextView versaoTV;
    private TextView autorTV;
    private TextView titulo;
    private int versaoHipotese;
    private int subversaoHipotese;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.hipotese_detalhada);
        recebeHipotese();
        editaHipotese();
        removeHipotese();
        validaHipotese();
        editaAutorHipotese();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Carrega a descricaoHipoteseTV selecionado na lista de hipoteses atraves do banco de dados do aplicativo.
     */
    void recebeHipotese()
    {
        String descricaoHipotese = HipotesesFragment.getHipoteseSelecionada();
        int idProjeto = ProjetoUtils.getIdProjeto();
        String dataHipotese;
        String autorHipotese;

        descricaoHipoteseTV = (TextView)findViewById(R.id.textohipotesedetalhada);
        descricaoHipoteseTV.setText(descricaoHipotese);

        dataTV = (TextView)findViewById(R.id.campoDataHipotese);
        dataHipotese = BDGerenciador.getInstance(this).selectDataHipotese(descricaoHipotese, idProjeto);
        dataTV.setText(dataHipotese);

        versaoTV = (TextView)findViewById(R.id.campoVersaoHipotese);
        versaoHipotese = BDGerenciador.getInstance(this).selectVersaoHipotese(descricaoHipotese, idProjeto);
        subversaoHipotese = BDGerenciador.getInstance(this).selectSubversaoHipotese(descricaoHipotese, idProjeto);
        versaoTV.setText(versaoHipotese + "." + subversaoHipotese);

        autorTV = (EditText)findViewById(R.id.campoAutorHipotese);
        autorHipotese = BDGerenciador.getInstance(this).selectAutorHipotese(descricaoHipotese, idProjeto);
        autorTV.setText(autorHipotese);
    }

    /**
     * Edita a descricaoHipoteseTV ao clicar no botao "Editar".
     */
    void editaHipotese()
    {
        Button bEditarHipotese = (Button)findViewById(R.id.botaoEditarHipotese);

        bEditarHipotese.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                exibeJanelaEditarHipotese();
            }
        });
    }

    /**
     * Edita o autorTV da descricaoHipoteseTV.
     */
    private void editaAutorHipotese()
    {
        autorTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                editaAutorHipoteseBD(descricaoHipoteseTV.getText().toString(), autorTV.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * Remove a descricaoHipoteseTV da lista e do banco de dados do aplicativo.
     */
    void removeHipotese()
    {
        Button bRemoverHipotese = (Button)findViewById(R.id.botaoRemoverHipotese);
        final AlertDialog.Builder alertBoxConfirmaExclusao = new AlertDialog.Builder(this);

        bRemoverHipotese.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                alertBoxConfirmaExclusao.setTitle(R.string.alert_remover_hipotese_titulo);
                alertBoxConfirmaExclusao.setMessage(R.string.alert_remover_hipotese_msg);
                alertBoxConfirmaExclusao.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String hipotese = HipotesesFragment.getHipoteseSelecionada();
                        HipotesesFragment.atualizaListaRemovido(HipotesesFragment.getPosicaoHipoteseSelecionada());
                        HipotesesUtils.removeHipoteseBD(TelaHipoteseDetalhadaActivity.this, hipotese,
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
     * Valida a descricaoHipoteseTV movendo-a para a lista de requisitos do aplicativo.
     */
    void validaHipotese()
    {
        Button bValidarHipotese = (Button)findViewById(R.id.botaoValidarHipotese);
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        bValidarHipotese.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                alertbox.setTitle(R.string.alert_validar_hipotese_titulo);
                alertbox.setMessage(R.string.alert_validar_hipotese_msg);
                alertbox.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String hipotese = HipotesesFragment.getHipoteseSelecionada();
                        HipotesesFragment.atualizaListaRemovido(HipotesesFragment.getPosicaoHipoteseSelecionada());
                        HipotesesUtils.removeHipoteseBD(TelaHipoteseDetalhadaActivity.this, hipotese,
                                ProjetoUtils.getIdProjeto());
                        HipotesesUtils.validaHipoteseBD(TelaHipoteseDetalhadaActivity.this, hipotese, dataTV.getText().toString(),
                                versaoHipotese, subversaoHipotese, autorTV.getText().toString(), ProjetoUtils.getIdProjeto());
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
     * Exibe a janela para editar a descricao da descricaoHipoteseTV.
     */
    void exibeJanelaEditarHipotese()
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.alert_editar_hipotese);
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout layoutEditar = (LinearLayout)inflater.inflate(R.layout.alert_editar, null);

        final EditText entrada = (EditText) layoutEditar.findViewById(R.id.descricao_item);
        final NumberPicker npVersao = (NumberPicker) layoutEditar.findViewById(R.id.versao_item);
        final NumberPicker npSubversao = (NumberPicker) layoutEditar.findViewById(R.id.subversao_item);
        npVersao.setMinValue(1);
        npVersao.setMaxValue(10);
        npSubversao.setMinValue(0);
        npSubversao.setMaxValue(9);

        entrada.setText(descricaoHipoteseTV.getText().toString());
        npVersao.setValue(versaoHipotese);
        npSubversao.setValue(subversaoHipotese);
        final String descricaoAtual = entrada.getText().toString();

        alert.setView(layoutEditar);
        alert.create();

        alert.setPositiveButton(R.string.alert_salvar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!entrada.getText().toString().equals(""))
                {
                    HipotesesUtils.editaHipotese(TelaHipoteseDetalhadaActivity.this, descricaoAtual, entrada.getText().toString(),
                                                 npVersao.getValue(), npSubversao.getValue(),
                                                 HipotesesFragment.getPosicaoHipoteseSelecionada(), ProjetoUtils.getIdProjeto());
                    descricaoHipoteseTV.setText(entrada.getText());
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
     * Atualiza o autorTV da descricaoHipoteseTV no banco de dados do aplicativo.
     *
     * @param descricaoAtual A descricao atual da descricaoHipoteseTV
     * @param autorNovo O novo autorTV da descricaoHipoteseTV
     */
    void editaAutorHipoteseBD(String descricaoAtual, String autorNovo)
    {
        int idHipotese = BDGerenciador.getInstance(this).selectHipotesePorDescricao(descricaoAtual,
                                                                                     ProjetoUtils.getIdProjeto());
        BDGerenciador.getInstance(this).updateAutorHipotese(idHipotese, autorNovo);
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
                Intent i = new Intent(TelaHipoteseDetalhadaActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
