#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'


echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken"  http://localhost:80/api/v1/users/client/photo)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"

photoLink=$(echo "$response_body" | jq -r '.photoLink')

curl --request GET \
  --url http://localhost:8080$photoLink > "./media/client_image.png"
