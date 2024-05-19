#!/bin/bash

CYAN='\033[0;36m'
NC='\033[0m' #NO_COLOR

handle_users_command() {
    case $1 in
        client-register)
            bash ./commands/users/client_register.sh
            ;;
        freelancer-register)
            bash ./commands/users/freelancer_register.sh
            ;;
        login)
            bash ./commands/users/login.sh
            ;;
        freelancer-add-achievment)
            bash ./commands/users/freelancer_add_achievment.sh
            ;;
        freelancer-add-education)
            bash ./commands/users/freelancer_add_education.sh
            ;;
        freelancer-add-experience)
            bash ./commands/users/freelancer_add_experience.sh
            ;;
        freelancer-add-language)
            bash ./commands/users/freelancer_add_language.sh
            ;;
        freelancer-add-skill)
            bash ./commands/users/freelancer_add_skill.sh
            ;;
        client-get-photo)
            bash ./commands/users/client_get_photo.sh
            ;;
        client-get-profile)
            bash ./commands/users/client_get_profile.sh
            ;;
        client-add-photo)
            bash ./commands/users/client_add_photo.sh
            ;;
        client-set-profile)
            bash ./commands/users/client_set_profile.sh
            ;;
        freelancer-add-photo)
            bash ./commands/users/freelancer_add_photo.sh
            ;;
        freelancer-get-photo)
            bash ./commands/users/freelancer_get_photo.sh
            ;;
        freelancer-get-brief)
            bash ./commands/users/freelancer_get_brief.sh
            ;;
        freelancer-get-profile)
            bash ./commands/users/freelancer_get_profile.sh
            ;;
        freelancer-add-resume)
            bash ./commands/users/freelancer_add_resume.sh
            ;;
        freelancer-get-resume)
            bash ./commands/users/freelancer_get_resume.sh
            ;;
        freelancer-set-profile)
            bash ./commands/users/freelancer_set_profile.sh
            ;;
        freelancer-get-achievments)
            bash ./commands/users/freelancer_get_achievments.sh
            ;;
        freelancer-get-experience)
            bash ./commands/users/freelancer_get_experience.sh
            ;;
        freelancer-get-education)
            bash ./commands/users/freelancer_get_education.sh
            ;;
        freelancer-get-languages)
            bash ./commands/users/freelancer_get_languages.sh
            ;;
        freelancer-get-skills)
            bash ./commands/users/freelancer_get_skills.sh
            ;;
        exit)
            echo -e "${CYAN}Exiting Users service.${NC}"
            exit 0
            ;;
        *)
            echo -e "${RED}Unknown command: ${NC}$1${NC}."
            ;;
    esac
}

while true; do
    echo -n -e "${CYAN}users> ${NC}"
    read user_command
    handle_users_command "$user_command"
done