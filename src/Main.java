import models.ATM;
import repository.FileBankEntityRepository;
import views.ConsoleView;

public class Main {
    public static void main(String[] args) {
        FileBankEntityRepository repository = new FileBankEntityRepository();
        ATM atm = repository.getATM();
        atm.setCards(repository.getCards());
        ConsoleView consoleView = new ConsoleView(atm, repository);
        consoleView.run();
    }
}