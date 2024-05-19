#!/bin/bash

inputs=()

while true; do
    read -p "Enter a "$1" (or type 'done' to finish): " item
    if [[ "$item" == "done" ]]; then
        break
    else
        inputs+=("$item")
    fi
done

echo $(jq --compact-output --null-input '$ARGS.positional' --args -- "${inputs[@]}")