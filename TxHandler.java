public class TxHandler {

	private UTXOPool utxoPool;

	 /* Creates a public ledger whose current UTXOPool (collection of unspent 
	* transaction outputs) is utxoPool. This should make a defensive copy of 
	* utxoPool by using the UTXOPool(UTXOPool uPool) constructor.
	*/
   public TxHandler(UTXOPool utxoPool) {
	  this.utxoPool = new UTXOPool(utxoPool);
   }

   /* Returns true if 
	* (1) all outputs claimed by tx are in the current UTXO pool, 
	* (2) the signatures on each input of tx are valid, 
	* (3) no UTXO is claimed multiple times by tx, 
	* (4) all of tx’s output values are non-negative, and
	* (5) the sum of tx’s input values is greater than or equal to the sum of   
		  its output values;
	   and false otherwise.
	*/

   public boolean isValidTx(Transaction tx) {
	  
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

		 if (!RSAKey.verifySignature(pubKey, message, signature)) {	
			return false;
		 }

		 // 3 No UTXO is claimed multiple times by tx
	  }




		 



	  
	  return false;
   }

   /* Handles each epoch by receiving an unordered array of proposed 
	* transactions, checking each transaction for correctness, 
	* returning a mutually valid array of accepted transactions, 
	* and updating the current UTXO pool as appropriate.
	*/
   public Transaction[] handleTxs(Transaction[] possibleTxs) {
	  // IMPLEMENT THIS
	  return null;
   }
	/* Creates a public ledger whose current UTXOPool (collection of unspent 
	 * transaction outputs) is utxoPool. This should make a defensive copy of 
	 * utxoPool by using the UTXOPool(UTXOPool uPool) constructor.
	 */
	public TxHandler(UTXOPool utxoPool) {
		// IMPLEMENT THIS
		this.utxoPool = new UTXOPool(utxoPool);
	}

	/* Returns true if 
	 * (1) all outputs claimed by tx are in the current UTXO pool, 
	 * (2) the signatures on each input of tx are valid, 
	 * (3) no UTXO is claimed multiple times by tx, 
	 * (4) all of tx’s output values are non-negative, and
	 * (5) the sum of tx’s input values is greater than or equal to the sum of   
	        its output values;
	   and false otherwise.
	 */

	public boolean isValidTx(Transaction tx) {
		// IMPLEMENT THIS
		return false;
	}

	/* Handles each epoch by receiving an unordered array of proposed 
	 * transactions, checking each transaction for correctness, 
	 * returning a mutually valid array of accepted transactions, 
	 * and updating the current UTXO pool as appropriate.
	 */
	public Transaction[] handleTxs(Transaction[] possibleTxs) {
		// IMPLEMENT THIS
		return null;
	}

} 
