#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "Enter achievment name: " achievment_name
read -p "awarded by: " awarded_by
read -p "achievment description: " achievement_description
read -p "award date (YYYY-MM-DD): " award_date

# Create a JSON payload
json_payload=$(cat <<EOF
{
    "achievement_name": "$achievment_name",
    "awarded_by": "$awarded_by",
    "achievement_description": "$achievement_description",
    "award_date": "$award_date"
}
EOF
)

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/users/freelancer/achievements)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"