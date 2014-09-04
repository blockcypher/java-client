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

