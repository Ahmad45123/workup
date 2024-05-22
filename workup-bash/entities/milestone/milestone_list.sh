#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

inputs=()

while true; do
    echo -e "${YELLOW}enter milestone or done${NC}" >&2
    item=$(bash ./entities/milestone/milestone.sh)
    if [[ "$item" == "done" ]]; then
        break
    else
        inputs+=("$item")
    fi
done

json_output=$(printf "%s\n" "${inputs[@]}" | jq -s '.')

echo "$json_output"