import java.util.ArrayList;
import java.util.List;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class Inicio {

	public static void main(String[] args) {
		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		engine.getEPAdministrator().getConfiguration().addEventType(TemperatureEvent.class);

		String epl = "select temp from TemperatureEvent";
		EPStatement statement = engine.getEPAdministrator().createEPL(epl);

		// 4ª Questão
		
		/*List<Double> totalTemp = new ArrayList<>();

		List<Long> tuple = new ArrayList<>();
		Long initTime = System.currentTimeMillis();
		tuple.add(initTime);
		tuple.add((long) 0);
		
		statement.addListener( (newData, oldData) -> {
			double novo = (double) newData[0].get("temp");
			if(totalTemp.size() == 100) {
				double sum = 0;
				for(int i=0; i<totalTemp.size();i++) {
					sum += (double) totalTemp.get(i);
				}
				System.out.println(String.format("Média das últimas 3 temperaturas até a temperatura: %.2f°: %.2f°", totalTemp.get(99), sum/totalTemp.size()));
				totalTemp.clear();
			} 
			if((System.currentTimeMillis() - tuple.get(0)) >= 180000) {
				System.out.println(String.format("Após 3 minutos houve %d eventos", tuple.get(1)));
				tuple.set(0, System.currentTimeMillis());
			}
			if(novo > 25.0) {
				System.out.println(String.format("A temperatura %.2f° é superior à 25°", novo));
			}
			totalTemp.add(novo);
			tuple.set(1, tuple.get(1)+1);
		});*/

		new SendEvents(engine).start();
	}

}
