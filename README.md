# javaSmartPhoneMobileApp

At unit4 stage, we try to implement more difficult part such as retailer can upload item's picture to remote database and user can also search the item by ketwords from the remote database and return search result. After completing this part, retailer can use add item function to add anything into database.

The other thing is we have slightly changed the table schema because we have found we can use String type to store image information via JSON encoding and decoding. Moreover, for the user and retailer's password colum, we have used md5() method to encode the information. And for the Login class, we make it as "pareant" so that it can be extended by Consumer and Retailer class. 


