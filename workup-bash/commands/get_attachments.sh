#!/bin/bash

YELLOW='\033[0;33m'
NC='\033[0m'

#reading the array from the json attachments response
readarray -t attachments < <(echo $1 | jq -c '.[]')

mkdir -p "./media/proposal"$2"_attachments"

for attachment in ${attachments[@]}; do

    url=$(echo "$attachment" | jq -r '.url')
    curl --request GET \
        --url http://localhost:8080"$url" \
        --output "./media/proposal"$2"_attachments/$2.pdf"
done