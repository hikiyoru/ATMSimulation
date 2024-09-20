import controllers.ATMController;
import repositories.FileBankEntityRepository;
import views.ConsoleView;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Specify the paths to the data files in the program launch arguments (first arg - atm.txt, second arg - cards.txt)");
            System.exit(1);
        }
        FileBankEntityRepository repository = new FileBankEntityRepository(args[0], args[1]);
        ConsoleView consoleView = new ConsoleView();
        ATMController controller = new ATMController(repository, consoleView);
        controller.run();
    }
}