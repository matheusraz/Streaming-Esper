import java.util.Scanner;

import com.espertech.esper.client.EPServiceProvider;

public class SendEvents extends Thread {

	private EPServiceProvider engine;

	public SendEvents(EPServiceProvider engine) {
		this.engine = engine;
	}

	public void run() {
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
	}
}
