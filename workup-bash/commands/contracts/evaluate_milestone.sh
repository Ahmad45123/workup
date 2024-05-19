#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "milestone id: " milestoneId

EVAL_STATUS=("OPEN" "IN_PROGRESS" "IN_REVIEW" "ACCEPTED" "PAID")

evaluatedState=$(bash ./commands/dropdown.sh ${EVAL_STATUS[@]})

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

json_payload=$(cat <<EOF
{
    "milestoneId": "$milestoneId",
    "evaluatedState": "$evaluatedState"
}
EOF
)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload"  http://localhost:80/api/v1/contracts/milestones/${milestoneId}/evaluate)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"