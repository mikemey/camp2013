# pay-broker
Broker service for payments (enabling strategy).

##Configuration
In src/main/resources/config/heroku.yaml file, the services.citizenFrontend.payPath must contain a ```{TXID}``` variable, which will be replaced with the generated ```transactionId```

##Payment states

A payment can have the following states:
- 'init': when a payment was created with POST /payments
- 'card': when card details were submitted with POST /payments/{transactionId}/card
- 'OK' / 'ERROR': when a response from the payment provider was received


### Table of Contents
**[Installation Instructions](#installation-instructions)**  
**[Jump to payments get](#get-payments)**  


## API

| Path                                        | Supported Methods | Description                                                |
| ------------------------------------------- | ------------------| ---------------------------------------------------------- |
|```/payments```                          |        GET       |  Returns a list of all transactions         |
|```/payments```                              |        POST       | Initiates the payment request                              |
|```/payments```                              |        GET       | Get a list of payments                              |
|```/payments/{transactionId}/card```         |        POST       | Makes payment with card                                    |
|```/payments/{transactionId}```              |        GET        | Returns the payment information                            |


## Installation Instructions


### GET /payments

This endpoint returns a list of all transactions (i.e. payments with their latest state).

####Request example

GET: /payments

####Response example
Content-Type: application/json

Status: 200
```json
[ 
  { 
    "reference":"REF-123",
	"transactionId": "dbe8524a-1489-4dfe-823d-09024c933947",
	"status": "init",
	"paymentId": null,
	"timestamp": 1426692079145,
	"amount": "123456"
  },
  { 
    "reference": "REF-124",
    "transactionId": "8f430116-5485-4a4c-a08a-711b903732a2",
	"status": "error",
	"paymentId": "PAY-456",
	"timestamp": 1426692080520,
	"amount": "123456"
  },
  {
    "reference": "REF-125",
	"transactionId": "af97416a-b806-4624-8b9c-7854b6cdb9ef",
	"status": "ok",
	"paymentId": "PAY-457",
	"timestamp": 1426692080918,
	"amount": "123456"
  }
]
```


###POST /payments

This endpoint initiates the payment request. It will receive the payment details and it will respond with the ```transactionId``` and the ```url``` to proceed with the payment.

####Request example

POST: /payments

Content-Type: application/json

```json
{
    "reference": "REF-1235",
    "amount": "123456",
    "returnUrl": "http://service_url/returnFromPayment"
}
```

#####Request field description

```reference``` (mandatory) This is the service payment reference

```amount``` (mandatory) This is the amount in pence. The amount must be greater than 0 AND must start with a number greater than 1.

```returnUrl``` (mandatory) The url in the calling service for redirecting the user after the payment details have been submitted to the payment provider.

####Response example
Status: 200
Content-Type: application/json
```json
{
    "transactionId": "79fb61b7-0501-4e3e-a8c6-dce47e5813d5",
    "url": "http://host/someUrl/79fb61b7-0501-4e3e-a8c6-dce47e5813d5"
}
```
#####Response field description
```transactionId``` (always present) This is a unique id associated to the payment.

```url``` (always present) This is the url where the user should be redirected to proceed with the payment.

###GET /payments

This endpoint is for retrieving a list of payments with information about their current status.
####Filtering
It is possible to filter the list payments received by ```status```, ```amount``` and ```date```
and this is done through the following query parameters:
-  ```status``` - this will be an exact match and the status of the payment (can be init, card, ok, error)
-  ```minAmount``` - filter the payments so that only those that have  ```minAmount``` <= ```amount``` will be returned 
-  ```maxAmount``` - filter the payments so that only those that have  ```maxAmount``` >= ```amount``` will be returned
-  ```minDate```   - filter the payments so that only those that have  ```minDate``` <= ```date```     will be returned 
-  ```maxDate```   - filter the payments so that only those that have  ```maxDate``` >= ```date```     will be returned


###POST /payments/{transactionId}/card

This endpoint receives the card details and sends the payment to the payment provider. Once the payment provider responds to the request it will redirect the user to the provided ```returnUrl```.

####Request example

POST: /payments/{transactionId}/card
Content-Type: application/json
```json
{
    "cardno" : "123456",
    "cvc"    : "456",
    "enddate": "03/20"
}
```

#####Request field description

```cardno```  (mandatory) This is the card number. It must either 13 (MasterCard), 15 (American Express) or 16 (Rest) digits. The card number is also validated using the Luhn algorith.

```cvc```     (mandatory) This is the card CVC. It must contain 3-4 digits.

```enddate``` (mandatory) This is the card end date in the format MM/YY, where MM is the month and YY the last two digits of the year. It must not be in the past or an invalid date.

####Response example
Status: 200
Content-Type: application/json
```json
{
    "url": "http://host/someUrl/79fb61b7-0501-4e3e-a8c6-dce47e5813d5",
    "error": "Invalid card details"
}
```
#####Response field description

```url```    (always present) This is the service url where the user should be redirected when the payment is processed.

```error```  (optional) This field is only present when invalid card details are sent and it contains the error message.


###GET /payments/{transactionId}

This endpoint returns the payment information given a ```transactionId```.

####Request example

GET: /payments/{transactionId}

#####Request field description

```transactionId``` (always present) This is a unique id associated to the payment.

####Response example
Content-Type: application/json

#####Succesful payment for the given ```transactionId```
Status: 200
```json
{
    "paymentId": "a8c6-dce47e5813d5"
}
```

#####Payment not found for the given ```transactionId```
Status: 404
```json
{
    "message": "Payment details not found."
}
```

#####Payment in an invalid state (not in state 'OK' or 'ERROR')
Status: 403
```json
{
    "message": "Forbidden request."
}
```

#####Response field description

```paymentId```  (optional) This is the paymentId from the connector which is associated to the ```transactionId```. Only present when ```transactionId``` is found.

```message```    (optional) Only present when the ```paymentId``` associated with the ```transactionId``` is not found.
