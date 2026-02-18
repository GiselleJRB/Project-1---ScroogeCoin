public class TxHandler {
	private UTXOPool utxoPool;
	public TxHandler(UTXOPool utxoPool) {
		this.utxoPool = new UTXOPool(utxoPool);
	}
   public boolean isValidTx(Transaction tx) {
	   HashSet<UTXO> seen = new HashSet<>(); //creates collection that doesnt allow duplicates
	   double sum_input = 0;
	   double sum_output = 0;
	  
	  // Loop to run through all inputs in the transaction and check for validity
	  for (int ix = 0; ix < tx.numInputs(); ix++) {
		 Transaction.Input input = tx.getInput(ix);
		 // Create a UTXO corresponding to the input and check if it is in the current UTXO pool
		 UTXO ix_utxo = new UTXO(input.prevTxHash, input.outputIndex);

		 // 1 check if output claimed by tx are in current utxo pool
		 if (!utxoPool.contains(ix_utxo)){
			return false;
		 }

		 // 2 Check if the signatures on each input of tx are valid (use verifySignature from canvas instructions)
		 Transaction.Output output = utxoPool.getTxOutput(ix_utxo);
		 RSAKey public_key = output.address; // get publickey of output
		 byte[] message = tx.getRawDataToSign(ix); // get raw data to sign for the input
		 byte[] signature = input.signature; // get signature from the input

		 if (!RSAKey.verifySignature(public_key, message, signature)) {	
			return false;
		 }

		 // 3 No UTXO is claimed multiple times by tx
		  if (seen.contains(ix_utxo)) {
			  return false;
		  }
		  seen.add(ix_utxo);
		  sum_input += output.value; //input sum
	  }
		// 4  all of tx’s output values are non-negative, and
	   for (int i=0; i< tx.numOutputs(); i++){
		   Transaction.Output out= tx.getOutput(i); //this gets the current objects output
		   if (out.value < 0) {
			   return false;
		   }
		   sum_output +=out.value;
		}
	   //(5) the sum of tx’s input values is greater than or equal to the sum of its output values;
	   	if(sum_input < sum_output){
			return false;
		}
	  return true;
   }
   public Transaction[] handleTxs(Transaction[] possibleTxs) {
	   ArrayList<Transaction> acceptedTxs = new ArrayList<>();
        boolean valid = true;
        while (valid) {
            valid = false;
            for (Transaction tx : possibleTxs) {
                if (isValidTx(tx)) {
                    acceptedTxs.add(tx);
                    for (int i = 0; i < tx.numInputs(); i++) {
                        Transaction.Input in = tx.getInput(i);
                        UTXO utxo = new UTXO(in.prevTxHash, in.outputIndex);
                        utxoPool.removeUTXO(utxo);
                    }
                    for (int i = 0; i < tx.numOutputs(); i++) {
                        UTXO newUTXO = new UTXO(tx.getHash(), i);
                        utxoPool.addUTXO(newUTXO, tx.getOutput(i));
                    }
                    valid = true;
                }
            }
        }
        Transaction[] transact = acceptedTxs.toArray(new Transaction[acceptedTxs.size()]);
        return transact;
   }
} 
