
package fr.school42.Numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number < 2)  throw new IllegalNumberException("Number must be >= 2");
           
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true; 
       }
    
    public int digitsSum(int number) {
        int sum = 0;
        number = Math.abs(number);  
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}