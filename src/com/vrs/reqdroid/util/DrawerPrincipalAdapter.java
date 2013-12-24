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
import android.widget.ImageView;
import android.widget.TextView;

import com.vrs.reqdroid.R;

/**
 * Classe que define o layout customizado da lista do drawer principal com as funcionalidades.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */

public class DrawerPrincipalAdapter extends BaseAdapter {
    private final String[] titulosFuncionalidadesList;
    private final Context context;
    private final LayoutInflater mInflater;

    public DrawerPrincipalAdapter(Context context, String[] tituloFuncionalidades)
    {
        this.titulosFuncionalidadesList = tituloFuncionalidades;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return titulosFuncionalidadesList.length;
    }

    public Object getItem(int position) {
        return titulosFuncionalidadesList[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderDrawerItem holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.drawer_principal_item, null);
            holder = new ViewHolderDrawerItem();
            holder.tituloFuncionalidade = (TextView) convertView.findViewById(R.id.textoFuncionalidade);
            holder.iconeFuncionalidade = (ImageView) convertView.findViewById(R.id.iconeFuncionalidade);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderDrawerItem) convertView.getTag();
        }

        holder.tituloFuncionalidade.setText(titulosFuncionalidadesList[position]);

        String iconeFuncionalidade = context.getResources().getStringArray(R.array.icones_funcionalidades_array)[position];
        int imageId = context.getResources().getIdentifier(iconeFuncionalidade, "drawable", context.getPackageName());
        holder.iconeFuncionalidade.setImageResource(imageId);
        return convertView;
    }
}
/**
 * Classe auxiliar para definir os itens customizados da lista
 * do drawer principal.
 *
 * @author Vinicius Rodrigues Silva
 * @version 1.0
 */
class ViewHolderDrawerItem {
    TextView tituloFuncionalidade;
    ImageView iconeFuncionalidade;
}