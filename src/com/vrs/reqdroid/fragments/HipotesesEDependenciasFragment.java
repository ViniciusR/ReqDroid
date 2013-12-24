/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vrs.reqdroid.activities.TelaPrincipalActivity;
import com.vrs.reqdroid.R;


/**
 * Implementa a activity que contem as abas (tabs) das telas de hipoteses e dependencias do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class HipotesesEDependenciasFragment extends Fragment{


    private FragmentTabHost hipotesesEDependenciasTabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState)
    {
        hipotesesEDependenciasTabHost = new FragmentTabHost(getActivity());
        hipotesesEDependenciasTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tabcontent);

        hipotesesEDependenciasTabHost.addTab(hipotesesEDependenciasTabHost.newTabSpec("tabHipoteses").setIndicator(getResources().getString(R.string.tela_tabs_hed_hipoteses)),
                 HipotesesFragment.class, null);
        hipotesesEDependenciasTabHost.addTab(hipotesesEDependenciasTabHost.newTabSpec("tabDependencias").setIndicator(getResources().getString(R.string.tela_tabs_hed_dependencias)),
                DependenciasFragment.class, null);

        //O Android possui um bug onde as tabs "roubam" o foco das entradas de texto, o codigo abaixo corrige isso.
        //Mas somente para API 12 ou superior
        if ( Build.VERSION.SDK_INT >= 12)
        {
                hipotesesEDependenciasTabHost.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewDetachedFromWindow(View v) {}

                @Override
                public void onViewAttachedToWindow(View v) {
                    hipotesesEDependenciasTabHost.getViewTreeObserver().removeOnTouchModeChangeListener(hipotesesEDependenciasTabHost);
                }
            });
        }
        return hipotesesEDependenciasTabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hipotesesEDependenciasTabHost = null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(), TelaPrincipalActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
