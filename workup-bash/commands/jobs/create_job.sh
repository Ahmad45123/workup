#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "title: " title
read -p "description: " description
read -p "location: " location
read -p "budget: " budget
EXPERIENCE=("ENTRY_LEVEL" "INTERMEDIATE" "EXPERT")
experience=$(bash ./commands/dropdown.sh ${EXPERIENCE[@]})
skills=$(bash ./commands/take_list.sh "skill")

json_payload=$(cat <<EOF
{
    "title": "$title",
    "description": "$description",
    "location": "$location",
    "budget": $budget,
    "skills": $skills,
    "experience": "$experience"
}
EOF
)

echo "$json_payload"

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/jobs)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"