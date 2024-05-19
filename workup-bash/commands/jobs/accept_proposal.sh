#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "jon id: " jobId
read -p "proposal id: " proposalId


echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" http://localhost:80/api/v1/jobs/{$jobId}/proposals/{$proposalId}/accept)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"