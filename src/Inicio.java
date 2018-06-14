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


		// 4ª QUESTÃO

		/*String epl = "select temp from TemperatureEvent";
		EPStatement statement = engine.getEPAdministrator().createEPL(epl);

		List<Double> totalTemp = new ArrayList<>();

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


		//5ª QUESTÃO

		//Usando o código puro dando select completo.

		/*String epl = "select nCracha, nome, isEntrando from CatracaEvent";
		EPStatement statement = engine.getEPAdministrator().createEPL(epl);

		List<Long> tuple = new ArrayList<>();
		tuple.add(System.currentTimeMillis());
		tuple.add((long) 0);
		tuple.add((long) 0);
		List<Pair<Long, Boolean>> status = new ArrayList<>();

		statement.addListener((newData, oldData) -> {
			CatracaEvent novo = new CatracaEvent((long) newData[0].get("nCracha"), (String) newData[0].get("nome"), (Boolean) newData[0].get("isEntrando"));

			Pair<Boolean, Integer> pStatus = searchPersonStatus(status, novo.getnCracha());
			if(pStatus != null && pStatus.getFirst().equals(novo.getisEntrando())) {
				if(novo.getisEntrando() == true) {
					System.out.println(String.format("A pessoa de identificação %d não pode entrar uma vez já dentro\n", novo.getnCracha()));
				} else {
					System.out.println(String.format("A pessoa de identificação %d não pode sair uma vez já fora\n", novo.getnCracha()));
				}
			} else if(pStatus == null) {
				status.add(new Pair<Long, Boolean>(novo.getnCracha(), novo.getisEntrando()));
				tuple.set(1, tuple.get(1)+1);
			} else {
				status.set(pStatus.getSecond(), new Pair<Long, Boolean>(novo.getnCracha(),novo.getisEntrando()));
				if(novo.getisEntrando()) {
					tuple.set(1, tuple.get(1)+1);
				} else {
					tuple.set(2, tuple.get(2)+1);
				}
			}
			if((System.currentTimeMillis() - tuple.get(0)) >= 10000) {
				System.out.println(String.format("Entraram %d pessoas na última hora", tuple.get(1)));
				System.out.println(String.format("Saíram %d pessoas na última hora", tuple.get(2)));
				tuple.set(0, System.currentTimeMillis());
			}
		});

		// 5ª QUESTÃO 
		 * Filtrando pelo select
		 */

		String epl1 = "select count(nCracha) as qtdCracha from CatracaEvent where isEntrando = true";
		String epl2 = "select count(nCracha) as qtdCracha from CatracaEvent where isEntrando = false";
		String epl3 = "select nCracha, isEntrando from CatracaEvent";

		EPStatement statement1 = engine.getEPAdministrator().createEPL(epl1);
		EPStatement statement2 = engine.getEPAdministrator().createEPL(epl2);
		EPStatement statement3 = engine.getEPAdministrator().createEPL(epl3);

		List<Long> tuple = new ArrayList<>();
		tuple.add(System.currentTimeMillis());
		tuple.add(System.currentTimeMillis());

		statement1.addListener((newData, oldData) -> {
			long qtd = (long) newData[0].get("qtdCracha");
			boolean baterCrachaAtual = (boolean) newData[0].get("isEntrando");
			boolean baterCrachaAntigo = (boolean) oldData[0].get("isEntrando");

			if((System.currentTimeMillis() - tuple.get(0)) >= 10000) {
				System.out.println(String.format("Entraram %d pessoas na última hora", qtd));
				tuple.set(0, System.currentTimeMillis());
			}
			if(baterCrachaAtual == baterCrachaAntigo) {
				System.out.println();
			}
		});

		statement2.addListener((newData, oldData) -> {
			long qtd = (long) newData[0].get("qtdCracha");
			if((System.currentTimeMillis() - tuple.get(1)) >= 10000) {
				System.out.println(String.format("Saíram %d pessoas na última hora", qtd));
				tuple.set(1, System.currentTimeMillis());
			}
		});

		statement3.addListener((newData, oldData) -> {
			long crachaNovo = (long) newData[0].get("nCracha");
			boolean novo = (boolean) newData[0].get("isEntrando");
			if(oldData != null) {
				boolean velho = (boolean) oldData[0].get("isEntrando");
				if(novo == true && novo == velho) {
					System.out.println(String.format("Não é possível a pessoa de identificação %d entrar sem estar fora do prédio", crachaNovo));
				}
				else if(novo == false && novo == velho) {
					System.out.println(String.format("Não é possível a pessoa de identificação %d sair sem estar dentro do prédio", crachaNovo));
				}
			}
		});

		new SendEvents(engine).start();
	}

	public static Pair<Boolean, Integer> searchPersonStatus(List<Pair<Long, Boolean>> status, Long nCracha) {
		for(int i = 0; i<status.size(); ++i) {
			if(status.get(i).getFirst().equals(nCracha)) {
				return new Pair<Boolean, Integer>(status.get(i).getSecond(), i);
			}
		}
		return null;
	}

}
