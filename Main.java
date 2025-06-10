public class Main {
    public static void main(String[] args) {
        ManipulacaoGrafos grafo = new ManipulacaoGrafos();

        grafo.lerGrafo("grafo.txt");
        grafo.relatorioGrafo("grafo2.txt");
        grafo.representacaoGrafo(2);
        grafo.buscaGrafo(1,3, "grafo3.txt");
    }
}
