package br.univel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Conta {

	private int id;
	private String codigo;
	private List<Conta> filhas;
	private int levelId;
	private BigDecimal valor;

	// GETTERS/SETTERS

	public int getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public List<Conta> getFilhas() {
		return filhas;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setFilhas(List<Conta> filhas) {
		this.filhas = filhas;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValor() {
		return valor;
	}

	// UTEIS

	public void add(Conta conta) {

		String[] caminho = conta.getCodigo().split("\\.");

		int[] caminhoInt = new int[caminho.length];

		for (int i = 0; i < caminho.length; i++) {
			caminhoInt[i] = Integer.parseInt(caminho[i]);
		}

		Conta step = this;
		for (int i = 0; i < caminhoInt.length; i++) {
			step = step.getFilha(caminhoInt[i]);
		}

		step.setId(conta.getId());
		step.setCodigo(conta.getCodigo());
		step.setValor(conta.getValor());

	}

	public void setValor(String codigo, BigDecimal valor) {

		String[] caminho = codigo.split("\\.");

		int[] caminhoInt = new int[caminho.length];

		for (int i = 0; i < caminho.length; i++) {
			caminhoInt[i] = Integer.parseInt(caminho[i]);
		}

		Conta step = this;
		for (int i = 0; i < caminhoInt.length; i++) {
			step = step.getFilha(caminhoInt[i]);
		}

		step.setValor(valor);
	}

	private Conta getFilha(int i) {

		Conta filha = null;

		if (filhas == null) {
			filhas = new ArrayList<Conta>();
		}

		for (Conta c : filhas) {
			if (c.getLevelId() == i) {
				filha = c;
			}
		}

		if (filha == null) {
			filha = new Conta();
			filha.setLevelId(i);
			filhas.add(filha);
		}

		return filha;
	}

	public void zeraNulos() {
		zeraNulos(this);
	}

	private void zeraNulos(Conta c) {
		if (c.getValor() == null) {
			c.setValor(new BigDecimal(0));
		}

		if (c.getFilhas() != null) {
			for (Conta filha : c.getFilhas()) {
				zeraNulos(filha);
			}
		}

	}

	public void updateTotalizadores() {

		BigDecimal total = new BigDecimal(0);

		if (getFilhas() != null) {

			for (Conta filha : getFilhas()) {
				total = total.add(getTotalDasFilhas(filha));
			}

			setValor(total);

		} else {
			total = total.add(getValor());

		}
	}

	private BigDecimal getTotalDasFilhas(Conta c) {
		BigDecimal total = new BigDecimal(0);

		if (c.getFilhas() != null) {
			for (Conta filha : c.getFilhas()) {
				total = total.add(getTotalDasFilhas(filha));
			}
			c.setValor(total);
		} else {
			if (c.getValor() != null) {
				total = total.add(c.getValor());
			}
		}
		return total;
	}

	// TOSTRING E RELACIONADOS

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		int level = 1;

		sb.append(toString(level, this));

		return sb.toString();
	}

	private String toString(int level, Conta c) {

		StringBuilder sb = new StringBuilder();

		sb.append(getDesenhoLinha(level));

		if (c.getCodigo() == null) {
			sb.append("NULL");
		} else {
			sb.append(c.getCodigo());
		}

		sb.append(" - ").append(c.getId()).append(" - ").append(c.getValor());
		sb.append('\n');

		if (c.getFilhas() != null) {
			for (Conta filha : c.getFilhas()) {
				sb.append(toString(level + 1, filha));
			}
		}

		return sb.toString();
	}

	private String getDesenhoLinha(int level) {
		char[] ar = new char[level + 1];
		Arrays.fill(ar, ' ');
		ar[ar.length - 2] = '|';
		ar[ar.length - 1] = '-';
		return new String(ar);
	}

}