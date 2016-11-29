package com.itchihuahuaii.aplicacioncafeteria;

/**
 * Created by usuario1 on 11/23/2016.
 */
public class Cliente {

    int id;
    String nombre;
    String nick;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String tipo;
    String password;


    Cliente(int id,String nombre,String nick,String tipo,String password){
        this.id=id;
        this.nombre=nombre;
        this.nick=nick;
        this.tipo=tipo;
        this.password=password;
    }

}
