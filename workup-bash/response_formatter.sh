#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

case $1 in
    200) echo -e "${GREEN}200 OK${NC}" ;;
    201) echo -e "${GREEN}201 Created${NC}" ;;
    204) echo -e "${GREEN}204 No Content${NC}" ;;
    400) echo -e "${YELLOW}400 Bad Request${NC}" ;;
    401) echo -e "${YELLOW}401 Unauthorized${NC}" ;;
    403) echo -e "${YELLOW}403 Forbidden${NC}" ;;
    404) echo -e "${YELLOW}404 Not Found${NC}" ;;
    500) echo -e "${RED}500 Internal Server Error${NC}" ;;
    502) echo -e "${RED}502 Bad Gateway${NC}" ;;
    503) echo -e "${RED}503 Service Unavailable${NC}" ;;
    504) echo -e "${RED}504 Gateway Timeout${NC}" ;;
    *) echo -e "${YELLOW}Unknown Status Code${NC}" ;;
esac

echo "$2" | jq

