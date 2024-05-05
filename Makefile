.PHONY: build

build:
	mvn -DskipTests package
	docker build ./services/jobs --tag ahmad45123/workup:service_jobs
	docker build ./services/payments --tag ahmad45123/workup:service_payments
	docker build ./services/users --tag ahmad45123/workup:service_users
	docker build ./services/contracts --tag ahmad45123/workup:service_contracts

up: 
	docker compose up --force-recreate

reload:
	mvn -DskipTests package
	docker compose up --detach --build 