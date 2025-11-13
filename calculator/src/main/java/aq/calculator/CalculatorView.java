package aq.calculator;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorView {

    private CalculatorController calculatorController;

    private final String TITLE = "Калькулятор v.1.0";
    public static final String BTN_ADD_TITLE = "+";
    public static final String BTN_SUB_TITLE = "-";
    public static final String BTN_MUL_TITLE = "*";
    public static final String BTN_DIV_TITLE = "/";
    public static final String BTN_EQU_TITLE = "=";
    public static final String BTN_PERCENT_TITLE = "%";
    public static final String BTN_POINT_TITLE = ".";
    public static final String BTN_C_TITLE = "C";
    public static final String BTN_CE_TITLE = "CE";
    public static final String BTN_B_TITLE = "B";
    public static final String EMPTY_TEXT = "";

    private final int WIDTH_COMPONENT_CALCULATOR = 265;
    private final int HEIGHT_DISPLAY_PANEL = 80;
    private final int HEIGHT_BUTTON_PANEL = 320;
    private final int ROW_COUNT = 5;
    private final int COLUMN_COUNT = 4;
    public static final int MAX_NUMBER_COUNT = 15;

    private boolean isAllowCleanDisplay = false;

    private JFrame frame;
    private JPanel panelBase;
    private JPanel panelButton;
    private JPanel panelDisplay;
    private JTextField textFieldDisplay;
    private final JButton[][] BUTTONS;
    private final String[] BTN_INITIALS =
            {		BTN_B_TITLE, 	BTN_C_TITLE, 		BTN_CE_TITLE, 		BTN_PERCENT_TITLE,
                    "7", 			"8", 				"9", 				BTN_ADD_TITLE,
                    "4", 			"5", 				"6", 				BTN_SUB_TITLE,
                    "1", 			"2", 				"3", 				BTN_MUL_TITLE,
                    "0", 			BTN_POINT_TITLE, 	BTN_EQU_TITLE, 	    BTN_DIV_TITLE
            };

    public CalculatorView(CalculatorController calculatorController) {
        BUTTONS = new JButton[ROW_COUNT][COLUMN_COUNT];
        this.calculatorController = calculatorController;
    }

    public void createView() {
        EventQueue.invokeLater(() -> {
            getContent();
            setSettingsFrame();
        });
    }

    private void setSettingsFrame() {
        frame.setResizable(false);
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (WIDTH_COMPONENT_CALCULATOR / 2), 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void getContent() {
        packContent();
        addContent();
        eventMouseHandle();
    }

    private void packContent() {
        packFrame();
        packBasePanel();
        packDisplayPanel();
        packButtonPanel();
        packTextFieldDisplay();
        packButtons();
        setButtonsLocation();
    }

    private void packFrame() {
        frame = new JFrame(TITLE);
    }

    private void packBasePanel() {
        panelBase = new JPanel();
        panelBase.setLayout(new BorderLayout());
    }

    private void packDisplayPanel() {
        panelDisplay = new JPanel();
        panelDisplay.setPreferredSize(new Dimension(WIDTH_COMPONENT_CALCULATOR, HEIGHT_DISPLAY_PANEL));
        panelDisplay.setLayout(new BorderLayout());
    }

    private void packButtonPanel() {
        panelButton = new JPanel();
        panelButton.setLayout(new SpringLayout());
        panelButton.setPreferredSize(new Dimension(WIDTH_COMPONENT_CALCULATOR, HEIGHT_BUTTON_PANEL));
    }

    private void packTextFieldDisplay() {
        textFieldDisplay = new JTextField() {
            private static final long serialVersionUID = 1L;
            @Override
            protected Document createDefaultModel() {
                return new LimitField();
            }
        };
        textFieldDisplay.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        textFieldDisplay.setFont(new Font("Arial", Font.PLAIN, 30));
        textFieldDisplay.setEditable(false);
        textFieldDisplay.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        textFieldDisplay.setText(EMPTY_TEXT);
    }

    private void packButtons() {
        int initialIndex = 0;

        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                JButton button = new JButton(BTN_INITIALS[initialIndex]);
                button.setName(BTN_INITIALS[initialIndex]);
                button.setPreferredSize(new Dimension(55, 55));
                button.setFont(new Font("arial", Font.PLAIN, 15));

                if(BTN_INITIALS[initialIndex].equals(BTN_C_TITLE)) {
                    button.setToolTipText("Сброс");
                } else if(BTN_INITIALS[initialIndex].equals(BTN_CE_TITLE)) {
                    button.setToolTipText("Очистка содеожимого дисплея");
                } else if(BTN_INITIALS[initialIndex].equals(BTN_B_TITLE)) {
                    button.setToolTipText("Удаление последнего введенного символа");
                }
                BUTTONS[i][j] = button;
                initialIndex++;
            }
        }
    }

    private void setButtonsLocation() {
        int marginNorthSide = 10;
        int marginWestSide = 15;
        int marginBetweenButton = 5;

        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                if(i == 0) {
                    ((SpringLayout) panelButton.getLayout()).putConstraint(SpringLayout.NORTH, BUTTONS[i][j], marginNorthSide,
                            SpringLayout.NORTH, panelButton);
                    if(j == 0) {
                        ((SpringLayout) panelButton.getLayout()).putConstraint(SpringLayout.WEST, BUTTONS[i][j], marginWestSide,
                                SpringLayout.WEST, panelButton);
                    } else {
                        ((SpringLayout) panelButton.getLayout()).putConstraint(SpringLayout.WEST, BUTTONS[i][j], marginBetweenButton,
                                SpringLayout.EAST,  BUTTONS[i][j - 1]);
                    }
                } else {
                    ((SpringLayout) panelButton.getLayout()).putConstraint(SpringLayout.NORTH, BUTTONS[i][j], marginBetweenButton,
                            SpringLayout.SOUTH, BUTTONS[i - 1][j]);
                    if(j == 0) {
                        ((SpringLayout) panelButton.getLayout()).putConstraint(SpringLayout.WEST, BUTTONS[i][j], marginWestSide,
                                SpringLayout.WEST, panelButton);
                    } else {
                        ((SpringLayout) panelButton.getLayout()).putConstraint(SpringLayout.WEST, BUTTONS[i][j], marginBetweenButton,
                                SpringLayout.EAST,  BUTTONS[i][j - 1]);
                    }
                }
            }
        }
    }

    private void addContent() {
        addButtonsOnButtonPanel();
        addTextFieldDisplayToDisplayPanel();
        addDisplayPanelOnBasePanel();
        addButtonPanelOnBasePanel();
        addBasePanelOnFrame();
    }

    private void addButtonsOnButtonPanel() {
        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                panelButton.add(BUTTONS[i][j]);
            }
        }
    }

    private void addTextFieldDisplayToDisplayPanel() {
        panelDisplay.add(textFieldDisplay);
    }

    private void addDisplayPanelOnBasePanel() {
        panelBase.add(panelDisplay, BorderLayout.NORTH);
    }

    private void addButtonPanelOnBasePanel() {
        panelBase.add(panelButton, BorderLayout.CENTER);
    }

    private void addBasePanelOnFrame() {
        frame.add(panelBase);
    }

    private void eventMouseHandle() {
        ActionListener mouseListener = (ae) -> {
            JButton button = (JButton) ae.getSource();
            String textButton = button.getText();
            calculatorActionHandler(textButton);
        };

        for(JButton[] buttonRow : BUTTONS) {
            for(JButton button : buttonRow) {
                button.addActionListener(mouseListener);
            }
        }
    }

    private void calculatorActionHandler(String textButton) {
        String textDisplay = null;
        if(isAllowCleanDisplay()) {
            cleanDisplay();
            textDisplay = textFieldDisplay.getText();
            disallowCleanDisplay();
        } else {
            textDisplay = textFieldDisplay.getText();
        }
        Pattern patternNumbers = Pattern.compile("\\d");
        Matcher matcherNumber = patternNumbers.matcher(textButton);
        switch (textButton) {
            case BTN_POINT_TITLE:
                handleButtonPointEvent(textDisplay, textButton);
                break;
            case BTN_SUB_TITLE:
                handleButtonSubEvent(textDisplay, textButton);
                break;
            case BTN_ADD_TITLE:
                handleButtonAddEvent(textDisplay, textButton);
                break;
            case BTN_DIV_TITLE:
                handleButtonDivEvent(textDisplay, textButton);
                break;
            case BTN_MUL_TITLE:
                handleButtonMulEvent(textDisplay, textButton);
                break;
            case BTN_PERCENT_TITLE:
                handleButtonPercentEvent(textDisplay, textButton);
                break;
            case BTN_B_TITLE:
                handleButtonB_Event(textDisplay);
                break;
            case BTN_C_TITLE:
                handleButtonC_Event(textDisplay);
                break;
            case BTN_CE_TITLE:
                handleButtonCE_Event(textDisplay);
                break;
            case BTN_EQU_TITLE:
                handleButtonEqualsEvent(textDisplay, textButton);
                break;
            default:
                if(matcherNumber.find()) {
                    if(isAllowCleanDisplay()) {
                        cleanDisplay();
                        disallowCleanDisplay();
                    }
                    if (isDisableButton(BTN_B_TITLE)) {
                        enableButton(BTN_B_TITLE);
                    }
                    if(isDisableButton(BTN_CE_TITLE)) {
                        enableButton(BTN_CE_TITLE);
                    }
                    if(isDisableButton(BTN_PERCENT_TITLE)) {
                        enableButton(BTN_PERCENT_TITLE);
                    }
                    setTextDisplay(textDisplay, textButton);
                } else {
                    cleanDisplay();
                    if(isDisableButton(BTN_POINT_TITLE)) {
                        enableButton(BTN_POINT_TITLE);
                    }
                }
                break;
        }
    }

    private void handleButtonPointEvent(String textDisplay, String textButton) {
        if(textDisplay.equals(EMPTY_TEXT)) {
            setTextDisplay(EMPTY_TEXT, "0" + textButton);
            disableButton(BTN_POINT_TITLE);
        } else {
            setTextDisplay(textDisplay, textButton);
            disableButton(BTN_POINT_TITLE);
        }
    }

    private void handleButtonPercentEvent(String textDisplay, String textButton) {
        calculatorController.pushOperand(textDisplay);
        calculatorController.pushOperator(textButton);
        cleanDisplay();
        String resultString = calculatorController.getResult();
        setTextDisplay(resultString, EMPTY_TEXT);
        disableButton(BTN_CE_TITLE);
        disableButton(BTN_B_TITLE);
        disableButton(BTN_PERCENT_TITLE);
    }

    private void handleButtonB_Event(String textDisplay) {
        if(!textDisplay.equals(EMPTY_TEXT)) {
            if(textDisplay.length() > 1) {
                String operand = textDisplay.substring(0, textDisplay.length() - 1);
                setTextDisplay(operand, EMPTY_TEXT);
                Pattern patternPoint = Pattern.compile("\\.");
                Matcher matcherPoint = patternPoint.matcher(operand);
                if(!matcherPoint.find()) {
                    if(isDisableButton(BTN_POINT_TITLE)) {
                        enableButton(BTN_POINT_TITLE);
                    }
                }
            } else {
                cleanDisplay();
            }
        }
    }

    private void handleButtonCE_Event(String textDisplay) {
        if(!textDisplay.equals(EMPTY_TEXT)) {
            cleanDisplay();
        }
        if (isDisableButton(BTN_B_TITLE)) {
            enableButton(BTN_B_TITLE);
        }
        if(isDisableButton(BTN_POINT_TITLE)) {
            enableButton(BTN_POINT_TITLE);
        }
    }

    private void handleButtonC_Event(String textDisplay) {
        if(textDisplay.equals(CalculatorModel.TO_LONG_RESULT_VALUE_ERROR_MESSAGE) ||
                textDisplay.equals(CalculatorModel.DIVIDE_BY_ZERO_ERROR_MESSAGE)) {
            enableAllButtons();
        }
        cleanDisplay();
        calculatorController.cleanMemory();
        if (isDisableButton(BTN_B_TITLE)) {
            enableButton(BTN_B_TITLE);
        }
        if(isDisableButton(BTN_CE_TITLE)) {
            enableButton(BTN_CE_TITLE);
        }
        if(isDisableButton(BTN_POINT_TITLE)) {
            enableButton(BTN_POINT_TITLE);
        }
        if(isDisableButton(BTN_PERCENT_TITLE)) {
            enableButton(BTN_PERCENT_TITLE);
        }
    }

    private void enableAllButtons() {
        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                if(!BUTTONS[i][j].isEnabled()) {
                    BUTTONS[i][j].setEnabled(true);
                }
            }
        }
    }

    private void handleButtonEqualsEvent(String textDisplay, String textButton) {
        calculatorController.pushOperand(textDisplay);
        calculatorController.pushOperator(textButton);
        cleanDisplay();
        String resultString = calculatorController.getResult();
        if(resultString.equals(CalculatorModel.TO_LONG_RESULT_VALUE_ERROR_MESSAGE) ||
                resultString.equals(CalculatorModel.DIVIDE_BY_ZERO_ERROR_MESSAGE)) {
            setTextDisplay(resultString, EMPTY_TEXT);
            enableOnlyC_Button();
        } else {
            setTextDisplay(resultString, EMPTY_TEXT);
            disableButton(BTN_B_TITLE);
            disableButton(BTN_CE_TITLE);
            disableButton(BTN_PERCENT_TITLE);
            if(isDisableButton(BTN_POINT_TITLE)) {
                enableButton(BTN_POINT_TITLE);
            }
        }
    }

    private void enableOnlyC_Button() {
        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                if(!BUTTONS[i][j].getText().equals(BTN_C_TITLE)) {
                    BUTTONS[i][j].setEnabled(false);
                }
            }
        }
    }

    private void handleButtonSubEvent(String textDisplay, String textButton) {
        if(textDisplay.equals(EMPTY_TEXT)) {
            setTextDisplay(textDisplay, textButton);
        } else {
            calculatorController.pushOperand(textDisplay);
            calculatorController.pushOperator(textButton);
            allowCleanDisplay();
            String resultString = calculatorController.getResult();
            setTextDisplay(resultString == EMPTY_TEXT ? textDisplay : resultString, EMPTY_TEXT);
            if(isDisableButton(BTN_POINT_TITLE)) {
                enableButton(BTN_POINT_TITLE);
            }
        }
    }

    private void handleButtonAddEvent(String textDisplay, String textButton) {
        if(textDisplay.equals(EMPTY_TEXT)) {
            setTextDisplay(textDisplay, EMPTY_TEXT);
        } else {
            calculatorController.pushOperand(textDisplay);
            calculatorController.pushOperator(textButton);
            allowCleanDisplay();
            String resultString = calculatorController.getResult();
            setTextDisplay(resultString == EMPTY_TEXT ? textDisplay : resultString, EMPTY_TEXT);
            if(isDisableButton(BTN_POINT_TITLE)) {
                enableButton(BTN_POINT_TITLE);
            }
        }
    }

    private void handleButtonMulEvent(String textDisplay, String textButton) {
        if(textDisplay.equals(EMPTY_TEXT)) {
            setTextDisplay(textDisplay, EMPTY_TEXT);
        } else {
            calculatorController.pushOperand(textDisplay);
            calculatorController.pushOperator(textButton);
            allowCleanDisplay();
            String resultString = calculatorController.getResult();
            setTextDisplay(resultString == EMPTY_TEXT ? textDisplay : resultString, EMPTY_TEXT);
            if(isDisableButton(BTN_POINT_TITLE)) {
                enableButton(BTN_POINT_TITLE);
            }
        }
    }

    private void handleButtonDivEvent(String textDisplay, String textButton) {
        if(textDisplay.equals(EMPTY_TEXT)) {
            setTextDisplay(textDisplay, EMPTY_TEXT);
        } else {
            calculatorController.pushOperand(textDisplay);
            calculatorController.pushOperator(textButton);
            allowCleanDisplay();
            String resultString = calculatorController.getResult();
            setTextDisplay(resultString == EMPTY_TEXT ? textDisplay : resultString, EMPTY_TEXT);
            if(isDisableButton(BTN_POINT_TITLE)) {
                enableButton(BTN_POINT_TITLE);
            }
        }
    }

    private void cleanDisplay() {
        textFieldDisplay.setText(EMPTY_TEXT);
    }

    private void setTextDisplay(String textDisplay, String textButton) {
        String operand = textDisplay + textButton;
        textFieldDisplay.setText(operand);
    }

    private boolean isAllowCleanDisplay() {
        return isAllowCleanDisplay;
    }

    private void allowCleanDisplay() {
        isAllowCleanDisplay = true;
    }

    private void disallowCleanDisplay() {
        isAllowCleanDisplay = false;
    }

    public void enableButton(String buttonTitle) {
        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                if(BUTTONS[i][j].getText().equals(buttonTitle)) {
                    BUTTONS[i][j].setEnabled(true);
                }
            }
        }
    }

    public void disableButton(String buttonTitle) {
        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                if(BUTTONS[i][j].getText().equals(buttonTitle)) {
                    BUTTONS[i][j].setEnabled(false);
                }
            }
        }
    }

    public boolean isEnableButton(String buttonTitle) {
        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                if(BUTTONS[i][j].getText().equals(buttonTitle)) {
                    return BUTTONS[i][j].isEnabled();
                }
            }
        }
        return false;
    }

    public boolean isDisableButton(String buttonTitle) {
        for(int i = 0; i < ROW_COUNT; i++) {
            for(int j = 0; j < COLUMN_COUNT; j++) {
                if(BUTTONS[i][j].getText().equals(buttonTitle)) {
                    return !BUTTONS[i][j].isEnabled();
                }
            }
        }
        return false;
    }

    private class LimitField extends PlainDocument {
        private static final long serialVersionUID = 1L;
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if(str == null) {
                return;
            }
            if((getLength() + str.length()) <= MAX_NUMBER_COUNT) {
                super.insertString(offs, str, a);
            } else {
                textFieldDisplay.setText(str.substring(0, str.length() -  1));
            }
        }
    }
}
