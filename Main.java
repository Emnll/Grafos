public class Main {
    public static void main(String[] args) {
        ManipulacaoGrafos grafo = new ManipulacaoGrafos();


        grafo.lerGrafo("grafo.txt");
        //grafo.relatorioGrafo("grafo2.txt");
        grafo.representacaoGrafo(1);
        //grafo.buscaGrafo(2,2, "grafo3.txt");
        //grafo.componentesConexos("grafo4.txt");

        //No bfs o caminho escolhido para o heap é a primeira aresta que a lista tem, ou seja, no texto será a primeira aresta daquele vértice
        // No 1 ele escolhe o 6 pois o 6 é a primeira
        grafo.distanciaGrafo(8, 5);
    }
}
