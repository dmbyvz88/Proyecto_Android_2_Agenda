package com.example.dmb.proyecto_agenda;
/**
 * Diego Murillo Barrantes
 */
public class Notas {
    //Variable de notas
    long id;
    String titulo;
    String descripcion;
    String lugar;
    String hora;
    /**
     * metodo get de id
     *
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * metodo set de id
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * metodo get de titulo
     *
     * @return
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * metodo set de titulo
     *
     * @param titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * metodo get de descripcion
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * metodo set de descripcion
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * metodo get de lugar
     *
     * @return
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * metodo set de lugar
     *
     * @param lugar
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * metodo get de hora
     *
     * @return
     */
    public String getHora() {
        return hora;
    }

    /**
     * metodo set de hora
     *
     * @param hora
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    public String toString() {
        return "Titulo: " + titulo + "\n" + "Hora: " + hora;
    }
}
