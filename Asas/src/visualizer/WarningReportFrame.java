package visualizer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.ByteArrayInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;

import org.xhtmlrenderer.resource.XMLResource;
import org.xhtmlrenderer.simple.FSScrollPane;
import org.xhtmlrenderer.simple.XHTMLPanel;

import statePersistence.StateService;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

public class WarningReportFrame extends JFrame{
	private XHTMLPanel reportText;
	private JButton okButton;
	
	public WarningReportFrame(String text){
		if(StateService.getInstance().hasValidState()){
			this.setTitle("Alertas Pendentes");
			configureElements(text);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setMinimumSize(new Dimension(400,400));
			this.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(this, "Nenhum estado válido foi carregado.");
		}
	}
	
	private void configureElements(String text){
		reportText = new XHTMLPanel();
		writeOnHtmlPanel(text);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{438, 0};
		gbl_contentPane.rowHeights = new int[]{234, 25, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		JScrollPane jp = new FSScrollPane(reportText);
		contentPane.add(jp, gbc);
		
		okButton = new JButton("Ok");
		okButton.setHorizontalAlignment(SwingConstants.LEFT);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.EAST;
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 1;
		contentPane.add(okButton, gbc_btnOk);		
	}
	
	private void writeOnHtmlPanel(String html){
		ByteArrayInputStream input = new ByteArrayInputStream(html.getBytes());
		XMLResource document = XMLResource.load(input);
		reportText.setDocument(document.getDocument());
	}
}
