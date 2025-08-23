package fr.school42.Numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {

	// Test lwl: Check isPrime with ACTUAL prime numbers
	@ParameterizedTest // ah hadi for multiple tests, not only one as @Test
	@ValueSource(ints = {2, 3, 5, 7, 11})
	void isPrimeForPrimes(int number) {
	    NumberWorker worker = new NumberWorker();
		assertTrue(worker.isPrime(number));
	}
    
	// Test tani: Check isPrime with NON-prime numbers
	@ParameterizedTest
	@ValueSource(ints = {4, 6, 8, 9, 10})
	void isPrimeForNotPrimes(int number) {
	    NumberWorker worker = new NumberWorker();
		assertFalse(worker.isPrime(number));
	}
	
	// Test talet: Check isPrime with INVALID numbers (should throw exception)
	@ParameterizedTest
	@ValueSource(ints = {-5, -1, 0, 1})
	void isPrimeForIncorrectNumbers(int number) {
    	NumberWorker worker = new NumberWorker();
    	assertThrows(IllegalNumberException.class, () -> {
    	    worker.isPrime(number);
    	});
	}
	
	// Test rabe3:  Check digitsSum with various numbers (using CSV file)
	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv")
	void digitsSumtest(int number, int expectedSum) {
	    NumberWorker worker = new NumberWorker();
		assertEquals(expectedSum, worker.digitsSum(number));
	}
}
