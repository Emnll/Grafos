import java.io.*;
import java.util.*;

public class ManipulacaoGrafos {
    List<Aresta>[][] matrizAdjacencia;
    ArrayList<LinkedList<Aresta>> listaAdjacencia = new ArrayList<>();
    int quantidadeArestas;
    int tamGrafo;

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

            for (int i = 0; i < tamGrafo; i++) {
                listaAdjacencia.add(new LinkedList<>());
            }

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

                if(resultado.get(0) != resultado.get(1)) {
                    matrizAdjacencia[resultado.get(0) - 1][resultado.get(1) - 1].add(aresta);
                }

                matrizAdjacencia[resultado.get(1) - 1][resultado.get(0) - 1].add(aresta2);


                listaAdjacencia.get(resultado.get(0) - 1).add(aresta2);
                listaAdjacencia.get(resultado.get(1) - 1).add(aresta);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
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
            System.out.println("An error occurred.");
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






}
