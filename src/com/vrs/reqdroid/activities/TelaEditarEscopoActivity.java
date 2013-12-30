/*
 * Este arquivo esta sujeito aos termos e condicoes definidos
 * no arquivo 'LICENSE.txt, o qual e parte deste pacote de codigo fonte.
 */

package com.vrs.reqdroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vrs.reqdroid.R;
import com.vrs.reqdroid.dao.BDGerenciador;
import com.vrs.reqdroid.models.Projeto;
import com.vrs.reqdroid.util.AlertsUtil;
import com.vrs.reqdroid.util.ProjetoUtils;


/**
 * Implementa a tela de editar escopo do projeto do aplicativo.
 *
 * @author Vinicius Rodrigues Silva <vinicius.rodsilva@gmail.com>
 * @version 1.0
 */
public class TelaEditarEscopoActivity extends ActionBarActivity{
	
	private EditText eTitulo;
    private EditText eDescricao;
    private EditText eBeneficios;
    private EditText eObjetivos;
    private EditText ePublicoAlvo;
    private String tituloProjeto;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.editar_escopo);
        preencheInformacoesAtuais();
        salvaAlteracoesDoProjeto();
        descartaAlteracoesDoProjeto(); 
    }

    /**
     * Descarta as alteracoes realizadas nos itens do escopo do projeto.
     */
   private void descartaAlteracoesDoProjeto() 
   {
      Button bDescartar = (Button)findViewById(R.id.botaoDescartarAlteracoesProjeto);   
       
       bDescartar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            finish();    
            Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_descartar_alteracoes, Toast.LENGTH_SHORT);
            toast.show();
           }
       });
    }
   
   /**
    * Salva as alteracoes feitas no escopo do projeto.
    */
   private void salvaAlteracoesDoProjeto()
   {
        final Button button = (Button) findViewById(R.id.botaoAlterarProjeto); 
        final String tituloAtual = ProjetoUtils.getTituloProjeto();
        
        button.setOnClickListener(new View.OnClickListener() {  
            public void onClick(View v) 
            {
              getTextViews();  
              if (camposPreenchidos())
              {  
                  if (!(projetoExistente(eTitulo.getText().toString()))
                       || (eTitulo.getText().toString().equals(tituloAtual)))
                  {
                    Projeto projeto = modificaProjetoExistente();
                    updateProjeto(projeto.getId(),projeto.getTitulo(),projeto.getDescricao(),projeto.getBeneficios(),
                                  projeto.getObjetivos(),projeto.getPublicoAlvo());
                    ProjetoUtils.setTituloProjeto(projeto.getTitulo());
                    finish();
                  }
                  else  if ((projetoExistente(eTitulo.getText().toString()))
                            && (!eTitulo.getText().toString().equals(tituloAtual)))
                    AlertsUtil.exibeAlertaProjetoExistente(TelaEditarEscopoActivity.this);
              }
              else 
               AlertsUtil.exibeAlertaCamposNaoPreenchidos(TelaEditarEscopoActivity.this);
            }  
        });  
    }

   /**
    * Cria um novo objeto projeto com os novos detalhes do escopo atualizado.
    *
    * @return O projeto criado
    */
   private Projeto modificaProjetoExistente()
    {
        TelaEscopoActivity.getProjeto().setTitulo(eTitulo.getText().toString());
        TelaEscopoActivity.getProjeto().setDescricao(eDescricao.getText().toString());
        TelaEscopoActivity.getProjeto().setBeneficios(eBeneficios.getText().toString());
        TelaEscopoActivity.getProjeto().setObjetivos(eObjetivos.getText().toString());
        TelaEscopoActivity.getProjeto().setPublicoAlvo(ePublicoAlvo.getText().toString());

        return TelaEscopoActivity.getProjeto();
    }
   
   /**
    * Atualiza o projeto no banco de dados com as novas informacoes do escopo.
    * 
    * @param id O id do projeto
    * @param titulo O titulo do projeto
    * @param descricao A descricao do projeto
    * @param beneficios Os beneficios do projeto
    * @param objetivos Os objetivos do projeto
    * @param publicoAlvo O publico-alvo do projeto
    */
   private void updateProjeto(int id, String titulo, String descricao, String beneficios, String objetivos, String publicoAlvo) 
    {       
        BDGerenciador.getInstance(this).updateProjeto(id, titulo, descricao, beneficios, objetivos, publicoAlvo);
    }  
   
   /**
    * Verifica se o projeto ja existe, ou seja, se um projeto com o mesmo titulo ja foi cadastrado.
    * 
    * @param titulo O titulo do projeto
    * @return true se o projeto ja existe
    */
   private boolean projetoExistente(String titulo)
   {
     return (BDGerenciador.getInstance(this).selectProjetoExistente(titulo));
   }
   
   /**
    * Preenche as informacoes atuais do escopo na tela.
    */
   private void preencheInformacoesAtuais()
   {
	  getTextViews();
      Projeto projeto = TelaEscopoActivity.getProjeto();

	  eTitulo.setText(projeto.getTitulo());
	  eDescricao.setText(projeto.getDescricao());
	  eBeneficios.setText(projeto.getBeneficios());
	  eObjetivos.setText(projeto.getObjetivos());
	  ePublicoAlvo.setText(projeto.getPublicoAlvo());
   }

    /**
     * Atribui os valores dos textos dos itens do escopo na interface grafica.
     */
    private void getTextViews()
    {
        eTitulo = (EditText)findViewById(R.id.novotextotitulosistema);
        eDescricao = (EditText)findViewById(R.id.novotextodescricao);
        eBeneficios = (EditText)findViewById(R.id.novotextobenefícios);
        eObjetivos = (EditText)findViewById(R.id.novotextoobjetivos);
        ePublicoAlvo = (EditText)findViewById(R.id.novotextopublicoalvo);
    }

    /**
     * Verifica se todos os campos do espoco foram preenchidos.
     *
     * @return true, se todos os campos foram preenchidos
     */
    private boolean camposPreenchidos()
    {
        return !(("".equals(eTitulo.getText().toString()) || eTitulo.getText().toString() == null) ||
                ("".equals(eDescricao.getText().toString()) || eDescricao.getText().toString() == null) ||
                ("".equals(eBeneficios.getText().toString()) || eBeneficios.getText().toString() == null) ||
                ("".equals(eObjetivos.getText().toString()) || eObjetivos.getText().toString() == null) ||
                ("".equals(ePublicoAlvo.getText().toString()) || ePublicoAlvo.getText().toString() == null));
    }
}


