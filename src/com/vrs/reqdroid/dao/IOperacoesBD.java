/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.dao;

import java.util.List;

import com.vrs.reqdroid.models.Dependencia;
import com.vrs.reqdroid.models.Projeto;


/**
 * Implementa uma interface com assinaturas de metodos utilizados por operacoes do banco de dados do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
@SuppressWarnings("SameParameterValue")
public interface IOperacoesBD {
	
	public long insertProjeto(String titulo, String descricao, String beneficios, String objetivos, String publicoalvo, String data);
	public long insertRequisito(String descricao, String data, int prioridade, int versao, String autor, int idprojeto);
	public long insertRequisitoAtrasado(String descricao, String data, int prioridade, int versao, String autor, int idprojeto);
	public long insertCaracteristicasUsuario(int experiencia, int pericia, int treinamento, int idprojeto);
	public long insertHipotese(String descricao, String data, int versao, String autor, int idProjeto);
	public long insertProjetoRequisito(int idProjeto, int idRequisito, int numero);
	public long insertProjetoRequisitoAtrasado(int idProjeto, int idRequisito, int numero);
	public long insertDependencia(int idProjeto, int idPrimeiroRequisito, int idSegundoRequisito);
	
	public void deleteAllProjetos();
	public void deleteProjeto(int idProjeto);
	public void deleteRequisito(String descricao);
	public void deleteRequisito(int id);
	public void deleteRequisitoAtrasado(int id);
	public void deleteRequisitoAtrasado(String descricao);
	public void deleteHipotese(int id);
	public void deleteDependencia(int idProjeto, int idPrimeiroRequisito, int idSegundoRequisito);
	
	public void updateRequisito(int id, String descricaoNova, int versaoNova);
	public void updatePrioridadeRequisito(int id, int prioridadeNova);
	public void updatePrioridadeRequisitoAtrasado(int id, int prioridadeNova);
	public void updateAutorRequisito(int id, String autorNovo);
	public void updateAutorRequisitoAtrasado(int id, String autorNovo);
	public void updateProjeto(int id, String tituloNovo, String descricaoNova, String beneficiosNovos, String objetivosNovos, String publicoAlvoNovo);
	public void updateRequisitoAtrasado(int id, String descricaoNova, int versaoNova);
	public void updateExperiencia(int idProjeto, int experienciaNova);
	public void updatePericia(int idProjeto, int periciaNova);
	public void updateTreinamento(int idProjeto, int treinamentoNovo);
	public void updateHipotese(int id, String descricaoNova, int versaoNova);
	public void updateAutorHipotese(int id, String autorNovo);
	
	public List<String> selectAllProjetos();
	public List<Projeto> selectAllProjetosComData();
    public List<Projeto> selectProjetosRecentesComData();
	public List<String> selectDatasAllProjetos();
	public int selectRequisitoPorDescricao(String descricao, int idProjeto);
	public String selectDataRequisito(String descricao, int idProjeto);
	public String selectAutorRequisito(String descricao, int idProjeto);
	public int selectVersaoRequisito(String descricao, int idProjeto);
	public int selectPrioridadeRequisito(String descricao, int idProjeto);
	public String selectDataRequisitoAtrasado(String descricao, int idProjeto);
	public String selectAutorRequisitoAtrasado(String descricao, int idProjeto);
	public int selectVersaoRequisitoAtrasado(String descricao, int idProjeto);
	public int selectPrioridadeRequisitoAtrasado(String descricao, int idProjeto);
	public List<String> selectRequisito(int idProjeto);
	public Projeto selectProjeto(String titulo);
	public int selectIdProjeto(String titulo);
	public boolean selectProjetoExistente(String titulo);
	public int selectRequisitoAtrasadoPorDescricao(String descricao, int idProjeto);
	public int selectExperienciaUsuario(int idProjeto);
	public int selectPericiaUsuario(int idProjeto);
	public int selectTreinamentoUsuario(int idProjeto);
	public List<String> selectHipotese(int idProjeto);
	public int selectHipotesePorDescricao(String descricao, int idProjeto);
	public String selectDataHipotese(String descricao, int idProjeto);
	public int selectVersaoHipotese(String descricao, int idProjeto);
	public String selectAutorHipotese(String descricao, int idProjeto);
	public int selectNumeroRequisito(int idProjeto, int idRequisito);
	public int selectNumeroRequisito(String descricao, int idProjeto);
	public int selectNumeroRequisitoAtrasado(String descricao, int idProjeto);
	public List<Integer> selectAllNumerosRequisitos(int idProjeto);
	public int selectRequisitoPorNumero(int idProjeto, int numero);
	public List<Dependencia> selectDependencias(int idProjeto);
	
	public int getIdUltimoProjeto();
	public int getIdUltimoRequisito();
	public int getIdUltimoRequisitoAtrasado();
	public int getNumeroUltimoRequisito(int idProjeto);
	public int getNumeroUltimoRequisitoAtrasado(int idProjeto);
}
