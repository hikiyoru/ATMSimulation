package repositories;

import models.ATM;
import models.ATMState;
import models.Card;
import utils.DateFormatHelper;

import java.io.*;
import java.util.*;

public class FileBankEntityRepository implements BankEntityRepository<ATM, Map<String, Card>> {
    private final String fileAtmPath;
    private final String fileCardsPath;
    private final Map<String, Card> cards;
    private final ATM atm;

    public FileBankEntityRepository(String fileAtmPath, String fileCardsPath) {
        this.fileAtmPath = fileAtmPath;
        this.fileCardsPath = fileCardsPath;
        this.cards = loadCards();
        this.atm = loadATM();
    }

    private ATM loadATM() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileAtmPath))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(" ");
                if (data.length == 2) {
                    return new ATM(data[0], ATMState.fromString(data[1]), null, null);
                } else {
                    return new ATM(data[0], ATMState.fromString(data[1]), cards.get(data[2]), Boolean.parseBoolean(data[3]));
                }
            } else {
                throw new IOException("Empty file: " + fileAtmPath);
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    private Map<String, Card> loadCards() {
        Map<String, Card> cards = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileCardsPath))) {
            String line;
            String[] data;
            while ((line = reader.readLine()) != null) {
                data = line.split(" ");
                cards.put(data[0], new Card(data[0], data[1], Byte.parseByte(data[2]), data[3], DateFormatHelper.parseDate(data[4])));
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return cards;
    }

    @Override
    public ATM getATM() {
        return atm;
    }

    @Override
    public Map<String, Card> getCards() {
        return cards;
    }

    @Override
    public void saveATM() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileAtmPath))) {
           writer.write(atm.toString());
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public void saveCards() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileCardsPath))) {
            for (Card value : cards.values()) {
                writer.write(value.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public void saveAll() {
        saveATM();
        saveCards();
    }
}
