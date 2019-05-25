package club.candy.web3j.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class ERC20Airdropper extends Contract {
	private static final String BINARY = "608060405260008054600160a060020a0319163317905561060e806100256000396000f3006080604052600436106100535763ffffffff60e060020a600035041663062e43a181146100585780633dba73181461007b5780638da5cb5b14610117578063ccdd197914610148578063f2fde38b146101ad575b600080fd5b34801561006457600080fd5b50610079600160a060020a03600435166101ce565b005b34801561008757600080fd5b50604080516020600460248035828101358481028087018601909752808652610079968435600160a060020a031696369660449591949091019291829185019084908082843750506040805187358901803560208181028481018201909552818452989b9a9989019892975090820195509350839250850190849080828437509497506102f09650505050505050565b34801561012357600080fd5b5061012c610458565b60408051600160a060020a039092168252519081900360200190f35b34801561015457600080fd5b50604080516020600460248035828101358481028087018601909752808652610079968435600160a060020a03169636966044959194909101929182918501908490808284375094975050933594506104679350505050565b3480156101b957600080fd5b50610079600160a060020a0360043516610590565b60008054600160a060020a031633146101e657600080fd5b5060008054604080517f70a0823100000000000000000000000000000000000000000000000000000000815230600482015290518493600160a060020a038086169463a9059cbb9491169285926370a0823192602480820193602093909283900390910190829087803b15801561025c57600080fd5b505af1158015610270573d6000803e3d6000fd5b505050506040513d602081101561028657600080fd5b50516040805160e060020a63ffffffff8616028152600160a060020a039093166004840152602483019190915251604480830192600092919082900301818387803b1580156102d457600080fd5b505af11580156102e8573d6000803e3d6000fd5b505050505050565b60008054600160a060020a0316331461030857600080fd5b825160001061031657600080fd5b815183511461032457600080fd5b5060005b82518110156104525783600160a060020a031663a9059cbb848381518110151561034e57fe5b90602001906020020151848481518110151561036657fe5b906020019060200201516040518363ffffffff1660e060020a0281526004018083600160a060020a0316600160a060020a0316815260200182815260200192505050600060405180830381600087803b1580156103c257600080fd5b505af11580156103d6573d6000803e3d6000fd5b505050507fb88903f74059b09b78248a0df6ba49200ca616f185ca84aca28d3e74e754ab86838281518110151561040957fe5b90602001906020020151838381518110151561042157fe5b602090810290910181015160408051600160a060020a039094168452918301528051918290030190a1600101610328565b50505050565b600054600160a060020a031681565b60008054600160a060020a0316331461047f57600080fd5b825160001061048d57600080fd5b5060005b82518110156104525783600160a060020a031663a9059cbb84838151811015156104b757fe5b90602001906020020151846040518363ffffffff1660e060020a0281526004018083600160a060020a0316600160a060020a0316815260200182815260200192505050600060405180830381600087803b15801561051457600080fd5b505af1158015610528573d6000803e3d6000fd5b505050507fb88903f74059b09b78248a0df6ba49200ca616f185ca84aca28d3e74e754ab86838281518110151561055b57fe5b602090810290910181015160408051600160a060020a03909216825291810185905281519081900390910190a1600101610491565b600054600160a060020a031633146105a757600080fd5b600160a060020a038116156105df576000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b505600a165627a7a7230582075283ab64367bc72c70e54906786c551e2b1e80310a9ee82f4ee9dd2451b45980029";

    public static final String FUNC_WITHDRAWALTOKEN = "withdrawalToken";

    public static final String FUNC_MULTISENDDIFF = "multisendDiff";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_MULTISEND = "multisend";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event TOKENDROP_EVENT = new Event("TokenDrop", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected ERC20Airdropper(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ERC20Airdropper(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> withdrawalToken(String _tokenAddr) {
        final Function function = new Function(
                FUNC_WITHDRAWALTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_tokenAddr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> multisendDiff(String _tokenAddr, List<String> dests, List<BigInteger> values) {
        final Function function = new Function(
                FUNC_MULTISENDDIFF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_tokenAddr), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(dests, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> multisend(String _tokenAddr, List<String> dests, BigInteger _value) {
        final Function function = new Function(
                FUNC_MULTISEND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_tokenAddr), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(dests, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<TokenDropEventResponse> getTokenDropEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENDROP_EVENT, transactionReceipt);
        ArrayList<TokenDropEventResponse> responses = new ArrayList<TokenDropEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenDropEventResponse typedResponse = new TokenDropEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TokenDropEventResponse> tokenDropEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TokenDropEventResponse>() {
            @Override
            public TokenDropEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENDROP_EVENT, log);
                TokenDropEventResponse typedResponse = new TokenDropEventResponse();
                typedResponse.log = log;
                typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TokenDropEventResponse> tokenDropEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENDROP_EVENT));
        return tokenDropEventObservable(filter);
    }

    public static RemoteCall<ERC20Airdropper> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ERC20Airdropper.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ERC20Airdropper> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ERC20Airdropper.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static ERC20Airdropper load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC20Airdropper(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ERC20Airdropper load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC20Airdropper(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TokenDropEventResponse {
        public Log log;

        public String receiver;

        public BigInteger amount;
    }
}
