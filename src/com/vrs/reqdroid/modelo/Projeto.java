/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.modelo;

/**
 * Implementa uma classe que representa o modelo de um projeto do sistema.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class Projeto {
    private String titulo;
    private String descricao;
    private String beneficios;
    private String objetivos;
    private String publicoAlvo;
    private String data;
    private int id;
    
    public Projeto(String titulo, String descricao, String beneficios, String objetivos, String publicoAlvo)
    {
        this.titulo = titulo;
        this.descricao = descricao;
        this.beneficios = beneficios;
        this.objetivos = objetivos;
        this.publicoAlvo = publicoAlvo;
    }
    
    public Projeto(String titulo, String data)
    {
        this.titulo = titulo;
        this.data = data;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the beneficios
     */
    public String getBeneficios() {
        return beneficios;
    }

    /**
     * @param beneficios the beneficios to set
     */
    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    /**
     * @return the objetivos
     */
    public String getObjetivos() {
        return objetivos;
    }

    /**
     * @param objetivos the objetivos to set
     */
    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    /**
     * @return the publicoAlvo
     */
    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    /**
     * @param publicoAlvo the publicoAlvo to set
     */
    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }
    
    /**
     * @return the publicoAlvo
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the project date to set
     */
    public void setData(String data) {
        this.data = data;
    }
   
   
}
