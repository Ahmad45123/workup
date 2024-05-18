#!/bin/bash

CYAN='\033[0;36m'
NC='\033[0m' # No Color

handle_payments_command() {
    case $1 in
        cget-my-payment-requests)
            bash ./commands/payments/cget_my_payment_requests.sh
            ;;
        cget-my-payment-transactions)
            bash ./commands/payments/cget_my_payment_transactions.sh
            ;;
        fget-my-payment-requests)
            bash ./commands/payments/fget_my_payment_requests.sh
            ;;
        fget-my-payment-transactions)
            bash ./commands/payments/fget_my_payment_transactions.sh
            ;;
        get-wallet-transactions)
            bash ./commands/payments/get_wallet_transactions.sh
            ;;
        get-wallet-transaction)
            bash ./commands/payments/get_wallet_transaction.sh
            ;;
        get-my-wallet)
            bash ./commands/payments/get_wallet.sh
            ;;
        withdraw-from-wallet)
            bash ./commands/payments/withdraw_from_wallet.sh
            ;;
        pay-request)
            bash ./commands/payments/pay_request.sh
            ;;
        exit)
            echo -e "${CYAN}Exiting Jobs service.${NC}"
            exit 0
            ;;
        *)
            echo -e "${RED}Unknown command: ${NC}$1${NC}."
            ;;
    esac
}

while true; do
    echo -n -e "${CYAN}payments> ${NC}"
    read payments_command
    handle_payments_command "$payments_command"
done