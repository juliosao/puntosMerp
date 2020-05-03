package com.sao.puntosmerp.PjItem;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sao.puntosmerp.MerpUtils.Pj;
import com.sao.puntosmerp.PointsActivity;
import com.sao.puntosmerp.R;

import java.util.Arrays;
import java.util.LinkedList;

public class PjAdapter extends RecyclerView.Adapter<PjItem> {
    private LinkedList<Pj> mDataset;
    View view;
    PjListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PjAdapter(PjListener l) {
        mDataset = new LinkedList<>();
        listener = l;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PjItem onCreateViewHolder(ViewGroup parent,
                                     int viewType) {
        // create a new view
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_pj_view, parent, false);
        final PjItem vh = new PjItem(view);

        Button btn = view.findViewById(R.id.btnPuntos);
        btn.setOnClickListener (new OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = vh.getAdapterPosition();
                Log.d("TAG", "PJ: "+mDataset.get(pos).getNombre());
                listener.onPointsRequested(pos);
            }
        });

        btn = view.findViewById(R.id.btnPuntosGrupo);
        btn.setOnClickListener (new OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = vh.getAdapterPosition();
                Log.d("TAG", "PJ: "+mDataset.get(pos).getNombre());
                listener.onGroupPointsRequested(pos);
            }
        });

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PjItem holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.setData(mDataset.get(position));
    }

    public void setDataset(LinkedList<Pj> ds)
    {
        mDataset = ds;
        notifyDataSetChanged();
    }

    public LinkedList<Pj> getDataset()
    {
        return mDataset;
    }


    public void set(int idx, Pj pj)
    {
        mDataset.set(idx,pj);
        notifyItemChanged(idx);
    }

    public Pj get(int pos)
    {
        return mDataset.get(pos);
    }

    public void add(Pj pj)    {
        int pos = mDataset.size();
        mDataset.add(pj);
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Establece el item seleccionado
     * @param idx
     */
    public void setSelected(int idx)
    {
        for(int i=0; i<mDataset.size(); i++ )
            this.mDataset.get(idx).setSelected(i==idx);
        notifyDataSetChanged();
    }


}
