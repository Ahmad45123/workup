#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken"  http://localhost:80/api/v1/payments/freelancers/me/transactions)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"