package club.candy.web3j.common;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import club.candy.web3j.contracts.ERC20Code;

public class EthUtil {

	static final Logger log = LoggerFactory.getLogger(EthUtil.class);
	
	
	// GAS价格
	public static BigInteger GAS_PRICE = Unit.GWEI.getWeiFactor().toBigInteger().multiply(BigInteger.valueOf(2));;
	// GAS上限
	public static BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000L);

	// 交易费用
	public static BigInteger GAS_VALUE = BigInteger.valueOf(1300_000L);
	

	public static BigInteger valueEth(Double value) {
		return valueEth(BigDecimal.valueOf(value)).toBigInteger();
	}
	
	public static BigDecimal valueEth(BigDecimal value){
		return Convert.toWei(value, Unit.ETHER);
	}
	
	public static BigInteger valueGWei(Double value) {
		return valueGWei(BigDecimal.valueOf(value)).toBigInteger();
	}
	
	public static BigDecimal valueGWei(BigDecimal value){
		return Convert.toWei(value, Unit.GWEI);
	}
	
	public static BigInteger value(Long val,int decimalUnits){
		return BigInteger.valueOf(val).multiply(BigInteger.TEN.pow(decimalUnits));
	}
	
	public static BigInteger contractValue(ERC20Code erc,Double number) throws Exception{
		return contractValue(erc,BigDecimal.valueOf(number)).toBigInteger();
	}
	
	public static BigDecimal contractValue(ERC20Code erc,BigDecimal number) throws Exception{
		return number.multiply(BigDecimal.TEN.pow(erc.decimals().send().intValue()));
	}
	
}
