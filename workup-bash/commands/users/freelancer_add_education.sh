#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "School name: " school_name
read -p "degree: " degree
read -p "education start date (YYYY-MM-DD): " education_start_date
read -p "city: " city
read -p "end date (YYYY-MM-DD): " end_date
read -p "major: " major 
read -p "education description: " education_description
read -p "grade: " grade

# Create a JSON payload
json_payload=$(cat <<EOF
{
    "school_name": "$school_name",
    "degree": "$degree",
    "education_start_date": "$education_start_date",
    "city": "$city",
    "end_date":"$end_date",
    "major":"$major",
    "education_description":"$education_description",
    "grade": "$grade"
}
EOF
)

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/users/freelancer/education)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"