package com.example.agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class FourColumn_ListAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Contato> contatos;
    private int mViewResourceId;

    public FourColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Contato> contatos) {
        super(context, textViewResourceId, contatos);
        this.contatos = contatos;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Contato contato = contatos.get(position);

        if (contato != null) {
            TextView nome = (TextView) convertView.findViewById(R.id.nome);
            TextView telefone = (TextView) convertView.findViewById(R.id.telefone);
            TextView nascimento = (TextView) convertView.findViewById(R.id.nascimento);

            if (nome != null) {
                nome.setText(contato.getNome());
            }

            if (telefone != null) {
                telefone.setText(contato.getTelefone());
            }

            if (nascimento != null) {
                nascimento.setText(contato.getNascimento());
            }
        }
        return convertView;
    }
}
