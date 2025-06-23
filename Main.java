public class Main {
    public static void main(String[] args) {
        ManipulacaoGrafos grafo = new ManipulacaoGrafos();

        //Escolher qual arquivo deseja usar: 1 -> sem peso, 2 -> com peso, 3 -> com peso negativo
        String formato = "3";

        // Função para ler e guardaar a estrutura do grafo
        grafo.lerGrafo(String.format("grafo%s.txt", formato));

        // Relatório contendo as propriedades do grafo, como quantidade de arestas, quantidade de vértices, grau médio e distribuição empírica dos graus
        grafo.relatorioGrafo(String.format("relatorioGrafo%s.txt", formato));

        // Representação da lista de adjacência
        grafo.representacaoGrafo(1);

        // Representação da matriz de adjacência
        grafo.representacaoGrafo(2);

        // Busca em DFS começando do nó no segundo argumento
        grafo.buscaGrafo(1,8,String.format("buscaDFSGrafo%s.txt", formato) );

        // Busca em BFS começando do nó no segundo argumento
        grafo.buscaGrafo(2,8,String.format("buscaBFSGrafo%s.txt", formato) );

        // Função para mapear os componentes
        grafo.componentesConexos(String.format("componentesGrafo%s.txt", formato));

        // Distância do nó do argumento 1 para o argumento 2
        grafo.distanciaGrafo(8, 5);
    }
}
