import Models.ATM;
import Repository.FileBankEntityRepository;
import Views.ConsoleView;

public class Main {
    public static void main(String[] args) {
        FileBankEntityRepository repository = new FileBankEntityRepository();
        ATM atm = repository.getATM();
        atm.setCards(repository.getCards());
        ConsoleView consoleView = new ConsoleView(atm, repository);
        consoleView.run();
    }
}