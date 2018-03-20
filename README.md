# RestaurantApp/Request REST API
- This project serves as one of two API endpoint to an android mobile app
- All response will be in JSON format unless specified otherwise

# Database
---
- Redis Server 3.0.6

# Usage
### Request
---
- To view all requests server received

`localhost:8080/requests`

Method used: `GET`

Sample JSON:
```
{[
  {
    "seq": 0,
    "receiptSeq": "1",
    "tableNo": "1",
    "itemSeq": "2",
    "itemName": "White Panda",
    "quan": 2001,
    "subscribers": null,
    "done": false
  },
  {
    "seq": 1,
    "receiptSeq": "1",
    "tableNo": "1",
    "itemSeq": "4000",
    "itemName": "Black Panda",
    "quan": 2,
    "subscribers": null,
    "done": false
  }
]
```


- To view a specific item 

`localhost:8080/items/{id}`

Method used: `GET`

Sample JSON:
```
{
	"seqId": 1,
	"itemId": "I0001",
	"itemName": "Sushi",
	"price": 50000,
	"categoryByCategorySeqId": null,
	"available": true,
	"_links": {
		"self": {
			"href": "http://localhost:8080/items/I0001"
		},
		"category": {
			"href": "http://localhost:8080/categories/2"
		}
	}
}
```

`id`: the id of the item in the database, eg: `I0001`, `I0002`, etc...

- To find an item based on a condition(s)

`localhost:8080/items/search?name1=value1&name2=value2...`

Method used: `GET`

`name`: the attribute of the item, eg: name, price, isAvailable, etc...

`value`: value of that attribute

### Receipt
---
- To view all receipts

`localhost:8080/receipts`

Method used: `GET`

Sample JSON:
```
{
	"_embedded": {
		"receiptList": [
			{
				"seqId": 1,
				"total": 70000,
				"issueDate": "2018-03-13T21:04:26.000+0000",
				"paid": false,
				"dinerTableByTableSeqId": null,
				"_links": {
					"self": {
						"href": "http://localhost:8080/receipts/1"
					},
					"table": {
						"href": "http://localhost:8080/tables/T001"
					}
				}
			},
			{
				"seqId": 2,
				"total": 80000,
				"issueDate": "2017-03-13T21:05:05.000+0000",
				"paid": true,
				"dinerTableByTableSeqId": null,
				"_links": {
					"self": {
						"href": "http://localhost:8080/receipts/2"
					},
					"table": {
						"href": "http://localhost:8080/tables/T001"
					}
				}
			},
			...
		]
	}
}
```

- To view a specific receipt

`localhost:8080/receipts/{id}`

Method used: `GET`

Sample JSON:
```
{
	"seqId": 1,
	"total": 70000,
	"issueDate": "2018-03-13T21:04:26.000+0000",
	"paid": false,
	"dinerTableByTableSeqId": null,
	"_links": {
		"self": {
			"href": "http://localhost:8080/receipts/1"
		},
		"table": {
			"href": "http://localhost:8080/tables/T001"
		}
	}
}
```

`id`: the id of an receipt, eg: 1, 2, 3, etc... (auto increment)

- To create an receipt

`localhost:8080/receipts`

Method used: `POST`

Request must include a JSON body param

This API is called when customers want to receipt something and an receipt is created in the database

- To edit an receipt 

`localhost:8080/receipts/{id}`

Method used: `PUT`

Request must include a JSON body param

This API is called when the waiter sends an checkout request, then the cashier would confirm and the `total` field of the receipt in the database will be changed from 0 to the total amount

`id`: id of an receipt, eg: 1, 2, 3, etc... (auto increment)

- To add more to receipt detail

`localhost:8080/receiptdetails/{id}`

Method used: `POST`

Request must include a JSON body param

This API is called after the receipt is created and has been associated with items, then customers want to receipt more

`id`: id of an receipt detail, eg: 1, 2, 3, etc... (auto increment)

### Table
---
- To view all tables

`localhost:8080/tables`

Method used: `GET`

Sample JSON:
```
{
	"_embedded": {
		"dinerTableList": [
			{
				"seqId": 1,
				"tableId": "T001",
				"statusByStatusSeqId": null,
				"_links": {
					"self": {
						"href": "http://localhost:8080/tables/T001"
					},
					"status": {
						"href": "http://localhost:8080/status/3"
					}
				}
			},
			{
				"seqId": 2,
				"tableId": "T002",
				"statusByStatusSeqId": null,
				"_links": {
					"self": {
						"href": "http://localhost:8080/tables/T002"
					},
					"status": {
						"href": "http://localhost:8080/status/1"
					}
				}
			},
			...
		]
	}
}
```

- To view detail info of a table

`localhost:8080/tables/{id}`

Method used: `GET`

Sample JSON:
```
{
	"seqId": 1,
	"tableId": "T001",
	"statusByStatusSeqId": null,
	"_links": {
		"self": {
			"href": "http://localhost:8080/tables/T001"
		},
		"status": {
			"href": "http://localhost:8080/status/3"
		}
	}
}
```

`id`: the id of a table, eg: `T001`, `T002`, `T003`, etc...

- To find all unpaid receipt of an occupied table

`localhost:8080/tables/{id}/receipts

Method used: `GET`

Sample JSON:
```
{
	"_embedded": {
		"receiptDetailList": [
			{
				"seqId": 1,
				"quantity": 1,
				"receiptByReceiptSeqId": null,
				"itemByItemSeqId": null,
				"_links": {
					"receipt": {
						"href": "http://localhost:8080/receipts/1"
					},
					"item": {
						"href": "http://localhost:8080/items/I0001"
					}
				}
			},
			{
				"seqId": 2,
				"quantity": 1,
				"receiptByReceiptSeqId": null,
				"itemByItemSeqId": null,
				"_links": {
					"receipt": {
						"href": "http://localhost:8080/receipts/1"
					},
					"item": {
						"href": "http://localhost:8080/items/I0002"
					}
				}
			},
			...
		]
	}
}
```

- To find a table based on status

`localhost:8080/tables/search?status=value`

Method used: `GET`

Sample JSON:
```
{
	"_embedded": {
		"dinerTableList": [
			{
				"seqId": 1,
				"tableId": "T001",
				"statusByStatusSeqId": null,
				"_links": {
					"self": {
						"href": "http://localhost:8080/tables/T001"
					},
					"status": {
						"href": "http://localhost:8080/status/3"
					}
				}
			}
		]
	}
}
```

`status`: fixed query param name indicating the status of a table

`value`: the status of a table, eg: `AVA` (Available), `RES` (Reserved), `OCC` (Occupied)

