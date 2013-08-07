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
import android.widget.TextView;

import com.vrs.reqdroid.R;

import java.util.ArrayList;

/**
 * Classe que define o layout customizado da lista de requisitos.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */

public class ListViewRequisitosAdapter extends BaseAdapter {
    private final ArrayList<String> requisitosArrayList;
    private final View.OnClickListener botaoEditarListener;

    private final LayoutInflater mInflater;

    public ListViewRequisitosAdapter(Context context, ArrayList<String> requisitos, View.OnClickListener botaoEditarListener)
    {
        requisitosArrayList = requisitos;
        mInflater = LayoutInflater.from(context);
        this.botaoEditarListener = botaoEditarListener;
    }

    public int getCount() {
        return requisitosArrayList.size();
    }

    public Object getItem(int position) {
        return requisitosArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderRequisitos holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.requisitoitem, null);
            holder = new ViewHolderRequisitos();
            holder.descricaoRequisitos = (TextView) convertView.findViewById(R.id.txtRequisito);
            holder.botaoOpcoes = (ImageButton) convertView.findViewById(R.id.bOpcoesRequisito);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderRequisitos) convertView.getTag();
        }

        holder.descricaoRequisitos.setText(requisitosArrayList.get(position));
        holder.botaoOpcoes.setOnClickListener(botaoEditarListener);
        return convertView;
    }
}
/**
 * Classe auxiliar para definir os itens customizados da lista
 * de requisitos.
 *
 * @author Vinicius Rodrigues Silva
 * @version 1.0
 */
class ViewHolderRequisitos {
    TextView descricaoRequisitos;
    ImageButton botaoOpcoes;
}