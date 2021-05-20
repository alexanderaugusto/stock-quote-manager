Feature: User can create new quotes to a stock

Scenario: Insert some quotes to a stock
	Given a stock of id '<stock>' quote of date '<date>' and value '<value>'
	When call the api route /quote passing the data
	Then the quote is created and the response status will be <status>
	
	Examples: 
	| stock | 	 date    | value | status |
	| petr4 | 2020-01-11 |  200  |  201   |
	| petr4 | 2020-02-22 |  450  |  201   |
	| petr4 | 2020-04-25 |  -400 |  400   |
	| petr4 | 2020-04-   |  300  |  400   |
	| petr4 | 2020-04-   |   a   |  400   |
	