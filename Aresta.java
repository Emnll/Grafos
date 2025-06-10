public class Aresta {
    int vertice;
    float peso;

    public Aresta(int vertice, int peso){
        this.vertice = vertice;
        this.peso = peso;
    }

    public int getVertice(){
        return vertice;
    }

    public float getPeso(){
        return peso;
    }

    @Override
    public String toString() {
        return vertice + " " + peso;
    }
}
