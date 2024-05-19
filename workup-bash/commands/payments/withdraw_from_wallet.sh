#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "amount: " amount
read -p "description: " description

json_payload=$(cat <<EOF
{
    "freelancerId": "",
    "amount": "$amount",
    "description": "$description"
}
EOF
)

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload"  http://localhost:80/api/v1/payments/freelancers/me/wallet/withdraw)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"