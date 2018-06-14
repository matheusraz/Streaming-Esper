import java.sql.NClob;
import java.util.ArrayList;
import java.util.List;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.collection.Pair;

public class Inicio {

	public static void main(String[] args) {
		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		engine.getEPAdministrator().getConfiguration().addEventType(TemperatureEvent.class);

		// 4� QUEST�O
		// Por query
		
		String epl = "select temp from TemperatureEvent";
		EPStatement statement = engine.getEPAdministrator().createEPL(epl);

		statement.addListener((newData, oldData) -> {
			
		});
		
		// 5� QUEST�O 
		// Filtrando pelo select

		String epl1 = "select count(nCracha) as qtdCracha from CatracaEvent#time(10 sec) where isEntrando = true";
		String epl2 = "select count(nCracha) as qtdCracha from CatracaEvent#time(10 sec) where isEntrando = false";
		String epl3 = "select nCracha, isEntrando from CatracaEvent";

		EPStatement statement1 = engine.getEPAdministrator().createEPL(epl1);
		EPStatement statement2 = engine.getEPAdministrator().createEPL(epl2);
		EPStatement statement3 = engine.getEPAdministrator().createEPL(epl3);

		statement1.addListener((newData, oldData) -> {
			long qtd = (long) newData[0].get("qtdCracha");
			System.out.println(String.format("Entraram %d pessoas na �ltima hora", qtd));
		});

		statement2.addListener((newData, oldData) -> {
			long qtd = (long) newData[0].get("qtdCracha");
			System.out.println(String.format("Sa�ram %d pessoas na �ltima hora", qtd));
		});

		statement3.addListener((newData, oldData) -> {
			long crachaNovo = (long) newData[0].get("nCracha");
			boolean novo = (boolean) newData[0].get("isEntrando");
			if(oldData != null) {
				boolean velho = (boolean) oldData[0].get("isEntrando");
				if(novo == true && novo == velho) {
					System.out.println(String.format("N�o � poss�vel a pessoa de identifica��o %d entrar sem estar fora do pr�dio", crachaNovo));
				}
				else if(novo == false && novo == velho) {
					System.out.println(String.format("N�o � poss�vel a pessoa de identifica��o %d sair sem estar dentro do pr�dio", crachaNovo));
				}
			}
		});

		new SendEvents(engine).start();
	}

}
