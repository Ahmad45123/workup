#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'


echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X GET -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken"  http://localhost:80/api/v1/jobs/me/proposals)

response_body=$(cat response_body)

rm response_body

proposalsArr=$(echo $response_body | jq -r '.proposals')

readarray -t proposals < <(echo $proposalsArr | jq -c '.[]')

i=1

for proposal in ${proposals[@]}; do
    attachements=$(echo $proposal | jq -r '.attachments')
    bash ./commands/get_attachments.sh "$attachements" "$i"
    i=$(($i+1))
done

bash ./response_formatter.sh "$status_code" "$response_body"