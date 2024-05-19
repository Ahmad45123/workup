#!/bin/bash

CYAN='\033[0;36m'
NC='\033[0m' # No Color

handle_contracts_command() {
    case $1 in
        get-contract)
            bash ./commands/contracts/get_contract.sh
            ;;
        get-contract-milestones)
            bash ./commands/contracts/get_contract_milestones.sh
            ;;
        get-milestone)
            bash ./commands/contracts/get_milestone.sh
            ;;
        get-pending-terminations)
            bash ./commands/contracts/get_pending_terminations.sh
            ;;
        request-contract-termination)
            bash ./commands/contracts/request_termination.sh
            ;;
        evaluate-milestone)
            bash ./commands/contracts/evaluate_milestone.sh
            ;;
        handle-termination-request)
            bash ./commands/contracts/handle_termination_request.sh
            ;;
        progress-milestone)
            bash ./commands/contracts/progress_milestone.sh
            ;;
        exit)
            echo -e "${CYAN}Exiting Jobs service.${NC}"
            exit 0
            ;;
        *)
            echo -e "${RED}Unknown command: ${NC}$1${NC}."
            ;;
    esac
}

while true; do
    echo -n -e "${CYAN}contracts> ${NC}"
    read contracts_command
    handle_contracts_command "$contracts_command"
done