package numeros.pares.impares.primos;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class PrimosCalculador extends SwingWorker<Integer, Integer> {
	
	private static final SecureRandom gerador = new SecureRandom();
	private final JTextArea intermediateJTextArea;
	private final JButton btnPrimes;
	private final JButton btnCancel;
	private final JLabel statusLabel;
	private final boolean[] primes;
	
	public PrimosCalculador(int max, JTextArea intermediateJTextArea, JLabel statusLabel, 
			JButton btnPrimes, JButton btnCancel) {
		this.intermediateJTextArea = intermediateJTextArea;
		this.btnPrimes = btnPrimes;
		this.btnCancel = btnCancel;
		this.statusLabel = statusLabel;
		primes = new boolean[max];
		
		Arrays.fill(primes, true);
	}

	@Override
	protected Integer doInBackground() throws Exception {
		
		int contador = 0;
		
		for(int i = 2; i < primes.length; i++) {
			if(isCancelled())
				return contador;
			else {
				setProgress(100 * (i + 1) / primes.length);
				
				try {
					
					Thread.sleep(gerador.nextInt(5));
				} catch (InterruptedException e) {
					statusLabel.setText("Thread paralizada");
					return contador;
				}
				if(primes[i]) {
					publish(i);
					++contador;
					
					for(int j = i + i; j < primes.length; j += i )
						primes[j] = false;
				}
			}
		}
			
		return contador;
	}
	
	protected Integer doInBackground1() throws Exception {
		
		int contador = 0;
		
		for(int i = 2; i < primes.length; i++) {
			if(isCancelled())
				return contador;
			else {
				setProgress(100 * (i + 1) / primes.length);
				
				try {
					
					Thread.sleep(gerador.nextInt(5));
				} catch (InterruptedException e) {
					statusLabel.setText("Thread paralizada");
					return contador;
				}
				if(primes[i]) {
					publish(i);
					++contador;
					
					for(int j = i + i; j < primes.length; j += i )
						primes[j] = false;
				}
			}
		}
			
		return contador;
	}
	
	protected void process(List<Integer> publishValor) {
		for(int i = 0; i < publishValor.size(); i++)
			intermediateJTextArea.append(publishValor.get(i) + "\n");
	}
	
	protected void done() {
		btnPrimes.setEnabled(true);
		btnCancel.setEnabled(true);
		
		try {
			statusLabel.setText("Gerado " + get() + " números primos.");
			
		} catch (InterruptedException | ExecutionException |
				CancellationException e) {
			statusLabel.setText(e.getMessage());
		}
	}
	
	
	
}
