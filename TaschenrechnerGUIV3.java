package taschenrechner;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class TaschenrechnerGUIV3 extends JFrame {

	// automatisch über Eclipse eingefügt
	private static final long serialVersionUID = 4668009235734676602L;

	// die Komponente
	// zwei Eingabefelder
	// jetzt mit Format-Vorgaben
	private JFormattedTextField eingabe1, eingabe2;
	// eine Kombinationfeld
	private JComboBox<String> komboListBox;
	// eine Schaltfläche
	private JButton schaltflaecheBeenden;
	// Label für die Ausgabe
	private JLabel ausgabe;

	// die innere Klasse für die Ereignisverarbeitung
	class MeinListener implements ActionListener, ItemListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// wurde auf Beenden geklickt?
			// dann das Programm beenden
			if (e.getActionCommand().equals("ende"))
				System.exit(0);
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
//			in einem Object ablegen
			Object ausloeser = e.getSource();
			// prüfen ob komponent JComboBox ist ausgelöst
			if (ausloeser instanceof JComboBox) {
				ausgabe.setText(berechnen());
			}
		}
	}

	// der Konstruktor
	public TaschenrechnerGUIV3(String titel) {

		super(titel);

		// insgesamt vier Panels
		JPanel panelEinAus, panelBerechnung, panelButtons, gross;

		// die Panels über die Methoden erstellen
		panelEinAus = panelEinAusErzeugen();
		panelBerechnung = panelBerechnungErzeugen();
		panelButtons = panelButtonErzeugen();

		// das Border-Layout benutzen
		// es ist der Standard und muss nicht gesetzt werden

		// die Panels hinzufügen
		// die beiden größeren Panel fassen wir noch einmal zusammen
		gross = new JPanel();
		gross.add(panelEinAus);
		gross.add(panelBerechnung);
		// die beiden kommen in die Mitte
		add(gross, BorderLayout.CENTER);
		// das Panel mit die Button nach rechts
		add(panelButtons, BorderLayout.EAST);

		// die Standard-Aktion setzen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// packen und anzeigen
		pack();
		setVisible(true);

		// Größenänderungen sind nicht zugelassen
		// damit das mühsam erstellte Layout nicht durcheinander kommt
		setResizable(false);
	}

	// die Methode erzeugt das Panel für die Ein- und Ausgabe
	// und liefert es zurück
	private JPanel panelEinAusErzeugen() {
		JPanel tempPanel = new JPanel();
		// es enthält die Eingabefelder mit beschreibendem Text und die Ausgabe
		// für die Eingabefelder wird jetzt auch ein Format vorgegeben
		eingabe1 = new JFormattedTextField(new DecimalFormat("#.##"));
		eingabe2 = new JFormattedTextField(new DecimalFormat("#.##"));
		ausgabe = new JLabel("");

		// das Panel bekommt ein GridLaoyut mit zwei Spalten und etwas Abstand
		tempPanel.setLayout(new GridLayout(0, 2, 10, 10));
		// ein beschreibendes Label für die erste Eingabe
		tempPanel.add(new JLabel("Zahl 1:"));
		// das erste Eingabefeld
		tempPanel.add(eingabe1);

		// und jetzt das zweite Eingabefeld
		tempPanel.add(new JLabel("Zahl 2: "));
		tempPanel.add(eingabe2);

		// und nun das Ausgabefeld für das Ergebnis
		tempPanel.add(new JLabel("Ergebnis: "));
		tempPanel.add(ausgabe);

		// einen Rahmen um das Panel ziehen
		tempPanel.setBorder(new TitledBorder("Ein- und Ausgabe"));

		// das Panel zurückliefern
		return tempPanel;
	}

	// die Methode erzeugt das Panel für die Auswahl
	// der Rechenoperation und liefert es zurück
	private JPanel panelBerechnungErzeugen() {

		JPanel tempPanel = new JPanel();
		// das Panel erhällt KombinationFelder
		// Kombinationfelder mit Array
		String kobbinationListe[] = { "addition", "substraktion", "multiplikation", "division" };
		komboListBox = new JComboBox<String>(kobbinationListe);
		tempPanel.add(komboListBox);

		// das Panel bekommt ein GridLaoyut mit einer Spalte
		tempPanel.setLayout(new GridLayout(0, 1));

		// und einen Rahmen
		tempPanel.setBorder(new TitledBorder("Operation: "));

		MeinListener listener = new MeinListener();
		komboListBox.addItemListener(listener);

		// das Panel zurückliefern
		return tempPanel;
	}

	// die Methode erzeugt das Panel für die Schaltfläche
	// und liefert es zurück
	private JPanel panelButtonErzeugen() {
		JPanel tempPanel = new JPanel();

		schaltflaecheBeenden = new JButton(" Beenden ");
		// das Aktion-Command setzen
		schaltflaecheBeenden.setActionCommand("ende");

		// Zwischenpanel für die Schaltflächen
		// ebenfalls ein GridLayout
		tempPanel.setLayout(new GridLayout(0, 1));
		tempPanel.add(schaltflaecheBeenden);
		// zwei leere Labels einfügen
		tempPanel.add(new JLabel());
		tempPanel.add(new JLabel());
		tempPanel.add(schaltflaecheBeenden);

		// die Schaltflächen mit dem Listener verbinden
		MeinListener listener = new MeinListener();
		schaltflaecheBeenden.addActionListener(listener);

		// das Panel zurückliefern
		return tempPanel;
	}

	// die Methode berechnet das Ergebnis und liefert es als
	// Zeichenkette zurück
	private String berechnen() {
		// ergebnis muss initialisiert werden
		double zahl1, zahl2, ergebnis = 0;
		boolean fehlerFlag = false;

		// ungültige Werte können wegen der Maske nicht im Feld stehen
		// allerdings müssen wir die Ausnahme ParseException behandeln
		// da weisen wir einfach den Wert 0 zu
		try {
			// für das Konvertieren
			Number wert = NumberFormat.getNumberInstance(Locale.GERMANY).parse(eingabe1.getText());
			zahl1 = wert.doubleValue();
		} catch (Exception ParseException) {
			zahl1 = 0;
		}

		try {
			// für das Konvertieren
			Number wert = NumberFormat.getNumberInstance(Locale.GERMANY).parse(eingabe2.getText());
			zahl2 = wert.doubleValue();
		} catch (Exception ParseException) {
			zahl2 = 0;
		}

		// welche Operation ist ausgewählt?
		if (komboListBox.getSelectedIndex() == 0)
			ergebnis = zahl1 + zahl2;
		if (komboListBox.getSelectedIndex() == 1)
			ergebnis = zahl1 - zahl2;
		if (komboListBox.getSelectedIndex() == 2)
			ergebnis = zahl1 * zahl2;
//		//bei der Division überprüfen wir den zweiten Wert auf 0
		if (komboListBox.getSelectedIndex() == 3) {
			if (zahl2 != 0)
				ergebnis = zahl1 / zahl2;
			else
				fehlerFlag = true;
		}
//		//wenn es keine Probleme gegeben hat, liefern wir das Ergebnis zurück
		if (fehlerFlag == false) {
//			//das Ergebnis zurückgeben
			return (Double.toString(ergebnis));
		} else
			return ("n. def.");
	}

	public static void main(String[] args) {
		new TaschenrechnerGUIV3("Taschen Rechner 3");
	}
}
