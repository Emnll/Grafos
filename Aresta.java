public class Aresta {
    int vertice;
    Float peso;

    public Aresta(int vertice, Float peso){
        this.vertice = vertice;
        this.peso = peso;
    }

    public int getVertice(){
        return vertice;
    }

    public Float getPeso(){
        return peso;
    }

    @Override
    public String toString() {
        return vertice + (peso != null ? " " + peso : "");
    }
}
