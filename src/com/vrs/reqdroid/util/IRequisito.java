/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.util;

/**
 * Implementa uma interface com assinaturas de metodos utilizados em operacoes com requisitos e requisitos atrasados.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public interface IRequisito {

	//void configuraItensLinha();
	//void adicionaRequisitoCampoDeTexto();
	//void adicionaLinhaNaTabela(TableRow.LayoutParams linhaLayoutParams, TableRow.LayoutParams campoRequisitoLayoutParams, TableRow.LayoutParams botaoRemoverMembroLayoutParams, String texto);
	//void removeRequisitoAtalho();
   //void selecionaLinha();
	void salvaRequisitoBD();
	void adicionaRequisito(String descricao, String data, int idprojeto);
	void removeRequisitoBD(String descricao);
	void carregaRequisitosBD(int idProjeto);
}
