/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.modelo.Projeto;

import java.util.ArrayList;

/**
 * Classe que define o layout customizado da lista de projetos a serem abertos.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */

public class ListViewProjetosAdapter extends BaseAdapter {
    private final ArrayList<Projeto> projetosArrayList;

    private final LayoutInflater mInflater;

    public ListViewProjetosAdapter(Context context, ArrayList<Projeto> projetos) {
        projetosArrayList = projetos;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return projetosArrayList.size();
    }

    public Object getItem(int position) {
        return projetosArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderProjetos holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.projetoitem, null);
            holder = new ViewHolderProjetos();
            holder.textoTitulo = (TextView) convertView.findViewById(R.id.tituloProjeto);
            holder.textoData = (TextView) convertView.findViewById(R.id.dataProjeto);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderProjetos) convertView.getTag();
        }

        holder.textoTitulo.setText(projetosArrayList.get(position).getTitulo());
        holder.textoData.setText(projetosArrayList.get(position).getData());
        return convertView;
    }
}
/**
 * Classe auxiliar para definir os itens customizados da lista
 * de projetos a serem abertos.
 *
 * @author Vinicius Rodrigues Silva
 * @version 1.0
 */
class ViewHolderProjetos {
    TextView textoTitulo;
    TextView textoData;
}
