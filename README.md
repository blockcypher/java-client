java-client
===========

Java SDK for BlockCypher

Maven
-----
Add this to your POM:

```xml
    <dependencies>
        <dependency>
            <groupId>com.blockcypher</groupId>
            <artifactId>java-client</artifactId>
            <version>0.1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>BlockCypher Maven Repo</id>
            <url>https://raw.github.com/blockcypher/java-client/mvn-repo/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
```	
	
Transaction Example
-----
* Get an existing transaction with a given hash:

```java
// Choose API version / currency / network, here v1 on Bitcoin's testnet network
BlockCypherContext context = new BlockCypherContext("v1", "btc", "test3");
String txHash = "09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35"
Transaction transaction = context.getTransactionService().getTransaction(txHash);
System.out.println("Transaction is confirmed? " + transaction.getConfirmed());
System.out.println("Transaction fees are:     " + transaction.getFees());
```

It will print the following:
```
Transaction is confirmed? 2014-08-03T15:52:11Z
Transaction fees are: 0
```

* Create a transaction of 500000 satoshis from address mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz to address n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB

```java
BlockCypherContext context = new BlockCypherContext("v1", "btc", "test3");
// WIF Format of your private Key
String myPrivateKey = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
IntermediaryTransaction unsignedTx = context.getTransactionService()
    .newTransaction(
        new ArrayList<String>(Arrays.asList("mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz")),
        new ArrayList<String>(Arrays.asList("n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB")),
        500000
    );
SignUtils.signWithBase58KeyWithPubKey(unsignedTx, myPrivateKey);

Transaction tx = context.getTransactionService().sendTransaction(unsignedTx);

System.out.println("Sent transaction: " + GsonFactory.getGsonPrettyPrint().toJson(tx));
```

It will print the following:
```
Sent transaction: {
  "hash": "219e58e159851626b728a10d8f2aedcfca387dd4371242f421be27f70f19585a",
  "block_height": -1,
  "addresses": [
    "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz",
    "n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"
  ],
  "total": 89410000,
  "fees": 0,
  "relayed_by": "127.0.0.1:50987",
  "confirmed": "0001-01-01T00:00:00Z",
  "received": "2014-09-04T15:23:43.95376663Z",
  "ver": 1,
  "lock_time": 0,
  "vin_sz": 1,
  "vout_sz": 2,
  "confirmations": 0,
  "inputs": [
    {
      "prev_hash": "075f42cabe7eb01efd905d1ca0cb9fa23dd46db0658cd92c0198ef38814999f5",
      "output_index": 3,
      "script": "483045022100cca2901981d1c1840830b261963814eb1f14c56627e2a171b07a35ad44d49fbe0220485909ab2b79017861444840efce615e63a0d79fd1b69ef88c3ec00f1af255b5012102b93edbd6aa7df3510a1e76d355428f382e5a25e167560eea34055f6f98d6bae4",
      "output_value": 89410000,
      "addresses": [
        "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"
      ],
      "script_type": "pay-to-pubkey-hash"
    }
  ],
  "outputs": [
    {
      "value": 500000,
      "script": "76a914f343f510e12156df80fee18ea1a319002f55747788ac",
      "spent_by": "",
      "addresses": [
        "n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"
      ],
      "script_type": "pay-to-pubkey-hash"
    },
    {
      "value": 88910000,
      "script": "76a914a4e9eecbbfd050cb2d47eb0452a97ccb607f53c788ac",
      "spent_by": "",
      "addresses": [
        "mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"
      ],
      "script_type": "pay-to-pubkey-hash"
    }
  ]
}
```