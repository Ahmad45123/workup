#!/bin/bash

source ./.env

read -p "attachement name: " name

if [[ "$name" == "done" ]]; then
    echo "done"
    exit 0
fi

read -p "attachement path: " path

bearerToken=$(cat ./token.txt)

filename="${bearerToken}${name}"

status_code=$(curl -o response_body --location 'http://localhost:8080/upload/resume/' \
-u "$UPLOAD_USER:$UPLOAD_PASSWORD" \
--form "resource=@"$path"" \
--form "filename="$filename"")

response_body=$(cat response_body)

resumeLink=$(echo "$response_body" | jq -r '.path')

json_payload=$(cat <<EOF
{
    "name": "$name",
    "url": "$resumeLink"
}
EOF
)

echo "$json_payload"