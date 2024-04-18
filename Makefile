.PHONY: build

build:
	mvn -DskipTests package
	docker compose build

up: 
	docker compose up