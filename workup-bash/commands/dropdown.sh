#!/bin/bash

# Define the options
OPTIONS=("$@")

echo "Select your experience level:" > /dev/null
select OPTION in "${OPTIONS[@]}"; do
    if [[ " ${OPTIONS[@]} " =~ " ${OPTION} " ]]; then
        echo "$OPTION"
        break
    else
        echo "Invalid selection. Please choose a valid option." > /dev/null
    fi
done