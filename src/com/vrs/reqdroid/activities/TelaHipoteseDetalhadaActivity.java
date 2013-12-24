/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private TextView hipotese;
    private TextView data;
    private TextView versao;
    private TextView autor;
    private TextView titulo;
    private int versaoHipotese;

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
     * Carrega a hipotese selecionado na lista de hipoteses atraves do banco de dados do aplicativo.
     */
    void recebeHipotese()
    {
        String texto = HipotesesFragment.getHipoteseSelecionada();
        int idProj = ProjetoUtils.getIdProjeto();
        String dataHipotese;
        String autorHipotese;

        hipotese = (TextView)findViewById(R.id.textohipotesedetalhada);
        hipotese.setText(texto);

        data = (TextView)findViewById(R.id.campoDataHipotese);
        dataHipotese = BDGerenciador.getInstance(this).selectDataHipotese(texto, idProj);
        data.setText(dataHipotese);

        versao = (TextView)findViewById(R.id.campoVersaoHipotese);
        versaoHipotese = BDGerenciador.getInstance(this).selectVersaoHipotese(texto, idProj);
        versao.setText(versaoHipotese + ".0");

        autor = (EditText)findViewById(R.id.campoAutorHipotese);
        autorHipotese = BDGerenciador.getInstance(this).selectAutorHipotese(texto, idProj);
        autor.setText(autorHipotese);
    }

    /**
     * Edita a hipotese ao clicar no botao "Editar".
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
     * Edita o autor da hipotese.
     */
    private void editaAutorHipotese()
    {
        autor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                editaAutorHipoteseBD(hipotese.getText().toString(), autor.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * Remove a hipotese da lista e do banco de dados do aplicativo.
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
     * Valida a hipotese movendo-a para a lista de requisitos do aplicativo.
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
                        HipotesesUtils.validaHipoteseBD(TelaHipoteseDetalhadaActivity.this, hipotese, data.getText().toString(),
                                versaoHipotese, autor.getText().toString(), ProjetoUtils.getIdProjeto());
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
     * Exibe a janela para editar a descricao da hipotese.
     */
    void exibeJanelaEditarHipotese()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.alert_editar_hipotese);

        final EditText entrada = new EditText(this);
        entrada.setText(hipotese.getText().toString());
        alert.setView(entrada);

        alert.setPositiveButton(R.string.alert_salvar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!entrada.getText().toString().equals(""))
                {
                    versaoHipotese++;
                    String descricaoAtual = hipotese.getText().toString();
                    hipotese.setText(entrada.getText().toString());
                    versao.setText(versaoHipotese + ".0");
                    HipotesesFragment.atualizaLista(HipotesesFragment.getPosicaoHipoteseSelecionada(),
                            entrada.getText().toString());

                    HipotesesUtils.editaHipoteseBD(TelaHipoteseDetalhadaActivity.this, descricaoAtual, entrada.getText().toString(),
                            versaoHipotese, ProjetoUtils.getIdProjeto());
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
     * Atualiza o autor da hipotese no banco de dados do aplicativo.
     *
     * @param descricaoAtual A descricao atual da hipotese
     * @param autorNovo O novo autor da hipotese
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
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpTo(this,new Intent(this, DrawerPrincipalActivity.class));
        }
        return true;
    }
}
