/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.fragments.RequisitosAtrasadosFragment;

import java.util.ArrayList;

/**
 * Classe responsavel por realizar operacoes relacionadas aos requisitos.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class RequisitosAtrasadosUtils extends Activity {

    /**
     * Adiciona o requisito atrasado no banco de dados.
     *
     * @param context O contexto que sera utilizado
     * @param descricao A descricao do requisito
     * @param data A data do requisito
     * @param idProjeto O id do projeto
     */
    public static void salvaRequisitoBD(Context context, String descricao, String data, int idProjeto)
    {
        String autor = "";
        BDGerenciador.getInstance(context).
                insertRequisitoAtrasado(descricao, data, 3, 1, 0, autor, idProjeto);
        int idRequisito = BDGerenciador.getInstance(context).getIdUltimoRequisitoAtrasado();
        int numeroRequisitos = BDGerenciador.getInstance(context).getNumeroUltimoRequisitoAtrasado(idProjeto);
        BDGerenciador.getInstance(context).insertProjetoRequisitoAtrasado(idProjeto, idRequisito, numeroRequisitos + 1);

    }

    /**
     * Remove o requisito atrasado do banco de dados do aplicativo.
     *
     * @param context O contexto que sera utilizado
     * @param descricao A descricao do requisito
     */
    public static void removeRequisitoBD(Context context, String descricao, int idProjeto)
    {
        int idRequisito = BDGerenciador.getInstance(context).selectRequisitoAtrasadoPorDescricao(descricao, idProjeto);
        BDGerenciador.getInstance(context).deleteRequisitoAtrasado(idRequisito);
    }

    /**
     * Deleta o requisito atrasado da tabela de requisitos do banco de dados do aplicativo
     * e o insere na tabela de requisitos atrasados.
     *
     * @param context O contexto que sera utilizado
     * @param descricao A descricao do requisito
     * @param data A data do requisito
     * @param prioridade A prioridade do requisito
     * @param autor O autor do requisito
     * @param idProjeto O id do projeto
     */
    public static void moveRequisitoBD(Context context, String descricao, String data, int prioridade, int versao, int subversao, String autor, int idProjeto)
    {
        BDGerenciador.getInstance(context).insertRequisito(descricao, data, prioridade, versao, subversao, autor, idProjeto);
        int idRequisito = BDGerenciador.getInstance(context).getIdUltimoRequisito();
        int numeroRequisitos = BDGerenciador.getInstance(context).getNumeroUltimoRequisito(idProjeto);
        BDGerenciador.getInstance(context).insertProjetoRequisito(idProjeto, idRequisito, numeroRequisitos + 1);
    }

    /**
     * Atualiza o requisito no banco de dados do aplicativo.
     *
     * @param context O contexto que sera utilizado
     * @param descricaoAtual A descricao atual do requisito
     * @param descricaoNova A descricao nova do requisito
     * @param versaoNova A versao nova do requisito
     * @param idProjeto O id do projeto
     */
    public static void editaRequisitoBD(Context context, String descricaoAtual, String descricaoNova, int versaoNova, int subversaoNova, int idProjeto)
    {
        int idRequisito;
        idRequisito = BDGerenciador.getInstance(context).selectRequisitoAtrasadoPorDescricao(descricaoAtual, idProjeto);
        BDGerenciador.getInstance(context).updateRequisitoAtrasado(idRequisito, descricaoNova, versaoNova, subversaoNova);
    }

    /**
     * Carrega a lista de requisitos do banco de dados.
     *
     * @param context O contexto que sera utilizado
     * @param idProjeto O id do projeto
     * @return A lista de requisitos
     */
    public static ArrayList<String> carregaRequisitosBD(Context context, int idProjeto)
    {
        return (ArrayList<String>) BDGerenciador.getInstance(context).selectRequisitoAtrasado(idProjeto);
    }

    /**
     * Verifica se o campo de inserir requisito foi preenchido.
     *
     * @return True se o campo foi preenchido
     */
    public static boolean requisitoPreenchido(String textoRequisito)
    {
        return !(("".equals(textoRequisito) || textoRequisito == null));
    }

    /**
     * Remove um requisito da lista.
     *
     * @param context O contexto que sera utilizado
     * @param requisitos A lista de requisitos
     * @param descricao A descricao do requisito
     * @param posicao A posicao do requisito na lista
     * @param idProjeto O id do projeto
     * @param lvRequisitosAtraasadosAdapter O adapter da lista de requisitos
     */
    public static void removeRequisito(final Context context, final ArrayList<String> requisitos, final String descricao,
                                       final int posicao, final int idProjeto, final ListViewRequisitosAtrasadosAdapter lvRequisitosAtraasadosAdapter)
    {
        AlertDialog.Builder alertBoxConfirmaExclusao = new AlertDialog.Builder(context);

        alertBoxConfirmaExclusao.setTitle(R.string.alert_remover_requisito_titulo);
        alertBoxConfirmaExclusao.setMessage(R.string.alert_remover_requisito_msg);

        alertBoxConfirmaExclusao.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                removeRequisitoBD(context, descricao, idProjeto);
                requisitos.remove(posicao);
                lvRequisitosAtraasadosAdapter.notifyDataSetChanged();
            }
        });
        alertBoxConfirmaExclusao.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertBoxConfirmaExclusao.show();
    }

    /**
     * Move um requisito da lista de requisitos atrasados.
     *
     * @param context O contexto que sera utilizado
     * @param requisitos A lista de requisitos
     * @param descricao A descricao do requisito
     * @param posicao A posicao do requisito na lista
     * @param idProjeto O id do projeto
     * @param lvRequisitosAtrasadosAdapter O adapter da lista de requisitos atrasados
     */
    public static void moveRequisito(final Context context, final ArrayList<String> requisitos, final String descricao,
                                     final int posicao, final int idProjeto, final ListViewRequisitosAtrasadosAdapter lvRequisitosAtrasadosAdapter)
    {
        final String data = BDGerenciador.getInstance(context).selectDataRequisitoAtrasado(descricao, idProjeto);
        final int prioridade = BDGerenciador.getInstance(context).selectPrioridadeRequisitoAtrasado(descricao, idProjeto);
        final int versaoRequisito = BDGerenciador.getInstance(context).selectVersaoRequisitoAtrasado(descricao, idProjeto);
        final int subversaoRequisito = BDGerenciador.getInstance(context).selectSubversaoRequisitoAtrasado(descricao, idProjeto);
        final String autor = BDGerenciador.getInstance(context).selectAutorRequisitoAtrasado(descricao,idProjeto);

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);

        alertbox.setTitle(R.string.alert_atrasar_requisito_titulo);
        alertbox.setMessage(R.string.alert_atrasar_requisito_msg);
        alertbox.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                removeRequisitoBD(context, descricao, idProjeto);
                moveRequisitoBD(context, descricao, data, prioridade,
                        versaoRequisito, subversaoRequisito, autor, idProjeto);
                requisitos.remove(posicao);
                lvRequisitosAtrasadosAdapter.notifyDataSetChanged();
                Toast.makeText(context, R.string.tela_requisitos_msg_movido, Toast.LENGTH_SHORT).show();
            }
        });
        alertbox.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertbox.show();
    }

    /**
     * Edita um requisito da lista.
     *
     * @param context O contexto que sera utilizado
     * @param descricaoAtual A descricao atual do requisito
     * @param posicao A posicao do requisito na lista
     * @param idProjeto O id do projeto
     */
    public static void editaRequisito(Context context, String descricaoAtual,String descricaoNova,
                                      int versaoValor, int subversaoValor, int posicao, int idProjeto)
    {
        editaRequisitoBD(context, descricaoAtual, descricaoNova, versaoValor, subversaoValor, idProjeto);
        RequisitosAtrasadosFragment.atualizaLista(posicao, descricaoNova);
    }
}
