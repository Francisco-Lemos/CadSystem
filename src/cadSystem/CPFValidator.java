package cadSystem;

public class CPFValidator {

	static boolean validaCpf(String teste) {
		String testeCpf = "";
		for (int i = 0; i < teste.length(); i++) {
			if(teste.charAt(i) == '.' || teste.charAt(i) == '-') continue;
			testeCpf += teste.charAt(i);
		}
		if (testeCpf.charAt(0) == testeCpf.charAt(1))
		if (testeCpf.length() != 11) return false;
		String protoCpf = testeCpf.substring(0, 9);
		int quant = 0;
		for (int i = 1; i < protoCpf.length(); i++) {
			if(protoCpf.charAt(i-1) == protoCpf.charAt(i))quant++;
			if (quant >= 8) return false;
		} 
		protoCpf = AdicionaDigitos(protoCpf);
		if(protoCpf.equals(testeCpf)) return true;
		else return false;
		
	}
	
	private static String AdicionaDigitos(String cpf) {
		while(cpf.length() < 11) {
			int multiplicador = cpf.length() + 1;
			int preDigit = 0;
			for (int i = 0; i < cpf.length(); i++) {
				preDigit += (multiplicador * Character.getNumericValue(cpf.charAt(i)));
				multiplicador--;
			}
			preDigit = (int) (11 - (preDigit%11));
			if (preDigit > 9) preDigit = 0;
			cpf += preDigit;
		}
		return cpf;
	}
	
}
