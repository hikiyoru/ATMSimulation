import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        DataFileManager dataFileManager = new DataFileManager();
        ATM atm = new ATM(dataFileManager);

        atm.withdraw(new BigDecimal(2000));
        atm.deposit(new BigDecimal(1000));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> dataFileManager.write(atm)));
    }
}