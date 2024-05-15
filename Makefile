.PHONY: build

build:
	mvn -DskipTests package
	docker build ./services/jobs --tag workup:service_jobs
	docker build ./services/payments --tag workup:service_payments
	docker build ./services/users --tag workup:service_users
	docker build ./services/contracts --tag workup:service_contracts
	docker build ./webserver --tag workup:webserver

up: 
	docker stack deploy -c compose.yaml -c compose.override.yaml workup

portainer:
	docker stack deploy -c portainer-agent-stack.yml portainer

reload:
	mvn -DskipTests package
	docker compose up --detach --build 

format:
	mvn git-code-format:format-code -Dgcf.globPattern=**/*