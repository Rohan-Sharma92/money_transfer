# Implementation of REST API for money transfers between accounts

## Technology stack
- Java 8
- Maven
- Spark Framework (with embedded Jetty)
- google/gson
- Guice
- JUnit 4.12

## How to run
- java -jar "money_transfer-0.0.1-SNAPSHOT.jar"

## JSON Request Examples
- Account Add :
  {
	"id":<id>,
	"name":<name>
  }
 - Add money in wallet :
  {
	"accountId":<accountId>,
	"amount":<amount>,
	"currency":<currency>
  }
 - Transfer money :
  {
	"accountId":<creditAccount>,
	"amount":<amount>,
	"currency":<currency>,
	"debitAccount":<debitAccount>
   }

## Available services
- POST [http://localhost:4567/accounts/:id] : Service to add an account. It accepts an account object in JSON format.
- GET [http://localhost:4567/accounts/:id] : Service to retrieve details of an account with specified id.
- GET [http://localhost:4567/accounts/] : Service to retrieve all configured accounts.
- POST [http://localhost:4567/wallet] : Service to add money in wallet. It accepts the transfer details in JSON format.
- GET [http://localhost:4567/wallet/:id] : Service to retrieve wallet balance.
- POST [http://localhost:4567/transfer] : Service to transfer money between accounts. It accepts the transfer details in JSON format. 
