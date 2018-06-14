import java.util.Scanner;

import com.espertech.esper.client.EPServiceProvider;

public class SendEvents extends Thread {

	private EPServiceProvider engine;

	public SendEvents(EPServiceProvider engine) {
		this.engine = engine;
	}

	//4ª Questão
	
	/*public void run() {
		try(Scanner scan = new Scanner(System.in)) {
			try {
				String input;
				while(true) {
					System.out.println("Informe o valor da temperatura: ");
					input = scan.nextLine();
					engine.getEPRuntime().sendEvent(new TemperatureEvent(Double.parseDouble(input)));
				}
			} catch (Exception e) {
				System.out.println("Fim do envio de eventos.");
			}
		}
	}*/
	
	
	//5ª Questão
	public void run() {
		try(Scanner scan = new Scanner(System.in)) {
			try {
				String inputCracha;
				String inputNome;
				String passagem;
				boolean isEntrando;
				while(true) {
					System.out.println("Informe o numero do crachá: ");
					inputCracha = scan.nextLine();
					System.out.println("Informe seu nome: ");
					inputNome = scan.nextLine();
					System.out.println("Informe se está entrando (1) ou saindo (0): ");
					passagem = scan.nextLine();
					isEntrando = (Short.parseShort(passagem) == 1) ? true : false;
					engine.getEPRuntime().sendEvent(new CatracaEvent(Long.parseLong(inputCracha), inputNome, isEntrando));
				}
			} catch (Exception e) {
				System.out.println("Fim do envio de eventos.");
			}
		}
	}
	
}
