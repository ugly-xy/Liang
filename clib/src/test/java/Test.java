import java.io.IOException;

import com.tinify.Result;
import com.tinify.Source;
import com.tinify.Tinify;
import com.we.common.utils.JSONUtil;

public class Test {

	public static void main(String[] args) {
		String s = "http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/100";
		s = s.substring(0, s.length()-3)+"640";
		System.out.println(s);
		
		
		String key = "/start aa88c12469aa608872f876078b538b45bf0f1d86".substring(7);
		System.out.println(key);
		
	}
	
	
}
