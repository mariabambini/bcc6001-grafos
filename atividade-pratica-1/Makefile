PYTHON := ./.venv/bin/python
PIP := ./.venv/bin/pip

setup:
	@python3 -m venv .venv 
	@$(PIP) install --upgrade pip
	@$(PIP) install -r requirements.txt

install:
	@$(PIP) install -r requirements.txt

freeze: 
	@$(PIP) freeze > requirements.txt

run:
	install