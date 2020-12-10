package cadSystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Window extends JFrame {

	public static JMenuItem registerProviderMenuItem, registerEmployeeMenuItem, addContractMenuItem, exitMenuItem;
	public static JMenuItem consultAndAlterMenuItem;

	public static JPanel mainPanelCard, addContractPanelCard;

	public static JTextField nameEmployeeField, cpfEmployeeField, birthEmployeeField, emailEmployeeField,
			landlineEmployeeField, cellPhoneEmployeeField, adrressEmployeeField, cnpjTextField, providerNameField,
			landlineProviderField, cellPhoneProviderField, emailProviderField, providerAdrressField,
			functionEmployeeField, startDateContractEmployeeField, endDateContractEmployeeField, salaryEmployeeField,
			departmentEmployeeField, objectProviderContractField, startDateContractProviderField,
			endDateContractProviderField, paymentSignContractField, amountPaymentContractField;

	public static JComboBox<String> providerClassificationCombo;

	public static JRadioButton maleRadio, femRadio, employeeContractRadio, providerContractRadio, employeeConsultRadio,
			providerConsultRadio;

	public static JList<String> consultList, selectedList, listForContracts;

	public static DefaultListModel<String> consultMainListModel, selectedListModel, listModelforContracts;

	public static JLabel cpfStatusLabel, cnpjStatusLabel;
	public static ButtonGroup consultPerson, genderRadioGroup, consultContractPerson;

	private Listener listener = new Listener();

	public Window() {
		setTitle("CadSystem");
		setSize(500, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setBackground(MyStyle.graySky);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		registerProviderMenuItem = new JMenuItem("FORNECEDOR");
		registerProviderMenuItem.addActionListener(listener);
		registerEmployeeMenuItem = new JMenuItem("EMPREGADO");
		addContractMenuItem = new JMenuItem("ADICIONAR CONTRATO");
		addContractMenuItem.addActionListener(listener);
		registerEmployeeMenuItem.addActionListener(listener);
		exitMenuItem = new JMenuItem("SAIR");
		exitMenuItem.addActionListener(listener);
		JMenu registerMenu = new JMenu("CADASTRO");
		registerMenu.add(registerProviderMenuItem);
		registerMenu.add(registerEmployeeMenuItem);
		registerMenu.add(addContractMenuItem);
		registerMenu.add(exitMenuItem);

		consultAndAlterMenuItem = new JMenuItem("CONSULTAR/ALTERAR");
		consultAndAlterMenuItem.addActionListener(listener);
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

		setDisplayTitle("EMPREGADO", employeeRegisterPanel);

		JPanel employeeRegisterMainPanel = new JPanel();
		employeeRegisterMainPanel.setLayout(new GridLayout(6, 1));

		nameEmployeeField = new JTextField(33);
		setPanel(" NOME :", nameEmployeeField, employeeRegisterMainPanel);

		JLabel employeeCpfLabel = new JLabel(" CPF :", JLabel.LEFT);
		employeeCpfLabel.setFont(MyStyle.helveticaBold);
		cpfEmployeeField = new JTextField(10);
		cpfEmployeeField.setFont(MyStyle.helveticaBold);
		cpfEmployeeField.addFocusListener(listener);
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
		setPanel(" TEL :", landlineEmployeeField, employeePhonesPanel);
		cellPhoneEmployeeField = new JTextField("(88) ", 13);
		setPanel(" CEL :", cellPhoneEmployeeField, employeePhonesPanel);
		employeeRegisterMainPanel.add(employeePhonesPanel);

		adrressEmployeeField = new JTextField(34);
		setPanel(" END.:", adrressEmployeeField, employeeRegisterMainPanel);

		setAddAndExitButtons("ADD/ALT EMPREGADO", employeeRegisterMainPanel);

		employeeRegisterPanel.add("Center", employeeRegisterMainPanel);

		JPanel providerRegisterPanel = new JPanel();
		providerRegisterPanel.setLayout(new BorderLayout());

		setDisplayTitle("FORNECEDOR", providerRegisterPanel);

		JPanel providerRegisterMainPanel = new JPanel();
		providerRegisterMainPanel.setLayout(new GridLayout(7, 1));

		providerNameField = new JTextField(33);
		setPanel(" NOME :", providerNameField, providerRegisterMainPanel);

		JLabel clientCnpjLabel = new JLabel(" CNPJ :", JLabel.LEFT);
		clientCnpjLabel.setFont(MyStyle.helveticaBold);
		cnpjTextField = new JTextField(23);
		cnpjTextField.setFont(MyStyle.helveticaBold);
		cnpjTextField.addFocusListener(listener);
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
		setPanel(" TEL :", landlineProviderField, providerPhonesPanel);
		cellPhoneProviderField = new JTextField("(88) ", 13);
		setPanel(" CEL :", cellPhoneProviderField, providerPhonesPanel);
		providerRegisterMainPanel.add(providerPhonesPanel);

		emailProviderField = new JTextField(33);
		setPanel(" EMAIL :", emailProviderField, providerRegisterMainPanel);

		providerAdrressField = new JTextField(34);
		setPanel(" END.:", providerAdrressField, providerRegisterMainPanel);

		setAddAndExitButtons("ADD/ALT FORNECEDOR", providerRegisterMainPanel);

		providerRegisterPanel.add("Center", providerRegisterMainPanel);

		JPanel consultPanel = new JPanel();
		setDisplayTitle("CONSULTAR/ALTERAR", consultPanel);

		JPanel consultMainPanel = new JPanel();
		consultMainPanel.setLayout(new GridLayout(2, 1, 5, 5));

		consultMainListModel = new DefaultListModel<String>();
		consultList = new JList<String>(consultMainListModel);
		providerConsultRadio = new JRadioButton("FORNECEDOR");
		employeeConsultRadio = new JRadioButton("EMPREGADO");
		consultPerson = new ButtonGroup();
		setHigherPanel(employeeConsultRadio, providerConsultRadio, consultPerson, consultList, consultMainPanel);

		selectedListModel = new DefaultListModel<String>();
		selectedList = new JList<String>(selectedListModel);
		setChangeButtons("ALTERAR", "EXCLUIR", "VOLTAR", new JScrollPane(selectedList), consultMainPanel);

		consultPanel.add(consultMainPanel);

		JPanel addContractPanel = new JPanel();
		setDisplayTitle("ADICIONAR CONTRATO", addContractPanel);

		JPanel addContractMainPanel = new JPanel();
		addContractMainPanel.setLayout(new GridLayout(2, 1));

		listModelforContracts = new DefaultListModel<String>();
		listForContracts = new JList<String>(listModelforContracts);
		employeeContractRadio = new JRadioButton("EMPREGADO");
		providerContractRadio = new JRadioButton("FORNECEDOR");
		consultContractPerson = new ButtonGroup();
		setHigherPanel(employeeContractRadio, providerContractRadio, consultContractPerson, listForContracts,
				addContractMainPanel);

		JPanel addEmployeeContractPanelCard = new JPanel();
		addEmployeeContractPanelCard.setLayout(new GridLayout(4, 1, 3, 3));

		functionEmployeeField = new JTextField(30);
		setPanel("FUNÇÃO: ", functionEmployeeField, addEmployeeContractPanelCard);

		JPanel datesContractPanel = new JPanel();
		datesContractPanel.setLayout(new GridLayout(1, 2));
		startDateContractEmployeeField = new JTextField(8);
		setPanel("INÍCIO: ", startDateContractEmployeeField, datesContractPanel);
		endDateContractEmployeeField = new JTextField(8);
		setPanel("FIM: ", endDateContractEmployeeField, datesContractPanel);
		addEmployeeContractPanelCard.add(datesContractPanel);

		JPanel salaryAndDepartmentPanel = new JPanel();
		salaryAndDepartmentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		salaryEmployeeField = new JTextField("00,00", 6);
		setPanel("SALÁRIO: R$ ", salaryEmployeeField, salaryAndDepartmentPanel);
		departmentEmployeeField = new JTextField(15);
		setPanel("DEPART: ", departmentEmployeeField, salaryAndDepartmentPanel);
		addEmployeeContractPanelCard.add(salaryAndDepartmentPanel);

		setAddAndExitButtons("ADICIONAR CONTRATO", addEmployeeContractPanelCard);

		JPanel addProviderContractPanelCard = new JPanel();
		addProviderContractPanelCard.setLayout(new GridLayout(4, 1));

		objectProviderContractField = new JTextField(30);
		setPanel("OBJETO: ", objectProviderContractField, addProviderContractPanelCard);

		JPanel panelContractProviderDates = new JPanel();
		panelContractProviderDates.setLayout(new GridLayout(1, 2));
		startDateContractProviderField = new JTextField(8);
		setPanel("DT ACORDO: ", startDateContractProviderField, panelContractProviderDates);
		endDateContractProviderField = new JTextField(8);
		setPanel("DT ENTREGA: ", endDateContractProviderField, panelContractProviderDates);
		addProviderContractPanelCard.add(panelContractProviderDates);

		JPanel panelContractProviderPayment = new JPanel();
		panelContractProviderPayment.setLayout(new GridLayout(1, 2));
		paymentSignContractField = new JTextField("00,00", 9);
		setPanel("SINAL: R$ ", paymentSignContractField, panelContractProviderPayment);
		amountPaymentContractField = new JTextField("00,00", 10);
		setPanel("TOTAL: R$ ", amountPaymentContractField, panelContractProviderPayment);
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

	private void setHigherPanel(JRadioButton employeeRadio, JRadioButton providerRadio, ButtonGroup buttonGroup,
			JList<String> listForContracts, JPanel maindPanel) {
		JPanel higherpanel = new JPanel();
		higherpanel.setLayout(new BorderLayout());
		JPanel selectedPersonPanel = new JPanel();
		selectedPersonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		employeeRadio.setFont(MyStyle.helveticaBold);
		employeeRadio.setBackground(MyStyle.graySky);
		employeeRadio.addItemListener(listener);
		providerRadio.addItemListener(listener);
		providerRadio.setFont(MyStyle.helveticaBold);
		providerRadio.setBackground(MyStyle.graySky);
		buttonGroup.add(employeeRadio);
		buttonGroup.add(providerRadio);
		selectedPersonPanel.add(employeeRadio);
		selectedPersonPanel.add(providerRadio);
		higherpanel.add("North", selectedPersonPanel);
		listForContracts.addListSelectionListener(listener);
		higherpanel.add("Center", new JScrollPane(listForContracts));
		maindPanel.add(higherpanel);
	}

	private void setAddAndExitButtons(String labelButton, JPanel mainPanel) {
		JButton addButton = new JButton(labelButton);
		addButton.setBackground(MyStyle.greenSky);
		addButton.setForeground(Color.WHITE);
		addButton.setFont(MyStyle.helveticaBold);
		addButton.addActionListener(listener);
		JButton exitButton = new JButton("VOLTAR");
		exitButton.setBackground(MyStyle.BlueSky);
		exitButton.setForeground(Color.WHITE);
		exitButton.setFont(MyStyle.helveticaBold);
		exitButton.addActionListener(listener);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(addButton);
		panel.add(exitButton);
		mainPanel.add(panel);

	}

	private void setDisplayTitle(String title, JPanel panel) {
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
		alterButton.addActionListener(listener);
		excludeButton.addActionListener(listener);
		exitButton.addActionListener(listener);
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

	private void setPanel(String textLabel, JTextField field, JPanel panel) {
		JLabel label = new JLabel(textLabel, JLabel.LEFT);
		label.setFont(MyStyle.helveticaBold);
		field.setFont(MyStyle.helveticaBold);
		JPanel flowPanel = new JPanel();
		flowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(label);
		flowPanel.add(field);
		panel.add(flowPanel);

	}
}