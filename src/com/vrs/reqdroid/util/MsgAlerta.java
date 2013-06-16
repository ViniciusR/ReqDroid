/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.vrs.reqdroid.R;

/**
 * Implementa uma classe que exibe mensagens de alerta padrao no aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class MsgAlerta {
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
}
