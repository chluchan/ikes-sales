package com.ikes.sales.model;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded.Nullable;
import org.springframework.data.relational.core.mapping.Table;

@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Table("sales_transaction")
public class Transaction {
  @Id
  @Getter
  @Setter
  @Column("id")
  private Long id;

  @Nullable
  @Column("account_id")
  private Long accountId;

  @Getter
  @Setter
  @Column("account_amount_millis")
  private long accountAmountMillis;

  @Getter
  @Setter
  @Column("cash_amount_millis")
  private long cashAmountMillis;

  @Getter
  @Setter
  @Column("transaction_date_time")
  private LocalDateTime transactionDateTime;

  public Optional<Long> getAccountId() {
    return Optional.ofNullable(accountId);
  }

  public Transaction setAccountId(long accountId) {
    this.accountId = accountId;
    return this;
  }

  public Transaction clearAccountId() {
    this.accountId = null;
    return this;
  }

  public Transaction setAccountId(Optional<Long> accountId) {
    if (accountId.isPresent()) {
      this.accountId = accountId.get();
    } else {
      clearAccountId();
    }

    return this;
  }
}
