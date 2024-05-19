#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "termination request id: " contractTerminationRequestId

EVAL_STATUS=("PENDING" "REJECTED" "ACCEPTED")

chosenStatus=$(bash ./commands/dropdown.sh ${EVAL_STATUS[@]})

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

json_payload=$(cat <<EOF
{
    "contractTerminationRequestId": "$contractTerminationRequestId",
    "chosenStatus": "$chosenStatus"
}
EOF
)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload"  http://localhost:80/api/v1/contracts/terminations/${contractTerminationRequestId}/handle)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"