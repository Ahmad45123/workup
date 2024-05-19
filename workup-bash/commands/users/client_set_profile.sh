#!/bin/bash

# Define color codes
YELLOW='\033[0;33m'
NC='\033[0m'

read -p "Enter username: " username
read -p "Enter email: " email
read -p "Enter your industry: " industry
read -p "Enter your city: " city
read -p "Enter your Employee count: " employee_count
read -p "Enter description: " description

# Create a JSON payload
json_payload=$(cat <<EOF
{
    "name": "$username",
    "email": "$email",
    "city": "$city",
    "description": "$description",
    "industry": "$industry",
    "employeeCount": "$employee_count"
}
EOF
)

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/users/client/profile)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"