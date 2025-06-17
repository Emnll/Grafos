# Grafos
Uma biblioteca que permitirá o usuário manipular grafos

### Formato do Arquivo de Entrada
O arquivo de entrada deverá ser escrito da seguinte forma:

* Em formato txt

* Vértices de 1 - X -> O vértice obrigatoriamente deve começar com 1

* A primeira linha deve conter a quantidade de vértices

* As linhas seguintes devem descrever as arestas, sendo sua estrutura a seguinte: Vértice1 Vértice2 Peso*
* Sendo o peso opcional e os items separados por apenas 1 espaço

* O Grafo aceito é não direcionado, pode ou não ser simples e pode ou não ser conexo

### Explicação dos arquivos e parâmetros
Há três arquivos:
* Aresta.java:
  
  Nesta Classe você poderá encontrar a classe de estrutura de aresta do projeto;
  
* Main:
  
  Nesta Classe você poderá encontrar um exemplo de funcionamento do projeto. Consumindo um grafo em formato .txt como entrada e fornecendo três arquivos e um plot no terminal como saída principal do exemplo;
  
* ManipulacaoGrafos:
  
   Nesta Classe você encontrará a principal classe do projeto que irá definir a estrutura de representação do grafo juntamente com suas funções.
  
* Nela você encontrará:
  
  * Estruturas de Dados:
    
    * matrizAdjacencia: Estrutura responsável pela representação do grafo via matriz de adjacência;
      
    * listaAdjacencia: Estrutura responsável pela representação do grafo via lista de adjacência;
      
    * quantidadeArestas: Variável responsável por armazenar a qtd de arestas do grafo;
      
    * quantidadeVertices: Variável responsável por armazenar a qtd de vértices do grafo;
      
    * visitados: Estrutura responsável por manter os dados os vértices visitados em um dado momento.
      
  * Métodos:
    
    * lerGrafos: Espera como entrada um arquivo .txt contendo o grafo nos moldes do trabalho;
      
    * relatorioGrafo: Aceita como entrada um arquivo contendo o grafo; Escreve um arquivo contendo as informações como: número de vértices, número de arestas, grau médio e  distrituibção empírica do grau dos vértices.
      
    * represetacaoGrafo: Aceita como parâmetro a escolha de representação do grafo via terminal sendo: 1- Lista de adjacencia e; 2- Matriz de adjacencia.
      
    * buscaGrafo: Aceita os parâmetros escolha, noInicial, nomeArquivo:
       *  escolha: 1 ou 2 para escolha entre DFS(1) e BFS(2);
       *  noInicial: valor do nó inicial (obs.: Precisa existir o vértice para nó inicial, não tendo qualquer tipo de verificação caso seja posto um valor para vértice que não existe no grafo);
       *  nomeArquivo: nome do arquivo para ser escrito.
      
    * bfs: Aceita como parâmetros no, pais, niveis:
       * no: no inicial a ser adicionado a fila; 
       * pais: lista para manter a relação entre ancestralidade do no atual e suas adjacencias;
       * niveis: adiciona o no vizinho ao proximo nivel (imaginando uma árvore).
      
    * dfs:  Aceita como parâmetros no, pai, nivel, pais, niveis:
       * no: no inicial a ser adicionado a estrutura visitados;
       * pai: atribui ancestralidade ao nó atual com o nó anterior (raíz: -1);
       * nivel: nível atual do nó (raíz: 0) 
       * pais: lista para manter a informação da relação da atribuição de ancestralidade;
       * niveis: lista para manter a informação da relação da atribuição de níveis.
      
    * escreverBusca:  Aceita como parâmetros pais, niveis, arquivo, tipoBusca:
       * pais: Lista contendo as relação de parenstesco feitas dado um dos tipos de algoritmos;
       * niveis: Lista contendo as relação de níveis feitas dado um dos tipos de algoritmos;
       * arquivo: nome arquivo de escrita;
       * tipoBusca: nome do algoritmo usado para busca.
      
    * componentesConexos: Aceita como parâmetro saida:
       * saida: Nome do arquivo de escrita para o algortimo de componentes conexos.
      
    * dfsComponentes: Método para calculo do algoritmo dfs para cálculo de componentes conexas. Aceita como parâmetros atual, componente:
       * atual: nó inicial do componente ;
       * componente: lista de adjacências do componente.
      
