package repository;

import models.ATM;
import models.Card;

import java.io.*;
import java.util.*;

public class FileBankEntityRepository implements BankEntityRepository<ATM, Map<String, Card>> {
    private static final String FILE_ATM_PATH = "data/atm.txt";
    private static final String FILE_CARDS_PATH = "data/cards.txt";

    @Override
    public void saveATM(ATM atm) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ATM_PATH))) {
           writer.write(atm.toString());
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public void saveCards(Map<String, Card> cards) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CARDS_PATH))) {
            for (Card value : cards.values()) {
                writer.write(value.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public ATM getATM() {
        ATM atm = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ATM_PATH))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(" ");
                atm = new ATM(data[0], data[1], data[2]);
            } else {
                throw new IOException("Empty file: " + FILE_ATM_PATH);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return atm;
    }

    @Override
    public Map<String, Card> getCards() {
        Map<String, Card> cards = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CARDS_PATH))) {
            String line;
            String[] data;
            while ((line = reader.readLine()) != null) {
                data = line.split(" ");
                cards.put(data[0],new Card(data[0], data[1], (data[2]), data[3], data[4]));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return cards;
    }

    @Override
    public void update(ATM atm) {
        saveATM(atm);
        saveCards(atm.getCards());
    }
}
