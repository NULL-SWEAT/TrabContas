package br.univel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Tela extends JFrame {

	private JPanel contentPane;
	private JTextField cod;
	private JTextField val;
	private JTextArea textArea;
	private JTextField id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela frame = new Tela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Tela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.append(carregarConta());
		scrollPane.setViewportView(textArea);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{75, 33, 86, 24, 86, 83, 0};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblCodigo = new JLabel("Codigo");
		GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
		gbc_lblCodigo.anchor = GridBagConstraints.EAST;
		gbc_lblCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodigo.gridx = 0;
		gbc_lblCodigo.gridy = 0;
		panel.add(lblCodigo, gbc_lblCodigo);
		
		cod = new JTextField();
		GridBagConstraints gbc_cod = new GridBagConstraints();
		gbc_cod.gridwidth = 2;
		gbc_cod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cod.insets = new Insets(0, 0, 5, 5);
		gbc_cod.gridx = 1;
		gbc_cod.gridy = 0;
		panel.add(cod, gbc_cod);
		cod.setColumns(10);
		
		JLabel lblValor = new JLabel("Valor");
		GridBagConstraints gbc_lblValor = new GridBagConstraints();
		gbc_lblValor.anchor = GridBagConstraints.EAST;
		gbc_lblValor.insets = new Insets(0, 0, 5, 5);
		gbc_lblValor.gridx = 3;
		gbc_lblValor.gridy = 0;
		panel.add(lblValor, gbc_lblValor);
		
		val = new JTextField();
		val.setText("");
		GridBagConstraints gbc_val = new GridBagConstraints();
		gbc_val.fill = GridBagConstraints.HORIZONTAL;
		gbc_val.insets = new Insets(0, 0, 5, 5);
		gbc_val.gridx = 4;
		gbc_val.gridy = 0;
		panel.add(val, gbc_val);
		val.setColumns(10);
		
		JButton btnAddConta = new JButton("Add conta");
		GridBagConstraints gbc_btnAddConta = new GridBagConstraints();
		gbc_btnAddConta.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddConta.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAddConta.gridx = 5;
		gbc_btnAddConta.gridy = 0;
		panel.add(btnAddConta, gbc_btnAddConta);
		btnAddConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addConta();
			}
		});
		
		JLabel lblId = new JLabel("ID");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.insets = new Insets(0, 0, 0, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 1;
		panel.add(lblId, gbc_lblId);
		
		id = new JTextField();
		GridBagConstraints gbc_id = new GridBagConstraints();
		gbc_id.gridwidth = 2;
		gbc_id.insets = new Insets(0, 0, 0, 5);
		gbc_id.fill = GridBagConstraints.HORIZONTAL;
		gbc_id.gridx = 1;
		gbc_id.gridy = 1;
		panel.add(id, gbc_id);
		id.setColumns(10);
		
		JButton btnDeletar = new JButton("Deletar");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delConta();
			}
		});
		GridBagConstraints gbc_btnDeletar = new GridBagConstraints();
		gbc_btnDeletar.anchor = GridBagConstraints.WEST;
		gbc_btnDeletar.insets = new Insets(0, 0, 0, 5);
		gbc_btnDeletar.gridx = 3;
		gbc_btnDeletar.gridy = 1;
		panel.add(btnDeletar, gbc_btnDeletar);
		
	}
	
	public void addConta() {
		Conta c = new Conta();
		if(cod.getText().matches("(\\d+\\.?)*\\d+")) {
			c.setCodigo(cod.getText());
		}
		if(val.getText().matches("(\\d+\\.?)*\\d+")) {
			c.setValor(new BigDecimal(val.getText()));
		}
		ContaDAO cDao = new ContaDAO();
		cDao.insert(c);
		
		textArea.setText("");
		textArea.append(carregarConta());
		cod.setText("");
		val.setText("");
	}
	
	public void delConta() {
		ContaDAO cDao = new ContaDAO();
		if(id.getText().matches("\\d+")) {
			cDao.delete(Integer.parseInt(id.getText()));
			id.setText("");
			textArea.setText("");
			textArea.append(carregarConta());
		}
	}
	
	public String carregarConta() {
		Conta contaRoot = new Conta();
		
		ContaDAO cDao = new ContaDAO();
		
		for (Conta c : cDao.getContas()) {
			contaRoot.add(c);
		}

        // Zera todos os nulos.
        contaRoot.zeraNulos();

        // Calcula valores dos totalizadores.
        contaRoot.updateTotalizadores();

        return contaRoot.toString();
	}

}
