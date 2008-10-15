package logica;

public class StringTokenizer {

	String cadena = "";
	String delimiter = "";
	boolean hasNext = true;
	public int tokens;
	int index = 0;

	public StringTokenizer(String cadena, String delimiter) {
		if (delimiter.length() == 1) {
			for (int i = 0; i < cadena.length(); i++) {
				if ((cadena.charAt(i) == delimiter.charAt(0))) {
					tokens++;
				}
			}
		} else if (delimiter.length() == 2) {
			for (int i = 0; i < cadena.length(); i++) {
				if ((cadena.charAt(i) == delimiter.charAt(0) && cadena
						.charAt(i + 1) == delimiter.charAt(1))) {
					tokens++;
				}
			}

		}
		tokens++;
		this.cadena = cadena;
		this.delimiter = delimiter;
	}

	public String nextElement() {
		String res = "";
		int x = cadena.indexOf(delimiter, index);

		if (x < index) {
			res = cadena.substring(index, cadena.length());
			hasNext = false;
		} else {
			res = cadena.substring(index, x);
			index = x + delimiter.length();
		}
		return res;

	}

	public boolean hasNext() {
		return hasNext;
	}

}
