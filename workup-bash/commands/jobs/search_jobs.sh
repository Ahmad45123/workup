#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

pageState="null"

while true; do

    read -p "query: " query
    read -p "pageLimit: " pageLimit

    base_url="http://localhost:80/api/v1/jobs/search"

    url="${base_url}?query="$query"&pageLimit=${pageLimit}"

    if [[ "$pagingState" != "null" ]]; then
        url="${base_url}?query="$query"&pageLimit=${pageLimit}&pageState="$pageState""
    fi

    echo -e "${YELLOW}Sending your request to the server...${NC}"

    bearerToken=$(cat ./token.txt)

    status_code=$(curl -s -o response_body -w "%{http_code}" -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" "$url")

    response_body=$(cat response_body)

    rm response_body

    bash ./response_formatter.sh "$status_code" "$response_body"

    pageState=$(echo $response_body | jq -r '.pagingState')

    read -p "enough or otherwise for next " breaker

    if [[ $breaker == "enough" ]]; then
        break
    fi
done