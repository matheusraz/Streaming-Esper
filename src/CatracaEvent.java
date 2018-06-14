
public class CatracaEvent {

	private Long nCracha;
	private String nome;
	private Boolean isEntrando;
	
	public CatracaEvent(Long nCracha, String nome, Boolean isEntrando) {
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
	public Boolean getisEntrando() {
		return isEntrando;
	}
	public void setisEntrando(Boolean isEntrando) {
		this.isEntrando = isEntrando;
	}
	
}
