/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.vrs.reqdroid.R;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.fragments.RequisitosAtrasadosFragment;

import java.util.ArrayList;

/**
 * Classe responsavel por realizar operacoes relacionadas as hipoteses.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class HipotesesUtils extends Activity {

    /**
     * Adiciona a hipotese no banco de dados.
     *
     * @param context O contexto que sera utilizado
     * @param descricao A descricao da hipotese
     * @param data A data da hipotese
     * @param idProjeto O id do projeto
     */
    public static void salvaHipoteseBD(Context context, String descricao, String data, int idProjeto)
    {
        String autor = "";
        BDGerenciador.getInstance(context).insertHipotese(descricao, data, 1, 0, autor, idProjeto);
    }

    /**
     * Remove a hipotese do banco de dados do aplicativo.
     *
     * @param context O contexto que sera utilizado
     * @param descricao A descricao da hipotese
     */
    public static void removeHipoteseBD(Context context, String descricao, int idProjeto)
    {
        int idHipotese = BDGerenciador.getInstance(context).selectHipotesePorDescricao(descricao, idProjeto);
        BDGerenciador.getInstance(context).deleteHipotese(idHipotese);
    }

    /**
     * Insere a hipotese da tabela de hipoteses do banco de dados do aplicativo
     * na tabela de requisitos.
     *
     * @param context O contexto que sera utilizado
     * @param descricao A descricao da hipotese
     * @param data A data da hipotese
     * @param autor O autor da hipotese
     * @param idProjeto O id do projeto
     */
    public static void validaHipoteseBD(Context context, String descricao, String data, int versao, int subversao, String autor, int idProjeto)
    {
        BDGerenciador.getInstance(context).insertRequisito(descricao, data, 3, versao, subversao, autor, idProjeto);
        int idRequisito = BDGerenciador.getInstance(context).getIdUltimoRequisito();
        int numeroRequisitos = BDGerenciador.getInstance(context).getNumeroUltimoRequisito(idProjeto);
        BDGerenciador.getInstance(context).insertProjetoRequisito(idProjeto, idRequisito, numeroRequisitos + 1);
    }

    /**
     * Atualiza a hipotese no banco de dados do aplicativo.
     *
     * @param context O contexto que sera utilizado
     * @param descricaoAtual A descricao atual da hipotese
     * @param descricaoNova A descricao nova da hipotese
     * @param versaoNova A versao nova da hipotese
     * @param idProjeto O id do projeto
     */
    public static void editaHipoteseBD(Context context, String descricaoAtual, String descricaoNova, int versaoNova, int subversaoNova, int idProjeto)
    {
        int idHipotese = BDGerenciador.getInstance(context).selectHipotesePorDescricao(descricaoAtual, idProjeto);
        BDGerenciador.getInstance(context).updateHipotese(idHipotese, descricaoNova, versaoNova, subversaoNova);
    }

    /**
     * Carrega a lista de hipoteses do banco de dados.
     *
     * @param context O contexto que sera utilizado
     * @param idProjeto O id do projeto
     * @return A lista de hipoteses
     */
    public static ArrayList<String> carregaHipotesesBD(Context context, int idProjeto)
    {
        return (ArrayList<String>) BDGerenciador.getInstance(context).selectHipotese(idProjeto);
    }

    /**
     * Verifica se o campo de inserir hipotese foi preenchido.
     *
     * @return True se o campo foi preenchido
     */
    public static boolean hipotesePreenchida(String textoHipotese)
    {
        return !(("".equals(textoHipotese) || textoHipotese == null));
    }

    /**
     * Remove uma hipotese da lista.
     *
     * @param context O contexto que sera utilizado
     * @param hipoteses A lista de hipoteses
     * @param descricao A descricao da hipotese
     * @param posicao A posicao da hipotese na lista
     * @param idProjeto O id do projeto
     * @param lvHipotesesAdapter O adapter da lista de hipoteses
     */
    public static void removeHipotese(final Context context, final ArrayList<String> hipoteses, final String descricao,
                                       final int posicao, final int idProjeto, final ListViewHipotesesAdapter lvHipotesesAdapter)
    {
        AlertDialog.Builder alertBoxConfirmaExclusao = new AlertDialog.Builder(context);

        alertBoxConfirmaExclusao.setTitle(R.string.alert_remover_hipotese_titulo);
        alertBoxConfirmaExclusao.setMessage(R.string.alert_remover_hipotese_msg);

        alertBoxConfirmaExclusao.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                removeHipoteseBD(context, descricao, idProjeto);
                hipoteses.remove(posicao);
                lvHipotesesAdapter.notifyDataSetChanged();
            }
        });
        alertBoxConfirmaExclusao.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertBoxConfirmaExclusao.show();
    }

    /**
     * Move uma hipotese para a lista de requisitos.
     *
     * @param context O contexto que sera utilizado
     * @param hipoteses A lista de hipoteses
     * @param descricao A descricao da hipotese
     * @param posicao A posicao da hipotese na lista
     * @param idProjeto O id do projeto
     * @param lvHipotesesAdapter O adapter da lista de hipoteses
     */
    public static void validaHipotese(final Context context, final ArrayList<String> hipoteses, final String descricao,
                                     final int posicao, final int idProjeto, final ListViewHipotesesAdapter lvHipotesesAdapter)
    {
        final String data = BDGerenciador.getInstance(context).selectDataHipotese(descricao, idProjeto);
        final int versaoRequisito = BDGerenciador.getInstance(context).selectVersaoHipotese(descricao, idProjeto);
        final int subversaoRequisito = BDGerenciador.getInstance(context).selectSubversaoHipotese(descricao, idProjeto);
        final String autor = BDGerenciador.getInstance(context).selectAutorHipotese(descricao,idProjeto);

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);

        alertbox.setTitle(R.string.alert_validar_hipotese_titulo);
        alertbox.setMessage(R.string.alert_validar_hipotese_msg);
        alertbox.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                removeHipoteseBD(context, descricao, idProjeto);
                validaHipoteseBD(context, descricao, data,
                        versaoRequisito, subversaoRequisito, autor, idProjeto);
                hipoteses.remove(posicao);
                lvHipotesesAdapter.notifyDataSetChanged();
                //Toast.makeText(context, R.string.tela_hipoteses_movida, Toast.LENGTH_SHORT).show();
            }
        });
        alertbox.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertbox.show();
    }

    /**
     * Edita uma hipotese da lista.
     *
     * @param context O contexto que sera utilizado
     * @param descricaoAtual A descricao atual da hipotese
     * @param posicao A posicao da hipotese na lista
     * @param idProjeto O id do projeto
     */
    public static void editaHipotese(Context context, String descricaoAtual,String descricaoNova,
                                     int versaoValor, int subversaoValor,  int posicao, int idProjeto)
    {
        editaHipoteseBD(context, descricaoAtual, descricaoNova, versaoValor, subversaoValor, idProjeto);
        RequisitosAtrasadosFragment.atualizaLista(posicao, descricaoNova);
    }
}