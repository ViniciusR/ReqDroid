/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.dao.BDGerenciador;

import java.util.ArrayList;

/**
 * Classe responsavel por realizar operacoes relacionadas aos requisitos.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class RequisitosUtils extends Activity {

    /**
     * Adiciona o requisito no banco de dados.
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
                      insertRequisito(descricao, data, 3, 1, autor, idProjeto);
        int idRequisito = BDGerenciador.getInstance(context).getIdUltimoRequisito();
        int numeroRequisitos = BDGerenciador.getInstance(context).getNumeroUltimoRequisito(idProjeto);
        BDGerenciador.getInstance(context).insertProjetoRequisito(idProjeto, idRequisito, numeroRequisitos + 1);

    }

    /**
     * Remove o requisito do banco de dados do aplicativo.
     *
     * @param context O contexto que sera utilizado
     * @param descricao A descricao do requisito
     */
    public static void removeRequisitoBD(Context context, String descricao, int idProjeto)
    {
        int idRequisito = BDGerenciador.getInstance(context).selectRequisitoPorDescricao(descricao, idProjeto);
        BDGerenciador.getInstance(context).deleteRequisito(idRequisito);
    }

    /**
     * Insere o requisito da tabela de requisitos do banco de dados do aplicativo
     * na tabela de requisitos atrasados.
     *
     * @param context O contexto que sera utilizado
     * @param descricao A descricao do requisito
     * @param data A data do requisito
     * @param prioridade A prioridade do requisito
     * @param autor O autor do requisito
     * @param idProjeto O id do projeto
     */
    public static void moveRequisitoBD(Context context, String descricao, String data, int prioridade, int versao, String autor, int idProjeto)
    {
        BDGerenciador.getInstance(context).insertRequisitoAtrasado(descricao, data, prioridade, versao, autor, idProjeto);
        int idRequisito = BDGerenciador.getInstance(context).getIdUltimoRequisitoAtrasado();
        int numeroRequisitos = BDGerenciador.getInstance(context).getNumeroUltimoRequisitoAtrasado(idProjeto);
        BDGerenciador.getInstance(context).insertProjetoRequisitoAtrasado(idProjeto, idRequisito, numeroRequisitos + 1);
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
    public static void editaRequisitoBD(Context context, String descricaoAtual, String descricaoNova, int versaoNova, int idProjeto)
    {
        int idRequisito;
        idRequisito = BDGerenciador.getInstance(context).selectRequisitoPorDescricao(descricaoAtual, idProjeto);
        BDGerenciador.getInstance(context).updateRequisito(idRequisito, descricaoNova, versaoNova);
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
        return (ArrayList<String>) BDGerenciador.getInstance(context).selectRequisito(idProjeto);
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
     * @param lvRequisitosAdapter O adapter da lista de requisitos
     */
    public static void removeRequisito(final Context context, final ArrayList<String> requisitos, final String descricao,
                                       final int posicao, final int idProjeto, final ListViewRequisitosAdapter lvRequisitosAdapter)
    {
        AlertDialog.Builder alertBoxConfirmaExclusao = new AlertDialog.Builder(context);

        alertBoxConfirmaExclusao.setTitle(R.string.alert_remover_requisito_titulo);
        alertBoxConfirmaExclusao.setMessage(R.string.alert_remover_requisito_msg);

        alertBoxConfirmaExclusao.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                removeRequisitoBD(context, descricao, idProjeto);
                requisitos.remove(posicao);
                lvRequisitosAdapter.notifyDataSetChanged();
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
     * @param lvRequisitosAdapter O adapter da lista de requisitos
     */
    public static void moveRequisito(final Context context, final ArrayList<String> requisitos, final String descricao,
                               final int posicao, final int idProjeto, final ListViewRequisitosAdapter lvRequisitosAdapter)
    {
        final String data = BDGerenciador.getInstance(context).selectDataRequisito(descricao, idProjeto);
        final int prioridade = BDGerenciador.getInstance(context).selectPrioridadeRequisito(descricao, idProjeto);
        final int versaoRequisito = BDGerenciador.getInstance(context).selectVersaoRequisito(descricao, idProjeto);
        final String autor = BDGerenciador.getInstance(context).selectAutorRequisito(descricao,idProjeto);

        final AlertDialog.Builder alertbox = new AlertDialog.Builder(context);

        alertbox.setTitle(R.string.alert_atrasar_requisito_titulo);
        alertbox.setMessage(R.string.alert_atrasar_requisito_msg);
        alertbox.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                removeRequisitoBD(context, descricao, idProjeto);
                moveRequisitoBD(context, descricao, data, prioridade,
                        versaoRequisito, autor, idProjeto);
                requisitos.remove(posicao);
                lvRequisitosAdapter.notifyDataSetChanged();
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
     * @param requisitos A lista de requisitos
     * @param descricaoAtual A descricao atual do requisito
     * @param posicao A posicao do requisito na lista
     * @param idProjeto O id do projeto
     * @param lvRequisitosAdapter O adapter da lista de requisitos
     */
    public static void editaRequisito(final Context context, final ArrayList<String> requisitos, final String descricaoAtual,
                                final int posicao, final int idProjeto, final ListViewRequisitosAdapter lvRequisitosAdapter)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(R.string.alert_editar_requisito_titulo);

        final int versaoRequisito = BDGerenciador.getInstance(context).selectVersaoRequisito(descricaoAtual, idProjeto);
        final EditText entrada = new EditText(context);
        entrada.setText(descricaoAtual);
        alert.setView(entrada);

        alert.setPositiveButton(R.string.alert_salvar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!entrada.getText().toString().equals(""))
                {
                    requisitos.set(posicao, entrada.getText().toString());
                    lvRequisitosAdapter.notifyDataSetChanged();
                    editaRequisitoBD(context, descricaoAtual, entrada.getText().toString(),
                                     versaoRequisito + 1, idProjeto);
                }
            }
        });

        alert.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}
