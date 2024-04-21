.PHONY: build

build:
	mvn -DskipTests package
	docker compose build

up: 
	docker compose up

reload:
	mvn -DskipTests package
	docker compose up --detach --build 