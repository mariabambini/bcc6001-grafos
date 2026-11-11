PYTHON := ./.venv/bin/python
PIP := ./.venv/bin/pip

JAVAC := javac
JAVA := java
OUT := out
INPUT_DIR := data/input
OUTPUT_DIR := data/output
SOURCES := $(wildcard src/*.java src/utils/*.java)

ARGS := $(filter-out run run-graph run-cc run-ecc,$(MAKECMDGOALS))
GRAFO := $(firstword $(ARGS))

.PHONY: setup install freeze compile run run-graph run-cc run-ecc clean

setup:
	@python3 -m venv .venv
	@$(PIP) install --upgrade pip
	@$(PIP) install -r requirements.txt

install:
	@$(PIP) install -r requirements.txt

freeze:
	@$(PIP) freeze > requirements.txt

compile:
	@mkdir -p $(OUT)
	@$(JAVAC) -d $(OUT) $(SOURCES)

# Lê um grafo .gexf
run: compile
	@$(JAVA) -cp $(OUT) GexfParser $(INPUT_DIR)/$(GRAFO)

# Lê um grafo .txt 
run-graph: compile
	@$(JAVA) -cp $(OUT) Graph $(INPUT_DIR)/$(GRAFO)
  
# Computa a métrica Eccentricity de um grafo
run-ecc: compile
	@mkdir -p $(OUTPUT_DIR)
	@$(JAVA) -cp $(OUT) Eccentricity $(INPUT_DIR)/$(GRAFO) $(OUTPUT_DIR)/eccentricity.txt

# Computa a métrica Closeness Centrality de um grafo
run-cc: compile
	@mkdir -p $(OUTPUT_DIR)
	@$(JAVA) -cp $(OUT) ClosenessCentrality $(INPUT_DIR)/$(GRAFO) $(OUTPUT_DIR)/closeness_centrality.txt

clean:
	@rm -rf $(OUT)

%:
	@:
