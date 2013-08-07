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

public class ListViewRequisitosAtrasadosAdapter extends BaseAdapter {
    private final ArrayList<String> requisitosAtrasadosArrayList;
    private final View.OnClickListener botaoEditarListener;

    private final LayoutInflater mInflater;

    public ListViewRequisitosAtrasadosAdapter(Context context, ArrayList<String> requisitosAtrasados, View.OnClickListener botaoEditarListener)
    {
        requisitosAtrasadosArrayList = requisitosAtrasados;
        mInflater = LayoutInflater.from(context);
        this.botaoEditarListener = botaoEditarListener;
    }

    public int getCount() {
        return requisitosAtrasadosArrayList.size();
    }

    public Object getItem(int position) {
        return requisitosAtrasadosArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderRequisitosAtrasados holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.requisitoatrasadoitem, null);
            holder = new ViewHolderRequisitosAtrasados();
            holder.descricaoRequisito = (TextView) convertView.findViewById(R.id.txtRequisitoAtrasado);
            holder.botaoOpcoes = (ImageButton) convertView.findViewById(R.id.bOpcoesRequisitoAtrasado);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderRequisitosAtrasados) convertView.getTag();
        }

        holder.descricaoRequisito.setText(requisitosAtrasadosArrayList.get(position));
        holder.botaoOpcoes.setOnClickListener(botaoEditarListener);
        return convertView;
    }
}
/**
 * Classe auxiliar para definir os itens customizados da lista
 * de requisitos atrasados.
 *
 * @author Vinicius Rodrigues Silva
 * @version 1.0
 */
class ViewHolderRequisitosAtrasados {
    TextView descricaoRequisito;
    ImageButton botaoOpcoes;
}