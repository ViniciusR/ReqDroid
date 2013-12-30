/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import com.vrs.reqdroid.R;

/**
 * Implementa uma classe que exibe mensagens de alerta padrao no aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class AlertsUtil {
    /**
     * Exibe um alerta caso exista campos nao preenchidos.
     * 
     * @param context O contexto onde o metodo e utilizado.
     */
    public static void exibeAlertaCamposNaoPreenchidos(Context context)
    {  
      AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
      alertbox.setTitle(R.string.alert_titulo);
      alertbox.setMessage(R.string.alert_campos_preenchidos_msg);
      alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            alertbox.show();    
    }     
    
    /**
     * Exibe um alerta caso exista projeto existente.
     * 
     * @param context O contexto onde o metodo e utilizado.
     */
    public static void exibeAlertaProjetoExistente(Context context)
    {  
      AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
      alertbox.setTitle(R.string.alert_titulo);
      alertbox.setMessage(R.string.alert_mesmo_titulo_msg);
      alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            alertbox.show();    
    }

    /**
     * Exibe a janela para editar o requisito (somente na lista).
     */
    public static void exibeAlertaEditar(final Context context, final String descricaoAtual, final int versao,
                                         final int subversao, final int posicao, final int tipoItem)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);


        if (tipoItem == 3)
        {
            alert.setTitle(R.string.alert_editar_hipotese);
        }
        else
        {
            alert.setTitle(R.string.alert_editar_requisito_titulo);
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layoutEditar = (LinearLayout)inflater.inflate(R.layout.alert_editar, null);

        final EditText entrada = (EditText) layoutEditar.findViewById(R.id.descricao_item);
        final NumberPicker npVersao = (NumberPicker) layoutEditar.findViewById(R.id.versao_item);
        final NumberPicker npSubversao = (NumberPicker) layoutEditar.findViewById(R.id.subversao_item);
        npVersao.setMinValue(1);
        npVersao.setMaxValue(10);
        npSubversao.setMinValue(0);
        npSubversao.setMaxValue(9);

        entrada.setText(descricaoAtual);
        npVersao.setValue(versao);
        npSubversao.setValue(subversao);

        alert.setView(layoutEditar);
        alert.create();

        alert.setPositiveButton(R.string.alert_salvar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!entrada.getText().toString().equals(""))
                {
                    switch (tipoItem)
                    {
                        case 1: //Requisito
                            RequisitosUtils.editaRequisito(context, descricaoAtual, entrada.getText().toString(),
                                                            npVersao.getValue(), npSubversao.getValue(),
                                                            posicao, ProjetoUtils.getIdProjeto());
                            break;

                        case 2: //Requisito atrasado
                            RequisitosAtrasadosUtils.editaRequisito(context, descricaoAtual, entrada.getText().toString(),
                                                                    posicao, ProjetoUtils.getIdProjeto(),
                                                                    npVersao.getValue(), npSubversao.getValue());
                            break;

                        case 3: //Hipotese
                            HipotesesUtils.editaHipotese(context, descricaoAtual, entrada.getText().toString(),
                                                         posicao, ProjetoUtils.getIdProjeto(),
                                                         npVersao.getValue(), npSubversao.getValue());
                            break;
                        default:
                            break;
                    }

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
