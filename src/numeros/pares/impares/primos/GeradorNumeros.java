package numeros.pares.impares.primos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class GeradorNumeros extends JFrame{

	private static final long serialVersionUID = 1L;
	private final JTextField highestPrime = new JTextField();
	private final JButton btnBuscarPrimo = new JButton("Gerando primos");
	private final JButton btnBuscarImpares = new JButton("Gerando impares");
	private final JTextArea mostrarPrimos = new JTextArea();
	private final JButton btnCancelar = new JButton("Cancelar");
	private final JLabel statusLabel = new JLabel();
	private final JProgressBar progressBar = new JProgressBar();
	private PrimosCalculador calculador;
	private ImparesCalculador calculadorImpares;
	
	public GeradorNumeros() {
		super("Gerando números[Primos, Impares]");
		super.setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel();
		northPanel.add(new JLabel("Gerador de número [Primos, Ímpares]: "));
		highestPrime.setColumns(5);
		northPanel.add(highestPrime);
		
		btnBuscarImpares.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(10);
				mostrarPrimos.setText("");
				statusLabel.setText("");
				
				int numero;
				
				
				if(highestPrime.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Digite um numero inteiro","Campo vazio", JOptionPane.ERROR_MESSAGE);
				} else {
					
					try {
						numero = Integer.parseInt(highestPrime.getText());
					} catch (NumberFormatException exception) {
						statusLabel.setText("Digite um numero inteiro");
						return;
					}
					
					calculadorImpares = new ImparesCalculador(numero, mostrarPrimos, statusLabel, btnBuscarPrimo, btnCancelar);
					
					calculadorImpares.addPropertyChangeListener(new PropertyChangeListener() {
						
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							if(evt.getPropertyName().equals("progress")) {
								int novo_valor = (Integer) evt.getNewValue();
								progressBar.setValue(novo_valor);
							}
							
						}
					});
					btnBuscarPrimo.setEnabled(false);
					btnCancelar.setEnabled(true);
					
					calculadorImpares.execute();
				}
				
			}
			
		});
		
		btnBuscarPrimo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(10);
				mostrarPrimos.setText("");
				statusLabel.setText("");
				
				int numero;
				
				try {
					numero = Integer.parseInt(highestPrime.getText());
				} catch (NumberFormatException exception) {
					statusLabel.setText("Digite um numero inteiro");
					return ;
				}
				
				calculador = new PrimosCalculador(numero, mostrarPrimos, statusLabel, btnBuscarPrimo, btnCancelar);
				
				calculador.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if(evt.getPropertyName().equals("progress")) {
							int novo_valor = (Integer) evt.getNewValue();
							progressBar.setValue(novo_valor);
						}
						
					}
				});
				btnBuscarPrimo.setEnabled(false);
				btnCancelar.setEnabled(true);
				
				calculador.execute();
				
			}
		});
		
		northPanel.add(btnBuscarImpares);
		northPanel.add(btnBuscarPrimo);
		
		mostrarPrimos.setEditable(false);
		add(mostrarPrimos);
		add(new JScrollPane(mostrarPrimos, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
		
		JPanel southPanel =  new JPanel(new GridLayout(1, 3, 10, 10));
		btnCancelar.setEnabled(false);
		
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				progressBar.resetKeyboardActions();
				calculadorImpares.cancel(true);
				
			}
		});
		
		southPanel.add(btnCancelar);
		progressBar.setStringPainted(true);
		progressBar.setBackground(Color.ORANGE);
		southPanel.add(progressBar);
		southPanel.add(statusLabel);
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		this.setResizable(false);
		this.setSize(600, 650);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		GeradorNumeros application = new GeradorNumeros();
		application.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
