package org.fran.junitapp.example.models;


import org.fran.junitapp.example.exceptions.RedNumbersException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CountTest {
    Count count;
    @BeforeEach
    void initMethodTest(){
        this.count = new Count("Frank", new BigDecimal("1000.12345"));
        System.out.println("Method initialized");
    }

    @AfterEach
    void tearDown(){
        System.out.println("Method finished");
    }

    @BeforeAll
    static void beforeAll(){
        System.out.println("Test initialized");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("Test finished");
    }

    @Test
    @DisplayName("name testing")
    void testNameCount(){

        // count.setPerson("Frank");
        String expected = "Frank";
        String real = count.getPerson();
        assertNotNull(real, ()-> "Count must not be null");
        assertEquals(expected, real, ()->"Expected name in wrong");
        assertTrue(real.equals("Frank"));

    }

    @Test
    void testBalanceCount(){
        assertNotNull(count.getBalance());
        assertEquals(1000.12345, count.getBalance().doubleValue());
        assertFalse(count.getBalance().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenceCount(){
        count = new Count("John Doe", new BigDecimal("1000.123"));
        Count count2 = new Count("John Doe", new BigDecimal("1000.123"));
        assertEquals(count, count2);
    }

    @Test
    void testDebitCount(){
        count.debit(new BigDecimal(100));
        assertNotNull(count.getBalance());
        assertEquals(900, count.getBalance().intValue());
        assertEquals("900.12345", count.getBalance().toString());
    }

    @Test
    void testCreditCount(){
        count.credit(new BigDecimal(100));
        assertNotNull(count.getBalance());
        assertEquals(1100, count.getBalance().intValue());
        assertEquals("1100.12345", count.getBalance().toString());
    }

    @Test
    void testRedNumbersExceptionCount(){
        Exception exception = assertThrows(RedNumbersException.class, () ->{
            count.debit(new BigDecimal(1500));
        });
        String real = exception.getMessage();
        String expected = "insufficient money";
        assertEquals(expected,real);
    }

    @Test
    void testTranferMoneyCounts(){
        Count count1 = new Count("Mark", new BigDecimal("2500"));
        Count count2 = new Count("Andrew", new BigDecimal("1500.8989"));

        Bank bank = new Bank();
        bank.setName("Banco del Estado");
        bank.transfer(count2, count1, new BigDecimal("500"));

        assertEquals("1000.8989", count2.getBalance().toPlainString());
        assertEquals("3000", count1.getBalance().toPlainString());

    }

    @Test
    void testRelationBankCounts(){
        Count count1 = new Count("Mark", new BigDecimal("2500"));
        Count count2 = new Count("Andrew", new BigDecimal("1500.8989"));

        Bank bank = new Bank();
        bank.addCount(count1);
        bank.addCount(count2);

        bank.setName("Banco del Estado");
        bank.transfer(count2, count1, new BigDecimal("500"));

        assertAll(()->{
                    assertEquals("1000.8989", count2.getBalance().toPlainString());},
                ()->assertEquals("3000", count1.getBalance().toString()),
                ()->assertEquals(2, bank.getCounts().size()),
                ()->assertEquals("Banco del Estado", count1.getBank().getName()),
                ()->assertEquals("Andrew", bank.getCounts().stream().filter(c ->c.getPerson().equals("Andrew"))
                        .findFirst()
                        .get().getPerson()),
                ()->assertTrue(bank.getCounts().stream().anyMatch(c ->c.getPerson().equals("Andrew"))));


    }
}