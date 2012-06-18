package de.fehler;

public class ProduktivFehler {

	public ProduktivFehler()
	{
		// TODO 20.12.2011 zu alt
		// TODO 21.12.2011 Stichtag
		// TODO 22.12.2011 noch mit drin!
		// macht nichts, es folgen die Kommentare aus der Produktion:
        // TODO 01.05.2012 muss JSF-Kontekst mocken, z.B. in MbNeuJsfBeanUtils.ladeBackingBean() zuerst in Springkontekst suchen
        // TODO 01.05.2012 Daten von wrappers hier kriegen
        // TODO 01.05.2012 MNVQSUCH RuleSearchControllerBeanImpl.saveSettings() Hibernate-Problem
        // TODO 01.05.2012 controllerBean.setAktiverReiter() nicht so praktisch
        // TODO 01.05.2012 Rotumrandungsproblem, z.B. ClientMeldungsSrvImpl.colorRequiredField,
        // RuleSearchControllerBeanImpl.markModelYearAsError
        // TODO 01.05.2012 Seitenlaenge
		String test = new String("TestKommentare");
		test.hashCode();
	}
	public static void main(String[] args) {
		new ProduktivFehler();
	}
}
