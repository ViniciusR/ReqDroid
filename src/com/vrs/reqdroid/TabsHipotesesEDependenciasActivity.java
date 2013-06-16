/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import android.os.Bundle;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TextView;


/**
 * Implementa a activity que contem as abas (tabs) das telas de hipoteses e dependencias do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class TabsHipotesesEDependenciasActivity extends ActivityGroup {
    
    private static TabHost tabHost;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.tabshipotesesedependencias);

        tabHost = (TabHost)findViewById(R.id.tabshd);
        tabHost.setup(this.getLocalActivityManager());
        TabHost.TabSpec spec;
        Intent intent;
        
        View viewHipoteses = constroiTabView(this, getResources().getString(R.string.tela_tabs_hed_hipoteses));
        View viewDependencias = constroiTabView(this, getResources().getString(R.string.tela_tabs_hed_dependencias));
             
        /*
         * Cada Tab adicionada tem sua propria Activity, que por sua vez , tem seu proprio .xml(layout) e tambem tem um elemento Drawable. Esse elemento eu usei para mudar o icone da Tab.         
         * Quando ela nao esta ativa, o icone e cinza. Quando ela esta ativa, o icone fica colorido e indica pro usuario que ele esta naquela Tab.
         *  Nesse meu exemplo, eu tenho 2 Tabs, 4 Activities, 2 Layouts, 2 drawable que faz a mudanca de icone, 1 para cada Tab.
        */                 
        //Adiciona Tab #1
        intent = new Intent().setClass(TabsHipotesesEDependenciasActivity.this, HipotesesActivity.class);
        spec = tabHost.newTabSpec("0").setIndicator(viewHipoteses).setContent(intent);
        tabHost.addTab(spec);
 
        // Adiciona Tab #2
        intent = new Intent().setClass(TabsHipotesesEDependenciasActivity.this, DependenciasActivity.class);
        spec = tabHost.newTabSpec("1").setIndicator(viewDependencias).setContent(intent);
        tabHost.addTab(spec);
 
        tabHost.setCurrentTab(0);
    }
    
    /**
     * Constroi as abas conforme parametros de layouts customizados.
     * 
     * @param context O contexto de onde as abas ficarao
     * @param label  O titulo das abas
     * @return Um layout com o titulo personalizado da aba
     */
    private static LinearLayout constroiTabView(Context context, String label){

        LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout ll = (LinearLayout)li.inflate(R.layout.tab, null);


        LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, label.length() + 1);
        ll.setLayoutParams(layoutParams);
      
        final TextView tv = (TextView)ll.findViewById(R.id.tab_modelo);

        tv.setText(label);

        return ll;
    }    
}