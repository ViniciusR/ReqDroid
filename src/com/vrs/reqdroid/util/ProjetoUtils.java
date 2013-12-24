/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */
package com.vrs.reqdroid.util;

/**
 * Classe responsavel por realizar operacoes relacionadas aos projetos.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class ProjetoUtils {
    private static ProjetoUtils ourInstance = new ProjetoUtils();
    private static int idProjeto;

    private static String tituloProjeto;

    public static ProjetoUtils getInstance() {
        return ourInstance;
    }

    private ProjetoUtils() {
    }

    public static int getIdProjeto() {
        return idProjeto;
    }

    public static void setIdProjeto(int idProjeto) {
        ProjetoUtils.idProjeto = idProjeto;
    }

    public static String getTituloProjeto() {
        return tituloProjeto;
    }

    public static void setTituloProjeto(String tituloProjeto) {
        ProjetoUtils.tituloProjeto = tituloProjeto;
    }
}
