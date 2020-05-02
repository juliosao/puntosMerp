package com.sao.puntosmerp;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sao.puntosmerp.MerpUtils.Pj;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.sao.puntosmerp", appContext.getPackageName());

        Pj pj = new Pj();
        pj.setPuntos(10000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),1 );
        Log.d("Test","Comprobando nivel 1");

        pj.setPuntos(20000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),2 );
        Log.d("Test","Comprobando nivel 2");

        pj.setPuntos(30000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),3 );
        Log.d("Test","Comprobando nivel 3");

        pj.setPuntos(40000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),4 );
        Log.d("Test","Comprobando nivel 4");

        pj.setPuntos(50000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),5 );
        Log.d("Test","Comprobando nivel 5");

        pj.setPuntos(60000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),5 );
        Log.d("Test","Comprobando nivel 5(2)");

        pj.setPuntos(70000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),6 );
        Log.d("Test","Comprobando nivel 6(");

        pj.setPuntos(80000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),6 );
        Log.d("Test","Comprobando nivel 6(2)");

        pj.setPuntos(90000);
        pj.calcNivel();
        assertEquals(pj.getNivel(),7 );
    }
}
