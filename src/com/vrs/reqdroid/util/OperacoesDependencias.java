/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */


package com.vrs.reqdroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.vrs.reqdroid.R;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.modelo.Dependencia;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsavel por realizar operacoes relacionadas as dependencias.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class OperacoesDependencias extends Activity {

    /**
     * Carrega a lista de dependencias do banco de dados.
     *
     * @param context O contexto que sera utilizado
     * @param idProjeto O id do projeto
     * @return A lista de dependencias
     */
    public static ArrayList<Dependencia> carregaDependenciasBD(Context context, int idProjeto)
    {
        ArrayList<Dependencia> ids = (ArrayList<Dependencia>) BDGerenciador.getInstance(context).selectDependencias(idProjeto);
        ArrayList<Dependencia> numeros = new ArrayList<Dependencia>();

        //A lista de ID's retorna somente os ids dos requisitos. O importante no carregamento das dependencias e o numero
        //do requisito para a exibicao na tela.
        //Meio "inapropriado", eu sei. :-)
        for (int i = 0; i < ids.size(); i++)
        {
            int requisito1 = BDGerenciador.getInstance(context).selectNumeroRequisito(idProjeto, ids.get(i).getPrimeiroRequisito());
            int requisito2 = BDGerenciador.getInstance(context).selectNumeroRequisito(idProjeto, ids.get(i).getSegundoRequisito());
            Dependencia dependencia = new Dependencia(requisito1, requisito2);
            numeros.add(dependencia);
        }
        return numeros;
    }

    /**
     * Salva a dependencia no banco de dados do aplicativo.
     *
     * @param idProjeto O id do projeto a ser inserida a dependencia
     */
    public static void  salvaDependenciaBD(Context context, Dependencia dependencia, int idProjeto)
    {
        int idRequisito1 = BDGerenciador.getInstance(context).selectRequisitoPorNumero(idProjeto, dependencia.getPrimeiroRequisito());
        int idRequisito2 = BDGerenciador.getInstance(context).selectRequisitoPorNumero(idProjeto, dependencia.getSegundoRequisito());
        BDGerenciador.getInstance(context).insertDependencia(idProjeto, idRequisito1, idRequisito2);
    }

    /**
     * Remove a dependencia do banco de dados do aplicativo.
     *
     * @param context O contexto que sera utiliazdo
     * @param idProjeto O id do projeto
     * @param dependencia A dependencia a ser removida
     */
    public static void removeDependenciaBD(Context context, int idProjeto, Dependencia dependencia)
    {
        int idRequisito1 = BDGerenciador.getInstance(context).selectRequisitoPorNumero(idProjeto, dependencia.getPrimeiroRequisito());
        int idRequisito2 = BDGerenciador.getInstance(context).selectRequisitoPorNumero(idProjeto, dependencia.getSegundoRequisito());
        BDGerenciador.getInstance(context).deleteDependencia(idProjeto, idRequisito1, idRequisito2);
    }

    /**
     * Remove uma dependencia da lista.
     *
     * @param context O contexto que sera utilizado
     * @param dependencias A lista de dependencias
     * @param dependencia A dependencia a ser removida
     * @param posicao A posicao do requisito na lista
     * @param idProjeto O id do projeto
     * @param lvDependenciasAdapter O adapter da lista de dependencias
     */
    public static void removeDependencia(final Context context, final ArrayList<Dependencia> dependencias, final Dependencia dependencia,
                                         final int posicao, final int idProjeto, final ListViewDependenciasAdapter lvDependenciasAdapter)
    {
        AlertDialog.Builder alertBoxConfirmaExclusao = new AlertDialog.Builder(context);

        alertBoxConfirmaExclusao.setTitle(R.string.alert_remover_dependencia_titulo);
        alertBoxConfirmaExclusao.setMessage(R.string.alert_remover_dependencia_msg);

        alertBoxConfirmaExclusao.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                removeDependenciaBD(context, idProjeto, dependencia);
                dependencias.remove(posicao);
                lvDependenciasAdapter.notifyDataSetChanged();
            }
        });
        alertBoxConfirmaExclusao.setNegativeButton(R.string.alert_cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alertBoxConfirmaExclusao.show();
    }

    /**
     * Preenche os dois spinners (lista de selecao) da tela com os numeros do requisitos.
     */
    public static void preencheSpinners(Context context, Spinner primeiro, Spinner segundo, int idProjeto)
    {
        List<Integer> numerosRequisitos = BDGerenciador.getInstance(context).selectAllNumerosRequisitos(idProjeto);

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(context, R.layout.itemspinnerdependencia, numerosRequisitos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        primeiro.setAdapter(arrayAdapter);
        segundo.setAdapter(arrayAdapter);
    }

    /**
     * Verifica se os spinners possuem itens.
     *
     * @param s1 O primeiro spinner da tela de dependencias
     * @param s2 O segundo spinner da tela de dependencias
     * @return true se ambos os spinners possuem itens
     */
    public static boolean possuiItens(Spinner s1, Spinner s2)
    {
        return s1.getChildCount() > 0 && s2.getChildCount() > 0;
    }
}