import controllers.ATMController;
import repositories.FileBankEntityRepository;
import views.ConsoleView;

public class Main {
    public static void main(String[] args) {
        FileBankEntityRepository repository = new FileBankEntityRepository("data/atm.txt", "data/cards.txt");
        ConsoleView consoleView = new ConsoleView();
        ATMController controller = new ATMController(repository, consoleView);
        controller.run();
    }
}