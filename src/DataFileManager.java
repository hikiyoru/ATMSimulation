import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataFileManager implements ReadableWritable<ATM> {

    private static final String FILE_PATH = "data/data.txt";

    @Override
    public void read(ATM atm) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();

            String[] data = line.split(" ");
            atm.setATMState(data[0]);
            atm.setBalance(new BigDecimal(data[1]));
            atm.setCardNumber(data[2]);
            while ((line = reader.readLine()) != null) {
                data = line.split(" ");
                atm.addCard(new Card(data[0], data[1], Byte.parseByte(data[2]), new BigDecimal(data[3]), DateFormatHelper.toDateFormat(data[4])));
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    @Override
    public void write(ATM atm) {
        String line;
        List<String> lines = new ArrayList<>();
        lines.add(atm.getATMState() + " " + atm.getBalance() + " " + atm.getCardNumber());
        lines.addAll(atm.getAllCardsToString());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String str : lines) {
                writer.write(str + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
