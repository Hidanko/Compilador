package executargramatica;

/**
 *
 * @author Daniel
 */
public class Symbol {


    private String id;
    private String tipo;
    private boolean ini;
    private boolean usada;
    private String escopo;
    private boolean param;
    private int pos;
    private boolean vet;
    private boolean func;

    public Symbol(String id, String tipo, boolean ini, boolean usada, String escopo, boolean param, int pos, boolean vet, boolean func) {
        this.id = id;
        this.tipo = tipo;
        this.ini = ini;
        this.usada = usada;
        this.escopo = escopo;
        this.param = param;
        this.pos = pos;
        this.vet = vet;
        this.func = func;
        
      //  printSymbol();
    }
    
    public void printSymbol () {
        System.out.print(this.id + " |  ");
        System.out.print(this.tipo + " |  ");
        System.out.print(this.ini + " |  ");
        System.out.print(this.usada + " |  ");
        System.out.print(this.escopo + " |  ");
        System.out.print(this.param + " |  ");
        System.out.print(this.pos + " |  ");
        System.out.print(this.vet + " |  ");
        System.out.println(this.func + " |  ");
    }
    public String getId() {
        return id;
    }

    public boolean isFunc() {
        return func;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isIni() {
        return ini;
    }

    public boolean isUsada() {
        return usada;
    }

    public boolean isParam() {
        return param;
    }

    public int getPos() {
        return pos;
    }

    public boolean isVet() {
        return vet;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setIni(boolean ini) {
        this.ini = ini;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public void setParam(boolean param) {
        this.param = param;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setVet(boolean vet) {
        this.vet = vet;
    }

    public void setFunc(boolean func) {
        this.func = func;
    }
    
    public String getEscopo() {
        return escopo;
    }
}