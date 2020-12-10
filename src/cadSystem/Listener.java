package cadSystem;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Listener extends JFrame implements ActionListener, ListSelectionListener, ItemListener, FocusListener {

	public Listener() {
		// TODO Auto-generated constructor stub
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public void actionPerformed(ActionEvent event) {
		CardLayout cards = (CardLayout) Window.mainPanelCard.getLayout();

		if (event.getSource() instanceof JMenuItem) {
			if (event.getSource() == Window.registerProviderMenuItem) {
				cards.show(Window.mainPanelCard, "providerRegisterCard");
				clearAll();
				return;
			}
			if (event.getSource() == Window.registerEmployeeMenuItem) {
				cards.show(Window.mainPanelCard, "employeeRegisterCard");
				clearAll();
				return;
			}
			if (event.getSource() == Window.addContractMenuItem) {
				cards.show(Window.mainPanelCard, "addContract");
				clearAll();
				return;
			}
			if (event.getSource() == Window.consultAndAlterMenuItem) {
				cards.show(Window.mainPanelCard, "consultRegisters");
				clearAll();
				return;
			}
			if (event.getSource() == Window.exitMenuItem) {
				System.exit(0);
				return;
			}

		}

		if (event.getSource() instanceof JButton) {
			JButton button = (JButton) event.getSource();
			if (button.getText().equals("VOLTAR")) {
				showInicialPanel();
				return;
			} else if (button.getText().equals("ADICIONAR CONTRATO")) {
				String sql = createSql(Window.employeeContractRadio, Window.providerContractRadio,
						Window.listForContracts, Window.listModelforContracts);
				if (sql.equals("false")) {
					return;
				}
				boolean employeisSelect = Window.employeeContractRadio.isSelected();
				boolean providerisSelect = Window.providerContractRadio.isSelected();
				String date = Window.listForContracts.getSelectedValue().toString();
				String personRegister = (employeisSelect) ? date.substring(date.indexOf("CPF:") + 5)
						: date.substring(date.indexOf("CNPJ:") + 6);

				if (DataBase.getConnection()) {
					String contracts = "";
					DataBase.setResultSet(sql);
					try {
						while (DataBase.resultSet.next()) {
							contracts = (DataBase.resultSet.getString("contracts") == null) ? ""
									: DataBase.resultSet.getString("contracts");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (employeisSelect) {
						String function = Window.functionEmployeeField.getText().trim();
						String startDate = Window.startDateContractEmployeeField.getText().trim();
						String endDate = Window.endDateContractEmployeeField.getText().trim();

						try {
							Date start = sdf.parse(startDate);
							Date end = sdf.parse(endDate);
							if (end.compareTo(start) <= 0) {
								JOptionPane.showMessageDialog(this,
										"A data de início do contrato não pode ser posterior à data de fim",
										"Erro de processamento", JOptionPane.ERROR_MESSAGE);
								return;
							}
						} catch (ParseException e) {
							JOptionPane.showMessageDialog(this, "Indique as datas no formato DD/MM/AAAA",
									"Erro de processamento", JOptionPane.ERROR_MESSAGE);
							return;
						}
						String salary = String.format("%.2f",
								Double.parseDouble((!Window.salaryEmployeeField.getText().trim().equals(""))
										? Window.salaryEmployeeField.getText().trim().replace(',', '.')
										: "0"));
						String department = Window.departmentEmployeeField.getText().trim();
						if (department.equals("") || startDate.equals("") || salary.equals("") || function.equals("")) {
							JOptionPane.showMessageDialog(this,
									"Há campos obrigatórios vazios!",
									"Erro de processamento", JOptionPane.ERROR_MESSAGE);
							return;
						}
						String contract = function + "/*/" + startDate + "/*/" + endDate + "/*/" + salary + "/*/"
								+ department + "/*/";
						sql = "UPDATE `employee` SET`contracts`='" + contracts + contract + "'WHERE cpf='"
								+ personRegister + "'";
					} else if (providerisSelect) {
						String object = Window.objectProviderContractField.getText().trim();
						String startDate = Window.startDateContractProviderField.getText().trim();
						String endDate = Window.endDateContractProviderField.getText().trim();
						String signal = String.format("%.2f",
								Double.parseDouble((Window.paymentSignContractField.getText().trim().equals(""))
										? Window.paymentSignContractField.getText().trim().replace(',', '.')
										: "0"));
						String amount = String.format("%.2f",
								Double.parseDouble((Window.amountPaymentContractField.getText().trim().equals(""))
										? Window.amountPaymentContractField.getText().trim().replace(',', '.')
										: "0"));
						if (object.equals("") || startDate.equals("") || signal.equals("0") || amount.equals("0")) {
							JOptionPane.showMessageDialog(this,
									"Há campos obrigatórios vazios!",
									"Erro de processamento", JOptionPane.ERROR_MESSAGE);
							return;
						}
						String contract = object + "/*/" + startDate + "/*/" + endDate + "/*/" + signal + "/*/" + amount
								+ "/*/";
						sql = "UPDATE providers SET`contracts`='" + contracts + contract + "'WHERE cnpj='"
								+ personRegister + "'";
					} else {
						return;
					}
					if (DataBase.runSQL(sql) > 0) {
						DataBase.closeAll();
						JOptionPane.showMessageDialog(this, "Dados incluídos/alterados com sucesso!",
								"Atualização processada", JOptionPane.INFORMATION_MESSAGE);
						clearAll();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Erro em sua requisição!\nTente reiniciar a aplicação.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

			} else if (button.getText().equals("ALTERAR")) {

				if (!DataBase.getConnection()) {
					JOptionPane.showMessageDialog(this,
							"Falha na conexão com o banco de dados!\nTente reiniciar a aplicação.", "Exclusão de dados",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String sql = createSql(Window.employeeConsultRadio, Window.providerConsultRadio, Window.consultList,
						Window.consultMainListModel);
				if (sql.equals("false"))
					return;

				DataBase.setResultSet(sql);

				try {
					String gender = "";
					while (DataBase.resultSet.next()) {
						if (Window.employeeConsultRadio.isSelected()) {
							Window.nameEmployeeField.setText(DataBase.resultSet.getString("name"));
							Window.cpfEmployeeField.setText(DataBase.resultSet.getString("cpf"));
							Window.birthEmployeeField.setText(DataBase.resultSet.getString("birth"));
							Window.landlineEmployeeField.setText(DataBase.resultSet.getString("landline"));
							Window.cellPhoneEmployeeField.setText(DataBase.resultSet.getString("cellPhone"));
							Window.emailEmployeeField.setText(DataBase.resultSet.getString("email"));
							Window.adrressEmployeeField.setText(DataBase.resultSet.getString("address"));
						} else if (Window.providerConsultRadio.isSelected()) {
							Window.providerNameField.setText(DataBase.resultSet.getString("name"));
							Window.cnpjTextField.setText(DataBase.resultSet.getString("cnpj"));
							Window.landlineProviderField.setText(DataBase.resultSet.getString("landline"));
							Window.cellPhoneProviderField.setText(DataBase.resultSet.getString("cellPhone"));
							Window.emailProviderField.setText(DataBase.resultSet.getString("email"));
							Window.providerAdrressField.setText(DataBase.resultSet.getString("address"));
							Window.providerClassificationCombo
									.setSelectedItem(DataBase.resultSet.getString("classification"));
						}

					}
					if (gender.equals("MASCULINO")) {
						Window.maleRadio.setSelected(true);
					} else if (gender.equals("FEMININO")) {
						Window.femRadio.setSelected(true);
					} else {
						Window.genderRadioGroup.clearSelection();
					}

					if (Window.employeeConsultRadio.isSelected()) {
						cards.show(Window.mainPanelCard, "employeeRegisterCard");
					} else if (Window.providerConsultRadio.isSelected()) {
						cards.show(Window.mainPanelCard, "providerRegisterCard");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					DataBase.closeAll();

				}
			} else if (button.getText().equals("EXCLUIR")) {
				if (Window.consultList.getSelectedValue() == null)
					return;
				String date = Window.consultList.getSelectedValue().toString();
				String sql = "";
				if (DataBase.getConnection()) {
					String person = "";
					if (Window.employeeConsultRadio.isSelected()) {
						sql = "DELETE FROM employee WHERE cpf='" + date.substring(date.indexOf("CPF:") + 5) + "'";
						person = "EMPREGADO";
					} else if (Window.providerConsultRadio.isSelected()) {
						sql = "DELETE FROM providers WHERE cnpj='" + date.substring(date.indexOf("CNPJ:") + 6) + "'";
						person = "FORNECEDOR";
					} else {
						return;
					}
					int option = JOptionPane.showConfirmDialog(this,
							"Deseja EXCLUIR o " + person.toLowerCase() + " "
									+ date.substring(0, date.indexOf("-CPF:") - 1) + "?",
							"Exclusão de dados", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (option == 1 || option == -1)
						return;

					Window.selectedListModel.removeAllElements();
					if (DataBase.runSQL(sql) > 0) {
						JOptionPane.showMessageDialog(this, "Exclusão efetivada com sucesso!", "Exclusão de dados",
								JOptionPane.INFORMATION_MESSAGE);
						Window.consultMainListModel.removeElement(Window.consultList.getSelectedValue());
					} else {
						JOptionPane.showMessageDialog(this,
								"Falha na operação!\nReinicie o aplicativo e tente novamente", "Exclusão de dados",
								JOptionPane.ERROR_MESSAGE);
					}
					DataBase.closeAll();
				} else {
					JOptionPane.showMessageDialog(this,
							"Falha na conexão com o banco de dados!\nOperação não realizada.", "Exclusão de dados",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			else if (button.getText().equals("ADD/ALT EMPREGADO")) {
				String name = Window.nameEmployeeField.getText().trim().toUpperCase();
				String protoCpf = Window.cpfEmployeeField.getText().trim().toUpperCase();
				String birth = Window.birthEmployeeField.getText().trim().toUpperCase();
				try {
					sdf.parse(birth);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(this, "Data de nascimento inválida!", "Erro de processamento",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					if (!CPFValidator.validaCpf(protoCpf))
						return;
				} catch (java.lang.NumberFormatException e) {
					Window.cpfStatusLabel.setText("CPF INVÁLIDO");
					Window.cpfStatusLabel.setForeground(MyStyle.redSky);
					return;
				}
				if (!Window.maleRadio.isSelected() && !Window.femRadio.isSelected())
					return;
				String cpf = "";
				for (int i = 0; i < protoCpf.length(); i++) {
					if (protoCpf.charAt(i) == '.' || protoCpf.charAt(i) == '-' || protoCpf.charAt(i) == ' '
							|| protoCpf.charAt(i) == '/' || protoCpf.charAt(i) == '*' || protoCpf.charAt(i) == '%'
							|| protoCpf.charAt(i) == '+')
						continue;
					cpf += protoCpf.charAt(i);
				}
				String gender = (Window.maleRadio.isSelected()) ? "MASCULINO" : "FEMININO";
				String email = Window.emailEmployeeField.getText().trim();
				String landline = (Window.landlineEmployeeField.getText().trim().equals("(88)")) ? ""
						: Window.landlineEmployeeField.getText().trim();
				String cellPhone = (Window.cellPhoneEmployeeField.getText().trim().equals("(88)")) ? ""
						: Window.cellPhoneEmployeeField.getText().trim();
				String address = Window.adrressEmployeeField.getText().trim().toUpperCase();

				if (name.equals("") ||( landline.equals("") || cellPhone.equals("")) || address.equals("")) {
					JOptionPane.showMessageDialog(this,
							"Há campos obrigatórios vazios!\n"
							+ "O nome, cpf, nascimento, sexo, endereço e um telefone de contato são obrigatórios!",
							"Erro de processamento", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (!DataBase.getConnection()) {
					JOptionPane.showMessageDialog(this,
							"Falha na conexão com o banco de dados!\nOperação não realizada.", "Exclusão de dados",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String sql = "SELECT * FROM `employee` WHERE cpf='" + cpf + "'";

				DataBase.setResultSet(sql);
				String id = "";
				try {
					while (DataBase.resultSet.next()) {
						id = DataBase.resultSet.getString("id");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!id.equals("")) {
					sql = "UPDATE `employee` SET `name`='" + name + "',`cpf`='" + cpf + "',`birth`='" + birth
							+ "',`gender`='" + gender + "',`landline`='" + landline + "',`cellPhone`='" + cellPhone
							+ "',`email`='" + email + "',`address`='" + address + "' WHERE id='" + id + "'";
					int option = JOptionPane.showConfirmDialog(this,
							"Atenção! Este CPF já consta em nosso banco de dados e as informações serão sobrepostas.\n"
									+ "Deseja atualizar os dados do empregado " + name + "?",
							"Atualizaçao de dados", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (option == 1 || option == -1)
						return;
				} else {
					sql = "INSERT INTO employee (`name`, `cpf`, `birth`, `gender`, `landline`, `cellPhone`,`email`, `address`)"
							+ " VALUES ('" + name + "','" + cpf + "','" + birth + "','" + gender + "','" + landline
							+ "','" + cellPhone + "','" + email + "','" + address + "')";
					int option = JOptionPane.showConfirmDialog(this, "Deseja incluir o empregado " + name + "?",
							"Inclusão de dados", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (option == 1 || option == -1)
						return;
				}

				if (DataBase.runSQL(sql) > 0) {
					clearAll();
					JOptionPane.showMessageDialog(this, "Dados incluídos/alterados com sucesso!",
							"Atualização processada", JOptionPane.INFORMATION_MESSAGE);
					;
				} else {
					JOptionPane.showMessageDialog(this, "Comando não realizado!\nTente reiniciar a aplicação",
							"Erro de processamento", JOptionPane.ERROR_MESSAGE);
				}
				;
				DataBase.closeAll();
			} else if (button.getText().equals("ADD/ALT FORNECEDOR")) {
				String name = Window.providerNameField.getText().trim().toUpperCase();
				String protoCnpj = Window.cnpjTextField.getText().trim().toUpperCase();
				try {
					if (!CNPJValidator.validaCNPJ(protoCnpj))
						return;
				} catch (java.lang.NumberFormatException e) {
					Window.cnpjStatusLabel.setText("CNPJ INVÁLIDO");
					Window.cnpjStatusLabel.setForeground(MyStyle.redSky);
					return;
				}

				String cnpj = "";
				for (int i = 0; i < protoCnpj.length(); i++) {
					if (protoCnpj.charAt(i) == '.' || protoCnpj.charAt(i) == '-' || protoCnpj.charAt(i) == ' '
							|| protoCnpj.charAt(i) == '/' || protoCnpj.charAt(i) == '*' || protoCnpj.charAt(i) == '%'
							|| protoCnpj.charAt(i) == '+')
						continue;
					cnpj += protoCnpj.charAt(i);
				}

				String landline = (Window.landlineProviderField.getText().trim().equals("(88) ")) ? ""
						: Window.landlineProviderField.getText().trim();
				String classification = Window.providerClassificationCombo.getSelectedItem().toString();
				String cellPhone = (Window.cellPhoneProviderField.getText().trim().equals("(88) ")) ? ""
						: Window.cellPhoneProviderField.getText().trim();

				String email = Window.emailProviderField.getText().trim();
				String address = Window.providerAdrressField.getText().trim().toUpperCase();

				if (name.equals("") ||( landline.equals("") || cellPhone.equals("")) || address.equals("") || classification.equals("")) {
					JOptionPane.showMessageDialog(this,
							"Há campos obrigatórios vazios!\n"
							+ "O nome, cnpj, classificação, endereço e telefone de contato são obrigatórios!",
							"Erro de processamento", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (!DataBase.getConnection()) {
					JOptionPane.showMessageDialog(this,
							"Falha na conexão com o banco de dados!\nOperação não realizada.", "Exclusão de dados",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				String sql = "SELECT * FROM `providers` WHERE cnpj='" + cnpj + "'";

				DataBase.setResultSet(sql);
				String id = "";
				try {
					while (DataBase.resultSet.next()) {
						id = DataBase.resultSet.getString("id");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!id.equals("")) {
					sql = "UPDATE `providers` SET `name`='" + name + "',`cnpj`='" + cnpj + "',`classification`='"
							+ classification + "',`landline`='" + landline + "',`cellPhone`='" + cellPhone
							+ "',`email`='" + email + "',`address`='" + address + "' WHERE id='" + id + "'";
					int option = JOptionPane.showConfirmDialog(this,
							"Atenção! Este CNPJ já consta em nosso banco de dados e as informações serão sobrepostas.\n"
									+ "Deseja atualizar os dados do fornecedor " + name + "?",
							"Atualizaçao de dados", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (option == 1 || option == -1)
						return;
				} else {
					sql = "INSERT INTO providers (`name`, `cnpj`, `classification`, `landline`, `cellPhone`, `email`, `address`)"
							+ " VALUES ('" + name + "','" + cnpj + "','" + classification + "','" + landline + "','"
							+ cellPhone + "','" + email + "','" + address + "')";
					int option = JOptionPane.showConfirmDialog(this, "Deseja incluir o fornecedor " + name + "?",
							"Inclusão de dados", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (option == 1 || option == -1)
						return;
				}

				if (DataBase.runSQL(sql) > 0) {
					clearAll();
					JOptionPane.showMessageDialog(this, "Dados incluídos/alterados com sucesso!",
							"Atualização processada", JOptionPane.INFORMATION_MESSAGE);
					DataBase.closeAll();

				}
			}
		}
	}

	private void showInicialPanel() {
		CardLayout cards = (CardLayout) Window.mainPanelCard.getLayout();
		cards.show(Window.mainPanelCard, "initialPanel");
		clearAll();
	}

	private void clearAll() {
		Window.consultPerson.clearSelection();
		Window.consultContractPerson.clearSelection();
		Window.listModelforContracts.removeAllElements();
		Window.selectedListModel.removeAllElements();
		Window.consultMainListModel.removeAllElements();
		Window.providerNameField.setText("");
		Window.cnpjTextField.setText("");
		Window.landlineProviderField.setText("");
		Window.cellPhoneProviderField.setText("");
		Window.emailProviderField.setText("");
		Window.providerAdrressField.setText("");
		Window.providerClassificationCombo.setSelectedItem("");
		Window.nameEmployeeField.setText("");
		Window.cpfEmployeeField.setText("");
		Window.birthEmployeeField.setText("");
		Window.landlineEmployeeField.setText("(88) ");
		Window.cellPhoneEmployeeField.setText("(88) ");
		Window.emailEmployeeField.setText("");
		Window.adrressEmployeeField.setText("");
		Window.genderRadioGroup.clearSelection();
		Window.cpfStatusLabel.setText("CPF STATUS  ");
		Window.cpfStatusLabel.setForeground(MyStyle.graySky);
		Window.cnpjStatusLabel.setText("CPF STATUS  ");
		Window.cnpjStatusLabel.setForeground(MyStyle.graySky);
		Window.functionEmployeeField.setText("");
		Window.startDateContractEmployeeField.setText("");
		Window.endDateContractEmployeeField.setText("");
		Window.salaryEmployeeField.setText("");
		Window.departmentEmployeeField.setText("");
		Window.objectProviderContractField.setText("");
		Window.startDateContractProviderField.setText("");
		Window.endDateContractProviderField.setText("");
		Window.paymentSignContractField.setText("");
		Window.amountPaymentContractField.setText("");

	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		Window.selectedListModel.removeAllElements();
		if (event.getSource() == Window.consultList && Window.consultList.hasFocus()) {

			String sql = createSql(Window.employeeConsultRadio, Window.providerConsultRadio, Window.consultList,
					Window.consultMainListModel);
			if (sql.equals("false"))
				return;

			String person = (Window.employeeConsultRadio.isSelected()) ? "EMPREGADO" : "FORNECEDOR";

			DataBase.setResultSet(sql);
			try {
				String contracts = "";
				while (DataBase.resultSet.next()) {
					Window.selectedListModel.addElement("INFORMAÇÕES DO " + person + ":");
					if (Window.employeeConsultRadio.isSelected()) {
						Window.selectedListModel.addElement("NOME: " + DataBase.resultSet.getString("name") + ", SEXO: "
								+ DataBase.resultSet.getString("gender"));
						Window.selectedListModel.addElement("DT NASCIMENTO: " + DataBase.resultSet.getString("birth")
								+ ", CPF: " + DataBase.resultSet.getString("cpf"));
					} else if (Window.providerConsultRadio.isSelected()) {
						Window.selectedListModel.addElement("NOME: " + DataBase.resultSet.getString("name")
								+ ", CLASSIFICAÇÃO: " + DataBase.resultSet.getString("classification"));
						Window.selectedListModel.addElement("CNPJ: " + DataBase.resultSet.getString("cnpj"));
					}
					Window.selectedListModel.addElement("TELEFONE: " + DataBase.resultSet.getString("landline") + ", "
							+ DataBase.resultSet.getString("cellPhone") + ", EMAIL: "
							+ DataBase.resultSet.getString("email"));
					Window.selectedListModel.addElement("ENDEREÇO: " + DataBase.resultSet.getString("address"));
					contracts = (DataBase.resultSet.getString("contracts") == null) ? ""
							: DataBase.resultSet.getString("contracts");
				}
				int inicio = 0;
				int index = 0;
				String[] data = new String[5];
				int incide1 = 3;
				int indice2 = 4;
				String field1 = "FUNÇÃO: ";
				String field2 = ", SALÁRIO: ";
				String field3 = "DEPARTAMENTO: ";
				String field4 = ", STATUS: ";
				if (Window.providerConsultRadio.isSelected()) {
					field1 = "OBJETO: ";
					field2 = ", VALOR DO CONTRATO: ";
					field3 = "DATA DE CELEBRAÇÃO: ";
					field4 = ", STATUS: ";
					incide1 = 4;
					indice2 = 1;
				}
				Window.selectedListModel.addElement(
						"------------------------------------------------CONTRATO(S)------------------------------------------");
				for (int i = 0; i < contracts.length(); i++) {
					if (inicio <= contracts.length() - 1) {
						if (contracts.charAt(i) == '/' && contracts.charAt(i + 1) == '*'
								&& contracts.charAt(i + 2) == '/') {
							data[index] = contracts.substring(inicio, i);
							inicio = i + 3;
							index++;
						}
					}
					if (index == 5) {
						Window.selectedListModel.addElement(field1 + data[0] + field2 + data[incide1]);
						Window.selectedListModel.addElement(field3 + data[indice2] + field4 + getStatus(data[2]));

						Window.selectedListModel.addElement(
								"--------------------------------------------------------------------------------------------------------------");
						index = 0;
					}
				}
				if (contracts.length() == 0)
					Window.selectedListModel.addElement("Não há contratos cadastrados.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataBase.closeAll();
		}
	}

	private String createSql(JRadioButton employeeRadio, JRadioButton providerRadio, JList<String> list,
			DefaultListModel<String> listModel) {
		if (list.getSelectedValue() == null)
			return "false";
		String date = list.getSelectedValue().toString();
		String sql = "";
		if (DataBase.getConnection()) {
			if (employeeRadio.isSelected()) {
				sql = "SELECT * FROM employee WHERE cpf='" + date.substring(date.indexOf("CPF:") + 5) + "'";
			} else if (providerRadio.isSelected()) {
				sql = "SELECT * FROM providers WHERE cnpj='" + date.substring(date.indexOf("CNPJ:") + 6) + "'";
			}
		} else {
			listModel.addElement("Sem conexão com o banco de dados!");

		}
		return sql;

	}

	@Override
	public void itemStateChanged(ItemEvent event) {

		if (Window.employeeContractRadio.isSelected()) {
			showList(Window.listModelforContracts);
			alterForm(Window.addContractPanelCard, "addEmployeeContractPanel");
		} else if (Window.providerContractRadio.isSelected()) {
			showList(Window.listModelforContracts);
			alterForm(Window.addContractPanelCard, "addProviderContractPanel");
		} else if (Window.employeeConsultRadio.isSelected() || Window.providerConsultRadio.isSelected()) {
			Window.selectedListModel.removeAllElements();
			showList(Window.consultMainListModel);
		}
	}

	private void alterForm(JPanel contractPanel, String namePanelContract) {
		CardLayout cards2 = (CardLayout) contractPanel.getLayout();
		cards2.show(contractPanel, namePanelContract);
	}

	private void showList(DefaultListModel<String> modelList) {
		modelList.removeAllElements();
		String sql = "";
		String numberRegister = "";
		String labelNumberRegister = "";
		if (Window.employeeContractRadio.isSelected() || Window.employeeConsultRadio.isSelected()) {
			sql = "SELECT * FROM employee";
			numberRegister = "cpf";
			labelNumberRegister = " - CPF: ";

		} else if (Window.providerContractRadio.isSelected() || Window.providerConsultRadio.isSelected()) {
			sql = "SELECT * FROM providers";
			numberRegister = "cnpj";
			labelNumberRegister = " - CNPJ: ";
		}
		if (DataBase.getConnection()) {
			DataBase.setResultSet(sql);
			try {
				while (DataBase.resultSet.next()) {
					modelList.addElement("NOME: " + DataBase.resultSet.getString("name") + labelNumberRegister
							+ DataBase.resultSet.getString(numberRegister));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataBase.closeAll();
		} else {
			modelList.addElement("Sem conexão com o banco de dados!");
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == Window.cpfEmployeeField) {
			if (!Window.cpfEmployeeField.hasFocus()) {
				validationRegisterNumber(Window.cpfEmployeeField, Window.cpfStatusLabel);
			}
		}
		if (event.getSource() == Window.cnpjTextField) {
			if (!Window.cnpjTextField.hasFocus()) {
				validationRegisterNumber(Window.cnpjTextField, Window.cnpjStatusLabel);
			}
		}
	}

	private void validationRegisterNumber(JTextField registerField, JLabel statusLabel) {
		String registerNumber = registerField.getText().trim();
		boolean validator;
		String typeRegister;
		if (registerField.getText().trim().equals(""))
			return;
		if (registerField == Window.cpfEmployeeField) {
			try {
				validator = CPFValidator.validaCpf(registerNumber);
			} catch (java.lang.NumberFormatException e) {
				Window.cpfStatusLabel.setText("CPF INVÁLIDO");
				Window.cpfStatusLabel.setForeground(MyStyle.redSky);
				return;
			}
			typeRegister = "CPF ";
		} else {
			try {
				validator = CNPJValidator.validaCNPJ(registerNumber);
			} catch (java.lang.NumberFormatException e) {
				Window.cnpjStatusLabel.setText("CNPJ INVÁLIDO");
				Window.cnpjStatusLabel.setForeground(MyStyle.redSky);
				return;
			}
			typeRegister = "CNPJ ";
		}
		if (validator) {
			statusLabel.setForeground(MyStyle.greenSky);
			statusLabel.setText(typeRegister + "VÁLIDO  ");
		} else {
			statusLabel.setForeground(MyStyle.redSky);
			statusLabel.setText(typeRegister + "INVÁLIDO");
			return;
		}

	}

	private String getStatus(String endDate) throws ParseException {
		if (endDate.equals("")) {
			return "VIGENTE";
		} else if (sdf.parse(endDate).compareTo(new Date()) <= 0) {
			return "ENCERRADO";
		} else {
			return "VIGENTE";
		}
	}
}
