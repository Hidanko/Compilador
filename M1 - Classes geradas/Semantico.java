package executargramatica;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Semantico implements Constants
{
    List<Symbol> tabela;
    String tipo;
    Stack<String> escopos;
    int param;
    int scopeCounter;

    public Semantico() {
        this.param = 0;
        this.scopeCounter = 0;
        this.tabela = new ArrayList<>();
        this.escopos = new Stack<>();
        escopos.push("global");
    }
    
    public void executeAction(int action, Token token)	throws SemanticError
    {
        
        switch (action) {
            /**
             * case 1 até 8 trata do escopo e da inserção dos simbolos nas tabelas
             */
            case 1: // pega o tipo da variável/vetor/função/parametro na declaração para logo após inserir
                tipo = token.getLexeme();
                break;
            case 2: // insere uma variável na tabela
                if (inserirTabela(new Symbol(token.getLexeme(), tipo, false, false, escopos.peek(), false, 0, false, false)))
                    System.out.println("variável " + token.getLexeme() + " inserida com sucesso");
                else
                    System.out.println("variável " + token.getLexeme() + " já existe");
                break;
            case 3: // insere um vetor na tabela
                if (inserirTabela(new Symbol(token.getLexeme(), tipo, false, false, escopos.peek(), false, 0, true, false)))
                    System.out.println("vetor " + token.getLexeme() + " inserido com sucesso");
                else 
                    System.out.println("vetor " + token.getLexeme() + " já existe");
                break;
            case 4: // insere uma função na tabela e muda o escopo atual caso a inserção tenha sucesso;
                if(inserirTabela(new Symbol(token.getLexeme(), tipo, false, false, escopos.peek(), false, 0, false, true))){
                    escopos.push(token.getLexeme());
                    System.out.println("funcao " + token.getLexeme() + " foi inserida com sucesso");
                } else
                    System.out.println("funcao " + token.getLexeme() + " já existe");
                break;
            case 5: // inseri um parametro de função na tabela obs: adicionei <type> antes da função e mudei a regra de produção <param> na gramatica para facilitar a inserção na tabela;
                if (inserirTabela(new Symbol(token.getLexeme(), tipo, false, false, escopos.peek(), true, ++param, false, false)))
                    System.out.println("parametro " + token.getLexeme() + " foi inserido com sucesso");
                else 
                    System.out.println("parametro " + token.getLexeme() + " já existe");
                break;
            case 6: // quando fechar os parenteses dos parametros da função, zera o contador da posição do parametro
                param = 0;
                break;
            case 7: // quando fecha chaves, atualiza o escopo;
                escopos.pop();
                break;
            case 8:  // muda o escopo para desvio condicional e laços de repetição
                    // adicionei o scope counter pra diferenciar o nome, porque no caso de existirem desvios condicionais ou 
                    //  laços de repetição aninhados, o nome do escopo não muda;
                escopos.push("scope" + scopeCounter++);
                break;
                
            /**
             * 
             */
            case 9: // verifica se uma variável sendo utilizada já foi declarada, e seta ela como inicializada;
                if(buscaIdEscopo(action,token.getLexeme()))
                    System.out.println("var " + token.getLexeme() + " existe e foi setada como inicializada");
                else 
                    System.out.println("Aviso! variável " + token.getLexeme() + " não foi declarada");
                break;
            case 10: // verifica se uma variável sendo utilizada já foi declarada e seta ela como usada;
                if(buscaIdEscopo(action, token.getLexeme()))
                    System.out.println("var " + token.getLexeme() + " existe e foi setada como usada");
                else 
                    System.out.println("Aviso! variável " + token.getLexeme() + " não foi declarada");
                break;
            case 11:
                if (buscaIdEscopo(action, token.getLexeme())) 
                    System.out.println("Função " + token.getLexeme() + " existe e foi setada como usada");
                else 
                    System.out.println("Aviso! função " + token.getLexeme() + " não foi declarada");
        }
    }	
    
    /**
     * checa unicidade de variável, levando em conta o escopo
     * fiz de maneira que funções podem ter o mesmo nome de variáveis mas as variáveis, os vetores e os parâmetros não podem ter o mesmo nome
     */
    public boolean inserirTabela (Symbol novoSimbolo){
        boolean flag = false;
        
        for (String escopo : escopos) {
            //System.out.println("escopo sendo checado: "+escopo);
            for(Symbol simbolo : tabela) {
                if(simbolo.getId().equals(novoSimbolo.getId()) && simbolo.isFunc() == novoSimbolo.isFunc() && simbolo.getEscopo().equals(escopo)) {
                    flag = true;
                }
            }
        }
        
        if(!flag) {
            tabela.add(novoSimbolo);
            return true;
        } else {
            return false;
        }
    }
    
    // verifica se uma variável ou função foi declarada antes de ser usada;
    // Action serve como um filtro para funções e variáveis
    public boolean buscaIdEscopo (int action, String id) {
        
        for (String escopo : escopos) {
            for (Symbol simbolo : tabela) {
                // checagem para variáveis
                if ((action == 9 || action == 10) && simbolo.getId().equals(id) && simbolo.isFunc() == false && simbolo.getEscopo().equals(escopo)) {
                    
                    if (action == 9)
                        simbolo.setIni(true);
                    else if (action == 10)
                        simbolo.setUsada(true);
                    
                    return true;
                    
                } else // checagem para função
                    if (action == 11 && simbolo.getId().equals(id) && simbolo.isFunc() == true && simbolo.getEscopo().equals(escopo)) {
                        if (id.equals(escopos.peek()))
                            return false;
                        simbolo.setUsada(true);
                        return true;  
                    }
            }
        }
        return false;
    }
}
//tabela.add(new Symbol(token.getLexeme(), tipo, true, tipo, true, GO_TO, true, true));