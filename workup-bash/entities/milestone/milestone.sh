#!/bin/bash

read -p "milestone description: " description

if [[ "$description" == "done" ]]; then
    echo "done"
    exit 0
fi

read -p "amount: " amount
read -p "due date (YYYY-MM-DD): " dueDate

json_payload=$(cat <<EOF
{
    "description": "$description",
    "amount": $amount,
    "dueDate": "$dueDate"
}
EOF
)

echo "$json_payload"