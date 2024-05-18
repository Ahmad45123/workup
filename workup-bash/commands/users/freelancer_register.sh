#!/bin/bash

YELLOW='\033[0;33m'

read -p "Enter username: " fullname
read -p "Enter email: " email
read -p "Enter password: " password
read -p "Enter your job title: " job_title
read -p "Enter your city: " city
read -p "Enter your birth date (YYYY-MM-DD): " birth_date

# Create a JSON payload
json_payload=$(cat <<EOF
{
    "fullName": "$fullname",
    "email": "$email",
    "password": "$password",
    "jobTitle": "$job_title",
    "city": "$city",
    "birthDate": "$birth_date"
}
EOF
)

echo -e "${YELLOW}Sending your request to the server...${NC}"

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -d "$json_payload" http://localhost:80/api/v1/users/freelancers/register)

response_body=$(cat response_body)

token=$(echo "$response_body" | jq -r '.authToken')

echo "$token" > ./token.txt

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"