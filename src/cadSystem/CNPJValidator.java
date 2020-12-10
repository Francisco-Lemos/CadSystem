package cadSystem;

public class CNPJValidator {
	static boolean validaCNPJ(String teste) {
		String testeCNPJ = "";
		teste = teste.trim();
		if (teste.equals(""))
			return false;
		for (int i = 0; i < teste.length(); i++) {
			if (teste.charAt(i) == '.' || teste.charAt(i) == '-' || teste.charAt(i) == '/')
				continue;
			testeCNPJ += teste.charAt(i);
		}
		Long.parseLong(testeCNPJ);
		if (testeCNPJ.length() != 14)
			return false;

		int quant = 0;
		for (int i = 1; i < testeCNPJ.length(); i++) {
			if (testeCNPJ.charAt(i - 1) == testeCNPJ.charAt(i))
				quant++;
			if (quant >= 12)
				return false;
		}

		String protoCpf = AdicionaDigitos(testeCNPJ.substring(0, 12));
		if (protoCpf.equals(testeCNPJ))
			return true;
		else
			return false;

	}

	private static String AdicionaDigitos(String cnpj) {
		while (cnpj.length() < 14) {
			int sum = 0;
			int multiplicador = (cnpj.length() == 12) ? 5 : 6;
			for (int i = 0; i < cnpj.length(); i++) {
				sum += (multiplicador * Character.getNumericValue(cnpj.charAt(i)));
				if (multiplicador == 2)
					multiplicador = 10;
				multiplicador--;
			}
			int resto = sum % 11;
			int preDigit = (resto < 2) ? 0 : 11 - resto;
			cnpj += preDigit;
		}
		return cnpj;
	}
}
