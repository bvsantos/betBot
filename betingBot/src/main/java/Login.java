


import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import betBot.betingBot.App;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Login {

	protected Shell shlBettingBot;
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Login window = new Login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlBettingBot.open();
		shlBettingBot.layout();
		while (!shlBettingBot.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBettingBot = new Shell();
		shlBettingBot.setBackground(SWTResourceManager.getColor(0, 0, 0));
		shlBettingBot.setSize(450, 300);
		shlBettingBot.setText("Betting Bot");
		
		text = new Text(shlBettingBot, SWT.BORDER);
		text.setBounds(140, 44, 158, 34);
		
		
		text_1 = new Text(shlBettingBot, SWT.BORDER |SWT.PASSWORD);
		text_1.setBounds(140, 84, 158, 34);
		
		Label lblUsername = new Label(shlBettingBot, SWT.NONE);
		lblUsername.setForeground(SWTResourceManager.getColor(255, 255, 0));
		lblUsername.setBackground(SWTResourceManager.getColor(0, 0, 0));
		lblUsername.setTouchEnabled(true);
		lblUsername.setBounds(71, 54, 55, 15);
		lblUsername.setText("Username:");
		
		Label lblPassword = new Label(shlBettingBot, SWT.NONE);
		lblPassword.setBackground(SWTResourceManager.getColor(0, 0, 0));
		lblPassword.setForeground(SWTResourceManager.getColor(255, 255, 0));
		lblPassword.setBounds(71, 87, 55, 15);
		lblPassword.setText("Password:");
		
		Button btnNewButton = new Button(shlBettingBot, SWT.BORDER | SWT.FLAT | SWT.CENTER);
		btnNewButton.setGrayed(true);
		btnNewButton.setFont(SWTResourceManager.getFont("System", 16, SWT.NORMAL));
		btnNewButton.setForeground(SWTResourceManager.getColor(0, 0, 0));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				App.setLogin(text.getText(), text_1.getText());
				shlBettingBot.close();
				try {
					new BetInterface().open();
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(119, 138, 197, 65);
		btnNewButton.setText("Login");

	}
}
