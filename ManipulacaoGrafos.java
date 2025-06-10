import java.io.*;
import java.util.*;

public class ManipulacaoGrafos {
    List<Aresta>[][] matrizAdjacencia;
    ArrayList<LinkedList<Aresta>> listaAdjacencia = new ArrayList<>();
    int quantidadeArestas;
    int tamGrafo;
    List<Boolean> visitados;

    public void lerGrafo(String nomeArquivo){
        try {
            File myObj = new File(nomeArquivo);
            Scanner myReader = new Scanner(myObj);

            String primeiraLinha = myReader.nextLine();
            tamGrafo = Integer.parseInt(primeiraLinha);
            matrizAdjacencia = new List[tamGrafo][tamGrafo];

            for (int i = 0; i < tamGrafo; i++) {
                for (int j = 0; j < tamGrafo; j++) {
                    matrizAdjacencia[i][j] = new ArrayList<>();
                }
            }

            listaAdjacencia = new ArrayList<>(Collections.nCopies(tamGrafo, new LinkedList<>()));


            while (myReader.hasNextLine()) {
                quantidadeArestas++;
                String data = myReader.nextLine();
                String[] linha = data.split(" ");
                List<Integer> resultado = new ArrayList<>();
                for(int i = 0; i < 3; i++) {
                    resultado.add(Integer.parseInt(linha[i]));
                }

                Aresta aresta = new Aresta(resultado.get(0), resultado.get(2));
                Aresta aresta2 = new Aresta(resultado.get(1), resultado.get(2));

                if(!resultado.get(0).equals(resultado.get(1))) {
                    matrizAdjacencia[resultado.get(0) - 1][resultado.get(1) - 1].add(aresta);
                }

                matrizAdjacencia[resultado.get(1) - 1][resultado.get(0) - 1].add(aresta2);


                listaAdjacencia.get(resultado.get(0) - 1).add(aresta2);
                listaAdjacencia.get(resultado.get(1) - 1).add(aresta);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Houve um erro");
            e.printStackTrace();
        }
    }

    public List<Aresta>[][] getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public void relatorioGrafo(String nomeArquivo){
        int tamGrafo = listaAdjacencia.size();
        float somaGraus = 0;
        Map<Integer, Integer> contagemGrau = new HashMap<>();

        try{
            FileWriter myObj = new FileWriter(nomeArquivo);
            myObj.write("# n = " + tamGrafo);
            myObj.write("\n# m = " + quantidadeArestas);

            for (int i = 0; i < tamGrafo; i++) {
                somaGraus =+ listaAdjacencia.get(i).size();
                int grau = listaAdjacencia.get(i).size();
                contagemGrau.put(grau,contagemGrau.getOrDefault(grau, 0) + 1 );
            }

            myObj.write("\n# d_medio = " + somaGraus/tamGrafo);

            for (Map.Entry<Integer, Integer> entry : contagemGrau.entrySet()) {
                int grau = entry.getKey();
                int contagem = entry.getValue();
                double probabilidade = (double) contagem / tamGrafo;

                myObj.write("\n" + grau + " " + String.format("%.2f", probabilidade));
            }
            myObj.close();

        }catch (IOException e) {
            System.out.println("Houve um erro");
            e.printStackTrace();
        }

    }

    public void representacaoGrafo(int escolha){


        switch (escolha) {
            case 1:
                for (int i = 0; i < tamGrafo; i++) {
                    System.out.printf("%d: %s\n", (i + 1),listaAdjacencia.get(i).toString());
                }
                break;
            case 2:
                System.out.print("     ");
                for (int i = 0; i < tamGrafo; i++) {
                    System.out.printf("%-10s", i);
                }
                System.out.println("\n---------------------------------------------");

                // Imprime cada linha da matriz
                for (int i = 0; i < tamGrafo; i++) {
                    System.out.printf("%-2d | ", i); // Imprime o cabeçalho da linha
                    for (int j = 0; j < tamGrafo; j++) {
                        String cellContent = matrizAdjacencia[i][j].isEmpty() ? "[-]" : matrizAdjacencia[i][j].toString();
                        System.out.printf("%-10s", cellContent);
                    }
                    System.out.println();
                }

        }
    }

    public void buscaGrafo(int escolha, int noInicial, String nomeArquivo){

        List<Integer> pais = new ArrayList<>(Collections.nCopies(tamGrafo, -1));
        List<Integer> niveis = new ArrayList<>(Collections.nCopies(tamGrafo, -1));
        visitados = new ArrayList<>(Collections.nCopies(tamGrafo, false));
        noInicial--;
        switch (escolha) {
            case 1:

                dfs(noInicial, -1, 0, pais, niveis);
                escreverBusca(pais, niveis, nomeArquivo, "DFS");

                break;
            case 2:
                bfs(noInicial, pais, niveis);
                escreverBusca(pais, niveis, nomeArquivo, "BFS");
                break;
            default:
                System.out.println("Escolha outra opção");
                break;



        }
    }

    private void bfs(int no, List<Integer> pais, List<Integer> niveis){
        Queue<Integer> queue = new LinkedList<>();

        queue.add(no);
        visitados.set(no, true);
        niveis.set(no, 0);

        while(!queue.isEmpty()){
            int atual = queue.poll();
            for(Aresta viz: listaAdjacencia.get(atual)){
                int vizinho = viz.getVertice() - 1;
                if(!visitados.get(vizinho)){
                    visitados.set(vizinho, true);
                    queue.add(vizinho);
                    pais.set(vizinho, atual);
                    niveis.set(vizinho, niveis.get(atual) + 1);
                }
            }
        }


    }

    private void dfs(int no, int pai, int nivel, List<Integer> pais, List<Integer> niveis) {
        visitados.set(no, true);
        pais.set(no, pai);
        niveis.set(no, nivel);
        System.out.print(no + " ");

        for (Aresta viz : listaAdjacencia.get(no)) {
            int vizinho = viz.getVertice() - 1;
            if (!visitados.get(vizinho)) {
                dfs(vizinho, no, nivel + 1, pais, niveis);
            }
        }
    }

    private void escreverBusca(List<Integer> pais, List<Integer> niveis, String arquivo, String tipoBusca){
        try (PrintWriter escrita = new PrintWriter(new File(arquivo))) {
            escrita.println(tipoBusca + " Arvore de Busca");
            escrita.println("Vertice | Pai | Nivel");
            escrita.println("-------|--------|------");
            for (int i = 0; i < tamGrafo; i++) {
                int pai = pais.get(i) == -1 ? -1 : pais.get(i) + 1;
                escrita.printf("%6d | %6d | %5d%n", i + 1, pai, niveis.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void componentesConexos(String saida) {
        visitados = new ArrayList<>(Collections.nCopies(tamGrafo, false));
        List<List<Integer>> componentes = new ArrayList<>();
        List<Integer> tamanhos = new ArrayList<>();

        for (int i = 0; i < tamGrafo; i++) {
            if (!visitados.get(i)) {
                List<Integer> componente = new ArrayList<>();
                dfsComponentes(i, componente);
                componentes.add(componente);
                tamanhos.add(componente.size());
            }
        }

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < componentes.size(); i++) indices.add(i);
        indices.sort((a, b) -> Integer.compare(tamanhos.get(b), tamanhos.get(a)));

        try (PrintWriter writer = new PrintWriter(new File(saida))) {
            writer.println("Connected Components");
            writer.println("Number of components: " + componentes.size());
            writer.println();
            for (int idx : indices) {
                writer.println("Component " + (idx + 1) + ":");
                writer.println("Size: " + tamanhos.get(idx));
                writer.print("Vertices: ");
                List<Integer> comp = componentes.get(idx);
                for (int i = 0; i < comp.size(); i++) {
                    writer.print((comp.get(i) + 1)); // Adjust for 1-based indexing
                    if (i < comp.size() - 1) writer.print(", ");
                }
                writer.println("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void dfsComponentes(int atual, List<Integer> componente) {
        visitados.set(atual, true);
        componente.add(atual);
        for (Aresta aresta : listaAdjacencia.get(atual)) {
            int vizinho = aresta.getVertice() - 1;
            if (!visitados.get(vizinho)) {
                dfsComponentes(vizinho, componente);
            }
        }
    }
}
