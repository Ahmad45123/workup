.PHONY: build

build:
	mvn -DskipTests package
	docker compose build

up: 
	docker compose up --force-recreate

reload:
	mvn -DskipTests package
	docker compose up --detach --build 

format:
	mvn git-code-format:format-code -Dgcf.globPattern=**/*