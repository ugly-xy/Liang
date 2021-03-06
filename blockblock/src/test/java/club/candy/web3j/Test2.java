package club.candy.web3j;

import java.io.IOException;
import java.math.BigDecimal;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		System.out.println(Numeric.decodeQuantity("0x5533c6b0fb5a9208a3fb2abef7ffa580ab8c05b0a2aaf157f2dc912985224a4b"));
		EthService es = new EthService();
		Admin admin = es.initAdmin();
		String path = "/Users/walkerbao/Documents/keystore/UTC--2018-07-12T07-07-28.841142000Z--1dcd25daa86817b6782e479118c4faf960c99694.json";
		
		try {
			Credentials c =  WalletUtils.loadCredentials("lava2008", path);
			
//			String ths = EthService.sendTransaction(admin, c, "0xeBdF519aB51AD599779a5D111F76e6c6D8d50DA3", BigDecimal.ONE, Unit.KWEI);
//			System.out.println(ths);
			
			ObjectMapper mapper = new ObjectMapper();
			
			//EthService.getTransactionByHash(admin, "0x1753567c85837e766d71e1270df43cb4009d40d1ed7cbf604813d7a3133c3697").
			EthTransaction  etr = EthService.getTransactionByHash(admin, "0x80b5fd6f650bef7af43c1d485b24ee6ebc360e47594b23ade65d317ade5bb8b1");
			
			System.out.println(mapper.writeValueAsString(etr));
			
			
//			EthTransaction  etr2 = EthService.getTransactionByHash(admin, ths);
//			System.out.println(mapper.writeValueAsString(etr2));
			
			
			
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
