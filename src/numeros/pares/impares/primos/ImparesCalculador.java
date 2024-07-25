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

public class ImparesCalculador extends SwingWorker<Integer, Integer> {
	
	private static final SecureRandom gerador = new SecureRandom();
	private final JTextArea intermediateJTextArea;
	private final JButton btnImpares;
	private final JButton btnCancel;
	private final JLabel statusLabel;
	private final boolean[] impares;
	
	public ImparesCalculador(int max, JTextArea intermediateJTextArea, JLabel statusLabel, 
			JButton btnImpares, JButton btnCancel) {
		this.intermediateJTextArea = intermediateJTextArea;
		this.btnImpares = btnImpares;
		this.btnCancel = btnCancel;
		this.statusLabel = statusLabel;
		impares = new boolean[max];
		
		Arrays.fill(impares, true);
	}

	@Override
	protected Integer doInBackground() throws Exception {
		
		int contador = 0;
		
		for(int i = 1; i < impares.length; i++) {
			if(isCancelled())
				return contador;
			else {
				setProgress(100 * (i + 1) / impares.length);
				
				try {
					
					Thread.sleep(gerador.nextInt(5));
				} catch (InterruptedException e) {
					statusLabel.setText("Thread paralizada");
					return contador;
				}
				if(impares[i]) {
					publish(i);
					++contador;
					
					for(int j = i + i; j < impares.length; j += 2)
						impares[j] = false;
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
		btnImpares.setEnabled(true);
		btnCancel.setEnabled(true);
		
		try {
			statusLabel.setText("Gerado " + get() + " números impares.");
			
		} catch (InterruptedException | ExecutionException |
				CancellationException e) {
			statusLabel.setText(e.getMessage());
		}
	}
	
	
	
}
