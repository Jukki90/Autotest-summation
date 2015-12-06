package testsum.my.ru.test;


public class TestData {
	
	int firstOperand;
	int secondOperand;
	char operation;
	float result;
	
	public TestData(){
	}
	
	
	//-- getters and setters --
	public int getFirstOperand() {
		return firstOperand;
	}
	public void setFirstOperand(int firstNum) {
		this.firstOperand = firstNum;
	}
	public int getSecondOperand() {
		return secondOperand;
	}
	public void setSecondOperand(int secondNum) {
		this.secondOperand = secondNum;
	}
	public char getOperation() {
		return operation;
	}
	public void setOperation(char operation) {
		this.operation = operation;
	}
	public float getResult() {
		return result;
	}
	public void setResult(float result) {
		this.result = result;
	}

}
