.PHONY: build

build:
	mvn -DskipTests package
	docker build ./services/jobs --tag workup:service_jobs
	docker build ./services/payments --tag workup:service_payments
	docker build ./services/users --tag workup:service_users
	docker build ./services/contracts --tag workup:service_contracts

up: 
	docker compose up -c compose.yaml -c compose.override.yaml --force-recreate

upswarm: 
	docker stack deploy -c compose.yaml -c compose.override.yaml workup

portainer:
	docker stack deploy -c portainer-agent-stack.yml

reload:
	mvn -DskipTests package
	docker compose up --detach --build 