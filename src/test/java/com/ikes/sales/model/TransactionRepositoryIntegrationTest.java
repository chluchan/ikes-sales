package com.ikes.sales.model;

import com.ikes.sales.TestContainerApplicationContextInitializer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(initializers = {TestContainerApplicationContextInitializer.class})
public class TransactionRepositoryIntegrationTest {
  @Autowired
  private TransactionRepository transactionRepository;

  @Test
  void shouldCreateAndRetrieveATransaction() {
    Transaction transaction = new Transaction()
      .setAccountId(44L)
      .setAccountAmountMillis(42000L)
      .setCashAmountMillis(420L)
      .setTransactionDateTime(LocalDateTime.of(2021, 3, 10, 12, 0));

    long transactionId = transactionRepository.save(transaction).block().getId();
    Mono<Transaction> foundTransaction = transactionRepository.findById(transactionId);

    assertThat(foundTransaction.blockOptional()).isPresent();
  }

  @Test
  void shouldGenerateUniqueIds() {
    Transaction t1 = new Transaction()
      .setAccountId(44L)
      .setAccountAmountMillis(42000L)
      .setCashAmountMillis(420L)
      .setTransactionDateTime(LocalDateTime.of(2021, 3, 10, 12, 0));
    Transaction t2 = new Transaction()
      .setAccountId(44L)
      .setAccountAmountMillis(43000L)
      .setCashAmountMillis(430L)
      .setTransactionDateTime(LocalDateTime.of(2021, 3, 11, 12, 0));

    long t1Id = transactionRepository.save(t1).block().getId();
    long t2Id = transactionRepository.save(t2).block().getId();

    assertThat(t1Id).isNotEqualTo(t2Id);
  }
}
