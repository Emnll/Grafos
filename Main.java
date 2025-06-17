public class Main {
    public static void main(String[] args) {
        ManipulacaoGrafos grafo = new ManipulacaoGrafos();


        grafo.lerGrafo("grafo1.txt");
        grafo.relatorioGrafo("grafo2.txt");
        grafo.representacaoGrafo(2);
        grafo.buscaGrafo(2,2, "grafo3.txt");
        grafo.componentesConexos("grafo4.txt");
    }
}
