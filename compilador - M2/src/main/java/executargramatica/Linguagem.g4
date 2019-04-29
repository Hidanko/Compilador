grammar Linguagem;

parse:
         inicio
     ;

inicio:
          (soma NL)*
      ;

soma:
        {
            List<String> dados = new ArrayList<String>();
        }
        
        INTEIRO 
        { 
            dados.add($INTEIRO.text); 
        }
        
        (   
            OPERADOR
            { 
                dados.add($OPERADOR.text); 
            } 
            
            INTEIRO 
            { 
                dados.add($INTEIRO.text); 
            }
        )+
       
        {
            Integer resultado = Integer.parseInt(dados.get(0));
            
            dados.remove(0);
            
            do
            {
                String operador = dados.get(0);
                int num = Integer.parseInt(dados.get(1));
                
                if (operador.equals("+"))
                {
                    resultado = resultado + num;
                }                 
                else if (operador.equals("*"))
                {
                    resultado = resultado * num;
                }
                
                dados.remove(0);
                dados.remove(0);
            }
            while (!dados.isEmpty());
            
            System.out.println("Resultado = " + resultado);
        }
    ;

OPERADOR:
            '+' | '*'
        ;


INTEIRO:
           [0-9]+
       ;
           
NL:
          '\r'? '\n' 
      ;

ESPACO :
           [ \t]+ -> skip 
       ;
      