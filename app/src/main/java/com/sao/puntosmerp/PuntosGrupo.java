package com.sao.puntosmerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sao.puntosmerp.MerpUtils.Pj;

import java.util.prefs.PreferenceChangeEvent;

import static com.sao.puntosmerp.PointsActivity.IDX;
import static com.sao.puntosmerp.PointsActivity.PJ;
import static com.sao.puntosmerp.PointsActivity.GROUP;

public class PuntosGrupo extends AppCompatActivity {
    SeekBar sb;
    TextView lblMax;
    TextView lblDar;
    TextView lblTotal;
    Pj pj;
    int idx;
    int actual=0;
    int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos_grupo);

        Intent i = getIntent();
        pj = (Pj) i.getSerializableExtra(PJ);
        idx = i.getIntExtra(IDX, 0);
        max = i.getIntExtra(GROUP, 0);

        lblMax= findViewById(R.id.lblMax);
        lblMax.setText("" + max);

        lblTotal = findViewById(R.id.lblTotal);
        lblTotal.setText(""+pj.getPuntos());

        lblDar= findViewById(R.id.lblDar);

        sb = findViewById(R.id.seekBar);
        sb.setMax(max);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                actual=progress;
                lblDar.setText("" + progress);
                lblTotal.setText(""+(pj.getPuntos()+progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        FloatingActionButton btn = findViewById(R.id.btnOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pj.addPuntos(actual);
                Intent intent = new Intent();
                intent.putExtra(PJ, pj);
                intent.putExtra(IDX,idx);
                intent.putExtra(GROUP,max-actual);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
