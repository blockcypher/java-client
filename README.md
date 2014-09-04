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
Transaction transaction = context.getTransactionService().getTransaction("09a228c6cf72989d81cbcd3a906dcb1d4b4a4c1d796537c34925feea1da2af35");
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
// WIF Format
String myPrivateKey = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
IntermediaryTransaction unsignedTransaction = context.getTransactionService()
        .newTransaction(new ArrayList<String>(Arrays.asList("mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz")),
                new ArrayList<String>(Arrays.asList("n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB")),
                500000
        );
SignUtils.signWithBase58KeyWithPubKey(unsignedTransaction, myPrivateKey);

Transaction transaction = context.getTransactionService().sendTransaction(unsignedTransaction);

System.out.println("Sent transaction: " + GsonFactory.getGson().toJson(transaction));
```

It will print the following:
```
Sent transaction: {"hash":"9a08144072b00abb263a85db31f7b444e93aca6cc0daa5113d71ed46800a75ae","block_height":-1,"addresses":["mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz","n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"],"total":91570000,"fees":0,"relayed_by":"127.0.0.1:48758","confirmed":"0001-01-01T00:00:00Z","received":"2014-09-04T15:03:55.117978081Z","ver":1,"lock_time":0,"vin_sz":1,"vout_sz":2,"confirmations":0,"inputs":[{"prev_hash":"1fd15302b378630a997b7061667877e8d4de4cd1144a9fc868c2f36ab681ae7e","output_index":3,"script":"47304402203b6a118441e1e78185bf2df5592dfd9218f10ab964b7e200b3052522e434457a022053bf917ad005bfea29f7ac5b322a48a5af695bcf483047a6a32f350ae0305cff012102b93edbd6aa7df3510a1e76d355428f382e5a25e167560eea34055f6f98d6bae4","output_value":91570000,"addresses":["mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"],"script_type":"pay-to-pubkey-hash"}],"outputs":[{"value":500000,"script":"76a914f343f510e12156df80fee18ea1a319002f55747788ac","spent_by":"","addresses":["n3hDuRYeYaeV4aEBqYF9byMK5B2c3tR1nB"],"script_type":"pay-to-pubkey-hash"},{"value":91070000,"script":"76a914a4e9eecbbfd050cb2d47eb0452a97ccb607f53c788ac","spent_by":"","addresses":["mvYwMT3aZ5jNcRNNjv7ckxjbqMDtvQbAHz"],"script_type":"pay-to-pubkey-hash"}]}
```