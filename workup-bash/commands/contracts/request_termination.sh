#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "contract id: " contractId

read -p "reason for termination: " reason

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

json_payload=$(cat <<EOF
{
    "contractId": "$contractId",
    "reason": "$reason"
}
EOF
)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload"  http://localhost:80/api/v1/contracts/${contractId}/terminations/request)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"