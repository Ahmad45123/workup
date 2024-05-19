#!/bin/bash

# Define color codes
YELLOW='\033[0;33m'
NC='\033[0m'

read -p "Enter username: " fullName
read -p "Enter email: " email
read -p "Enter your job title: " jobTitle
read -p "Enter your city: " city
read -p "Enter your birth date (YYYY-MM-DD): " birthDate
read -p "Enter description: " description

# Create a JSON payload
json_payload=$(cat <<EOF
{
    "fullName": "$fullName",
    "email": "$email",
    "city": "$city",
    "description": "$description",
    "jobTitle": "$jobTitle",
    "birthDate": "$birthDate"
}
EOF
)

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/users/freelancer/profile)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"