/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.models.Dependencia;

import java.util.ArrayList;

/**
 * Classe que define o layout customizado da lista de dependencias.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */

public class ListViewDependenciasAdapter extends BaseAdapter {
    private final ArrayList<Dependencia> dependenciasArrayList;
    private final View.OnClickListener botaoRemoverListener;
    private final String tituloRequisito;

    private final LayoutInflater mInflater;

    public ListViewDependenciasAdapter(Context context, ArrayList<Dependencia> dependencias, View.OnClickListener botaoRemoverListener) {
        dependenciasArrayList = dependencias;
        mInflater = LayoutInflater.from(context);
        this.botaoRemoverListener = botaoRemoverListener;
        tituloRequisito = context.getString(R.string.tela_detalhes_requisito_nome_requisito);
    }

    public int getCount() {
        return dependenciasArrayList.size();
    }

    public Object getItem(int position) {
        return dependenciasArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderDependencia holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dependencia_item, null);
            holder = new ViewHolderDependencia();
            holder.textoRequisitoDependente1 = (TextView) convertView.findViewById(R.id.txtRequisitoDependente1);
            holder.imageSetaDependencia = (ImageView) convertView.findViewById(R.id.imgSetaDependencia);
            holder.textoRequisitoDependente2 = (TextView) convertView.findViewById(R.id.txtRequisitoDependente2);
            holder.botaoRemoverDependencia = (ImageButton) convertView.findViewById(R.id.botaoRemoverDependencia);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderDependencia) convertView.getTag();
        }

        holder.textoRequisitoDependente1.setText(tituloRequisito + String.valueOf(dependenciasArrayList.get(position).getPrimeiroRequisito()));
        holder.textoRequisitoDependente2.setText(tituloRequisito + String.valueOf(dependenciasArrayList.get(position).getSegundoRequisito()));
        holder.botaoRemoverDependencia.setOnClickListener(botaoRemoverListener);
        return convertView;
    }
}
/**
 * Classe auxiliar para definir os itens customizados da lista
 * de dependencias.
 *
 * @author Vinicius Rodrigues Silva
 * @version 1.0
 */
class ViewHolderDependencia {
    TextView textoRequisitoDependente1;
    ImageView imageSetaDependencia;
    TextView textoRequisitoDependente2;
    ImageButton botaoRemoverDependencia;
}