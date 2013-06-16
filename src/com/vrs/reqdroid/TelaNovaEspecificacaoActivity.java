/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENCA.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid;

import java.text.DateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.modelo.Projeto;
import com.vrs.reqdroid.util.MsgAlerta;

/**
 * Implementa a tela de criacao de um novo projeto.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaNovaEspecificacaoActivity extends Activity {

    private static EditText eTitulo; 
    private static EditText eDescricao;
    private static EditText eBeneficios; 
    private static EditText eObjetivos;
    private static EditText ePublicoAlvo;
    private static String tituloProjeto;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.novaespecificacao);
        salvaProjeto();
        descartaProjeto(); 
    }

    /**
     * Descarta o projeto criado ao clicar no botao "Descartar projeto".
     */
    private void descartaProjeto() 
    {
      Button bDescartar = (Button)findViewById(R.id.botaoDescartarProjeto);   
       
       bDescartar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            finish();    
            Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_descartar_projeto, Toast.LENGTH_SHORT);
            toast.show();
           }
       });
    }
    
    /**
     * Salva o projeto criado ao clicar no botao "Salvar".
     */
    private void salvaProjeto() 
    {
        final Button button = (Button) findViewById(R.id.botaoSalvarProjeto);   
        
        button.setOnClickListener(new View.OnClickListener() {  
            public void onClick(View v) 
            {
              getEditTexts();  
              if (camposPreenchidos())
              {  
                  if (!projetoExistente(eTitulo.getText().toString()))
                  {
                    Projeto projeto = criaProjeto();
                    adicionaProjeto(projeto.getTitulo(),projeto.getDescricao(),projeto.getBeneficios(),projeto.getObjetivos(),projeto.getPublicoAlvo(), projeto.getData());                    
                    tituloProjeto = projeto.getTitulo();
                    int idProjeto = (BDGerenciador.getInstance(TelaNovaEspecificacaoActivity.this).getIdUltimoProjeto());
                    configuraCaracteristicasDoUsuarioPadrao(idProjeto);
                    projeto.setId(idProjeto);
                    TelaVisaoGeralActivity.setIdProjeto(idProjeto);
                    TelaVisaoGeralActivity.setTituloProjeto(tituloProjeto);
                    abreTelaVisaoGeral();
                    finish();
                  }
                  else
                    MsgAlerta.exibeAlertaProjetoExistente(TelaNovaEspecificacaoActivity.this);     
              }
              else 
               MsgAlerta.exibeAlertaCamposNaoPreenchidos(TelaNovaEspecificacaoActivity.this);   
            }  
        });  
    }
    
    /**
     * Abre a tela de visao geral apos o projeto ser salvo. 
     */
    private void abreTelaVisaoGeral()
    {
       final Intent i = new Intent(TelaNovaEspecificacaoActivity.this, TelaVisaoGeralActivity.class);  
       startActivity(i); 
    }
   
    /**
     * Atribui as informacoes preenchidas pelo usuario nos campos do formulario. 
     */
    private void getEditTexts()
    {
      eTitulo = (EditText)findViewById(R.id.textotitulosistema);     
      eDescricao = (EditText)findViewById(R.id.textodescricao);
      eBeneficios = (EditText)findViewById(R.id.textobenefícios);
      eObjetivos = (EditText)findViewById(R.id.textoobjetivos);
      ePublicoAlvo = (EditText)findViewById(R.id.textopublicoalvo);
    }
     
    /**
     * Verifica se todos os campos do formulario foram preenchidos.
     * 
     * @return true se todos os campos foram preenchidos
     */
    private static boolean camposPreenchidos()
    {
      return !(("".equals(eTitulo.getText().toString()) || eTitulo.getText().toString() == null) ||
              ("".equals(eDescricao.getText().toString()) || eDescricao.getText().toString() == null) ||              
              ("".equals(eBeneficios.getText().toString()) || eBeneficios.getText().toString() == null) ||     
              ("".equals(eObjetivos.getText().toString()) || eObjetivos.getText().toString() == null) ||     
              ("".equals(ePublicoAlvo.getText().toString()) || ePublicoAlvo.getText().toString() == null));
    }
        
    /**
     * Cria um novo objeto projeto com as informacoes dos campos digitadas pelo usuario.
     * 
     * @return o objeto projeto
     */
    private static Projeto criaProjeto()
    {
        Projeto projeto = new Projeto(eTitulo.getText().toString(),eDescricao.getText().toString(),
                eBeneficios.getText().toString(),eObjetivos.getText().toString(),ePublicoAlvo.getText().toString());
        projeto.setData(getDataATual());
        return projeto;
    }
   
    /**
     * Insere o novo projeto criado no banco de dados do aplicativo.
     * 
     * @param titulo O titulo do projeto
     * @param descricao A descricao do projeto
     * @param beneficios Os beneficios do projeto
     * @param objetivos Os objetivos do projeto
     * @param publicoAlvo O publico alvo do projeto
     * @param data A data do projeto
     */
    private void adicionaProjeto(String titulo, String descricao, String beneficios, String objetivos, String publicoAlvo, String data) 
    {       
        BDGerenciador.getInstance(this).insertProjeto(titulo, descricao, beneficios, objetivos, publicoAlvo, data);
    }  
    
    /**
     * Insere os valores padrao das caracteristicas do usuario ao novo projeto:
     * (experiencia media, pericia media, nao necessita de treinamento para o usuario).
     * 
     * @param idProjeto O id do projeto
     */
    private void configuraCaracteristicasDoUsuarioPadrao(int idProjeto)
    {       
        BDGerenciador.getInstance(this).insertCaracteristicasUsuario(2, 2, 0, idProjeto);
    }  
    
    /**
     * Retorna a data atual para salvar o projeto.
     * 
     * @return A data atual
     */
    private static String getDataATual()
    {
 	   return DateFormat.getDateInstance().format(new Date());
    }
   
    /**
     * Verifica se um projeto com o mesmo titulo ja existe.
     * 
     * @param titulo O titulo do projeto
     * @return true se um projeto com o mesmo titulo ja existe
     */
    private boolean projetoExistente(String titulo)
    {
     return (BDGerenciador.getInstance(this).selectProjetoExistente(titulo));
    }
 
    /**
     * Retorna o titulo do projeto.
     * 
     * @return O titulo do projeto
     */
    public static String getTituloProjeto() {
        return tituloProjeto;
    }
}
