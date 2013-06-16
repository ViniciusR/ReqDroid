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
import com.vrs.reqdroid.util.ListViewRequisitosAtrasadosAdapter;
import com.vrs.reqdroid.util.OperacoesRequisitos;
import com.vrs.reqdroid.util.OperacoesRequisitosAtrasados;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementa a tela de listas de requisitosAtrasados.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaRequisitosAtrasadosActivity extends Activity {

    private static ArrayList<String> requisitosAtrasados;
    private static ListViewRequisitosAtrasadosAdapter lvRequisitosAtrasadosAdapter;
    private ListView lvRequisitosAtrasados;
    private static int idProjeto;
    private static EditText editTextRequisitoAtrasado;
    private static String requisitoSelecionado; //Util pra quando o requisito e selecionado para exibir detalhes.
    private static int posicaoRequisitoSelecionado; //Util pra quando o requisito e selecionado para exibir detalhes.
    private int posicao; //Util para a o menu de opcoes.
    private String descricao; //Util para o menu de opcoes.

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requisitosatrasados);
        init();
        carregaRequisitos();
        adicionaRequisito();
        exibeDetalhesRequisito();
    }

    /**
     * Carrega os requisitosAtrasados existentes no banco de dados e adiciona-os a lista.
     */
    private void carregaRequisitos()
    {
        requisitosAtrasados = OperacoesRequisitosAtrasados.carregaRequisitosBD(TelaRequisitosAtrasadosActivity.this, idProjeto);
        lvRequisitosAtrasados.setCacheColorHint(Color.TRANSPARENT);
        lvRequisitosAtrasados.setSelectionAfterHeaderView();
        lvRequisitosAtrasadosAdapter = new ListViewRequisitosAtrasadosAdapter(this, requisitosAtrasados, botaoOpcoesListener);
        lvRequisitosAtrasados.setAdapter(lvRequisitosAtrasadosAdapter);
    }

    /**
     * Adiciona um novo requisito na lista.
     */
    private void adicionaRequisito()
    {
        final Button botaoAdicionarRequisito = (Button) findViewById(R.id.botaoAdicionarRequisitoAtrasado);

        botaoAdicionarRequisito.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (OperacoesRequisitos.requisitoPreenchido(editTextRequisitoAtrasado.getText().toString()))
                {
                    salvaRequisito(editTextRequisitoAtrasado.getText().toString());
                    requisitosAtrasados.add(lvRequisitosAtrasados.getChildCount(), editTextRequisitoAtrasado.getText().toString());
                    editTextRequisitoAtrasado.setText("");
                    lvRequisitosAtrasadosAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Abre a tela de detalhes do requisito selecionado.
     */
    private void exibeDetalhesRequisito()
    {
        final Intent i = new Intent(TelaRequisitosAtrasadosActivity.this, TelaRequisitoAtrasadoDetalhadoActivity.class);

        lvRequisitosAtrasados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                LinearLayout caixaRequisito = (LinearLayout) view; //LinearLayout que contem o texto e botao
                final TextView txtRequisito = (TextView) caixaRequisito.getChildAt(0);
                requisitoSelecionado = txtRequisito.getText().toString();
                posicaoRequisitoSelecionado = position;
                startActivity(i);
            }
        });
    }

    /**
     * Listener do botao de opcoes do requisito.
     */
    private final View.OnClickListener botaoOpcoesListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LinearLayout caixaRequisito = (LinearLayout)view.getParent();
            TextView txtRequisito = (TextView)caixaRequisito.getChildAt(0);
            posicao = lvRequisitosAtrasados.getPositionForView(caixaRequisito);
            descricao = txtRequisito.getText().toString();

            //Se a versao do Android e 3.0 ou superior, exibe o PopUpMenu, senao, o ContextMenu.
            if (api4OuSuperior())
            {
                exibePopupMenuOpcoes(view);
            }
            else{
                ImageButton bOpcoes = (ImageButton)caixaRequisito.getChildAt(1); //O botao de opcoes.
                registerForContextMenu(bOpcoes);
                TelaRequisitosAtrasadosActivity.this.openContextMenu(bOpcoes);
            }
        }
    };

    /**
     * Cria o menu de contexto das opcoes do requisito.
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
        inflater.inflate(R.menu.menuopcoesrequisitoatrasado, menu);
    }

    /**
     * Realiza operacoes das operacoes de menu do requisito.
     * Funciona apenas para a versao 2.3 ou inferior do Android.
     *
     * @param item O item do menu
     * @return Verdadeiro se uma opcao do menu foi selecionada.
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.opcaoEditarRequisitoAtrasado:
                menuEdita();
                return true;
            case R.id.opcaoMoverRequisitoAtrasado:
                menuMove();
                return true;
            case R.id.opcaoDeletarRequisitoAtrasado:
                menuDeleta();
            default:
                return false;
        }
    }

    /**
     * Mostra o PopUpMenu das opcoes do requisito.
     * Funciona apenas para a versao 3.0 ou superior do Android.
     *
     * @param v a view do requisito
     *
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void exibePopupMenuOpcoes(final View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.menuopcoesrequisitoatrasado, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opcaoEditarRequisitoAtrasado:
                        menuEdita();
                        break;
                    case R.id.opcaoMoverRequisitoAtrasado:
                        menuMove();
                        break;
                    case R.id.opcaoDeletarRequisitoAtrasado:
                        menuDeleta();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    /**
     * Realiza a operacao de editar o requisito.
     */
    private void menuEdita()
    {
        OperacoesRequisitosAtrasados.editaRequisito(TelaRequisitosAtrasadosActivity.this, requisitosAtrasados, descricao,
                posicao, idProjeto, lvRequisitosAtrasadosAdapter);
    }

    /**
     * Realiza a operacao de mover (atrasar) o requisito.
     */
    private void menuMove()
    {
        OperacoesRequisitosAtrasados.moveRequisito(TelaRequisitosAtrasadosActivity.this, requisitosAtrasados, descricao,
                posicao, idProjeto, lvRequisitosAtrasadosAdapter);
    }

    /**
     * Realiza a operacao de deletar o requisito.
     */
    private void menuDeleta()
    {
        OperacoesRequisitosAtrasados.removeRequisito(TelaRequisitosAtrasadosActivity.this, requisitosAtrasados, descricao,
                posicao, idProjeto, lvRequisitosAtrasadosAdapter);
    }

    /**
     * Salva o requisito no banco de dados do aplicativo.
     */
    private void salvaRequisito(String descricaoRequisito)
    {
        OperacoesRequisitosAtrasados.salvaRequisitoBD(this, descricaoRequisito, getDataATual(), idProjeto);
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
     * Retorna a descricao do requisito selecionado.
     *
     * @return A descricao do requisito
     */
    public static String getRequisitoSelecionado()
    {
        return requisitoSelecionado;
    }

    /**
     * Retorna a posicao do requisito selecionado na lista.
     *
     * @return a posicao do requisito
     */
    public static int getPosicaoRequisitoSelecionado()
    {
        return posicaoRequisitoSelecionado;
    }

    /**
     * Atualiza a lista de requisitosAtrasados quando um requisito e editado na tela de detalhes.
     *
     * @param posicao a posicao do requisito na lista
     * @param descricao a descricao do requisito
     */
    public static void atualizaLista(int posicao, String descricao)
    {
        requisitosAtrasados.set(posicao, descricao);
        lvRequisitosAtrasadosAdapter.notifyDataSetChanged();
    }

    /**
     * Atualiza a lista de requisitosAtrasados quando um requisito e removido atraves da tela de detalhes.
     *
     * @param posicao A posicao do requisito na lista
     */
    public static void atualizaListaRemovido(int posicao)
    {
        requisitosAtrasados.remove(posicao);
        lvRequisitosAtrasadosAdapter.notifyDataSetChanged();
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
        lvRequisitosAtrasados = (ListView) findViewById(R.id.lvRequisitosAtrasados);
        requisitosAtrasados = new ArrayList<String>();
        editTextRequisitoAtrasado = (EditText) findViewById(R.id.etRequisitoAtrasado);
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
                Intent i = new Intent(TelaRequisitosAtrasadosActivity.this, TelaSobreActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }
}