package com.sao.puntosmerp.PjItem;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sao.puntosmerp.MerpUtils.Pj;
import com.sao.puntosmerp.R;

public class PjItem extends RecyclerView.ViewHolder {
    View view;
    EditText txtPj;
    TextView txtPuntos;
    Pj pj;

    public PjItem(@NonNull View itemView) {
        super(itemView);

        view=itemView;
        txtPj = itemView.findViewById(R.id.txtPj);
        txtPuntos = itemView.findViewById(R.id.txtPuntos);

        txtPj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                pj.setNombre(s.toString());
            }
        });
    }



    public void setData(Pj pj)
    {
        this.pj=pj;
        txtPj.setText(pj.getNombre());
        txtPuntos.setText(""+pj.getPuntos());
        view.setSelected(pj.isSelected());
    }

}
