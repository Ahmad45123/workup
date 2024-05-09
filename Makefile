.PHONY: build

build:
	mvn -DskipTests package

	docker build ./services/jobs --tag workup:service_jobs
    docker build ./services/payments --tag workup:service_payments
    docker build ./services/users --tag workup:service_users
    docker build ./services/contracts --tag workup:service_contracts


up: 
	docker compose up --force-recreate

reload:
	mvn -DskipTests package
	docker compose up --detach --build 