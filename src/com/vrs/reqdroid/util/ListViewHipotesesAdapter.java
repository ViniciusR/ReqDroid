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
 * Classe que define o layout customizado da lista de hipoteses.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */

public class ListViewHipotesesAdapter extends BaseAdapter {
    private final ArrayList<String> hipotesesArrayList;
    private final View.OnClickListener botaoEditarListener;

    private final LayoutInflater mInflater;

    public ListViewHipotesesAdapter(Context context, ArrayList<String> hipoteses, View.OnClickListener botaoEditarListener)
    {
        hipotesesArrayList = hipoteses;
        mInflater = LayoutInflater.from(context);
        this.botaoEditarListener = botaoEditarListener;
    }

    public int getCount() {
        return hipotesesArrayList.size();
    }

    public Object getItem(int position) {
        return hipotesesArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderHipoteses holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.hipotese_item, null);
            holder = new ViewHolderHipoteses();
            holder.descricaoHipotese = (TextView) convertView.findViewById(R.id.txtHipotese);
            holder.botaoOpcoes = (ImageButton) convertView.findViewById(R.id.bOpcoesHipotese);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolderHipoteses) convertView.getTag();
        }

        holder.descricaoHipotese.setText(hipotesesArrayList.get(position));
        holder.botaoOpcoes.setOnClickListener(botaoEditarListener);
        return convertView;
    }
}
/**
 * Classe auxiliar para definir os itens customizados da lista
 * de hipoteses.
 *
 * @author Vinicius Rodrigues Silva
 * @version 1.0
 */
class ViewHolderHipoteses {
    TextView descricaoHipotese;
    ImageButton botaoOpcoes;
}