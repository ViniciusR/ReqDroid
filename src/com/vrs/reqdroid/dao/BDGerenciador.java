/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import com.vrs.reqdroid.modelo.Dependencia;
import com.vrs.reqdroid.modelo.Projeto;


/**
 * Implementa a classe responsavel pelas operacoes de banco de dados do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class BDGerenciador implements IOperacoesBD{
       private static BDGerenciador instancia;
       
       private static final String DATABASE_NAME = "reqdroid.db";
       private static final int DATABASE_VERSION = 29;
       private static final String TABLE_PROJETOS  = "projetos";
       private static final String TABLE_REQUISITOS  = "requisitos";
       private static final String TABLE_REQUISITOS_ATRASADOS  = "requisitosatrasados";
       private static final String TABLE_CARACTERISTICAS_USUARIO  = "caracteristicasusuario";
       private static final String TABLE_HIPOTESES  = "hipoteses";
       private static final String TABLE_PROJETO_REQUISITOS  = "projeto_requisitos";
       private static final String TABLE_PROJETO_REQUISITOS_ATRASADOS  = "projeto_requisitos_atr";
       private static final String TABLE_DEPENDENCIAS  = "dependencias";
       
       private final Context context;
       private final SQLiteDatabase db;

       private final SQLiteStatement insertStmtProjeto;
       private final SQLiteStatement insertStmtRequisitos;
       private final SQLiteStatement insertStmtRequisitosAtrasados;
       private final SQLiteStatement insertStmtCaracteristicasUsuario;
       private final SQLiteStatement insertStmtHipoteses;
       private final SQLiteStatement insertStmtProjetoRequisitos;
       private final SQLiteStatement insertStmtProjetoRequisitosAtrasados;
       private final SQLiteStatement insertStmtDependencia;
       
       private static final String INSERT_PROJETOS = "insert into " + TABLE_PROJETOS  + "(titulo, descricao, beneficios, objetivos, publicoalvo, data) values (?,?,?,?,?,?)";
       private static final String INSERT_REQUISITOS = "insert into " + TABLE_REQUISITOS  + "(descricao, data, prioridade, versao, autor, idprojeto) values (?,?,?,?,?,?)";
       private static final String INSERT_REQUISITOS_ATRASADOS = "insert into " + TABLE_REQUISITOS_ATRASADOS  + "(descricao,  data, prioridade, versao, autor, idprojeto) values (?,?,?,?,?,?)";
       private static final String INSERT_CARACTERISTICAS_USUARIO = "insert into " + TABLE_CARACTERISTICAS_USUARIO  + "(experiencia,  pericia, treinamento, idprojeto) values (?,?,?,?)";
       private static final String INSERT_HIPOTESE = "insert into " + TABLE_HIPOTESES  + "(descricao,  data, versao, autor, idprojeto) values (?,?,?,?,?)";
       private static final String INSERT_PROJETO_REQUISITOS = "insert into " + TABLE_PROJETO_REQUISITOS  + "(idprojeto, idrequisito, numero) values (?,?,?)";
       private static final String INSERT_PROJETO_REQUISITOS_ATRASADOS = "insert into " + TABLE_PROJETO_REQUISITOS_ATRASADOS  + "(idprojeto, idrequisito, numero) values (?,?,?)";
       private static final String INSERT_DEPENDENCIA = "insert into " + TABLE_DEPENDENCIAS  + "(idprojeto, idprimeirorequisito, idsegundorequisito) values (?,?,?)";
       
       private BDGerenciador(Context context) {
            this.context = context;
            OpenHelper openHelper = new OpenHelper(this.context);
            this.db = openHelper.getWritableDatabase();
            this.insertStmtProjeto = this.db.compileStatement(INSERT_PROJETOS);
            this.insertStmtRequisitos = this.db.compileStatement(INSERT_REQUISITOS);
            this.insertStmtRequisitosAtrasados = this.db.compileStatement(INSERT_REQUISITOS_ATRASADOS);
            this.insertStmtCaracteristicasUsuario = this.db.compileStatement(INSERT_CARACTERISTICAS_USUARIO);
            this.insertStmtHipoteses = this.db.compileStatement(INSERT_HIPOTESE);
            this.insertStmtProjetoRequisitos = this.db.compileStatement(INSERT_PROJETO_REQUISITOS);
            this.insertStmtProjetoRequisitosAtrasados = this.db.compileStatement(INSERT_PROJETO_REQUISITOS_ATRASADOS);
            this.insertStmtDependencia = this.db.compileStatement(INSERT_DEPENDENCIA);
       }
   
   ////////////////////////////////////////////
   /////////////////// INSERT ////////////////
   //////////////////////////////////////////
       
   /**
    * Insere um novo projeto.
    * 
    * @param titulo O titulo do projeto
    * @param descricao A descricao do projeto
    * @param beneficios Os beneficios do projeto
    * @param objetivos Os objetivos do projeto
    * @param publicoalvo O publico alvo do projeto
    * @param data A data do projeto
    */
   public long insertProjeto(String titulo, String descricao, String beneficios, String objetivos, String publicoalvo, String data) {
    	  try{
            this.insertStmtProjeto.bindString(1, titulo);
            this.insertStmtProjeto.bindString(2, descricao);
            this.insertStmtProjeto.bindString(3, beneficios);
            this.insertStmtProjeto.bindString(4, objetivos);
            this.insertStmtProjeto.bindString(5, publicoalvo);
            this.insertStmtProjeto.bindString(6, data);
    	  }
    	  catch (Exception e) {
              Log.v("Insert projeto", e.getMessage(), e);
              e.printStackTrace();
          } 
         return this.insertStmtProjeto.executeInsert();
      }
      
   /**
    * Insere um novo requisito do projeto.
    * 
    * @param descricao A descricao do requisito
    * @param data A data do requisito
    * @param prioridade A prioridade do requisito
    * @param versao A versao do requisito
    * @param autor O autor do requisito
    * @param idprojeto O id do projeto
    */
   public long insertRequisito(String descricao, String data, int prioridade, int versao, String autor, int idprojeto) { 
    	  try {
            this.insertStmtRequisitos.bindString(1, descricao);
            this.insertStmtRequisitos.bindString(2, data);
            this.insertStmtRequisitos.bindLong(3, prioridade);
            this.insertStmtRequisitos.bindLong(4, versao);
            this.insertStmtRequisitos.bindString(5, autor);
            this.insertStmtRequisitos.bindLong(6, idprojeto);
    	  }
    	  catch (Exception e) {
              Log.v("Insert requisito", e.getMessage(), e);
              e.printStackTrace();
          } 
         return this.insertStmtRequisitos.executeInsert();
      }
   
   /**
    * Insere um novo requisito atrasado do projeto.
    * 
    * @param descricao A descricao do requisito atrasado
    * @param data A data do requisito atrasado
    * @param prioridade A prioridade do requisito atrasado
    * @param versao A versao do requisito atrasado
    * @param autor O autor do requisito atrasado
    * @param idprojeto O id do projeto atrasado
    */
   public long insertRequisitoAtrasado(String descricao, String data, int prioridade, int versao, String autor, int idprojeto) { 
 	  try {
 		 this.insertStmtRequisitosAtrasados.bindString(1, descricao);
         this.insertStmtRequisitosAtrasados.bindString(2, data);
         this.insertStmtRequisitosAtrasados.bindLong(3, prioridade);
         this.insertStmtRequisitosAtrasados.bindLong(4, versao);
         this.insertStmtRequisitosAtrasados.bindString(5, autor);
         this.insertStmtRequisitosAtrasados.bindLong(6, idprojeto);
 	  }
 	  catch (Exception e) {
           Log.v("Insert requisito atrasado", e.getMessage(), e);
           e.printStackTrace();
       } 
      return this.insertStmtRequisitosAtrasados.executeInsert();
   }
   
   /**
    * Insere as caracteristicas do usuario do sistema.
    * 
    * @param experiencia O nivel de experiencia do usuario
    * @param pericia O nivel de pericia do usuario
    * @param treinamento A necessidade de treinamento do usuario
    * @param idprojeto O id do projeto
    */
   public long insertCaracteristicasUsuario(int experiencia, int pericia, int treinamento, int idprojeto) { 
 	  try {
 		 this.insertStmtCaracteristicasUsuario.bindLong(1, experiencia);
 		 this.insertStmtCaracteristicasUsuario.bindLong(2, pericia);
 		 this.insertStmtCaracteristicasUsuario.bindLong(3, treinamento);
         this.insertStmtCaracteristicasUsuario.bindLong(4, idprojeto);
 	  }
 	  catch (Exception e) {
           Log.v("Insert caracteristicas usuario", e.getMessage(), e);
           e.printStackTrace();
       } 
      return this.insertStmtCaracteristicasUsuario.executeInsert();
   }
   
   /**
    * Insere uma nova hipotese do projeto.
    * 
    * @param descricao A descricao da hipotese
    * @param data A data da hipotese
    * @param versao A versao da hipotese
    * @param autor O autor da hipotese
    * @param idProjeto O id do projeto
    */
   public long insertHipotese(String descricao, String data, int versao, String autor, int idProjeto) { 
	 	  try {
	 		 this.insertStmtHipoteses.bindString(1, descricao);
	 		 this.insertStmtHipoteses.bindString(2, data);
	 		 this.insertStmtHipoteses.bindLong(3, versao);
	 		 this.insertStmtHipoteses.bindString(4, autor);
	         this.insertStmtHipoteses.bindLong(5, idProjeto);
	 	  }
	 	  catch (Exception e) {
	           Log.v("Insert hipotese", e.getMessage(), e);
	           e.printStackTrace();
	       } 
	      return this.insertStmtHipoteses.executeInsert();
	   }
   
   /**
    * Insere um novo numero que identifica o requisito no projeto.
    * 
    * @param idProjeto O id do projeto
    * @param idRequisito O id do requisito
    * @param numero O numero do requisito
    */
   public long insertProjetoRequisito(int idProjeto, int idRequisito, int numero) { 
	 	  try {
	         this.insertStmtProjetoRequisitos.bindLong(1, idProjeto);
	         this.insertStmtProjetoRequisitos.bindLong(2, idRequisito);
	         this.insertStmtProjetoRequisitos.bindLong(3, numero);
	 	  }
	 	  catch (Exception e) {
	           Log.v("Insert projeto requisito", e.getMessage(), e);
	           e.printStackTrace();
	       } 
	      return this.insertStmtProjetoRequisitos.executeInsert();
	   }
       
   /**
    * Insere um novo numero que identifica o requisito atrasado no projeto.
    * 
    * @param idProjeto O id do projeto
    * @param idRequisito O id do requisito atrasado
    * @param numero O numero do requisito atrasado
    */
   public long insertProjetoRequisitoAtrasado(int idProjeto, int idRequisito, int numero) { 
	 	  try {
	         this.insertStmtProjetoRequisitosAtrasados.bindLong(1, idProjeto);
	         this.insertStmtProjetoRequisitosAtrasados.bindLong(2, idRequisito);
	         this.insertStmtProjetoRequisitosAtrasados.bindLong(3, numero);
	 	  }
	 	  catch (Exception e) {
	           Log.v("Insert projeto requisito atrasado", e.getMessage(), e);
	           e.printStackTrace();
	       } 
	      return this.insertStmtProjetoRequisitosAtrasados.executeInsert();
	   }
   
   /**
    * Insere uma nova dependencia do projeto.
    * 
    * @param idProjeto O id do projeto
    * @param idPrimeiroRequisito O id do primeiro requisito
    * @param idSegundoRequisito O id do segundo requisito
    */
   public long insertDependencia(int idProjeto, int idPrimeiroRequisito, int idSegundoRequisito) { 
	 	  try {
	         this.insertStmtDependencia.bindLong(1, idProjeto);
	         this.insertStmtDependencia.bindLong(2, idPrimeiroRequisito);
	         this.insertStmtDependencia.bindLong(3, idSegundoRequisito);
	 	  }
	 	  catch (Exception e) {
	           Log.v("Insert dependencia", e.getMessage(), e);
	           e.printStackTrace();
	       } 
	      return this.insertStmtDependencia.executeInsert();
	   }
   
   ////////////////////////////////////////////
   /////////////////// DELETE ////////////////
   //////////////////////////////////////////

   /**
    * Deleta todos os projetos.
    */
   public void deleteAllProjetos() {
	  try {
		  this.db.delete(TABLE_PROJETOS , null, null);
	  }
	  catch (Exception e) {
          Log.v("Delete todos os requisitos", e.getMessage(), e);
          e.printStackTrace();
      } 
   }
      
   /**
    * Deleta um projeto.
    * 
    * @param idProjeto O id do projeto a ser deletado.
    */
   public void deleteProjeto(int idProjeto) {
      String whereProjeto =  "id = ?";
      String idString = String.valueOf(idProjeto);
      String[] whereArgsProjeto = {idString};
      try {
    	  this.db.delete(TABLE_PROJETOS, whereProjeto, whereArgsProjeto);
      }
      catch (Exception e) {
          Log.v("Delete projeto", e.getMessage(), e);
          e.printStackTrace();
      } 
      //String whereRequisito =  "idprojeto = id?";
      //String idString = String.valueOf(idProjeto);
      //String[] whereArgsProjeto = {idString};
   }
   
   /**
    * Deleta um requisito pela sua descricao.
    * 
    * @param descricao A descricao do requisito
    */
   public void deleteRequisito(String descricao) {
	      String where =  "descricao = " + descricao;
	      try {
	      this.db.delete(TABLE_REQUISITOS, where, null);
	      }
	      catch (Exception e) {
	          Log.v("Delete requisito", e.getMessage(), e);
	          e.printStackTrace();
	      } 
	   }
   
   /**
    * Deleta um requisito pelo seu id.
    * 
    * @param id O id do requisito
    */
   public void deleteRequisito(int id) {
      String where =  "id = " + String.valueOf(id);
      try{
      this.db.delete(TABLE_REQUISITOS, where, null);
      }
      catch (Exception e) {
          Log.v("Delete requisito", e.getMessage(), e);
          e.printStackTrace();
      } 
   }
   
   /**
    * Deleta um requisito atrasado pelo seu id.
    * 
    * @param id O id do requisito atrasado
    */
   public void deleteRequisitoAtrasado(int id) {
	      String where =  "id = " + String.valueOf(id);
	      try{
	      this.db.delete(TABLE_REQUISITOS_ATRASADOS, where, null);
	      }
	      catch (Exception e) {
	          Log.v("Delete requisito_atr", e.getMessage(), e);
	          e.printStackTrace();
	      } 
	   }
   
   /**
    * Deleta um requisito atrasado pela sua descricao.
    * 
    * @param descricao A descricao do requisito atrasado
    */
   public void deleteRequisitoAtrasado(String descricao) {
	      String where =  "descricao = " + descricao;
	      try {
	      this.db.delete(TABLE_REQUISITOS_ATRASADOS, where, null);
	      }
	      catch (Exception e) {
	          Log.v("Delete requisito_atr", e.getMessage(), e);
	          e.printStackTrace();
	      } 
	   }
   
   /**
    * Deleta uma hipotese pelo seu id.
    * 
    * @param id O id da hipotese
    */
   public void deleteHipotese(int id)
   {
	   String where =  "id = " + String.valueOf(id);
	      try{
	      this.db.delete(TABLE_HIPOTESES, where, null);
	      }
	      catch (Exception e) {
	          Log.v("Delete hipotese", e.getMessage(), e);
	          e.printStackTrace();
	      }    
   }
     
   /**
    * Deleta uma dependencia do projeto.
    * 
    * @param idProjeto O id do projeto
    * @param idPrimeiroRequisito O id do primeiro requisito
    * @param idSegundoRequisito O id do segundo requisito
    */
   public void deleteDependencia(int idProjeto, int idPrimeiroRequisito, int idSegundoRequisito)
   {
	   String where =  "idprojeto = " + String.valueOf(idProjeto) + " AND idprimeirorequisito = " + idPrimeiroRequisito + " AND idsegundorequisito = " + idSegundoRequisito;
	      try{
	      this.db.delete(TABLE_DEPENDENCIAS, where, null);
	      }
	      catch (Exception e) {
	          Log.v("Delete dependencia", e.getMessage(), e);
	          e.printStackTrace();
	      } 
   }
   
   ////////////////////////////////////////////
   /////////////////// UPDATE ////////////////
   //////////////////////////////////////////
   
   /**
    * Atualiza um requisito.
    * 
    * @param id O id do requisito
    * @param descricaoNova A nova descricao do requisito
    * @param versaoNova A nova versao do requisito
    */
   public void updateRequisito(int id, String descricaoNova, int versaoNova) {
      ContentValues novaDescricao = new ContentValues();
      novaDescricao.put("descricao", descricaoNova); 
      ContentValues novaVersao = new ContentValues();
      novaVersao.put("versao", versaoNova); 
      String where = "id = ?"; 
      String idString = String.valueOf(id);
      String[] whereArgs = {idString};
      try {
    	  this.db.update(TABLE_REQUISITOS, novaDescricao, where, whereArgs);
    	  this.db.update(TABLE_REQUISITOS, novaVersao, where, whereArgs);
      }
      catch (Exception e) {
          Log.v("Update requisito", e.getMessage(), e);
          e.printStackTrace();
      } 
   }
   
   /**
    * Atualiza um requisito atrasado.
    * 
    * @param id O id do requisito atrasado
    * @param descricaoNova A nova descricao do requisito atrasado
    * @param versaoNova A nova versao do requisito atrasado
    */
   public void updateRequisitoAtrasado(int id, String descricaoNova, int versaoNova)
   {
	   ContentValues novaDescricao = new ContentValues();
	      novaDescricao.put("descricao", descricaoNova); 
	      ContentValues novaVersao = new ContentValues();
	      novaVersao.put("versao", versaoNova); 
	      String where = "id = ?"; 
	      String idString = String.valueOf(id);
	      String[] whereArgs = {idString};
	      try {
	    	  this.db.update(TABLE_REQUISITOS_ATRASADOS, novaDescricao, where, whereArgs);
	    	  this.db.update(TABLE_REQUISITOS_ATRASADOS, novaVersao, where, whereArgs);
	      }
	      catch (Exception e) {
	          Log.v("Update requisito", e.getMessage(), e);
	          e.printStackTrace();
	      } 
   }
   
   /**
    * Atualiza a prioridade de um requisito.
    * 
    * @param id O id do requisito
    * @param prioridadeNova A nova prioridade do requisito
    */
   public void updatePrioridadeRequisito(int id, int prioridadeNova)
   {
	   ContentValues novaPrioridade = new ContentValues();
	   novaPrioridade.put("prioridade", prioridadeNova); 
	   String where = "id = ?"; 
	   String idString = String.valueOf(id);
	   String[] whereArgs = {idString};
	   try {
	       this.db.update(TABLE_REQUISITOS, novaPrioridade, where, whereArgs);
	    }
	   catch (Exception e) {
	       Log.v("Update requisito", e.getMessage(), e);
	       e.printStackTrace();
	   } 
   }
   
   /**
    * Atualiza a prioridade de um requisito atrasado.
    * 
    * @param id O id do requisito atrasado
    * @param prioridadeNova A nova prioridade do requisito atrasado
    */
   public void updatePrioridadeRequisitoAtrasado(int id, int prioridadeNova)
   {
	   ContentValues novaPrioridade = new ContentValues();
	   novaPrioridade.put("prioridade", prioridadeNova); 
	   String where = "id = ?"; 
	   String idString = String.valueOf(id);
	   String[] whereArgs = {idString};
	   try {
	       this.db.update(TABLE_REQUISITOS_ATRASADOS, novaPrioridade, where, whereArgs);
	    }
	   catch (Exception e) {
	       Log.v("Update requisito", e.getMessage(), e);
	       e.printStackTrace();
	   }  
   }
   
   /**
    * Atualiza o autor de um requisito.
    * 
    * @param id O id do requisito
    * @param autorNovo O novo autor do requisito
    */
   public void updateAutorRequisito(int id, String autorNovo)
   {
	   ContentValues novoAutor = new ContentValues();
	   novoAutor.put("autor", autorNovo); 
	   String where = "id = ?"; 
	   String idString = String.valueOf(id);
	   String[] whereArgs = {idString};
	   try {
	       this.db.update(TABLE_REQUISITOS, novoAutor, where, whereArgs);
	    }
	   catch (Exception e) {
	       Log.v("Update requisito", e.getMessage(), e);
	       e.printStackTrace();
	   } 
   }
      
   /**
    * Atualiza o autor de um requisito atrasado.
    * 
    * @param id O id do requisito atrasado
    * @param autorNovo O novo autor do requisito atrasado
    */
   public void updateAutorRequisitoAtrasado(int id, String autorNovo)
   {
	   ContentValues novoAutor = new ContentValues();
	   novoAutor.put("autor", autorNovo); 
	   String where = "id = ?"; 
	   String idString = String.valueOf(id);
	   String[] whereArgs = {idString};
	   try {
	       this.db.update(TABLE_REQUISITOS_ATRASADOS, novoAutor, where, whereArgs);
	    }
	   catch (Exception e) {
	       Log.v("Update requisito", e.getMessage(), e);
	       e.printStackTrace();
	   } 
   }
   
   /**
    * Atualiza a experiencia do usuario.
    * 
    * @param idProjeto O id do projeto
    * @param experienciaNova A nova experiencia do usuario 
    */
   public void updateExperiencia(int idProjeto, int experienciaNova)
   {
	   ContentValues novaExperiencia = new ContentValues();
	   novaExperiencia.put("experiencia", experienciaNova); 
	   String where = "idprojeto = ?"; 
	   String idString = String.valueOf(idProjeto);
	   String[] whereArgs = {idString};
	   try {
	       this.db.update(TABLE_CARACTERISTICAS_USUARIO, novaExperiencia, where, whereArgs);
	    }
	   catch (Exception e) {
	       Log.v("Update experiencia", e.getMessage(), e);
	       e.printStackTrace();
	   }  
   }
   
   
   /**
    * Atualiza a pericia do usuario.
    * 
    * @param idProjeto O id do projeto
    * @param periciaNova A nova pericia do usuario 
    */
   public void updatePericia(int idProjeto, int periciaNova)
   {
	   ContentValues novaPericia = new ContentValues();
	   novaPericia.put("pericia", periciaNova); 
	   String where = "idprojeto = ?"; 
	   String idString = String.valueOf(idProjeto);
	   String[] whereArgs = {idString};
	   try {
	       this.db.update(TABLE_CARACTERISTICAS_USUARIO, novaPericia, where, whereArgs);
	    }
	   catch (Exception e) {
	       Log.v("Update pericia", e.getMessage(), e);
	       e.printStackTrace();
	   }  
   }
   
   /**
    * Atualiza o nivel de treinamento do usuario.
    * 
    * @param idProjeto O id do projeto
    * @param treinamentoNovo O novo nivel de treinamento do usuario
    */
   public void updateTreinamento(int idProjeto, int treinamentoNovo)
   {
	   ContentValues novoTreinamento = new ContentValues();
	   novoTreinamento.put("treinamento", treinamentoNovo); 
	   String where = "idprojeto = ?"; 
	   String idString = String.valueOf(idProjeto);
	   String[] whereArgs = {idString};
	   try {
	       this.db.update(TABLE_CARACTERISTICAS_USUARIO, novoTreinamento, where, whereArgs);
	    }
	   catch (Exception e) {
	       Log.v("Update treinamento", e.getMessage(), e);
	       e.printStackTrace();
	   }  
   }
      
   /**
    * Atualiza as informacoes do escopo do projeto.
    * 
    * @param id O id do projeto
    * @param tituloNovo O novo titulo do projeto
    * @param descricaoNova A nova descricao do projeto
    * @param beneficiosNovos Os novos beneficios do projeto
    * @param objetivosNovos Os novos objetivos do projeto
    * @param publicoAlvoNovo O novo publico-alvo do projeto
    */
   public void updateProjeto(int id, String tituloNovo, String descricaoNova, String beneficiosNovos, String objetivosNovos, String publicoAlvoNovo) {
	      ContentValues novoEscopo = new ContentValues();
	      novoEscopo.put("titulo", tituloNovo); 
	      novoEscopo.put("descricao",descricaoNova);
	      novoEscopo.put("beneficios",beneficiosNovos);
	      novoEscopo.put("objetivos",objetivosNovos);
	      novoEscopo.put("publicoalvo",publicoAlvoNovo);
	      String where = "id = ?"; 
	      String idString = String.valueOf(id);
	      String[] whereArgs = {idString};
	      try {
	    	  this.db.update(TABLE_PROJETOS, novoEscopo, where, whereArgs);
	      }
	      catch (Exception e) {
	          Log.v("Update projeto", e.getMessage(), e);
	          e.printStackTrace();
	      } 
	   }
   
   /**
    * Atualiza uma hipotese do projeto.
    * 
    * @param id O id da hipotese
    * @param descricaoNova A nova descricao da hipotese
    * @param versaoNova A nova versao da hipotese
    */
   public void updateHipotese(int id, String descricaoNova, int versaoNova)
   {
	   ContentValues novaDescricao = new ContentValues();
	      novaDescricao.put("descricao", descricaoNova); 
	      ContentValues novaVersao = new ContentValues();
	      novaVersao.put("versao", versaoNova); 
	      String where = "id = ?"; 
	      String idString = String.valueOf(id);
	      String[] whereArgs = {idString};
	      try {
	    	  this.db.update(TABLE_HIPOTESES, novaDescricao, where, whereArgs);
	    	  this.db.update(TABLE_HIPOTESES, novaVersao, where, whereArgs);
	      }
	      catch (Exception e) {
	          Log.v("Update hipotese", e.getMessage(), e);
	          e.printStackTrace();
	      } 
   }

   /**
    * Atualiza o autor de uma hipotese do projeto.
    * 
    * @param id O id da hipotese
    * @param autorNovo O novo autor da hipotese
    */
   public void updateAutorHipotese(int id, String autorNovo)
   {
	   ContentValues novoAutor = new ContentValues();
	   novoAutor.put("autor", autorNovo); 
	   String where = "id = ?"; 
	   String idString = String.valueOf(id);
	   String[] whereArgs = {idString};
	   try {
	       this.db.update(TABLE_HIPOTESES, novoAutor, where, whereArgs);
	    }
	   catch (Exception e) {
	       Log.v("Update autor hipotese", e.getMessage(), e);
	       e.printStackTrace();
	   } 
   }
     
   
   ////////////////////////////////////////////
   /////////////////// SELECT ////////////////
   //////////////////////////////////////////
  
   /**
    * Select de todos os titulos dos projetos.
    * 
    * @return Uma lista com os titulos de todos os projetos
    */
   public List<String> selectAllProjetos() {
	      List<String> list = new ArrayList<String>();
	      Cursor cursor = null;
	      
	      try {
	    	  cursor = this.db.query(TABLE_PROJETOS , new String[] { "titulo" },null, null, null, null, "id desc"); //os projetos mais recentes serao listados primeiro
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  do {
	    			  list.add(cursor.getString(0));
	    		  } while (cursor.moveToNext());
	    	  }
	      }
	      catch (Exception e) {
	          Log.v("Select todos os projetos", e.getMessage(), e);
	          e.printStackTrace();
	      } 
	      finally{
	    	  if (cursor != null && !cursor.isClosed()) {
	    		  cursor.close();
	    	  }
	      }
	      return list;
	   }

    public List<Projeto> selectProjetosRecentesComData()
    {
        Projeto projeto;
        List<Projeto> list = new ArrayList<Projeto>();
        Cursor cursor = null;

        try {
            cursor = this.db.query(TABLE_PROJETOS , new String[] { "titulo", "data" },null, null, null, null, "id desc"); //os projetos mais recentes serao listados primeiro
            if (cursor.moveToFirst())
            {
                int i =0;
                do {
                    projeto = new Projeto(cursor.getString(0), cursor.getString(1));
                    list.add(projeto);
                    i++;
                } while (cursor.moveToNext() && i < 5);
            }
        }
        catch (Exception e) {
            Log.v("Select projetos recentes", e.getMessage(), e);
            e.printStackTrace();
        }
        finally{
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }
   
   /**
    * Select de todos os titulos e datas dos projetos.
    * 
    * @return Uma lista com os titulos e datas de todos os projetos
    */
   public List<Projeto> selectAllProjetosComData()
   {
	   Projeto projeto;
	   List<Projeto> list = new ArrayList<Projeto>();
	   Cursor cursor = null;
	      
	      try {
	    	  cursor = this.db.query(TABLE_PROJETOS , new String[] { "titulo", "data" },null, null, null, null, "id desc"); //os projetos mais recentes serao listados primeiro
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  do {
	    			  projeto = new Projeto(cursor.getString(0), cursor.getString(1));
	    			  list.add(projeto);
	    		  } while (cursor.moveToNext());
	    	  }
	      }
	      catch (Exception e) {
	          Log.v("Select todos os requisitos", e.getMessage(), e);
	          e.printStackTrace();
	      } 
	      finally{
	    	  if (cursor != null && !cursor.isClosed()) {
	    		  cursor.close();
	    	  }
	      }
	      return list;
   }
   
   /**
    * Select de todas as datas dos projetos.
    * 
    * @return Uma lista com as datas de todos os projetos
    */
   public List<String> selectDatasAllProjetos()
   {
	   List<String> list = new ArrayList<String>();
	      Cursor cursor = null;
	      
	      try {
	    	  cursor = this.db.query(TABLE_PROJETOS , new String[] {"data" },null, null, null, null, "id desc"); //os projetos mais recentes serao listados primeiro
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  do {
	    			  list.add(cursor.getString(0));
	    		  } while (cursor.moveToNext());
	    	  }
	      }
	      catch (Exception e) {
	          Log.v("Select todos os requisitos", e.getMessage(), e);
	          e.printStackTrace();
	      } 
	      finally{
	    	  if (cursor != null && !cursor.isClosed()) {
	    		  cursor.close();
	    	  }
	      }
	      return list;
   }
   
   /**
    * Select de um requisito pela sua descricao.
    * 
    * @param descricao A descricao do requisito
    * @param idProjeto O id do projeto
    * @return O id do requisito
    */
   public int selectRequisitoPorDescricao(String descricao, int idProjeto) {
      String select = "SELECT id FROM " + TABLE_REQUISITOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
      Cursor cursor = null;
      int idRequisito = 0;
      
      try {
    	  cursor = this.db.rawQuery(select, null);
    	  if (cursor.moveToFirst()) 
    	  {
    		  idRequisito = cursor.getInt(0);
    	  }
      }
      catch (Exception e) {
          Log.v(select, e.getMessage(), e);
          e.printStackTrace();
      } finally {
          if (cursor != null && !cursor.isClosed())
          	cursor.close();
      }
      return idRequisito;      
   }
   
   /**
    * Select da data de um requisito.
    * 
    * @param descricao A descricao do requisito
    * @param idProjeto O id do projeto
    * @return A data do requisito
    */
   public String selectDataRequisito(String descricao, int idProjeto)
   {
	   String select = "SELECT data FROM " + TABLE_REQUISITOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      String dataRequisito = "";
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  dataRequisito = cursor.getString(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return dataRequisito;   
   }
   
   /**
    * Select da data de um requisito atrasado.
    * 
    * @param descricao A descricao do requisito atrasado
    * @param idProjeto O id do projeto
    * @return A data do requisito atrasado
    */
   public String selectDataRequisitoAtrasado(String descricao, int idProjeto)
   {
	   String select = "SELECT data FROM " + TABLE_REQUISITOS_ATRASADOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      String dataRequisito = "";
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  dataRequisito = cursor.getString(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return dataRequisito; 
   }
   
   /**
    * Select do autor de um requisito.
    * 
    * @param descricao A descricao do requisito
    * @param idProjeto O id do projeto
    * @return O autor do requisito
    */
   public String selectAutorRequisito(String descricao, int idProjeto)
   {
	   String select = "SELECT autor FROM " + TABLE_REQUISITOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	   Cursor cursor = null;
	   String autorRequisito = "";
	      
	   try {
	       cursor = this.db.rawQuery(select, null);
	       if (cursor.moveToFirst()) 
	    	{
	    	   autorRequisito = cursor.getString(0);
	    	}
	   }
	   catch (Exception e) {
	      Log.v(select, e.getMessage(), e);
	      e.printStackTrace();
	   } finally {
	      if (cursor != null && !cursor.isClosed())
	       cursor.close();
	   }
	  return autorRequisito;   
   }
   
   /**
    * Select do autor de um requisito atrasado.
    * 
    * @param descricao A descricao do requisito atrasado
    * @param idProjeto O id do projeto
    * @return O autor do requisito atrasado
    */
   public String selectAutorRequisitoAtrasado(String descricao, int idProjeto)
   {
	   String select = "SELECT autor FROM " + TABLE_REQUISITOS_ATRASADOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	   Cursor cursor = null;
	   String autorRequisito = "";
	      
	   try {
	       cursor = this.db.rawQuery(select, null);
	       if (cursor.moveToFirst()) 
	    	{
	    	   autorRequisito = cursor.getString(0);
	    	}
	   }
	   catch (Exception e) {
	      Log.v(select, e.getMessage(), e);
	      e.printStackTrace();
	   } finally {
	      if (cursor != null && !cursor.isClosed())
	       cursor.close();
	   }
	  return autorRequisito;
   }
   
   /**
    * Select da versao de um requisito.
    * 
    * @param descricao A descricao do requisito
    * @param idProjeto O id do projeto
    * @return A versao do requisito 
    */
   public int selectVersaoRequisito(String descricao, int idProjeto)
   {
	   String select = "SELECT versao FROM " + TABLE_REQUISITOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int versaoRequisito = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  versaoRequisito = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return versaoRequisito;  	   
   }
   
   /**
    * Select da versao de um requisito atrasado.
    * 
    * @param descricao A descricao do requisito atrasado
    * @param idProjeto O id do projeto
    * @return A versao do requisito atrasado
    */
   public int selectVersaoRequisitoAtrasado(String descricao, int idProjeto)
   {
	   String select = "SELECT versao FROM " + TABLE_REQUISITOS_ATRASADOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int versaoRequisito = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  versaoRequisito = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return versaoRequisito;
   }
   
   /**
    * Select da prioridade de um requisito.
    * 
    * @param descricao A descricao do requisito
    * @param idProjeto O id do projeto
    * @return A prioridade do requisito
    */
   public int selectPrioridadeRequisito(String descricao, int idProjeto)
   {
	   String select = "SELECT prioridade FROM " + TABLE_REQUISITOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int prioridadeRequisito = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  prioridadeRequisito = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return prioridadeRequisito;  	   
   }
   
   /**
    * Select da prioridade de um requisito atrasado.
    * 
    * @param descricao A descricao do requisito atrasado
    * @param idProjeto O id do projeto
    * @return A prioridade do requisito atrasado
    */
   public int selectPrioridadeRequisitoAtrasado(String descricao, int idProjeto)
   {
	   String select = "SELECT prioridade FROM " + TABLE_REQUISITOS_ATRASADOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int prioridadeRequisito = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  prioridadeRequisito = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return prioridadeRequisito;  	   
   }
   
   /**
    * Select de um requisito atrasado pela sua descricao.
    * 
    * @param descricao A descricao do requisito atrasado
    * @param idProjeto O id do projeto
    * @return O id do requisito atrasado
    */
   public int selectRequisitoAtrasadoPorDescricao(String descricao, int idProjeto) {
	      String select = "SELECT id FROM " + TABLE_REQUISITOS_ATRASADOS + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int idRequisito = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  idRequisito = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return idRequisito;      
	   }
     
   /**
    * Select de todos os requisitos pela suas descricoes.
    * 
    * @param idProjeto O id do projeto
    * @return Uma lista com as descricoes de todos os requisitos
    */
   public List<String> selectRequisito(int idProjeto) {
      List<String> list = new ArrayList<String>();
      String select = "SELECT descricao FROM " + TABLE_REQUISITOS + " WHERE (idprojeto = " + idProjeto + ")";
      Cursor cursor = null;
      
      try {
    	  cursor = this.db.rawQuery(select, null);
    	  if (cursor.moveToFirst()) 
    	  {
    		  do {
    			  list.add(cursor.getString(0));
    		  } while (cursor.moveToNext());
    	  }
      }
      catch (Exception e) {
          Log.v(select, e.getMessage(), e);
          e.printStackTrace();
      } finally {
          if (cursor != null && !cursor.isClosed())
          	cursor.close();
      }
      return list;
   }

   /**
    * Select de todos os requisitos atrasados pela suas descricoes.
    * 
    * @param idProjeto O id do projeto
    * @return Uma lista com as descricoes de todos os requisitos atrasados
    */
   public List<String> selectRequisitoAtrasado(int idProjeto) {
	      List<String> list = new ArrayList<String>();
	      String select = "SELECT descricao FROM " + TABLE_REQUISITOS_ATRASADOS + " WHERE (idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  do {
	    			  list.add(cursor.getString(0));
	    		  } while (cursor.moveToNext());
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return list;
	   }
   
   /**
    * Select de todos os detalhes de um projeto
    * 
    * @param titulo O titulo do projeto
    * @return Um objeto projeto com todas as suas informacoes
    */
   public Projeto selectProjeto(String titulo) {  
      String select = "SELECT * FROM " + TABLE_PROJETOS + " WHERE (titulo = '" + titulo + "')";
      Cursor cursor = null;
      Projeto projeto = null;
      
      try {
    	  cursor = this.db.rawQuery(select, null);
    	  if (cursor.moveToFirst()) 
    	  {
             projeto = new Projeto(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                     cursor.getString(4), cursor.getString(5));
             projeto.setId(cursor.getInt(0));
             projeto.setData(cursor.getString(6));
    	  }
      }
      catch (Exception e) {
          Log.v(select, e.getMessage(), e);
          e.printStackTrace();
      } finally {
          if (cursor != null && !cursor.isClosed())
          	cursor.close();
      }
      return projeto;
   }
   
   /**
    * Select do id de um projeto
    * 
    * @param titulo O titulo do projeto
    * @return O id do projeto
    */
   public int selectIdProjeto(String titulo) {  
      String select = "SELECT id FROM " + TABLE_PROJETOS + " WHERE (titulo = '" + titulo + "')";
      Cursor cursor = null;
      int idProjeto = 0;
      
      try {
    	  cursor = this.db.rawQuery(select, null);
    	  if (cursor.moveToFirst()) 
    	  {     
    		  idProjeto = cursor.getInt(0);
    	  }
      }
      catch (Exception e) {
          Log.v(select, e.getMessage(), e);
          e.printStackTrace();
      } finally {
          if (cursor != null && !cursor.isClosed())
          	cursor.close();
      }
      return idProjeto;
   }
   
   /**
    * Select que verifica se um projeto ja existe pelo seu titulo.
    * 
    * @param titulo O titulo do projeto
    * @return True se um projeto com o mesmo titulo ja existe
    */
   public boolean selectProjetoExistente(String titulo)
   {
       String select = "SELECT id FROM " + TABLE_PROJETOS + " WHERE (titulo = '" + titulo + "')";
       Cursor cursor = null;
       int cont = 0;
       try {
       	cursor = this.db.rawQuery(select, null);     
       	if (cursor.moveToFirst()) 
       	   {
       		do {
       			cont++;             
       		   }while (cursor.moveToNext());
       	   }
       }
       catch (Exception e) {
           Log.v(select, e.getMessage(), e);
           e.printStackTrace();
       } finally {
           if (cursor != null && !cursor.isClosed())
           	cursor.close();
       }
       return (cont > 0);
   }
   
   /**
    * Select do nivel de experiencia do usuario.
    * 
    * @param idProjeto O id do projeto
    * @return O nivel de experiencia do usuario
    */
   public int selectExperienciaUsuario(int idProjeto)
   {
	   String select = "SELECT experiencia FROM " + TABLE_CARACTERISTICAS_USUARIO + " WHERE (idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int experiencia = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  experiencia = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return experiencia;     
   }
   
   /**
    * Select do nivel de pericia do usuario.
    * 
    * @param idProjeto O id do projeto
    * @return O nivel de pericia do usuario
    */
   public int selectPericiaUsuario(int idProjeto)
   {
	   String select = "SELECT pericia FROM " + TABLE_CARACTERISTICAS_USUARIO + " WHERE (idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int pericia = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  pericia = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return pericia;     
   }
   
   /**
    * Select do nivel de treinamento do usuario.
    * 
    * @param idProjeto O id do projeto
    * @return O nivel de treinamento do usuario
    */
   public int selectTreinamentoUsuario(int idProjeto)
   {
	   String select = "SELECT treinamento FROM " + TABLE_CARACTERISTICAS_USUARIO + " WHERE (idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int treinamento = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  treinamento = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return treinamento;     
   }
   
   /**
    * Select todas as hipoteses de um projeto
    * 
    * @param idProjeto O id do projeto
    * @return Uma lista com a descricao de todas as hipoteses do projeto
    */
   public List<String> selectHipotese(int idProjeto)
   {
	      List<String> list = new ArrayList<String>();
	      String select = "SELECT descricao FROM " + TABLE_HIPOTESES + " WHERE (idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  do {
	    			  list.add(cursor.getString(0));
	    		  } while (cursor.moveToNext());
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return list;
   }
   
   /**
    * Select de uma hipotese por sua descricao.
    * 
    * @param descricao A descricao da hipotese
    * @param idProjeto O id do projeto
    * @return O id da hipotese
    */
   public int selectHipotesePorDescricao(String descricao, int idProjeto)
   {
	   String select = "SELECT id FROM " + TABLE_HIPOTESES + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int idHipotese = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  idHipotese = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return idHipotese;     
   }
   
   /**
    * Select da data de uma hipotese.
    * 
    * @param descricao A descricao da hipotese
    * @param idProjeto O id do projeto
    * @return A data da hipotese
    */
   public String selectDataHipotese(String descricao, int idProjeto)
   {
	   String select = "SELECT data FROM " + TABLE_HIPOTESES + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      String dataHipotese = "";
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  dataHipotese = cursor.getString(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return dataHipotese;      	
   }

   /**
    * Select da versao de uma hipotese.
    * 
    * @param descricao A descricao da hipotese
    * @param idProjeto O id do projeto
    * @return A versao da hipotese
    */
   public int selectVersaoHipotese(String descricao, int idProjeto)
   {
	   String select = "SELECT versao FROM " + TABLE_HIPOTESES + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	      Cursor cursor = null;
	      int versaoHipotese = 0;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  versaoHipotese = cursor.getInt(0);
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return versaoHipotese;  	   
   }

   /**
    * Select do autor de uma hipotese.
    * 
    * @param descricao A descricao da hipotese
    * @param idProjeto O id do projeto
    * @return O autor da hipotese
    */
   public String selectAutorHipotese(String descricao, int idProjeto)
   {
	   String select = "SELECT autor FROM " + TABLE_HIPOTESES + " WHERE (descricao = '" + descricao + "' AND idprojeto = " + idProjeto + ")";
	   Cursor cursor = null;
	   String autorHipotese = "";
	      
	   try {
	       cursor = this.db.rawQuery(select, null);
	       if (cursor.moveToFirst()) 
	    	{
	    	   autorHipotese = cursor.getString(0);
	    	}
	   }
	   catch (Exception e) {
	      Log.v(select, e.getMessage(), e);
	      e.printStackTrace();
	   } finally {
	      if (cursor != null && !cursor.isClosed())
	       cursor.close();
	   }
	  return autorHipotese;
   }
   
   /**
    * Select do numero de um requisito.
    * 
    * @param descricao A descricao do requisito
    * @param idProjeto O id do projeto
    * @return O numero do requisito
    */
   public int selectNumeroRequisito(String descricao, int idProjeto)
   {
	   int idRequisito = selectRequisitoPorDescricao(descricao, idProjeto);
	   String select = "SELECT numero FROM " + TABLE_PROJETO_REQUISITOS + " WHERE (idrequisito = " + idRequisito + " AND idprojeto = " + idProjeto + ")";
	   
	   Cursor cursor = null;
	   int numero = 0;
	      
	   try {
	       cursor = this.db.rawQuery(select, null);
	       if (cursor.moveToFirst()) 
	    	{
	    	   numero = cursor.getInt(0);
	    	}
	   }
	   catch (Exception e) {
	      Log.v(select, e.getMessage(), e);
	      e.printStackTrace();
	   } finally {
	      if (cursor != null && !cursor.isClosed())
	       cursor.close();
	   }
	  return numero;   
   }
   
   /**
    * Select do numero de um requisito.
    * 
    * @param idProjeto O id do projeto
    * @param idRequisito O id do requisito
    * @return O numero do requisito
    */
   public int selectNumeroRequisito(int idProjeto, int idRequisito)
   {
	   String select = "SELECT numero FROM " + TABLE_PROJETO_REQUISITOS + " WHERE (idrequisito = " + idRequisito + " AND idprojeto = " + idProjeto + ")";
	   
	   Cursor cursor = null;
	   int numero = 0;
	      
	   try {
	       cursor = this.db.rawQuery(select, null);
	       if (cursor.moveToFirst()) 
	    	{
	    	   numero = cursor.getInt(0);
	    	}
	   }
	   catch (Exception e) {
	      Log.v(select, e.getMessage(), e);
	      e.printStackTrace();
	   } finally {
	      if (cursor != null && !cursor.isClosed())
	       cursor.close();
	   }
	  return numero;  
   }
   
   /**
    * Select do numero de um requisito atrasado.
    * 
    * @param descricao A descricao do requisito atrasado
    * @param idProjeto O id do projeto
    * @return O numero do requisito atrasado
    */
   public int selectNumeroRequisitoAtrasado(String descricao, int idProjeto)
   {
	   int idRequisito = selectRequisitoAtrasadoPorDescricao(descricao, idProjeto);
	   String select = "SELECT numero FROM " + TABLE_PROJETO_REQUISITOS_ATRASADOS + " WHERE (idrequisito = " + idRequisito + " AND idprojeto = " + idProjeto + ")";
	   
	   Cursor cursor = null;
	   int numero = 0;
	      
	   try {
	       cursor = this.db.rawQuery(select, null);
	       if (cursor.moveToFirst()) 
	    	{
	    	   numero = cursor.getInt(0);
	    	}
	   }
	   catch (Exception e) {
	      Log.v(select, e.getMessage(), e);
	      e.printStackTrace();
	   } finally {
	      if (cursor != null && !cursor.isClosed())
	       cursor.close();
	   }
	  return numero;   
   }
   
   /**
    * Select de todos os numeros dos requisitos.
    * 
    * @param idProjeto O id do projeto
    * @return Uma lista com todos os numeros dos requisitos
    */
   public List<Integer> selectAllNumerosRequisitos(int idProjeto)
   {
	   List<Integer> list = new ArrayList<Integer>();
	      Cursor cursor = null;
	      
	      try {
	    	  cursor = this.db.query(TABLE_PROJETO_REQUISITOS , new String[] { "numero" },"idprojeto =?", new String[] {String.valueOf(idProjeto)}, null, null, null); 
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  do {
	    			  list.add( cursor.getInt(0));
	    		  } while (cursor.moveToNext());
	    	  }
	      }
	      catch (Exception e) {
	          Log.v("Select todos os numeros requisitos", e.getMessage(), e);
	          e.printStackTrace();
	      } 
	      finally{
	    	  if (cursor != null && !cursor.isClosed()) {
	    		  cursor.close();
	    	  }
	      }
	      return list;
   }
   
   /**
    * Select de um requisito pelo seu numero.
    * 
    * @param idProjeto O id do projeto
    * @param numero O numero do requisito
    * @return O id do requisito
    */
   public int selectRequisitoPorNumero(int idProjeto, int numero)
   {
	   String select = "SELECT idrequisito FROM " + TABLE_PROJETO_REQUISITOS + " WHERE (numero = " + numero + " AND idprojeto = " + idProjeto + ")";
	   
	   Cursor cursor = null;
	   int idRequisito = 0;
	      
	   try {
	       cursor = this.db.rawQuery(select, null);
	       if (cursor.moveToFirst()) 
	    	{
	    	   idRequisito = cursor.getInt(0);
	    	}
	   }
	   catch (Exception e) {
	      Log.v(select, e.getMessage(), e);
	      e.printStackTrace();
	   } finally {
	      if (cursor != null && !cursor.isClosed())
	       cursor.close();
	   }
	  return idRequisito; 
   }
   
   /**
    * Select de todas as dependencias de um projeto.
    * 
    * @param idProjeto O id do projeto
    * @return Uma lista de objetos Dependencia com todas as informacoes sobre as dependencias.
    */
   public List<Dependencia> selectDependencias(int idProjeto)
   {
	   List<Dependencia> list = new ArrayList<Dependencia>();
	   
	   String select = "SELECT idprimeirorequisito, idsegundorequisito FROM " + TABLE_DEPENDENCIAS + " WHERE (idprojeto = " + idProjeto + ")";

	   Cursor cursor = null;
	      
	      try {
	    	  cursor = this.db.rawQuery(select, null);
	    	  if (cursor.moveToFirst()) 
	    	  {
	    		  do {
	    			  list.add(new Dependencia(cursor.getInt(0), cursor.getInt(1)));
	    		  } while (cursor.moveToNext());
	    	  }
	      }
	      catch (Exception e) {
	          Log.v(select, e.getMessage(), e);
	          e.printStackTrace();
	      } finally {
	          if (cursor != null && !cursor.isClosed())
	          	cursor.close();
	      }
	      return list;
   }
   
   /**
    * Retorna o id do ultimo projeto criado.
    * 
    * @return O id do ultimo projeto criado
    */
   public int getIdUltimoProjeto()
    {
        String select = "SELECT id FROM " + TABLE_PROJETOS;
        int id = 0;
        Cursor cursor = null;
        try {
        	cursor = this.db.rawQuery(select, null);        
        	if (cursor.moveToFirst()) 
        	{
        		do {
        			id = cursor.getInt(0);             
        		}while (cursor.moveToNext());
        	}
        }
        catch (Exception e) {
            Log.v(select, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
            	cursor.close();
        }
        return id;
    }
   
   /**
    * Retorna o id do ultimo requisito adicionado.
    * 
    * @return O id do ultimo requisito adicionado
    */
   public int getIdUltimoRequisito()
   {
	   String select = "SELECT id FROM " + TABLE_REQUISITOS;
       int id = 0;
       Cursor cursor = null;
       try {
       	cursor = this.db.rawQuery(select, null);        
       	if (cursor.moveToFirst()) 
       	{
       		do {
       			id = cursor.getInt(0);             
       		}while (cursor.moveToNext());
       	}
       }
       catch (Exception e) {
           Log.v(select, e.getMessage(), e);
           e.printStackTrace();
       } finally {
           if (cursor != null && !cursor.isClosed())
           	cursor.close();
       }
       return id;
   }
   
   /**
    * Retorna o id do ultimo requisito atrasado adicionado.
    * 
    * @return O id do ultimo requisito atrasado adicionado
    */
   public int getIdUltimoRequisitoAtrasado()
   {
	   String select = "SELECT id FROM " + TABLE_REQUISITOS_ATRASADOS;
       int id = 0;
       Cursor cursor = null;
       try {
       	cursor = this.db.rawQuery(select, null);        
       	if (cursor.moveToFirst()) 
       	{
       		do {
       			id = cursor.getInt(0);             
       		}while (cursor.moveToNext());
       	}
       }
       catch (Exception e) {
           Log.v(select, e.getMessage(), e);
           e.printStackTrace();
       } finally {
           if (cursor != null && !cursor.isClosed())
           	cursor.close();
       }
       return id;
   }
   
   /**
    * Retorna o numero do ultimo requisito adicionado.
    * 
    * @param idProjeto O id do projeto
    * @return O numero do ultimo requisito adicionado
    */
   public int getNumeroUltimoRequisito(int idProjeto)
   {
	   String select = "SELECT numero FROM " + TABLE_PROJETO_REQUISITOS + " WHERE idprojeto = " + idProjeto;
       int numero = 0;
       Cursor cursor = null;
       try {
       	cursor = this.db.rawQuery(select, null);        
       	if (cursor.moveToFirst()) 
       	{
       		do {
       			numero = cursor.getInt(0);             
       		}while (cursor.moveToNext());          
       	}
       }
       catch (Exception e) {
           Log.v(select, e.getMessage(), e);
           e.printStackTrace();
       } finally {
           if (cursor != null && !cursor.isClosed())
           	cursor.close();
       }
       return numero;
   }
   
   /**
    * Retorna o numero do ultimo requisito atrasado adicionado.
    * 
    * @param idProjeto O id do projeto
    * @return O numero do ultimo requisito atrasado adicionado
    */
   public int getNumeroUltimoRequisitoAtrasado(int idProjeto)
   {
	   String select = "SELECT numero FROM " + TABLE_PROJETO_REQUISITOS_ATRASADOS + " WHERE idprojeto = " + idProjeto;
       int numero = 0;
       Cursor cursor = null;
       try {
       	cursor = this.db.rawQuery(select, null);        
       	if (cursor.moveToFirst()) 
       	{
       		do {
       			numero = cursor.getInt(0);             
       		}while (cursor.moveToNext());            
       	}
       }
       catch (Exception e) {
           Log.v(select, e.getMessage(), e);
           e.printStackTrace();
       } finally {
           if (cursor != null && !cursor.isClosed())
           	cursor.close();
       }
       return numero;
   }
    
   /**
    * Classe que implementa metodos extendidos por SQLiteOpenHelper,
    * essenciais para o funcionamento do banco de dados do aplicativo.
    * 
    * @author Vinicius Rodrigues Silva <vinicinhopolaco@yahoo.com.br>
    * @version 1.0
    */
   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }
      
      //O Titulo do projeto e chave primaria.
      @Override
      public void onCreate(SQLiteDatabase db) {
    	 try{ 
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PROJETOS  + "(id INTEGER PRIMARY KEY autoincrement, titulo TEXT, descricao TEXT, beneficios TEXT, objetivos TEXT, publicoalvo TEXT, data TEXT)");
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_REQUISITOS  + "(id INTEGER PRIMARY KEY autoincrement, descricao TEXT, data TEXT, prioridade INTEGER, versao INTEGER, autor TEXT, idprojeto INTEGER, FOREIGN KEY(idprojeto) REFERENCES " + TABLE_PROJETOS + "(id) ON DELETE CASCADE)");   
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_REQUISITOS_ATRASADOS  + "(id INTEGER PRIMARY KEY autoincrement, descricao TEXT,  data TEXT, prioridade INTEGER, versao INTEGER, autor TEXT, idprojeto INTEGER, FOREIGN KEY(idprojeto) REFERENCES " + TABLE_PROJETOS + "(id) ON DELETE CASCADE)");
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CARACTERISTICAS_USUARIO  + "(id INTEGER PRIMARY KEY autoincrement, experiencia INTEGER DEFAULT '2',  pericia INTEGER DEFAULT '2', treinamento INTEGER DEFAULT '0', idprojeto INTEGER, FOREIGN KEY(idprojeto) REFERENCES " + TABLE_PROJETOS + "(id) ON DELETE CASCADE)");
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HIPOTESES  + "(id INTEGER PRIMARY KEY autoincrement, descricao TEXT, data TEXT, versao INTEGER, autor TEXT, idprojeto INTEGER, FOREIGN KEY(idprojeto) REFERENCES " + TABLE_PROJETOS + "(id) ON DELETE CASCADE)");
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PROJETO_REQUISITOS  + "(id INTEGER PRIMARY KEY autoincrement, numero INTEGER, idprojeto INTEGER, idrequisito INTEGER, FOREIGN KEY(idprojeto) REFERENCES " + TABLE_PROJETOS + "(id) ON DELETE CASCADE, FOREIGN KEY(idrequisito) REFERENCES " + TABLE_REQUISITOS + "(id) ON DELETE CASCADE)");
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PROJETO_REQUISITOS_ATRASADOS  + "(id INTEGER PRIMARY KEY autoincrement, numero INTEGER, idprojeto INTEGER, idrequisito INTEGER, FOREIGN KEY(idprojeto) REFERENCES " + TABLE_PROJETOS + "(id) ON DELETE CASCADE, FOREIGN KEY(idrequisito) REFERENCES " + TABLE_REQUISITOS_ATRASADOS + "(id) ON DELETE CASCADE)");
         db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DEPENDENCIAS  + "(id INTEGER PRIMARY KEY autoincrement, idprojeto INTEGER, idprimeirorequisito INTEGER, idsegundorequisito INTEGER, FOREIGN KEY(idprojeto) REFERENCES " + TABLE_PROJETOS + "(id) ON DELETE CASCADE, FOREIGN KEY(idprimeirorequisito) REFERENCES " + TABLE_REQUISITOS + "(id) ON DELETE CASCADE, FOREIGN KEY(idsegundorequisito) REFERENCES " + TABLE_REQUISITOS + "(id) ON DELETE CASCADE)");
    	 }
    	 catch (Exception e) {
             Log.v("Create table", e.getMessage(), e);
             e.printStackTrace();
         } 
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	 db.beginTransaction();
    	 
    	 try {
    		 Log.v("Banco de dados", "Atualizando banco de dados..."); 
    		 onCreate(db);
    		
    		 //db.execSQL("ALTER table " + TABLE_REQUISITOS_ATRASADOS + " ADD COLUMN titulo TEXT");
    		    		 
    		 
    		 //Atualizar caracteristicas do usuario para os projetos ja existentes.
    		 //db.execSQL(String.format( "INSERT INTO %s (%s) SELECT %s from %s", TABLE_CARACTERISTICAS_USUARIO, "idprojeto", "id", TABLE_PROJETOS));
    		    		 
    		 db.setTransactionSuccessful();
             
    	 }
    	 catch (Exception e) {
             Log.v("Erro ao atualizar banco de dados", e.getMessage(), e);
             e.printStackTrace();
    	 }
    	 finally
    	 {	
    		 db.endTransaction();
    	 }    	 
      }
      
      @Override
      public void onOpen(SQLiteDatabase db) {
         super.onOpen(db);
         if (!db.isReadOnly()) {
         // Enable foreign key constraints
         db.execSQL("PRAGMA foreign_keys = ON;");
         }
     }
   }
    
   /**
    * Retorna uma instancia desta classe, implementando o padrao Singleton.
    * 
    * @param context O contexto onde sera usada a instancia
    * @return A instancia da classe
    */
   public static BDGerenciador getInstance(Context context)
        {
            if (instancia == null)
            {
                instancia = new BDGerenciador(context);
            }
            return instancia;
        }
   
}

