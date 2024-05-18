#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

handle_service() {
    case $1 in
        users)
            bash ./services/users.sh
            ;;
        contracts)
            bash ./services/contracts.sh
            ;;
        payments)
            bash ./services/payments.sh
            ;;
        jobs)
            bash ./services/jobs.sh
            ;;
        exit)
            echo "Exiting Workup."
            rm ./token.txt
            exit 0
            ;;
        ls)
            echo "users"
            echo "payments"
            echo "jobs"
            echo "contracts"
            ;;
        *)
            echo -e "${RED}Invalid service. Available services: users, contracts, payments, jobs, exit $NC"
            ;;
    esac
}

touch token.txt

while true; do
    echo -ne "${YELLOW}workup>$NC "
    read service
    handle_service "$service"
done