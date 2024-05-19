#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "Enter email: " email
read -p "Enter password: " password

# Create a JSON payload
json_payload=$(cat <<EOF
{
    "email": "$email",
    "password": "$password"
}
EOF
)

echo -e "${YELLOW}Sending your request to the server...${NC}"

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -d "$json_payload" http://localhost:80/api/v1/users/login)

response_body=$(cat response_body)

token=$(echo "$response_body" | jq -r '.authToken')

echo "$token" > ./token.txt

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"


