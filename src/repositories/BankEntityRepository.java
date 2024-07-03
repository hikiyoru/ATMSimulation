package repositories;

import models.ATM;
import models.Card;
import java.util.Map;

public interface BankEntityRepository<T extends ATM, J extends Map<String, Card>> {
    T getATM();
    J getCards();
    void saveATM();
    void saveCards();
    void saveAll();
}
