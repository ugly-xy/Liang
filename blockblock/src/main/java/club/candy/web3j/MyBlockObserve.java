package club.candy.web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock.Block;

import rx.Subscription;

public class MyBlockObserve {

	private static Web3j web3j = null;
	
	public MyBlockObserve() {
		web3j = new EthService().initWeb3j();
	}
	

	/**
	 * 监听区块数据
	 */
	public static void observableBlockChain() {
		Subscription subscription = web3j.blockObservable(false).subscribe(block -> {
			Block blk = block.getResult();
			System.out.println("当前区块高度：" + blk.getNumber() + ",上一个区块：" + blk.getParentHash() + blk.getHash());
		});
		System.out.println("订阅的" + subscription.hashCode());
	}

}
