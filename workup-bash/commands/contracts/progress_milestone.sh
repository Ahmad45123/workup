#!/bin/bash

# this has to send the json payload because the endpoint in webserver needs requestbody
# even though not used there but have to send from here

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "milestone id: " milestoneId

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

json_payload=$(cat <<EOF
{
    "milestoneId": "$milestoneId"
}
EOF
)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/contracts/milestones/${milestoneId}/progress)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"