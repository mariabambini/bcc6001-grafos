# Atividade Prática 1 - Algoritmos em Grafos (BCC6001)

Dado um grafo não dirigido de entrada, calcular algumas estatísticas usando algoritmos apresentados em aula.

## Requisitos

- JDK 11 ou superior (`javac` e `java` no PATH)
- `make`

## Estrutura do projeto

```
.
├── data
│   ├── input/            # grafos de entrada (.gexf e .txt)
│   └── output/           # resultados gerados
├── src
│   ├── Graph.java        # estrutura de dados do grafo
│   ├── GexfParser.java   # leitor de arquivos .gexf
│   ├── parse.py
│   └── utils/            # Bag, In, Stack, StdIn, StdOut
├── out/                  # classes compiladas (gerada pelo make)
├── Makefile
├── README.md
└── requirements.txt
```

## Compilação

A partir da raiz do projeto:

```bash
make compile
```

## Execução

Ler um grafo GEXF:

```bash
make run
make run LesMiserables.gexf
```

Ler um grafo em arquivo de texto (formato `tinyG.txt`):

```bash
make run-graph
make run-graph tinyG.txt
```

Manualmente, sem o Makefile:

```bash
java -cp out GexfParser data/input/LesMiserables.gexf
java -cp out Graph data/input/tinyG.txt
```

Computar a métrica Closeness Centrality:

```bash
make run-cc LesMiserables.gexf
```

Computar a métrica Eccentricity:

```bash
make run-ecc LesMiserables.gexf
```

## Limpeza

```bash
make clean
```

## Ambiente Python

```bash
make setup   # cria o .venv e instala as dependências
make install # reinstala as dependências do requirements.txt
make freeze  # atualiza o requirements.txt
```

##

O código com a estrutura do grafo foi retirado do seguinte repositório: https://github.com/aistrate/AlgorithmsSedgewick/blob/master/4-Graphs/4-1-UndirectedGraphs/