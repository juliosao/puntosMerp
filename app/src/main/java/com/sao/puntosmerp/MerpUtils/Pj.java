package com.sao.puntosmerp.MerpUtils;

import java.io.Serializable;

public class Pj implements Serializable {
    private boolean selected;
    private int puntos;
    private int nivel;
    private String nombre;
    private String grupo;

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Devuelve el nivel del personaje, en base a su puntiacion y lo actualiza de cara a puntuaciones
     * @return El nivel del personaje
     */
    public int calcNivel()
    {
        int resultado;
        if(puntos < 0)
            resultado = 0;
        else if (puntos < 50000)
            resultado = puntos / 10000;
        else
            resultado = 5 + (puntos -50000)/20000;

        setNivel(resultado);
        return resultado;
    }

    /**
     * Calcula y suma a la puntuacion actual el numero de puntos debidos a un critico o varios
     * @param cadenaCritico Los criticos hechos al nivel deseado, ejemplo "abcxxb"
     * @return La puntuación obtenida
     */
    public int puntosCritico(String cadenaCritico) {
        int multiplicador = 0;
        int resultado;
        int nivelOponente=0;

        cadenaCritico = cadenaCritico.toLowerCase();
        for (int i = 0; i < cadenaCritico.length(); i++) {
            char c = cadenaCritico.charAt(i);
            switch (c) {
                case 'a':
                    multiplicador += 5;
                    break;
                case 'b':
                    multiplicador += 10;
                    break;
                case 'c':
                    multiplicador += 15;
                    break;
                case 'd':
                    multiplicador += 20;
                    break;
                case 'e':
                    multiplicador += 25;
                    break;
                case 'x':
                    multiplicador += 50;
                    break;
                default:
                    if(c>='0' && c<='9')
                    {
                        nivelOponente *= 10;
                        nivelOponente += c-'0';
                    }
            }
        }

        if (nivelOponente > 0)
            resultado = nivelOponente * multiplicador;
        else
            resultado = multiplicador / 2;

        puntos += resultado;
        return  resultado;
    }

    public int puntosCriticoRecibido(String cadenaCritico) {
        int multiplicador = 0;

        cadenaCritico = cadenaCritico.toLowerCase();
        for (int i = 0; i < cadenaCritico.length(); i++) {
            char c = cadenaCritico.charAt(i);
            switch (c) {
                case 'a':
                    multiplicador += 5;
                    break;
                case 'b':
                    multiplicador += 10;
                    break;
                case 'c':
                    multiplicador += 15;
                    break;
                case 'd':
                    multiplicador += 20;
                    break;
                case 'e':
                    multiplicador += 25;
                    break;
                case 'x':
                    multiplicador += 50;
                    break;
            }
        }

        multiplicador *= 20;

        puntos += multiplicador;
        return  multiplicador;
    }


    /**
     * Da puntos por pieza conseguida
     * @param nivelPieza Nivel de la pieza conseguida
     * @return Puntos obtenidos
     */
    public int puntosPieza(int nivelPieza)
    {
        int nivel = getNivel();
        int diferencia = nivel - nivelPieza;
        int resultado;

        if( (nivelPieza > nivel-2) && (nivelPieza!=0))
        {
            resultado = (nivelPieza -nivel) * 50 +200;
        }
        else if( (nivelPieza > nivel -5) && (nivelPieza !=0 ))
        {
            resultado = 150 -20 * diferencia -1;
        }
        else if(nivelPieza != 0)
        {
            resultado = 110 - 10 * (diferencia -3);
            if(resultado<0)
                resultado =0;
        }
        else
        {
            resultado = nivel < 10 ? 55 - 5 * nivel : 0;
        }

        puntos += resultado;
        return resultado;
    }

    /**
     * Devuelve los puntos hechos por una magia
     * @param nivelMagia Nivel de la magia hecha
     * @param limitar Si se debe permitir hacer una magia por encima del nivel del pj
     * @return El numero de puntos obtenidos
     */
    public int puntosMagia(int nivelMagia, boolean limitar)
    {
        int nivel = getNivel();
        int resultado;

        if(limitar && nivelMagia < nivel)
        {
            nivelMagia = nivel;
        }

        resultado = 100 -(10*nivel) +(10*nivelMagia);
        puntos+=resultado;
        return resultado;
    }

    public int addPuntos(int cantidad)
    {
        puntos += cantidad;
        return cantidad;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

}
