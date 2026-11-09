PYTHON := ./.venv/bin/python
PIP := ./.venv/bin/pip

JAVAC := javac
JAVA := java
OUT := out
INPUT_DIR := data/input
SOURCES := $(wildcard src/*.java src/utils/*.java)

ARGS := $(filter-out run run-graph,$(MAKECMDGOALS))
GRAFO := $(firstword $(ARGS))

.PHONY: setup install freeze compile run run-graph clean

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

clean:
	@rm -rf $(OUT)

%:
	@: