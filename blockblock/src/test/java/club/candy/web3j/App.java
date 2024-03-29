package club.candy.web3j;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.geth.Geth;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import club.candy.web3j.contracts.ERC20Airdropper;
import club.candy.web3j.contracts.ERC20Code;
import rx.Subscription;
import org.web3j.utils.Convert.Unit;

public class App {
	// GAS价格
	public static BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
	// GAS上限
	public static BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000L);

	// 交易费用
	public static BigInteger GAS_VALUE = BigInteger.valueOf(1300_000L);
	
	public static final String SMART_CONTRACT_ADDRESS =  "0xe970962d3e00911806cb704ee16dffafade750f2";
	
	public static final String AIRDROPER_CONTRACT_ADDRESS = "0xbaf9d9999abf3d5970bb89d50211215de666ed80";
	
	static String toAddr = "0x1DCD25daa86817B6782E479118c4Faf960c99694";
	static String toAddr2 = "0x376E6cCCd136BeAA18982545952c2B1eA43499BE";
	
	//0xa1ead9464bbdd9eb0a5d2753dae754422f4efc0b 
	//0x29f356dc9f4300ba9b58a0681da0701af03228ed
	//0xe970962d3e00911806cb704ee16dffafade750f2
	//0xd0f58fc8071e248b4313ce1e90ac5ac48bbbb6bf

	public static void main(String[] args)  {
		// 获取余额
		try {
			//String toAddr = "0xebdf519ab51ad599779a5d111f76e6c6d8d50da3";
			
			EthService es = new EthService();

			Web3j web3j = es.initWeb3j();
			
			
			
	        // 加载钱包
			String path = "/Users/walkerbao/Documents/keystore/UTC--2018-07-12T04-57-40.1749000Z--ebdf519ab51ad599779a5d111f76e6c6d8d50da3.json";
			Credentials credentials =   WalletUtils.loadCredentials("password", path);
			
//			String path = "/Users/walkerbao/Documents/keystore/UTC--2018-07-12T07-07-28.841142000Z--1dcd25daa86817b6782e479118c4faf960c99694.json";
//			Credentials credentials =  WalletUtils.loadCredentials("lava2008", path);
//			
//			String path2 = "/Users/walkerbao/Documents/keystore/UTC--2018-07-12T04-57-40.1749000Z--ebdf519ab51ad599779a5d111f76e6c6d8d50da3.json";
//			Credentials c2 =   WalletUtils.loadCredentials("password", path2);
			
//			ERC20Airdropper dr = ERC20Airdropper.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
//			System.out.println(dr.getContractAddress());
			
//			
//			ERC20Code rc = ERC20Code.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT, new BigInteger("1000000000000000000000000000"), "Peppa Pig", BigInteger.valueOf(18L), "PEPPA").send();
//			System.out.println(rc.getContractAddress());
			//System.out.println(Convert.toWei(BigDecimal.valueOf(1000), Unit.ETHER).toBigInteger());
			//pay(web3j, c2,credentials.getAddress());
			
//			pay(web3j, credentials,AIRDROPER_CONTRACT_ADDRESS);
//			
//			airdropper(web3j, credentials);
	        // 加载合约
	        //
			Set<String> sets = new HashSet<String>();
			sets.add(toAddr.toLowerCase());
			Admin admin = es.initAdmin();
			Subscription subscription = admin.blockObservable(false).subscribe(block -> {
				Block blk = block.getResult();
				//System.out.println("当前区块高度：" + blk.getNumber() + ",上一个区块：" + blk.getParentHash() +" "+ blk.getHash());
				List<TransactionResult>  trs = blk.getTransactions();
				for(TransactionResult tr :trs) {
					try {
						EthTransaction  etr = EthService.getTransactionByHash(web3j,tr.get().toString());
						Transaction t = etr.getResult();
						if(t.getValue().compareTo(BigInteger.ZERO)>0) {
							System.out.println(t.getHash()+" from:"+t.getFrom()+" to:"+t.getTo()+" v:"+t.getValue()+" s:"+t.getS());
							if(sets.contains(t.getTo())){
								System.out.println("aaaaaaaaaaaaaaa--->dddddddd");
								pay(web3j, credentials,t.getFrom(),t.getValue().multiply(BigInteger.valueOf(10000)));
							}
						}
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			System.out.println("订阅的" + subscription.hashCode());
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (InvalidAlgorithmParameterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchProviderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (CipherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void pay(Web3j web3j, Credentials credentials,String toAddr,BigInteger value) throws Exception, IOException {
		ERC20Code keySCode = ERC20Code.load(SMART_CONTRACT_ADDRESS, web3j, credentials, GAS_PRICE, GAS_LIMIT);
		
		System.out.println(keySCode.name().send());
		System.out.println(keySCode.symbol().send());
		System.out.println("from:"+keySCode.balanceOf(credentials.getAddress()).send()+"   to:"+keySCode.balanceOf(toAddr).send());
		System.out.println(keySCode.decimals().send());
		System.out.println(keySCode.isValid());
		
		System.out.println("ETH:"+EthService.getBalance(web3j, credentials.getAddress()));
		
		TransactionReceipt receipt = keySCode.transfer(toAddr, value).send();
		System.out.println(receipt.getTransactionHash());
      

		System.out.println("from:"+keySCode.balanceOf(credentials.getAddress()).send()+"   to:"+keySCode.balanceOf(toAddr).send());
	}

	private static void airdropper(Web3j web3j, Credentials credentials) throws Exception {
		//Airdropper dr = Airdropper.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
		ERC20Airdropper dr = ERC20Airdropper.load(AIRDROPER_CONTRACT_ADDRESS, web3j, credentials, GAS_PRICE, GAS_LIMIT);
		System.out.println(dr.getContractAddress());
		
		List<String> arr = new ArrayList<String>();
		arr.add(toAddr);
		arr.add(toAddr2);
		arr.add(toAddr);
		arr.add(toAddr2);
		arr.add(toAddr);
		arr.add(toAddr2);
		arr.add(toAddr);
		arr.add(toAddr2);
		arr.add(toAddr);
		arr.add(toAddr2);
		arr.add(toAddr);
		arr.add(toAddr2);
		arr.add(toAddr);
		arr.add(toAddr2);
		arr.add(toAddr);
		arr.add(toAddr2);
		arr.add(toAddr);
		arr.add(toAddr2);
		TransactionReceipt receipt = dr.multisend(SMART_CONTRACT_ADDRESS,arr, new BigInteger("10221312311231231")).send();
		System.out.println(receipt.getTransactionHash());
		
		List<BigInteger> vals = new ArrayList<BigInteger>();
		vals.add(new BigInteger("10221312311231231"));
		vals.add(new BigInteger("20221312311231231"));
		vals.add(new BigInteger("11221312311231231"));
		vals.add(new BigInteger("21221312311231231"));
		vals.add(new BigInteger("12221312311231231"));
		vals.add(new BigInteger("22221312311231231"));
		vals.add(new BigInteger("13221312311231231"));
		vals.add(new BigInteger("23221312311231231"));
		vals.add(new BigInteger("14221312311231231"));
		vals.add(new BigInteger("24221312311231231"));
		vals.add(new BigInteger("15221312311231231"));
		vals.add(new BigInteger("25221312311231231"));
		vals.add(new BigInteger("16221312311231231"));
		vals.add(new BigInteger("26221312311231231"));
		vals.add(new BigInteger("17221312311231231"));
		vals.add(new BigInteger("27221312311231231"));
		vals.add(new BigInteger("18221312311231231"));
		vals.add(Convert.toWei(BigDecimal.valueOf(1), Unit.ETHER).toBigInteger());
		
		TransactionReceipt receipt2 = dr.multisendDiff(SMART_CONTRACT_ADDRESS, arr, vals).send();
		System.out.println(receipt2.getTransactionHash());
		
		TransactionReceipt receipt3 = dr.withdrawalToken(SMART_CONTRACT_ADDRESS).send();
		System.out.println(receipt3.getTransactionHash());
	}

	/**
	 * 开始挖矿
	 */
	//@Test
	public void startMining() {

	}

	/**
	 * 新建用户
	 */
	//@Test
	public void CreateNewAccounts() {
		String account = "";
		try {
			EthService es = new EthService();
			Admin web3j = es.initAdmin();
			account = es.newAccount(web3j, "123456");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(account);
	}

	/**
	 * 获取钱包里的所有用户
	 */
	//@Test
	public void getAllAccounts() {
		try {
			EthService es = new EthService();
			Geth geth = es.initGeth();
			Request<?, EthAccounts> request = geth.ethAccounts();
			List<String> list = request.send().getAccounts();
			System.out.println(list.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户余额
	 */
	//@Test
	public void getAccountBalance() {
		try {
			String address = "0xebdf519ab51ad599779a5d111f76e6c6d8d50da3";// 0x8b7483ea3c7a545dbf93bf07764b479e0469e821
			EthService es = new EthService(); // 0xdbaea9b9d0b4d9dfff546466c89de34c9711a942
			Web3j web3 = es.initWeb3j();
			EthGetBalance ethGetBalance1 = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
			System.out.println(ethGetBalance1.getBalance());
			System.out.println(
					address + "的余额:" + Convert.fromWei(ethGetBalance1.getBalance().toString(), Convert.Unit.ETHER)); // .new
																														// 18))).setScale(6)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 转账
	 */
	//@Test
	public void transferBalance() {
		try {
			EthService es = new EthService();
			Admin admin = es.initAdmin();
			boolean flag = EthService.unlockAccount(admin, "0xebdf519ab51ad599779a5d111f76e6c6d8d50da3", "password",
					new BigInteger(1800 + ""));
			System.out.println(flag);

			BigInteger value = Convert.toWei("12", Convert.Unit.ETHER).toBigInteger();

			Credentials credentials = WalletUtils.loadCredentials("lava2008",
					"/Users/walkerbao/Documents/keystore/UTC--2018-07-12T07-07-28.841142000Z--1dcd25daa86817b6782e479118c4faf960c99694.json");

			// get the next available nonce
			BigInteger nonce = EthService.getNonce(admin, "0xebdf519ab51ad599779a5d111f76e6c6d8d50da3");

			// create our transaction
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, GAS_PRICE, GAS_LIMIT,
					"0x1dcd25daa86817b6782e479118c4faf960c99694", value);

			// sign & send our transaction
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			EthSendTransaction ethSendTransaction = admin.ethSendRawTransaction(hexValue).send();

			System.out.println(ethSendTransaction.getResult());
			System.out.println(ethSendTransaction.getTransactionHash());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 监听新区块
	 */
	//@Test
	public void observableBlockChain() {
		EthService es = new EthService();
		Admin admin = es.initAdmin();
		Subscription subscription = admin.blockObservable(false).subscribe(block -> {
			Block blk = block.getResult();
			System.out.println("当前区块高度：" + blk.getNumber() + ",上一个区块：" + blk.getParentHash() + blk.getHash());
		});
		System.out.println("订阅的" + subscription.hashCode());
	}

}
