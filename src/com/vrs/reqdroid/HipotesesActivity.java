/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.vrs.reqdroid.util.ListViewHipotesesAdapter;
import com.vrs.reqdroid.util.OperacoesHipoteses;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementa a tela de listas de hipoteses.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class HipotesesActivity extends Activity {

    private static ArrayList<String> hipoteses;
    private static ListViewHipotesesAdapter lvHipotesesAdapter;
    private ListView lvHipoteses;
    private static int idProjeto;
    private static EditText editTextHipotese;
    private static String hipoteseSelecionada; //Util pra quando a hipotese e selecionada para exibir detalhes.
    private static int posicaoHipoteseSelecionada; //Util pra quando a hipotese e selecionada para exibir detalhes.
    private int posicao; //Util para a o menu de opcoes.
    private String descricao; //Util para o menu de opcoes.

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hipoteses);
        init();
        carregaHipoteses();
        adicionaHipotese();
        exibeDetalhesHipotese();
    }

    /**
     * Carrega os hipoteses existentes no banco de dados e adiciona-os a lista.
     */
    private void carregaHipoteses()
    {
        hipoteses = OperacoesHipoteses.carregaHipotesesBD(HipotesesActivity.this, idProjeto);
        lvHipoteses.setCacheColorHint(Color.TRANSPARENT);
        lvHipoteses.setSelectionAfterHeaderView();
        lvHipotesesAdapter = new ListViewHipotesesAdapter(this, hipoteses, botaoOpcoesListener);
        lvHipoteses.setAdapter(lvHipotesesAdapter);
    }

    /**
     * Adiciona uma nova hipotese na lista.
     */
    private void adicionaHipotese()
    {
        final Button botaoAdicionarHipotese = (Button) findViewById(R.id.botaoAdicionarHipotese);

        botaoAdicionarHipotese.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (OperacoesHipoteses.hipotesePreenchida(editTextHipotese.getText().toString()))
                {
                    salvaHipotese(editTextHipotese.getText().toString());
                    hipoteses.add(lvHipoteses.getChildCount(), editTextHipotese.getText().toString());
                    editTextHipotese.setText("");
                    lvHipotesesAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Abre a tela de detalhes da hipotese selecionada.
     */
    private void exibeDetalhesHipotese()
    {
        final Intent i = new Intent(HipotesesActivity.this, HipoteseDetalhadaActivity.class);

        lvHipoteses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                LinearLayout caixaHipotese = (LinearLayout) view; //LinearLayout que contem o texto e botao
                final TextView txtHipotese = (TextView) caixaHipotese.getChildAt(0);
                hipoteseSelecionada = txtHipotese.getText().toString();
                posicaoHipoteseSelecionada = position;
                startActivity(i);
            }
        });
    }

    /**
     * Listener do botao de opcoes da hipotese.
     */
    private final View.OnClickListener botaoOpcoesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LinearLayout caixaHipotese = (LinearLayout)view.getParent();
            TextView txtHipotese = (TextView)caixaHipotese.getChildAt(0);
            posicao = lvHipoteses.getPositionForView(caixaHipotese);
            descricao = txtHipotese.getText().toString();

            //Se a versao do Android e 3.0 ou superior, exibe o PopUpMenu, senao, o ContextMenu.
            if (api4OuSuperior())
            {
                exibePopupMenuOpcoes(view);
            }
            else{
                ImageButton bOpcoes = (ImageButton)caixaHipotese.getChildAt(1); //O botao de opcoes.
                registerForContextMenu(bOpcoes);
                HipotesesActivity.this.openContextMenu(bOpcoes);
            }
        }
    };

    /**
     * Cria o menu de contexto das opcoes da hipotese.
     * Funciona apenas para a versao 2.3 ou inferior do Android.
     *
     * @param menu O menu de opcoes
     * @param v A view
     * @param menuInfo menuInfo
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuopcoeshipotese, menu);
    }

    /**
     * Realiza operacoes das operacoes de menu da hipotese.
     * Funciona apenas para a versao 2.3 ou inferior do Android.
     *
     * @param item O item do menu
     * @return Verdadeiro se uma opcao do menu foi selecionada.
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.opcaoEditarHipotese:
                menuEdita();
                return true;
            case R.id.opcaoValidarHipotese:
                menuValida();
                return true;
            case R.id.opcaoDeletarHipotese:
                menuDeleta();
            default:
                return false;
        }
    }

    /**
     * Mostra o PopUpMenu das opcoes da hipotese
     * Funciona apenas para a versao 3.0 ou superior do Android.
     *
     * @param v a view da hipotese
     *
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    void exibePopupMenuOpcoes(final View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.menuopcoeshipotese, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opcaoEditarHipotese:
                        menuEdita();
                        break;
                    case R.id.opcaoValidarHipotese:
                        menuValida();
                        break;
                    case R.id.opcaoDeletarHipotese:
                        menuDeleta();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    /**
     * Realiza a operacao de editar a hipotese.
     */
    private void menuEdita()
    {
        OperacoesHipoteses.editaHipotese(HipotesesActivity.this, hipoteses, descricao,
                posicao, idProjeto, lvHipotesesAdapter);
    }

    /**
     * Realiza a operacao de validar a hipotese.
     */
    private void menuValida()
    {
        OperacoesHipoteses.validaHipotese(HipotesesActivity.this, hipoteses, descricao,
                posicao, idProjeto, lvHipotesesAdapter);
    }

    /**
     * Realiza a operacao de deletar a hipotese.
     */
    private void menuDeleta()
    {
        OperacoesHipoteses.removeHipotese(HipotesesActivity.this, hipoteses, descricao,
                posicao, idProjeto, lvHipotesesAdapter);
    }

    /**
     * Salva o hipotese no banco de dados do aplicativo.
     */
    private void salvaHipotese(String descricaoHipotese)
    {
        OperacoesHipoteses.salvaHipoteseBD(this, descricaoHipotese, getDataATual(), idProjeto);
    }

    /**
     * Atribui a data atual.
     *
     * @return a data atual
     */
    private static String getDataATual()
    {
        return DateFormat.getDateInstance().format(new Date());
    }
    /**
     * Retorna a descricao da hipotese selecionada.
     *
     * @return A descricao da hipotese
     */
    public static String getHipoteseSelecionada()
    {
        return hipoteseSelecionada;
    }

    /**
     * Retorna a posicao da hipotese selecionada na lista.
     *
     * @return a posicao da hipotese
     */
    public static int getPosicaoHipoteseSelecionada()
    {
        return posicaoHipoteseSelecionada;
    }

    /**
     * Atualiza a lista de hipoteses quando uma hipotese e editada na tela de detalhes.
     *
     * @param posicao a posicao da hipotese na lista
     * @param descricao a descricao da hipotese
     */
    public static void atualizaLista(int posicao, String descricao)
    {
        hipoteses.set(posicao, descricao);
        lvHipotesesAdapter.notifyDataSetChanged();
    }

    /**
     * Atualiza a lista de hipoteses quando uma hipotese e removida atraves da tela de detalhes.
     *
     * @param posicao A posicao da hipotese na lista
     */
    public static void atualizaListaRemovido(int posicao)
    {
        hipoteses.remove(posicao);
        lvHipotesesAdapter.notifyDataSetChanged();
    }

    /**
     * Verifica se a versao da API atual (a que esta rodando o app) e superior ao Android 2.x.
     *
     * @return Verdadeiro se a API atual e superior ao Android 2.x.
     */
    private boolean api4OuSuperior()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Inicia as variaveis da classe.
     */
    private void init()
    {
        idProjeto = TelaVisaoGeralActivity.getIdProjeto();
        lvHipoteses = (ListView) findViewById(R.id.lvHipoteses);
        hipoteses = new ArrayList<String>();
        editTextHipotese = (EditText) findViewById(R.id.etHipotese);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusobre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusobre:
                Intent i = new Intent(HipotesesActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }
}