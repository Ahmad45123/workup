#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

source ./.env

read -p "photo location: " path
read -p "filename: " filename

echo -e "${YELLOW}Sending your request to the server...${NC}"

status_code=$(curl -o response_body --location 'http://localhost:8080/upload/icons/' \
-u "$UPLOAD_USER:$UPLOAD_PASSWORD" \
--form "resource=@"$path"" \
--form "filename="$filename"")

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"

photoLink=$(echo "$response_body" | jq -r '.path')

json_payload=$(cat <<EOF
{
    "photoLink": "$photoLink"
}
EOF
)

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/users/freelancer/photo)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"