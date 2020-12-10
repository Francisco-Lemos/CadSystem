package cadSystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Window2 extends JFrame implements ActionListener, ListSelectionListener, ItemListener, FocusListener {

	static JMenuItem registerProviderMenuItem, registerEmployeeMenuItem, addContractMenuItem, exitMenuItem;
	static JMenuItem consultAndAlterMenuItem;

	static JPanel mainPanelCard, addContractPanelCard;

	static JTextField nameEmployeeField, cpfEmployeeField, birthEmployeeField, emailEmployeeField,
			landlineEmployeeField, cellPhoneEmployeeField, adrressEmployeeField, cnpjTextField, providerNameField,
			landlineProviderField, cellPhoneProviderField, emailProviderField, providerAdrressField,
			functionEmployeeField, startDateContractEmployeeField, endDateContractEmployeeField, salaryEmployeeField,
			departmentEmployeeField, objectProviderContractField, startDateContractProviderField,
			endDateContractProviderField, paymentSignContractField, amountPaymentContractField;

	static JComboBox<String> providerClassificationCombo;

	static JRadioButton maleRadio, femRadio, employeeContractRadio, providerContractRadio, employeeConsultRadio,
			providerConsultRadio;

	static JList<String> consultList, selectedList, listForContracts;

	static DefaultListModel<String> consultMainListModel, selectedListModel, listModelforContracts;

	static JLabel cpfStatusLabel, cnpjStatusLabel;
	static ButtonGroup consultPerson, genderRadioGroup, consultContractPerson;

	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Window2() {
		setTitle("CadSystem");
		setSize(500, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setBackground(MyStyle.graySky);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		registerProviderMenuItem = new JMenuItem("FORNECEDOR");
		registerProviderMenuItem.addActionListener(this);
		registerEmployeeMenuItem = new JMenuItem("EMPREGADO");
		addContractMenuItem = new JMenuItem("ADICIONAR CONTRATO");
		addContractMenuItem.addActionListener(this);
		registerEmployeeMenuItem.addActionListener(this);
		exitMenuItem = new JMenuItem("SAIR");
		exitMenuItem.addActionListener(this);
		JMenu registerMenu = new JMenu("CADASTRO");
		registerMenu.add(registerProviderMenuItem);
		registerMenu.add(registerEmployeeMenuItem);
		registerMenu.add(addContractMenuItem);
		registerMenu.add(exitMenuItem);

		consultAndAlterMenuItem = new JMenuItem("CONSULTAR/ALTERAR");
		consultAndAlterMenuItem.addActionListener(this);
		JMenu consultMenu = new JMenu("CONSULTAR");
		consultMenu.add(consultAndAlterMenuItem);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(registerMenu);
		menuBar.add(consultMenu);

		JPanel initialPanel = new JPanel();
		initialPanel.setBackground(Color.WHITE);
		initialPanel.setLayout(new GridLayout(2, 1));
		JLabel topLabel = new JLabel("CadSystem 1.0", JLabel.CENTER);
		topLabel.setFont(MyStyle.superTitleHelvetica);
		topLabel.setVerticalAlignment(JLabel.BOTTOM);
		initialPanel.add(topLabel);
		ImageIcon mainImage = new ImageIcon("C:\\Users\\Francisco\\eclipse-curso_JAVA\\cadsystem\\img\\mainicon1.jpg");
		JLabel bottomLabel = new JLabel(mainImage, JLabel.CENTER);
		bottomLabel.setVerticalAlignment(JLabel.TOP);
		initialPanel.add(bottomLabel);

		JPanel employeeRegisterPanel = new JPanel();
		employeeRegisterPanel.setLayout(new BorderLayout());

		displayTitle("EMPREGADO", employeeRegisterPanel);

		JPanel employeeRegisterMainPanel = new JPanel();
		employeeRegisterMainPanel.setLayout(new GridLayout(6, 1));

		nameEmployeeField = new JTextField(33);
		criaPanel(" NOME :", nameEmployeeField, employeeRegisterMainPanel);

		JLabel employeeCpfLabel = new JLabel(" CPF :", JLabel.LEFT);
		employeeCpfLabel.setFont(MyStyle.helveticaBold);
		cpfEmployeeField = new JTextField(10);
		cpfEmployeeField.setFont(MyStyle.helveticaBold);
		cpfEmployeeField.addFocusListener(this);
		cpfStatusLabel = new JLabel("CPF STATUS  ", JLabel.CENTER);
		cpfStatusLabel.setFont(MyStyle.helveticaBold);
		cpfStatusLabel.setForeground(MyStyle.grayHell);
		JLabel birthEmployeeLabel = new JLabel("NASC.:", JLabel.LEFT);
		birthEmployeeLabel.setFont(MyStyle.helveticaBold);
		birthEmployeeField = new JTextField(9);
		birthEmployeeField.setFont(MyStyle.helveticaBold);
		JPanel cpfAndBirthPanel = new JPanel();
		cpfAndBirthPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		cpfAndBirthPanel.add(employeeCpfLabel);
		cpfAndBirthPanel.add(cpfEmployeeField);
		cpfAndBirthPanel.add(cpfStatusLabel);
		cpfAndBirthPanel.add(birthEmployeeLabel);
		cpfAndBirthPanel.add(birthEmployeeField);
		employeeRegisterMainPanel.add(cpfAndBirthPanel);

		JPanel genderAndEmailPanel = new JPanel();
		genderAndEmailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel employeeEmailLabel = new JLabel(" EMAIL :", JLabel.LEFT);
		employeeEmailLabel.setFont(MyStyle.helveticaBold);
		emailEmployeeField = new JTextField(20);
		emailEmployeeField.setFont(MyStyle.helveticaBold);
		JLabel employeeGenderLabel = new JLabel(" SEXO :", JLabel.LEFT);
		employeeGenderLabel.setFont(MyStyle.helveticaBold);
		maleRadio = new JRadioButton("M");
		maleRadio.setFont(MyStyle.helveticaBold);
		maleRadio.setBackground(MyStyle.graySky);
		femRadio = new JRadioButton("F");
		femRadio.setFont(MyStyle.helveticaBold);
		femRadio.setBackground(MyStyle.graySky);
		genderRadioGroup = new ButtonGroup();
		genderRadioGroup.add(maleRadio);
		genderRadioGroup.add(femRadio);
		genderAndEmailPanel.add(employeeEmailLabel);
		genderAndEmailPanel.add(emailEmployeeField);
		genderAndEmailPanel.add(employeeGenderLabel);
		genderAndEmailPanel.add(maleRadio);
		genderAndEmailPanel.add(femRadio);
		employeeRegisterMainPanel.add(genderAndEmailPanel);

		JPanel employeePhonesPanel = new JPanel();
		employeePhonesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		landlineEmployeeField = new JTextField("(88) ", 13);
		criaPanel(" TEL :", landlineEmployeeField, employeePhonesPanel);
		cellPhoneEmployeeField = new JTextField("(88) ", 13);
		criaPanel(" CEL :", cellPhoneEmployeeField, employeePhonesPanel);
		employeeRegisterMainPanel.add(employeePhonesPanel);

		adrressEmployeeField = new JTextField(34);
		criaPanel(" END.:", adrressEmployeeField, employeeRegisterMainPanel);

		setAddAndExitButtons("ADD/ALT EMPREGADO", employeeRegisterMainPanel);

		employeeRegisterPanel.add("Center", employeeRegisterMainPanel);

		JPanel providerRegisterPanel = new JPanel();
		providerRegisterPanel.setLayout(new BorderLayout());

		displayTitle("FORNECEDOR", providerRegisterPanel);

		JPanel providerRegisterMainPanel = new JPanel();
		providerRegisterMainPanel.setLayout(new GridLayout(7, 1));

		providerNameField = new JTextField(33);
		criaPanel(" NOME :", providerNameField, providerRegisterMainPanel);

		JLabel clientCnpjLabel = new JLabel(" CNPJ :", JLabel.LEFT);
		clientCnpjLabel.setFont(MyStyle.helveticaBold);
		cnpjTextField = new JTextField(23);
		cnpjTextField.setFont(MyStyle.helveticaBold);
		cnpjTextField.addFocusListener(this);
		cnpjStatusLabel = new JLabel("STATUS", JLabel.CENTER);
		cnpjStatusLabel.setForeground(MyStyle.grayHell);
		JPanel cnpjPanel = new JPanel();
		cnpjPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		cnpjPanel.add(clientCnpjLabel);
		cnpjPanel.add(cnpjTextField);
		cnpjPanel.add(cnpjStatusLabel);
		providerRegisterMainPanel.add(cnpjPanel);

		JLabel providerClassificationLabel = new JLabel(" CLASSIFICAÇÃO :", JLabel.LEFT);
		providerClassificationLabel.setFont(MyStyle.helveticaBold);
		String[] classifications = { "", "MATERIA PRIMA", "INTERNET", "MÃO DE OBRA", "MOBÍLIA", "PARQUE TECNOLÓGICO" };
		providerClassificationCombo = new JComboBox<String>(classifications);
		providerClassificationCombo.setFont(MyStyle.helveticaBold);
		JPanel panel6 = new JPanel();
		panel6.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel6.add(providerClassificationLabel);
		panel6.add(providerClassificationCombo);
		providerRegisterMainPanel.add(panel6);

		JPanel providerPhonesPanel = new JPanel();
		providerPhonesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		landlineProviderField = new JTextField("(88) ", 13);
		criaPanel(" TEL :", landlineProviderField, providerPhonesPanel);
		cellPhoneProviderField = new JTextField("(88) ", 13);
		criaPanel(" CEL :", cellPhoneProviderField, providerPhonesPanel);
		providerRegisterMainPanel.add(providerPhonesPanel);

		emailProviderField = new JTextField(33);
		criaPanel(" EMAIL :", emailProviderField, providerRegisterMainPanel);

		providerAdrressField = new JTextField(34);
		criaPanel(" END.:", providerAdrressField, providerRegisterMainPanel);

		setAddAndExitButtons("ADD/ALT FORNECEDOR", providerRegisterMainPanel);

		providerRegisterPanel.add("Center", providerRegisterMainPanel);

		JPanel consultPanel = new JPanel();
		displayTitle("CONSULTAR/ALTERAR", consultPanel);

		JPanel consultMainPanel = new JPanel();
		consultMainPanel.setLayout(new GridLayout(2, 1, 5, 5));

		consultMainListModel = new DefaultListModel<String>();
		consultList = new JList<String>(consultMainListModel);
		providerConsultRadio = new JRadioButton("FORNECEDOR");
		employeeConsultRadio = new JRadioButton("EMPREGADO");
		consultPerson = new ButtonGroup();
		addHigherPanel(employeeConsultRadio, providerConsultRadio, consultPerson, consultList, consultMainPanel);

		selectedListModel = new DefaultListModel<String>();
		selectedList = new JList<String>(selectedListModel);
		setChangeButtons("ALTERAR", "EXCLUIR", "VOLTAR", new JScrollPane(selectedList), consultMainPanel);

		consultPanel.add(consultMainPanel);

		JPanel addContractPanel = new JPanel();
		displayTitle("ADICIONAR CONTRATO", addContractPanel);

		JPanel addContractMainPanel = new JPanel();
		addContractMainPanel.setLayout(new GridLayout(2, 1));

		listModelforContracts = new DefaultListModel<String>();
		listForContracts = new JList<String>(listModelforContracts);
		employeeContractRadio = new JRadioButton("EMPREGADO");
		providerContractRadio = new JRadioButton("FORNECEDOR");
		consultContractPerson = new ButtonGroup();
		addHigherPanel(employeeContractRadio, providerContractRadio, consultContractPerson, listForContracts,
				addContractMainPanel);

		JPanel addEmployeeContractPanelCard = new JPanel();
		addEmployeeContractPanelCard.setLayout(new GridLayout(4, 1, 3, 3));

		functionEmployeeField = new JTextField(30);
		criaPanel("FUNÇÃO: ", functionEmployeeField, addEmployeeContractPanelCard);

		JPanel datesContractPanel = new JPanel();
		datesContractPanel.setLayout(new GridLayout(1, 2));
		startDateContractEmployeeField = new JTextField(8);
		criaPanel("INÍCIO: ", startDateContractEmployeeField, datesContractPanel);
		endDateContractEmployeeField = new JTextField(8);
		criaPanel("FIM: ", endDateContractEmployeeField, datesContractPanel);
		addEmployeeContractPanelCard.add(datesContractPanel);

		JPanel salaryAndDepartmentPanel = new JPanel();
		salaryAndDepartmentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		salaryEmployeeField = new JTextField("00,00", 6);
		criaPanel("SALÁRIO: R$ ", salaryEmployeeField, salaryAndDepartmentPanel);
		departmentEmployeeField = new JTextField(15);
		criaPanel("DEPART: ", departmentEmployeeField, salaryAndDepartmentPanel);
		addEmployeeContractPanelCard.add(salaryAndDepartmentPanel);

		setAddAndExitButtons("ADICIONAR CONTRATO", addEmployeeContractPanelCard);

		JPanel addProviderContractPanelCard = new JPanel();
		addProviderContractPanelCard.setLayout(new GridLayout(4, 1));

		objectProviderContractField = new JTextField(30);
		criaPanel("OBJETO: ", objectProviderContractField, addProviderContractPanelCard);

		JPanel panelContractProviderDates = new JPanel();
		panelContractProviderDates.setLayout(new GridLayout(1, 2));
		startDateContractProviderField = new JTextField(8);
		criaPanel("DT ACORDO: ", startDateContractProviderField, panelContractProviderDates);
		endDateContractProviderField = new JTextField(8);
		criaPanel("DT ENTREGA: ", endDateContractProviderField, panelContractProviderDates);
		addProviderContractPanelCard.add(panelContractProviderDates);

		JPanel panelContractProviderPayment = new JPanel();
		panelContractProviderPayment.setLayout(new GridLayout(1, 2));
		paymentSignContractField = new JTextField("00,00", 9);
		criaPanel("SINAL: R$ ", paymentSignContractField, panelContractProviderPayment);
		amountPaymentContractField = new JTextField("00,00", 10);
		criaPanel("TOTAL: R$ ", amountPaymentContractField, panelContractProviderPayment);
		addProviderContractPanelCard.add(panelContractProviderPayment);

		setAddAndExitButtons("ADICIONAR CONTRATO", addProviderContractPanelCard);

		addContractPanelCard = new JPanel();
		addContractPanelCard.setLayout(new CardLayout());
		addContractPanelCard.add(addEmployeeContractPanelCard, "addEmployeeContractPanel");
		addContractPanelCard.add(addProviderContractPanelCard, "addProviderContractPanel");

		addContractMainPanel.add(addContractPanelCard);
		addContractPanel.add("Center", addContractMainPanel);

		mainPanelCard = new JPanel();
		mainPanelCard.setLayout(new CardLayout());
		mainPanelCard.add(initialPanel, "initialPanel");
		mainPanelCard.add(employeeRegisterPanel, "employeeRegisterCard");
		mainPanelCard.add(providerRegisterPanel, "providerRegisterCard");
		mainPanelCard.add(consultPanel, "consultRegisters");
		mainPanelCard.add(addContractPanel, "addContract");

		add("North", menuBar);
		add("Center", mainPanelCard);
		add("South", new JLabel("  "));
		add("East", new JLabel("  "));
		add("West", new JLabel("  "));

	}

	private void addHigherPanel(JRadioButton employeeRadio, JRadioButton providerRadio, ButtonGroup buttonGroup,
			JList<String> listForContracts, JPanel maindPanel) {
		JPanel higherpanel = new JPanel();
		higherpanel.setLayout(new BorderLayout());
		JPanel selectedPersonPanel = new JPanel();
		selectedPersonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		employeeRadio.setFont(MyStyle.helveticaBold);
		employeeRadio.setBackground(MyStyle.graySky);
		employeeRadio.addItemListener(this);
		providerRadio.addItemListener(this);
		providerRadio.setFont(MyStyle.helveticaBold);
		providerRadio.setBackground(MyStyle.graySky);
		buttonGroup.add(employeeRadio);
		buttonGroup.add(providerRadio);
		selectedPersonPanel.add(employeeRadio);
		selectedPersonPanel.add(providerRadio);
		higherpanel.add("North", selectedPersonPanel);
		listForContracts.addListSelectionListener(this);
		higherpanel.add("Center", new JScrollPane(listForContracts));
		maindPanel.add(higherpanel);
	}

	private void setAddAndExitButtons(String labelButton, JPanel mainPanel) {
		JButton addButton = new JButton(labelButton);
		addButton.setBackground(MyStyle.greenSky);
		addButton.setForeground(Color.WHITE);
		addButton.setFont(MyStyle.helveticaBold);
		addButton.addActionListener(this);
		JButton exitButton = new JButton("VOLTAR");
		exitButton.setBackground(MyStyle.BlueSky);
		exitButton.setForeground(Color.WHITE);
		exitButton.setFont(MyStyle.helveticaBold);
		exitButton.addActionListener(this);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(addButton);
		panel.add(exitButton);
		mainPanel.add(panel);

	}

	private void displayTitle(String title, JPanel panel) {
		panel.setLayout(new BorderLayout());
		JLabel titleLabel = new JLabel(title, JLabel.CENTER);
		titleLabel.setFont(MyStyle.titleHelvetica);
		titleLabel.setForeground(MyStyle.grayHell);
		panel.add("North", titleLabel);
	}

	private void setChangeButtons(String labelAlterButton, String labelExcludeButton, String labelExitButton,
			JScrollPane jScrollPane, JPanel mainPanel) {
		JButton alterButton = new JButton(labelAlterButton);
		JButton excludeButton = new JButton(labelExcludeButton);
		JButton exitButton = new JButton(labelExitButton);
		alterButton.setBackground(MyStyle.orangeSky);
		alterButton.setFont(MyStyle.helveticaBold);
		alterButton.setForeground(Color.WHITE);
		excludeButton.setBackground(MyStyle.redSky);
		excludeButton.setFont(MyStyle.helveticaBold);
		excludeButton.setForeground(Color.WHITE);
		exitButton.setBackground(MyStyle.BlueSky);
		exitButton.setFont(MyStyle.helveticaBold);
		exitButton.setForeground(Color.WHITE);
		alterButton.addActionListener(this);
		excludeButton.addActionListener(this);
		exitButton.addActionListener(this);
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(1, 3, 5, 5));
		gridPanel.add(alterButton);
		gridPanel.add(excludeButton);
		gridPanel.add(exitButton);
		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		borderPanel.add("North", gridPanel);
		borderPanel.add("Center", jScrollPane);
		mainPanel.add(borderPanel);
	}

	private void criaPanel(String textLabel, JTextField field, JPanel panel) {
		JLabel label = new JLabel(textLabel, JLabel.LEFT);
		label.setFont(MyStyle.helveticaBold);
		field.setFont(MyStyle.helveticaBold);
		JPanel flowPanel = new JPanel();
		flowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(label);
		flowPanel.add(field);
		panel.add(flowPanel);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		CardLayout cards = (CardLayout) mainPanelCard.getLayout();

		if (event.getSource() instanceof JMenuItem) {
			if (event.getSource() == registerProviderMenuItem) {
				cards.show(mainPanelCard, "providerRegisterCard");
				clearAll();
				return;
			}
			if (event.getSource() == registerEmployeeMenuItem) {
				cards.show(mainPanelCard, "employeeRegisterCard");
				clearAll();
				return;
			}
			if (event.getSource() == addContractMenuItem) {
				cards.show(mainPanelCard, "addContract");
				clearAll();
				return;
			}
			if (event.getSource() == consultAndAlterMenuItem) {
				cards.show(mainPanelCard, "consultRegisters");
				clearAll();
				return;
			}
			if (event.getSource() == exitMenuItem) {
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
				String sql = createSql(employeeContractRadio, providerContractRadio, listForContracts,
						listModelforContracts);
				if (sql.equals("false")) {
					return;
				}
				boolean employeisSelect = employeeContractRadio.isSelected();
				boolean providerisSelect = providerContractRadio.isSelected();
				String date = listForContracts.getSelectedValue().toString();
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
						String function = functionEmployeeField.getText().trim();
						String startDate = startDateContractEmployeeField.getText().trim();
						String endDate = endDateContractEmployeeField.getText().trim();

						try {
							sdf.parse(startDate);
							sdf.parse(endDate);
						} catch (ParseException e) {
							JOptionPane.showMessageDialog(this, "Data de nascimento inválida!", "Erro de processamento",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						String salary = String.format("%.2f",
								Double.parseDouble((!salaryEmployeeField.getText().trim().equals(""))
										? salaryEmployeeField.getText().trim().replace(',', '.')
										: "0"));
						String department = departmentEmployeeField.getText().trim();
						if (department.equals("") || startDate.equals("") || salary.equals("") || function.equals("")) {
							return;
						}
						String contract = function + "/*/" + startDate + "/*/" + endDate + "/*/" + salary + "/*/"
								+ department + "/*/";
						sql = "UPDATE `employee` SET`contracts`='" + contracts + contract + "'WHERE cpf='"
								+ personRegister + "'";
					} else if (providerisSelect) {
						String object = objectProviderContractField.getText().trim();
						String startDate = startDateContractProviderField.getText().trim();
						String endDate = endDateContractProviderField.getText().trim();
						String signal = String.format("%.2f",
								Double.parseDouble((paymentSignContractField.getText().trim().equals(""))
										? paymentSignContractField.getText().trim().replace(',', '.')
										: "0"));
						String amount = String.format("%.2f",
								Double.parseDouble((amountPaymentContractField.getText().trim().equals(""))
										? amountPaymentContractField.getText().trim().replace(',', '.')
										: "0"));
						if (object.equals("") || startDate.equals("") || signal.equals("0") || amount.equals("0")) {
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
				String sql = createSql(employeeConsultRadio, providerConsultRadio, consultList, consultMainListModel);
				if (sql.equals("false"))
					return;

				DataBase.setResultSet(sql);

				try {
					String gender = "";
					while (DataBase.resultSet.next()) {
						if (employeeConsultRadio.isSelected()) {
							nameEmployeeField.setText(DataBase.resultSet.getString("name"));
							cpfEmployeeField.setText(DataBase.resultSet.getString("cpf"));
							birthEmployeeField.setText(DataBase.resultSet.getString("birth"));
							landlineEmployeeField.setText(DataBase.resultSet.getString("landline"));
							cellPhoneEmployeeField.setText(DataBase.resultSet.getString("cellPhone"));
							emailEmployeeField.setText(DataBase.resultSet.getString("email"));
							adrressEmployeeField.setText(DataBase.resultSet.getString("address"));
							gender = DataBase.resultSet.getString("gender");
						} else if (providerConsultRadio.isSelected()) {
							providerNameField.setText(DataBase.resultSet.getString("name"));
							cnpjTextField.setText(DataBase.resultSet.getString("cnpj"));
							landlineProviderField.setText(DataBase.resultSet.getString("landline"));
							cellPhoneProviderField.setText(DataBase.resultSet.getString("cellPhone"));
							emailProviderField.setText(DataBase.resultSet.getString("email"));
							providerAdrressField.setText(DataBase.resultSet.getString("address"));
							providerClassificationCombo.setSelectedItem(DataBase.resultSet.getString("classification"));
						}

					}
					if (gender.equals("MASCULINO")) {
						maleRadio.setSelected(true);
					} else if (gender.equals("FEMININO")) {
						femRadio.setSelected(true);
					} else {
						genderRadioGroup.clearSelection();
					}

					if (employeeConsultRadio.isSelected()) {
						cards.show(mainPanelCard, "employeeRegisterCard");
					} else if (providerConsultRadio.isSelected()) {
						cards.show(mainPanelCard, "providerRegisterCard");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					DataBase.closeAll();

				}
			} else if (button.getText().equals("EXCLUIR")) {
				if (consultList.getSelectedValue() == null)
					return;
				String date = consultList.getSelectedValue().toString();
				String sql = "";
				if (DataBase.getConnection()) {
					String person = "";
					if (employeeConsultRadio.isSelected()) {
						sql = "DELETE FROM employee WHERE cpf='" + date.substring(date.indexOf("CPF:") + 5) + "'";
						person = "EMPREGADO";
					} else if (providerConsultRadio.isSelected()) {
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

					selectedListModel.removeAllElements();
					if (DataBase.runSQL(sql) > 0) {
						JOptionPane.showMessageDialog(this, "Exclusão efetivada com sucesso!", "Exclusão de dados",
								JOptionPane.INFORMATION_MESSAGE);
						consultMainListModel.removeElement(consultList.getSelectedValue());
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
				String name = nameEmployeeField.getText().trim().toUpperCase();
				String protoCpf = cpfEmployeeField.getText().trim().toUpperCase();
				String birth = birthEmployeeField.getText().trim().toUpperCase();
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
					cpfStatusLabel.setText("CPF INVÁLIDO");
					cpfStatusLabel.setForeground(MyStyle.redSky);
					return;
				}
				if (!maleRadio.isSelected() && !femRadio.isSelected())
					return;
				String cpf = "";
				for (int i = 0; i < protoCpf.length(); i++) {
					if (protoCpf.charAt(i) == '.' || protoCpf.charAt(i) == '-' || protoCpf.charAt(i) == ' '
							|| protoCpf.charAt(i) == '/' || protoCpf.charAt(i) == '*' || protoCpf.charAt(i) == '%'
							|| protoCpf.charAt(i) == '+')
						continue;
					cpf += protoCpf.charAt(i);
				}
				String gender = (maleRadio.isSelected()) ? "MASCULINO" : "FEMININO";
				String email = emailEmployeeField.getText().trim();
				String landline = (landlineEmployeeField.getText().trim().equals("(88)")) ? ""
						: landlineEmployeeField.getText().trim();
				String cellPhone = (cellPhoneEmployeeField.getText().trim().equals("(88)")) ? ""
						: cellPhoneEmployeeField.getText().trim();
				String address = adrressEmployeeField.getText().trim().toUpperCase();

				if (!DataBase.getConnection()) {
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
				String name = providerNameField.getText().trim().toUpperCase();
				String protoCnpj = cnpjTextField.getText().trim().toUpperCase();
				try {
					if (!CNPJValidator.validaCNPJ(protoCnpj))
						return;
				} catch (java.lang.NumberFormatException e) {
					cnpjStatusLabel.setText("CNPJ INVÁLIDO");
					cnpjStatusLabel.setForeground(MyStyle.redSky);
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

				String landline = (landlineProviderField.getText().trim().equals("(88) ")) ? ""
						: landlineProviderField.getText().trim();
				String classification = providerClassificationCombo.getSelectedItem().toString();
				String cellPhone = (cellPhoneProviderField.getText().trim().equals("(88) ")) ? ""
						: cellPhoneProviderField.getText().trim();

				String email = emailProviderField.getText().trim();
				String address = providerAdrressField.getText().trim().toUpperCase();

				if (!DataBase.getConnection()) {
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
		CardLayout cards = (CardLayout) mainPanelCard.getLayout();
		cards.show(mainPanelCard, "initialPanel");
		clearAll();
	}

	private void clearAll() {
		consultPerson.clearSelection();
		consultContractPerson.clearSelection();
		listModelforContracts.removeAllElements();
		selectedListModel.removeAllElements();
		consultMainListModel.removeAllElements();
		providerNameField.setText("");
		cnpjTextField.setText("");
		landlineProviderField.setText("");
		cellPhoneProviderField.setText("");
		emailProviderField.setText("");
		providerAdrressField.setText("");
		providerClassificationCombo.setSelectedItem("");
		nameEmployeeField.setText("");
		cpfEmployeeField.setText("");
		birthEmployeeField.setText("");
		landlineEmployeeField.setText("(88) ");
		cellPhoneEmployeeField.setText("(88) ");
		emailEmployeeField.setText("");
		adrressEmployeeField.setText("");
		genderRadioGroup.clearSelection();
		cpfStatusLabel.setText("CPF STATUS  ");
		cpfStatusLabel.setForeground(MyStyle.graySky);
		cnpjStatusLabel.setText("CPF STATUS  ");
		cnpjStatusLabel.setForeground(MyStyle.graySky);
		functionEmployeeField.setText("");
		startDateContractEmployeeField.setText("");
		endDateContractEmployeeField.setText("");
		salaryEmployeeField.setText("");
		departmentEmployeeField.setText("");
		objectProviderContractField.setText("");
		startDateContractProviderField.setText("");
		endDateContractProviderField.setText("");
		paymentSignContractField.setText("");
		amountPaymentContractField.setText("");

	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		selectedListModel.removeAllElements();
		if (event.getSource() == consultList && consultList.hasFocus()) {

			String sql = createSql(employeeConsultRadio, providerConsultRadio, consultList, consultMainListModel);
			if (sql.equals("false"))
				return;

			String person = (employeeConsultRadio.isSelected()) ? "EMPREGADO" : "FORNECEDOR";

			DataBase.setResultSet(sql);
			try {
				String contracts = "";
				while (DataBase.resultSet.next()) {
					selectedListModel.addElement("INFORMAÇÕES DO " + person + ":");
					if (employeeConsultRadio.isSelected()) {
						selectedListModel.addElement("NOME: " + DataBase.resultSet.getString("name") + ", SEXO: "
								+ DataBase.resultSet.getString("gender"));
						selectedListModel.addElement("DT NASCIMENTO: " + DataBase.resultSet.getString("birth")
								+ ", CPF: " + DataBase.resultSet.getString("cpf"));
					} else if (providerConsultRadio.isSelected()) {
						selectedListModel.addElement("NOME: " + DataBase.resultSet.getString("name")
								+ ", CLASSIFICAÇÃO: " + DataBase.resultSet.getString("classification"));
						selectedListModel.addElement("CNPJ: " + DataBase.resultSet.getString("cnpj"));
					}
					selectedListModel.addElement("TELEFONE: " + DataBase.resultSet.getString("landline") + ", "
							+ DataBase.resultSet.getString("cellPhone") + ", EMAIL: "
							+ DataBase.resultSet.getString("email"));
					selectedListModel.addElement("ENDEREÇO: " + DataBase.resultSet.getString("address"));
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
				if (providerConsultRadio.isSelected()) {
					field1 = "OBJETO: ";
					field2 = ", VALOR DO CONTRATO: ";
					field3 = "DATA DE CELEBRAÇÃO: ";
					field4 = ", STATUS: ";
					incide1 = 4;
					indice2 = 1;
				}
				selectedListModel.addElement(
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
						selectedListModel.addElement(field1 + data[0] + field2 + data[incide1]);
						selectedListModel.addElement(field3 + data[indice2] + field4 + getStatus(data[2]));

						selectedListModel.addElement(
								"--------------------------------------------------------------------------------------------------------------");
						index = 0;
					}
				}
				if (contracts.length() == 0)
					selectedListModel.addElement("Não há contratos cadastrados.");
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

		if (employeeContractRadio.isSelected()) {
			showList(listModelforContracts);
			alterForm(addContractPanelCard, "addEmployeeContractPanel");
		} else if (providerContractRadio.isSelected()) {
			showList(listModelforContracts);
			alterForm(addContractPanelCard, "addProviderContractPanel");
		} else if (employeeConsultRadio.isSelected() || providerConsultRadio.isSelected()) {
			selectedListModel.removeAllElements();
			showList(consultMainListModel);
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
		if (employeeContractRadio.isSelected() || employeeConsultRadio.isSelected()) {
			sql = "SELECT * FROM employee";
			numberRegister = "cpf";
			labelNumberRegister = " - CPF: ";

		} else if (providerContractRadio.isSelected() || providerConsultRadio.isSelected()) {
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
		if (event.getSource() == cpfEmployeeField) {
			if (!cpfEmployeeField.hasFocus()) {
				validationRegisterNumber(cpfEmployeeField, cpfStatusLabel);
			}
		}
		if (event.getSource() == cnpjTextField) {
			if (!cnpjTextField.hasFocus()) {
				validationRegisterNumber(cnpjTextField, cnpjStatusLabel);
			}
		}
	}

	private void validationRegisterNumber(JTextField registerField, JLabel statusLabel) {
		String registerNumber = registerField.getText().trim();
		boolean validator;
		String typeRegister;
		if (registerField.getText().trim().equals(""))
			return;
		if (registerField == cpfEmployeeField) {
			try {
				validator = CPFValidator.validaCpf(registerNumber);
			} catch (java.lang.NumberFormatException e) {
				cpfStatusLabel.setText("CPF INVÁLIDO");
				cpfStatusLabel.setForeground(MyStyle.redSky);
				return;
			}
			typeRegister = "CPF ";
		} else {
			try {
				validator = CNPJValidator.validaCNPJ(registerNumber);
			} catch (java.lang.NumberFormatException e) {
				cnpjStatusLabel.setText("CNPJ INVÁLIDO");
				cnpjStatusLabel.setForeground(MyStyle.redSky);
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
