package testsum.my.ru.test;

import static org.junit.Assert.*; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test; 
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Step;


@RunWith(Parameterized.class)
public class MathTest 
{
	@Parameter("first operand")
	private int firstNum;
	@Parameter("second operand")
	private int secondNum;
	@Parameter("operation")
	private char operation;
	@Parameter("result from file")
	private float result;
	
	private Properties properties;
	
	public MathTest(@Parameter int first,@Parameter int second,@Parameter char oper,@Parameter float res){
		this.firstNum=first;
		this.secondNum=second;
		this.operation=oper;
		this.result=res;
	}
	
	
	@Parameters
	public static Collection<Object[]> data()throws IOException{		
		Collection<TestData> list = new ArrayList<TestData>();
		FileReader reader = new FileReader("data.txt");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line = bufferedReader.readLine();
		while(line!=null){
			String[] part = line.split(";");
			TestData data = new TestData();
			data.setFirstOperand(Integer.parseInt(part[0]));
			data.setSecondOperand(Integer.parseInt(part[1]));
			char ch = part[2].charAt(0);
			data.setOperation(ch);
			data.setResult(Float.parseFloat(part[3]));
			list.add(data);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		
		List<Object[]> resList = new ArrayList<Object[]>() ;
		for (TestData row : list){
			resList.add(new Object[]{row.getFirstOperand(), row.getSecondOperand(),row.getOperation(), row.getResult()});
			
		}
	
		
		return resList;
	}

	@Test
	@Step("Main Test")
	public void test(){
		float calcRes=0;
		if (operation=='+'){
			calcRes = firstNum+secondNum;
		}else if(operation=='-'){
			calcRes = firstNum-secondNum;
		}
		else if(operation=='*'){
			calcRes = firstNum*secondNum;
		}
		else if(operation=='/'){
			if(secondNum==0){ return;}
			calcRes = firstNum/secondNum+firstNum%secondNum;
		}
		//System.out.println(firstNum+","+secondNum+","+operation+","+result+","+calcRes);
		assertTrue(calcRes== result);
	}


}
