
public class CatracaEvent {

	private Long nCracha;
	private String nome;
	private boolean isEntrando = false;
	
	public CatracaEvent(Long nCracha, String nome, boolean isEntrando) {
		this.nCracha = nCracha;
		this.nome = nome;
		this.isEntrando = isEntrando;
	}
	
	public Long getnCracha() {
		return nCracha;
	}
	public void setnCracha(Long nCracha) {
		this.nCracha = nCracha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isEntrando() {
		return isEntrando;
	}
	public void setEntrando(boolean isEntrando) {
		this.isEntrando = isEntrando;
	}
	
}
