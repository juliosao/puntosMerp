package com.sao.puntosmerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sao.puntosmerp.MerpUtils.Pj;
import com.sao.puntosmerp.PjItem.PjAdapter;
import com.sao.puntosmerp.PjItem.PjListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements PjListener {
    public static final int REQUEST_PUNTOS = 0;
    public static final int REQUEST_GRUPO = 1;
    String FILENAME = "pjs";
    String TAG = "MainActivity";

    private LinkedList<Pj> pjs;
    private RecyclerView recyclerView;
    private PjAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int puntosGrupo = 0;
    private TextView txtPuntosGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Puntos MERP");

        recyclerView = (RecyclerView) findViewById(R.id.lstPjs);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtPuntosGrupo = findViewById(R.id.txtPuntosGrupo);


        mAdapter = new PjAdapter(this);
        recyclerView.setAdapter(mAdapter);

        if(savedInstanceState != null)
        {
            try {
                mAdapter.setDataset((LinkedList<Pj>) savedInstanceState.getSerializable("PJs"));
                puntosGrupo = savedInstanceState.getInt("Grp");

            }
            catch (Exception ex)
            {
                Log.d(TAG,"No se pudo restaurar el estado");
            }
        }
        else
        {
            try
            {
                File file = getFileStreamPath(FILENAME);
                if(file.exists() && file.canRead())
                {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    LinkedList<Pj> pjs = (LinkedList<Pj>) ois.readObject();
                    puntosGrupo = ois.readInt();
                    fis.close();
                    ois.close();
                    mAdapter.setDataset(pjs);
                }
            }
            catch (Exception ex)
            {
                Log.d(TAG,"Error de carga: "+ex);
            }

        }
        txtPuntosGrupo.setText(""+puntosGrupo);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("PJs",mAdapter.getDataset());
        savedInstanceState.putInt("Grp",puntosGrupo);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.setDataset((LinkedList<Pj>)savedInstanceState.getSerializable("PJs"));
        puntosGrupo=savedInstanceState.getInt("Grp");
        txtPuntosGrupo.setText(""+puntosGrupo);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        int idx;
        Pj pj;

        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {

                case REQUEST_PUNTOS:
                    idx = data.getIntExtra(PointsActivity.IDX,0);
                    puntosGrupo += data.getIntExtra(PointsActivity.GROUP,0);
                    txtPuntosGrupo.setText(""+puntosGrupo);

                    pj = (Pj)data.getSerializableExtra(PointsActivity.PJ);
                    mAdapter.set(idx,pj);

                    try
                    {
                        File file = getFileStreamPath(FILENAME);
                        FileOutputStream fos = new FileOutputStream(file);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(mAdapter.getDataset());
                        oos.writeInt(puntosGrupo);
                        oos.close();
                        fos.close();
                    }
                    catch (Exception ex)
                    {
                        Log.d(TAG,"Error de guardado: "+ex);
                    }

                    break;
                case REQUEST_GRUPO:
                    idx = data.getIntExtra(PointsActivity.IDX,0);
                    puntosGrupo = data.getIntExtra(PointsActivity.GROUP,0);
                    txtPuntosGrupo.setText(""+puntosGrupo);

                    pj = (Pj)data.getSerializableExtra(PointsActivity.PJ);
                    mAdapter.set(idx,pj);

                    try
                    {
                        File file = getFileStreamPath(FILENAME);
                        FileOutputStream fos = new FileOutputStream(file);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(mAdapter.getDataset());
                        oos.writeInt(puntosGrupo);
                        oos.close();
                        fos.close();
                    }
                    catch (Exception ex)
                    {
                        Log.d(TAG,"Error de guardado: "+ex);
                    }
                    break;
            }
        }

    }

    public void addPj(View v)
    {
        Pj pj = new Pj();
        pj.setPuntos(10000);
        pj.setNombre("Julio");
        pj.calcNivel();
        mAdapter.add(pj);
    }

    @Override
    public void onPointsRequested(int pos) {
        Intent i = new Intent(this,PointsActivity.class);
        i.putExtra(PointsActivity.PJ,mAdapter.get(pos));
        i.putExtra(PointsActivity.IDX,pos);
        startActivityForResult(i,REQUEST_PUNTOS);
    }

    @Override
    public void onGroupPointsRequested(int pos) {
        Intent i = new Intent(this,PuntosGrupo.class);
        i.putExtra(PointsActivity.PJ,mAdapter.get(pos));
        i.putExtra(PointsActivity.IDX,pos);
        i.putExtra(PointsActivity.GROUP,puntosGrupo);
        startActivityForResult(i,REQUEST_GRUPO);
    }
}
