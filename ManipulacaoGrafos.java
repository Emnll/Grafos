import java.io.*;
import java.util.*;

public class ManipulacaoGrafos {
    List<Aresta>[][] matrizAdjacencia;
    ArrayList<LinkedList<Aresta>> listaAdjacencia = new ArrayList<>();
    int quantidadeArestas;
    int quantidadeVertices;
    List<Boolean> visitados;

    public void lerGrafo(String nomeArquivo){
        try {
            File myObj = new File(nomeArquivo);
            Scanner myReader = new Scanner(myObj);

            String primeiraLinha = myReader.nextLine();
            quantidadeVertices = Integer.parseInt(primeiraLinha);
            matrizAdjacencia = new List[quantidadeVertices][quantidadeVertices];

            for (int i = 0; i < quantidadeVertices; i++) {
                for (int j = 0; j < quantidadeVertices; j++) {
                    matrizAdjacencia[i][j] = new ArrayList<>();
                }
            }

            for (int i = 0; i < quantidadeVertices; i++) {
                listaAdjacencia.add(new LinkedList<>());
            }

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();
                if (data.isEmpty()) continue;

                String[] linha = data.split(" ");
                if (linha.length < 2) continue;

                quantidadeArestas++;

                int origem = Integer.parseInt(linha[0]);
                int destino = Integer.parseInt(linha[1]);
                Float peso = (linha.length == 3) ? Float.parseFloat(linha[2]) : null;

                Aresta aresta = new Aresta(destino, peso);
                Aresta aresta2 = new Aresta(origem, peso);

                matrizAdjacencia[origem - 1][destino - 1].add(aresta);
                matrizAdjacencia[destino - 1][origem - 1].add(aresta2);

                listaAdjacencia.get(origem - 1).add(aresta);
                listaAdjacencia.get(destino - 1).add(aresta2);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Houve um erro");
            e.printStackTrace();
        }
    }

    public void relatorioGrafo(String nomeArquivo){
        int tamGrafo = quantidadeVertices;
        float somaGraus = 0;
        Map<Integer, Integer> contagemGrau = new HashMap<>();

        try{
            FileWriter myObj = new FileWriter(nomeArquivo);
            myObj.write("# n = " + tamGrafo);
            myObj.write("\n# m = " + quantidadeArestas);

            for (int i = 0; i < tamGrafo; i++) {
                int grau = listaAdjacencia.get(i).size();
                somaGraus += grau;
                contagemGrau.put(grau, contagemGrau.getOrDefault(grau, 0) + 1);
            }

            myObj.write("\n# d_medio = " + somaGraus/tamGrafo);

            int grauMaximo = Collections.max(contagemGrau.keySet());

            for (int grau = 0; grau <= grauMaximo; grau++) {
                int contagem = contagemGrau.getOrDefault(grau, 0);
                double probabilidade = (double) contagem / tamGrafo;
                myObj.write("\n" + grau + " " + String.format("%.2f", probabilidade));
            }

            myObj.close();

        } catch (IOException e) {
            System.out.println("Houve um erro");
            e.printStackTrace();
        }

    }

    public void representacaoGrafo(int escolha){
        switch (escolha) {
            case 1:
                for (int i = 0; i < quantidadeVertices; i++) {
                    System.out.printf("%d: %s\n", (i + 1),listaAdjacencia.get(i).toString());
                }
                break;
            case 2:
                System.out.print("     ");
                for (int i = 0; i < quantidadeVertices; i++) {
                    System.out.printf("%-10s", i + 1);
                }
                System.out.println("\n---------------------------------------------");

                for (int i = 0; i < quantidadeVertices; i++) {
                    System.out.printf("%-2d | ", i + 1);
                    for (int j = 0; j < quantidadeVertices; j++) {
                        if (matrizAdjacencia[i][j].isEmpty()) {
                            System.out.printf("%-10s", "[-]");
                        } else {
                            Aresta firstAresta = matrizAdjacencia[i][j].get(0);
                            if (firstAresta.getPeso() == null) {
                                System.out.printf("%-10s", "[" + matrizAdjacencia[i][j].size() + "]");
                            } else {
                                StringBuilder pesos = new StringBuilder("[");
                                for (int k = 0; k < matrizAdjacencia[i][j].size(); k++) {
                                    Aresta a = matrizAdjacencia[i][j].get(k);
                                    pesos.append(String.format(Locale.US, "%.1f", a.getPeso()));
                                    if (k != matrizAdjacencia[i][j].size() - 1) {
                                        pesos.append(",");
                                    }
                                }
                                pesos.append("]");
                                System.out.printf("%-10s", pesos);
                            }
                        }
                    }
                    System.out.println();
                }

        }
    }

    public void buscaGrafo(int escolha, int noInicial, String nomeArquivo){

        List<Integer> pais = new ArrayList<>(Collections.nCopies(quantidadeVertices, -1));
        List<Integer> niveis = new ArrayList<>(Collections.nCopies(quantidadeVertices, -1));
        visitados = new ArrayList<>(Collections.nCopies(quantidadeVertices, false));
        noInicial--;
        switch (escolha) {
            case 1:
                System.out.println("DFS - Lista de Visita: ");
                dfs(noInicial, -1, 0, pais, niveis);
                escreverBusca(pais, niveis, nomeArquivo, "DFS");

                break;
            case 2:
                System.out.println("BFS - Lista de Visita: ");
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
        System.out.print((no+1) + " ");
        while(!queue.isEmpty()){
            int atual = queue.poll();
            for(Aresta viz: listaAdjacencia.get(atual)){
                int vizinho = viz.getVertice() - 1;
                if(!visitados.get(vizinho)){
                    System.out.print((vizinho+1) + " ");
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
        System.out.print((no+1) + " ");

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
            for (int i = 0; i < quantidadeVertices; i++) {
                int pai = pais.get(i) == -1 ? -1 : pais.get(i) + 1;
                escrita.printf("%6d | %6d | %5d%n", i + 1, pai, niveis.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void componentesConexos(String saida) {
        visitados = new ArrayList<>(Collections.nCopies(quantidadeVertices, false));
        List<List<Integer>> componentes = new ArrayList<>();
        List<Integer> tamanhos = new ArrayList<>();

        for (int i = 0; i < quantidadeVertices; i++) {
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
            writer.println("Componentes Conexos");
            writer.println("Número de componentes: " + componentes.size());
            writer.println();
            for (int idx : indices) {
                writer.println("Componente " + (idx + 1) + ":");
                writer.println("Tamanho: " + tamanhos.get(idx));
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

    private int pesoNegativo(){
        for (int i = 0; i < quantidadeVertices; i++) {
            for(Aresta aresta : listaAdjacencia.get(i)){
                if(aresta.getPeso() < 0){
                    return 3;
                }
            }
        }
        return 2;
    }

    private int peso(){
        for (int i = 0; i < quantidadeVertices; i++) {
            for(Aresta aresta : listaAdjacencia.get(i)){
                if(aresta.getPeso() == null){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        }

        return -1;
    }

    private void bfsDist(int noInicial, int noFinal){
        List<Float> dist = new ArrayList<>(Collections.nCopies(quantidadeVertices, Float.MAX_VALUE));
        List<Integer> pai = new ArrayList<>(Collections.nCopies(quantidadeVertices, -1));
        visitados = new ArrayList<>(Collections.nCopies(quantidadeVertices, false));
        Queue<Integer> fila = new LinkedList<>();

        dist.set(noInicial-1, 0.0f);
        pai.set(noInicial-1, 0);
        fila.add(noInicial-1);

        if(noFinal == noInicial){
            System.out.println("O nó inicial e final são iguais :/");
            return;
        }

        while (!fila.isEmpty()) {
            int no = fila.poll();

            if (!visitados.get(no)) {
                visitados.set(no, true); // 0 true
                for(Aresta aresta : listaAdjacencia.get(no)){
                    int vizinho = aresta.getVertice() - 1;
                    Float distanciaAtt = dist.get(no) + 1;

                    if(dist.get(vizinho) == Float.MAX_VALUE){
                        dist.set(vizinho, distanciaAtt);
                        pai.set(vizinho, no+1);
                        fila.add(vizinho);
                    }
                }
            }

        }
        printaDistePai(dist, pai, noInicial, noFinal);
    }

    private void dijkstra(int noInicial, int noFinal){
        List<Float> dist = new ArrayList<>(Collections.nCopies(quantidadeVertices, Float.MAX_VALUE));
        List<Integer> pai = new ArrayList<>(Collections.nCopies(quantidadeVertices, -1));
        PriorityQueue<Aresta> heap = new PriorityQueue<>(Comparator.comparingDouble(Aresta::getPeso));

        dist.set(noInicial - 1, 0.0f);
        pai.set(noInicial - 1 , 0);
        heap.add(new Aresta(noInicial, 0.0f));

        while (!heap.isEmpty()) {
            Aresta aresta = heap.poll();
            int atual = aresta.getVertice() - 1;
            Float distanciaAtual = aresta.getPeso();

            if (distanciaAtual > dist.get(atual)) {
                continue;
            }

            if (atual + 1 == noFinal) {
                break;
            }

            for(Aresta aresta1 : listaAdjacencia.get(atual)){
                int vizinho = aresta1.getVertice() - 1;
                Float peso = aresta1.getPeso();
                Float distanciaPeso = distanciaAtual + peso;

                if(distanciaPeso < dist.get(vizinho)){
                    dist.set(vizinho, distanciaPeso);
                    pai.set(vizinho, atual+1);
                    heap.add(new Aresta(aresta1.getVertice(), distanciaPeso));
                }
            }
        }

        printaDistePai(dist, pai, noInicial, noFinal);

    }

    private void bellmanFord(int noInicial, int noFinal) {
        List<Float> dist = new ArrayList<>(Collections.nCopies(quantidadeVertices, Float.MAX_VALUE));
        List<Integer> pai = new ArrayList<>(Collections.nCopies(quantidadeVertices, -1));

        dist.set(noInicial-1, 0.0f);
         pai.set(noInicial-1, 0);

        for (int i = 0; i < quantidadeVertices; i++) {
            boolean atualizado = false;

            for (int u = 0; u < quantidadeVertices; u++) {
                if (dist.get(u) == Float.MAX_VALUE) {
                    continue;
                }
                for (Aresta aresta : listaAdjacencia.get(u)) {
                    int v = aresta.getVertice() - 1;
                    Float peso = aresta.getPeso();
                    if (dist.get(u) + peso < dist.get(v)) {
                        if (i == quantidadeVertices - 1) {
                            System.out.println("Há um ciclo negativo! Não é possível verificar a distância");
                            return;
                        }
                        dist.set(v, dist.get(u) + peso);
                        pai.set(v, u+1);
                        atualizado = true;
                    }
                }
            }
            if (!atualizado && i < quantidadeVertices -1) {
                break;
            }
        }
        printaDistePai(dist, pai, noInicial, noFinal);
    }

    private void printaDistePai(List<Float> dist, List<Integer> pai, int noInicial, int noFinal){
        System.out.println(pai);
        System.out.println(dist);
        if (dist.get(noFinal-1) == Float.MAX_VALUE) {
            System.out.println("O nó final não tem conexão com o nó inicial");
        } else{
            System.out.println("Distância mínima: " + dist.get(noFinal-1));


            LinkedList<Integer> path = new LinkedList<>();
            int atual = noFinal - 1;
            path.addFirst(noFinal);
            atual = pai.get(atual);

            while(atual > 0){
                path.addFirst(atual);
                atual = pai.get(atual-1);
            }

            System.out.print("Caminho: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
        }
    }



    public void distanciaGrafo(int noInicial, int noFinal) {
        int escolha = peso();
        if (escolha <= 0) {
            escolha = pesoNegativo();
        }
        System.out.println(escolha);

        switch (escolha) {
            case 1:
                bfsDist(noInicial, noFinal);
                break;
            case 2:
                dijkstra(noInicial,noFinal);
                break;
            case 3:
                bellmanFord(noInicial, noFinal);
                break;
            default:
                System.out.println("Opa deu erro");

        }


    }
}
