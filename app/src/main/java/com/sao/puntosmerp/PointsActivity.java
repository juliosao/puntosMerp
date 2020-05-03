package com.sao.puntosmerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sao.puntosmerp.MerpUtils.Pj;

public class PointsActivity extends AppCompatActivity implements TextWatcher {
    public static final String PJ = "pj";
    public static final String GROUP = "grp";
    public static final String IDX = "idx";

    Pj pj;
    int iniciales;
    int nuevos;
    int idx;

    TextView lblPuntosIniciales;
    TextView lblActual;
    EditText txtCriticos;
    EditText txtRecibidos;
    EditText txtPiezas;
    EditText txtMagias;
    EditText txtOtros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        Intent i = getIntent();
        pj = (Pj)i.getSerializableExtra(PJ);
        this.setTitle("Dar puntos a "+pj.getNombre());
        idx = i.getIntExtra(IDX,0);


        iniciales = pj.getPuntos();
        lblPuntosIniciales = findViewById(R.id.lblPuntos);
        lblPuntosIniciales.setText(""+iniciales);

        lblActual = findViewById(R.id.lblActual);
        lblActual.setText(""+pj.getPuntos());

        txtCriticos = findViewById(R.id.txtCriticos);
        txtCriticos.addTextChangedListener(this);

        txtRecibidos = findViewById(R.id.txtRecibidos);
        txtRecibidos.addTextChangedListener(this);

        txtPiezas = findViewById(R.id.txtPiezas);
        txtPiezas.addTextChangedListener(this);

        txtMagias = findViewById((R.id.txtMagias));
        txtMagias.addTextChangedListener(this);

        txtOtros = findViewById(R.id.txtOtros);
        txtOtros.addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Pasamos
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Pasamos
    }

    @Override
    public void afterTextChanged(Editable s) {
        String data;
        String datos[];
        String error="";

        try {
            nuevos=0;
            pj.setPuntos(iniciales);
            pj.calcNivel();

            // Críticos hechos
            error = "Criticos hechos";
            data = txtCriticos.getText().toString();
            datos = data.split("[ ,]+");
            for (String dato : datos) {
                if(!dato.isEmpty())
                    nuevos+=pj.puntosCritico(dato);
            }

            // Críticos recibidos
            error = "Criticos recibidos";
            data = txtRecibidos.getText().toString();
            datos = data.split("[ ,]+");
            for (String dato : datos) {
                if(!dato.isEmpty())
                    nuevos+=pj.puntosCriticoRecibido(dato);
            }

            // Piezas
            error = "Piezas";
            data = txtPiezas.getText().toString();
            datos = data.split("[ ,]+");
            for (String dato : datos) {
                if(!dato.isEmpty())
                    nuevos+=pj.puntosPieza(Integer.parseInt(dato));
            }

            // Piezas
            error = "Sortilegios";
            data = txtMagias.getText().toString();
            datos = data.split("[ ,]+");
            for (String dato : datos) {
                if(!dato.isEmpty())
                    nuevos+=pj.puntosMagia(Integer.parseInt(dato),false);
            }

            // Otros
            error = "Otros puntos";
            data = txtOtros.getText().toString();
            datos = data.split("[ ,]+");
            for (String dato : datos) {
                if(!dato.isEmpty())
                    nuevos+=pj.addPuntos(Integer.parseInt(dato));
            }

            lblActual.setText("" + pj.getPuntos());
            pj.calcNivel();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"No se han podido dar puntos, hay un error en "+error,Toast.LENGTH_LONG);
            lblActual.setText("Error en "+error);
        }

    }

    public void darPuntos(View v)
    {
        Intent intent = new Intent();
        intent.putExtra(PJ, pj);
        intent.putExtra(IDX,idx);
        intent.putExtra(GROUP,nuevos/2);
        setResult(RESULT_OK, intent);
        finish();
    }
}
