import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.swt.SWT;
import System.Bet;
import System.BotSystem;
import betBot.betingBot.App;

import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class BetInterface {
	boolean b1 = true;
	boolean b2 = true;
	boolean b3 = true;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BetInterface window = new BetInterface();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void open() throws ClientProtocolException, IOException {
		Display display = Display.getDefault();
		final Shell shell = new Shell();
		shell.setSize(750, 700);
		shell.setText("Pick Bets");
		shell.setLayout(null);
		final BotSystem b = new BotSystem();
		List list = new List(shell, SWT.BORDER);
		list.setBounds(48, 10, 643, 151);
		for(Bet be: b.getBet(0)) {
			list.add(be.getInfo());
		}
		
		
		List list_1 = new List(shell, SWT.BORDER);
		list_1.setBounds(48, 188, 643, 159);
		for(Bet be: b.getBet(1)) {
			list_1.add(be.getInfo());
		}
		List list_2 = new List(shell, SWT.BORDER);
		list_2.setBounds(48, 376, 643, 151);
		for(Bet be: b.getBet(2)) {
			list_2.add(be.getInfo());
		}
		
		Button btnMakeMoney = new Button(shell, SWT.NONE);
		btnMakeMoney.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					App.makeBet(b1, b2, b3, b);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				shell.close();
			}
		});
		btnMakeMoney.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnMakeMoney.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		btnMakeMoney.setBounds(271, 554, 187, 63);
		btnMakeMoney.setText("Make Money!");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(707, 101, 93, 16);
		
		Button button = new Button(composite, SWT.CHECK);
		button.setSelection(true);
		button.setBounds(0, 0, 93, 16);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(b1)
					b1 = false;
				else
					b1 = true;
			}
		});
		Button button_1 = new Button(shell, SWT.CHECK);
		button_1.setSelection(true);
		button_1.setBounds(707, 278, 93, 16);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(b2)
					b2 = false;
				else
					b2 = true;
			}
			}
		);
		
		Button button_2 = new Button(shell, SWT.CHECK);
		button_2.setSelection(true);
		button_2.setBounds(707, 498, 93, 16);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(b3)
					b3 = false;
				else
					b3 = true;
			
			}
		});

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
