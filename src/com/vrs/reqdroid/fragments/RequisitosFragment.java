/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.fragments;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.activities.TelaPrincipalActivity;
import com.vrs.reqdroid.activities.TelaRequisitoDetalhadoActivity;
import com.vrs.reqdroid.util.AlertsUtil;
import com.vrs.reqdroid.util.ListViewRequisitosAdapter;
import com.vrs.reqdroid.util.ProjetoUtils;
import com.vrs.reqdroid.util.RequisitosUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementa a tela (fragment) referente a funcionalidade de gerenciar lista de requisitos.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class RequisitosFragment extends Fragment {

    private static ArrayList<String> requisitos;
    private static ListViewRequisitosAdapter lvRequisitosAdapter;
    private ListView lvRequisitos;
    private static int idProjeto;
    private static EditText editTextRequisito;
    private static String requisitoSelecionado; //Util pra quando o requisito e selecionado para exibir detalhes.
    private static int posicaoRequisitoSelecionado; //Util pra quando o requisito e selecionado para exibir detalhes.

    private View rootView;

    public RequisitosFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.requisitos, container, false);
        getActivity().setTitle(getResources().getStringArray(R.array.titulos_funcionalidades_array)[0]);
        init();
        carregaRequisitos();
        adicionaRequisito();
        exibeDetalhesRequisito();
        return rootView;
    }

    /**
     * Carrega os requisitos existentes no banco de dados e adiciona-os a lista.
     */
    private void carregaRequisitos()
    {
        requisitos = RequisitosUtils.carregaRequisitosBD(getActivity(), idProjeto);
        lvRequisitos.setCacheColorHint(Color.TRANSPARENT);
        lvRequisitos.setSelectionAfterHeaderView();
        lvRequisitosAdapter = new ListViewRequisitosAdapter(getActivity(), requisitos, botaoOpcoesListener);
        lvRequisitos.setAdapter(lvRequisitosAdapter);
    }

    /**
     * Adiciona um novo requisito na lista.
     */
    private void adicionaRequisito()
    {
        final Button botaoAdicionarRequisito = (Button) rootView.findViewById(R.id.botaoAdicionarRequisito);

        botaoAdicionarRequisito.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (RequisitosUtils.requisitoPreenchido(editTextRequisito.getText().toString()))
                {
                    salvaRequisito(editTextRequisito.getText().toString());
                    requisitos.add(editTextRequisito.getText().toString());
                    editTextRequisito.setText("");
                    lvRequisitosAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Abre a tela de detalhes do requisito selecionado.
     */
    private void exibeDetalhesRequisito()
    {
        final Intent i = new Intent(getActivity(), TelaRequisitoDetalhadoActivity.class);

        lvRequisitos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                LinearLayout caixaRequisito = (LinearLayout)view; //LinearLayout que contem o texto e botao
                final TextView txtRequisito = (TextView)caixaRequisito.getChildAt(0);
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
            posicaoRequisitoSelecionado = lvRequisitos.getPositionForView(caixaRequisito);
            requisitoSelecionado = txtRequisito.getText().toString();

            //Se a versao do Android e 3.0 ou superior, exibe o PopUpMenu, senao, o ContextMenu.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                exibePopupMenuOpcoes(view);
            }
            else{
                ImageButton bOpcoes = (ImageButton)caixaRequisito.getChildAt(1); //O botao de opcoes.
                registerForContextMenu(bOpcoes);
                getActivity().openContextMenu(bOpcoes);
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
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_opcoes_requisito, menu);
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
            case R.id.opcaoEditarRequisito:
                menuEdita();
                return true;
            case R.id.opcaoMoverRequisito:
                menuMove();
                return true;
            case R.id.opcaoDeletarRequisito:
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
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_opcoes_requisito, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opcaoEditarRequisito:
                        menuEdita();
                        break;
                    case R.id.opcaoMoverRequisito:
                        menuMove();
                        break;
                    case R.id.opcaoDeletarRequisito:
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
        int versaoRequisito = RequisitosUtils.getVersaoRequisito(getActivity(), requisitoSelecionado, idProjeto);
        int subversaoRequisito = RequisitosUtils.getSubversaoRequisito(getActivity(), requisitoSelecionado, idProjeto);
        AlertsUtil.exibeAlertaEditar(getActivity(), requisitoSelecionado, versaoRequisito, subversaoRequisito, posicaoRequisitoSelecionado, 1);
    }

    /**
     * Realiza a operacao de mover (atrasar) o requisito.
     */
    private void menuMove()
    {
        RequisitosUtils.moveRequisito(getActivity(), requisitos, requisitoSelecionado,
                posicaoRequisitoSelecionado, idProjeto, lvRequisitosAdapter);
    }

    /**
     * Realiza a operacao de deletar o requisito.
     */
    private void menuDeleta()
    {
        RequisitosUtils.removeRequisito(getActivity(), requisitos, requisitoSelecionado,
                posicaoRequisitoSelecionado, idProjeto, lvRequisitosAdapter);
    }

    /**
     * Salva o requisito no banco de dados do aplicativo.
     */
    private void salvaRequisito(String descricaoRequisito)
    {
        RequisitosUtils.salvaRequisitoBD(getActivity(), descricaoRequisito, getDataATual(), idProjeto);
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
     * Retorna a posicaoItem do requisito selecionado na lista.
     *
     * @return a posicaoItem do requisito
     */
    public static int getPosicaoRequisitoSelecionado()
    {
        return posicaoRequisitoSelecionado;
    }

    /**
     * Atualiza a lista de requisitos quando um requisito e editado na tela de detalhes.
     *
     * @param posicao a posicaoItem do requisito na lista
     * @param descricao a descricao do requisito
     */
    public static void atualizaLista(int posicao, String descricao)
    {
        requisitos.set(posicao, descricao);
        lvRequisitosAdapter.notifyDataSetChanged();
    }

    /**
     * Atualiza a lista de requisitos quando um requisito e removido atraves da tela de detalhes.
     *
     * @param posicao A posicaoItem do requisito na lista
     */
    public static void atualizaListaRemovido(int posicao)
    {
        requisitos.remove(posicao);
        lvRequisitosAdapter.notifyDataSetChanged();
    }

    /**
     * Inicia as variaveis da classe.
     */
    private void init()
    {
        idProjeto = ProjetoUtils.getIdProjeto();
        lvRequisitos = (ListView) rootView.findViewById(R.id.lvRequisitos);
        requisitos = new ArrayList<String>();
        editTextRequisito = (EditText) rootView.findViewById(R.id.etRequisito);
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
