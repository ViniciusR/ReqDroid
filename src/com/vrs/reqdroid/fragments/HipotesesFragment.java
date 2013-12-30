/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.vrs.reqdroid.activities.TelaHipoteseDetalhadaActivity;
import com.vrs.reqdroid.activities.TelaPrincipalActivity;
import com.vrs.reqdroid.util.AlertsUtil;
import com.vrs.reqdroid.util.HipotesesUtils;
import com.vrs.reqdroid.util.ListViewHipotesesAdapter;
import com.vrs.reqdroid.util.ProjetoUtils;
import com.vrs.reqdroid.util.RequisitosUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Implementa a tela de listas de hipoteses.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class HipotesesFragment extends Fragment {

    private static ArrayList<String> hipoteses;
    private static ListViewHipotesesAdapter lvHipotesesAdapter;
    private ListView lvHipoteses;
    private static int idProjeto;
    private static EditText editTextHipotese;
    private static String hipoteseSelecionada; //Util pra quando a hipotese e selecionada para exibir detalhes.
    private static int posicaoHipoteseSelecionada; //Util pra quando a hipotese e selecionada para exibir detalhes.
    private View rootView;

    public HipotesesFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.hipoteses, container, false);
        getActivity().setTitle(getResources().getStringArray(R.array.titulos_funcionalidades_array)[2]);
        init();
        carregaHipoteses();
        adicionaHipotese();
        exibeDetalhesHipotese();
        return rootView;
    }

    /**
     * Carrega os hipoteses existentes no banco de dados e adiciona-os a lista.
     */
    private void carregaHipoteses()
    {
        hipoteses = HipotesesUtils.carregaHipotesesBD(getActivity(), idProjeto);
        lvHipoteses.setCacheColorHint(Color.TRANSPARENT);
        lvHipoteses.setSelectionAfterHeaderView();
        lvHipotesesAdapter = new ListViewHipotesesAdapter(getActivity(), hipoteses, botaoOpcoesListener);
        lvHipoteses.setAdapter(lvHipotesesAdapter);
    }

    /**
     * Adiciona uma nova hipotese na lista.
     */
    private void adicionaHipotese()
    {
        final Button botaoAdicionarHipotese = (Button) rootView.findViewById(R.id.botaoAdicionarHipotese);

        botaoAdicionarHipotese.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (HipotesesUtils.hipotesePreenchida(editTextHipotese.getText().toString()))
                {
                    salvaHipotese(editTextHipotese.getText().toString());
                    hipoteses.add(editTextHipotese.getText().toString());
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
        final Intent i = new Intent(getActivity(), TelaHipoteseDetalhadaActivity.class);

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
            posicaoHipoteseSelecionada = lvHipoteses.getPositionForView(caixaHipotese);
            hipoteseSelecionada = txtHipotese.getText().toString();

            //Se a versao do Android e 3.0 ou superior, exibe o PopUpMenu, senao, o ContextMenu.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                exibePopupMenuOpcoes(view);
            }
            else{
                ImageButton bOpcoes = (ImageButton)caixaHipotese.getChildAt(1); //O botao de opcoes.
                registerForContextMenu(bOpcoes);
                getActivity().openContextMenu(bOpcoes);
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
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_opcoes_hipotese, menu);
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
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_opcoes_hipotese, popupMenu.getMenu());

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
        int versaoRequisito = RequisitosUtils.getVersaoRequisito(getActivity(), hipoteseSelecionada, idProjeto);
        int subversaoRequisito = RequisitosUtils.getSubversaoRequisito(getActivity(), hipoteseSelecionada, idProjeto);

        AlertsUtil.exibeAlertaEditar(getActivity(), hipoteseSelecionada, versaoRequisito, subversaoRequisito, posicaoHipoteseSelecionada, 3);
    }

    /**
     * Realiza a operacao de validar a hipotese.
     */
    private void menuValida()
    {
        HipotesesUtils.validaHipotese(getActivity(), hipoteses, hipoteseSelecionada,
                posicaoHipoteseSelecionada, idProjeto, lvHipotesesAdapter);
    }

    /**
     * Realiza a operacao de deletar a hipotese.
     */
    private void menuDeleta()
    {
        HipotesesUtils.removeHipotese(getActivity(), hipoteses, hipoteseSelecionada,
                posicaoHipoteseSelecionada, idProjeto, lvHipotesesAdapter);
    }

    /**
     * Salva o hipotese no banco de dados do aplicativo.
     */
    private void salvaHipotese(String descricaoHipotese)
    {
        HipotesesUtils.salvaHipoteseBD(getActivity(), descricaoHipotese, getDataATual(), idProjeto);
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
     * Inicia as variaveis da classe.
     */
    private void init()
    {
        idProjeto = ProjetoUtils.getIdProjeto();
        lvHipoteses = (ListView) rootView.findViewById(R.id.lvHipoteses);
        hipoteses = new ArrayList<String>();
        editTextHipotese = (EditText) rootView.findViewById(R.id.etHipotese);

        //O Android possui um bug onde as tabs "roubam" o foco das entradas de texto, o codigo abaixo corrige isso.
        //Mas somente para API inferior a 12. Para maiores ja foi corrigido na classe HipotesesEDependenciasFragment
        if (Build.VERSION.SDK_INT < 12)
        {
            View.OnTouchListener focusHandler = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View arg0, MotionEvent event) {
                    arg0.requestFocusFromTouch();
                    return false;
                }
            };
            editTextHipotese.setOnTouchListener(focusHandler);
        }
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