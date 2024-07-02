package repositories;

import models.ATM;
import models.Card;
import java.util.Map;

public interface BankEntityRepository<T extends ATM, J extends Map<String, Card>> {
    void saveATM(T t);
    void saveCards(J j);
    T getATM();
    J getCards();
    void update(T t);
}
