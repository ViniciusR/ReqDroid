/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.modelo;

/**
 * Implementa uma classe que representa o modelo de uma dependencia do sistema.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class Dependencia {

    public Dependencia(int primeiroRequisito, int segundoRequisito)
    {
        this.primeiroRequisito = primeiroRequisito;
        this.segundoRequisito = segundoRequisito;
    }
	
	private int primeiroRequisito;
	public int getPrimeiroRequisito() {
		return primeiroRequisito;
	}
	public void setPrimeiroRequisito(int primeiroRequisito) {
		this.primeiroRequisito = primeiroRequisito;
	}
	public int getSegundoRequisito() {
		return segundoRequisito;
	}
	public void setSegundoRequisito(int segundoRequisito) {
		this.segundoRequisito = segundoRequisito;
	}
	private int segundoRequisito;
}
