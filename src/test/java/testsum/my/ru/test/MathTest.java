package testsum.my.ru.test;

import static org.junit.Assert.*; 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.junit.Test; 
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Stories;
import testsum.my.ru.test.TestData;;

@Features("Математические операции")
@Stories("Проверка математических операций")
@RunWith(Parameterized.class)
public class MathTest {
	@Parameter("first operand")
	private int firstNum;
	@Parameter("second operand")
	private int secondNum;
	@Parameter("operation")
	private char operation;
	@Parameter("result from file")
	private float result;
	
	public MathTest(@Parameter int first,@Parameter int second,@Parameter char oper,@Parameter float res)throws IOException{
		this.firstNum=first;
		this.secondNum=second;
		this.operation=oper;
		this.result=res;
	}
	
	private static String getProperty(String field)throws IOException{
		FileInputStream fis;
        Properties property = new Properties();
        String res=null;
        try {
            fis = new FileInputStream("src/config/config.properties");
            property.load(fis);
            res = property.getProperty(field);
           
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        	return res;
	}

	
	private static List<Object[]> getDataFromFile()throws IOException{
		Collection<TestData> list = new ArrayList<TestData>();
		InputStream instream = MathTest.class.getResourceAsStream(getProperty("link"));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(instream));
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
	


	@Parameters
	public static Collection<Object[]> data()throws IOException{
		return getDataFromFile();
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
		
		assertTrue(calcRes== result);
	}


}
