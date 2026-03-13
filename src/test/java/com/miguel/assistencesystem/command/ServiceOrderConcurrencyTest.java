package com.miguel.assistencesystem.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.miguel.assistencesystem.application.command.ServiceOrderService;
import com.miguel.assistencesystem.application.dto.command.ServiceOrderCreateDTO;
import com.miguel.assistencesystem.domain.model.Client;
import com.miguel.assistencesystem.domain.model.Product;
import com.miguel.assistencesystem.infrastructure.persistence.ServiceOrderJpaDAO;
import com.miguel.assistencesystem.support.TestFactory;

import jakarta.persistence.EntityManager;

@SpringBootTest
@ActiveProfiles("test")
class ServiceOrderConcurrencyTest {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @Autowired
    private ServiceOrderJpaDAO serviceOrderDAO;

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private PlatformTransactionManager transactionManager;

    @SuppressWarnings("unused")
	private void runInTransaction(Runnable action) {
        new TransactionTemplate(transactionManager).execute(status -> {
            action.run();
            return null;
        });
    }

    @Test
    void onlyOneOpenServiceOrderPerProduct_underConcurrency() throws Exception {
        // arrange: persist client and product
        Client client = TestFactory.client();
        Product product = TestFactory.identifiedProduct(client);

        runInTransaction(() -> {
            entityManager.persist(client);
            entityManager.persist(product);
            entityManager.flush();
            entityManager.clear();
        });

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(2);

        List<Throwable> errors = Collections.synchronizedList(new ArrayList<>());

        Runnable task = () -> {
            try {
                startLatch.await(); // ensure true concurrency
                serviceOrderService.openSO(
                    new ServiceOrderCreateDTO(product.getId(), "Concurrent problem")
                );
            } catch (Throwable t) {
                errors.add(t);
            } finally {
                doneLatch.countDown();
            }
        };

        executor.submit(task);
        executor.submit(task);

        // act
        startLatch.countDown(); // release both threads
        doneLatch.await();
        executor.shutdown();

        // assert: only one SO exists
        long count = serviceOrderDAO.countByProduct(product.getId());
        assertEquals(1, count, "Only one open service order must be persisted");

        // assert: exactly one failure occurred
        assertEquals(1, errors.size(), "One transaction must fail");

        // optional: inspect exception type
        Throwable error = errors.get(0);
        assertTrue(
            error instanceof DataIntegrityViolationException
                || error.getCause() instanceof ConstraintViolationException,
            "Failure must come from DB uniqueness constraint"
        );
    }
}

