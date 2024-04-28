package com.workup.payments.mapper;

import com.workup.payments.models.WalletTransaction;
import com.workup.shared.commands.payments.dto.WalletTransactionDTO;
import java.util.List;
import java.util.stream.Collectors;

public class WalletTransactionMapper {

  private WalletTransactionMapper() {}

  public static WalletTransactionDTO mapToWalletTransactionDTO(
      WalletTransaction walletTransaction) {
    return WalletTransactionDTO.builder()
        .withId(walletTransaction.getId())
        .withWalletId(walletTransaction.getWalletId())
        .withAmount(walletTransaction.getAmount())
        .withPaymentTransactionId(walletTransaction.getPaymentTransactionId())
        .withDescription(walletTransaction.getDescription())
        .withCreatedAt(walletTransaction.getCreatedAt())
        .withTransactionType(walletTransaction.getTransactionType())
        .build();
  }

  public static List<WalletTransactionDTO> mapToWalletTransactionDTOs(
      List<WalletTransaction> walletTransactions) {
    return walletTransactions.stream()
        .map(WalletTransactionMapper::mapToWalletTransactionDTO)
        .collect(Collectors.toList());
  }
}
