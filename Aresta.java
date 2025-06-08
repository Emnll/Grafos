public class Aresta {
    int vertice;
    int peso;

    public Aresta(int vertice, int peso){
        this.vertice = vertice;
        this.peso = peso;
    }

    public int getVertice(){
        return vertice;
    }

    public int getPeso(){
        return peso;
    }

    @Override
    public String toString() {
        return vertice + " " + peso;
    }
}
