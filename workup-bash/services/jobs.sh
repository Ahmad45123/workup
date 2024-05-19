#!/bin/bash

CYAN='\033[0;36m'
NC='\033[0m' # No Color

handle_jobs_command() {
    case $1 in
        get-job)
            bash ./commands/jobs/get_job.sh
            ;;
        create-job)
            bash ./commands/jobs/create_job.sh
            ;;
        get-my-jobs)
            bash ./commands/jobs/get_my_jobs.sh
            ;;
        get-job-proposals)
            bash ./commands/jobs/get_job_proposals.sh
            ;;
        get-my-proposals)
            bash ./commands/jobs/get_my_proposals.sh
            ;;
        accept-proposal)
            bash ./commands/jobs/accept_proposal.sh
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
    echo -n -e "${CYAN}jobs> ${NC}"
    read job_command
    handle_jobs_command "$job_command"
done