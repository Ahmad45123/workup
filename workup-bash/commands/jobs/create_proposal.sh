#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

read -p "job id: " jobId
read -p "cover letter: " coverLetter

echo "what is the proposed duration"
DURATIONS=("LESS_THAN_A_MONTH" "ONE_TO_THREE_MONTHS" "THREE_TO_SIX_MONTHS" "MORE_THAN_SIX_MONTHS")
jobDuration=$(bash ./commands/dropdown.sh ${DURATIONS[@]})

#attachements list
attachments=$(bash ./entities/attachment/attachment_list.sh)

#milestones list
milestones=$(bash ./entities/milestone/milestone_list.sh)

json_payload=$(cat <<EOF
{
    "coverLetter": "$coverLetter",
    "jobId": "$jobId",
    "jobDuration": "$jobDuration",
    "attachments": $attachments,
    "milestones": $milestones
}
EOF
)

echo "$json_payload"

echo -e "${YELLOW}Sending your request to the server...${NC}"

bearerToken=$(cat ./token.txt)

status_code=$(curl -s -o response_body -w "%{http_code}" -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $bearerToken" -d "$json_payload" http://localhost:80/api/v1/jobs/{$jobId}/proposals)

response_body=$(cat response_body)

rm response_body

bash ./response_formatter.sh "$status_code" "$response_body"