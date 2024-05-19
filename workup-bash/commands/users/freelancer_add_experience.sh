#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "company name: " company_name
read -p "job title: " job_title
read -p "employment start (YYYY-MM-DD): " employment_start
read -p "city: " city
read -p "employment end (YYYY-MM-DD): " employment_end
read -p "experience description: " experience_description

# Create a JSON payload
json_payload=$(cat <<EOF
{
    "company_name": "$company_name",
    "job_title": "$job_title",
    "employment_start": "$employment_start",
    "city": "$city",
    "employment_end":"$employment_end",
    "experience_description":"$experience_description"
}
EOF
)

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/users/freelancer/experience)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"