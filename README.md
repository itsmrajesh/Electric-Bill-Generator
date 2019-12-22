# Electric-Bill-Generator
This is console based JDBC Application for Paying and Managing Electricity Bills.
This application having 3 profiles 


### For updated docs [click here](https://github.com/itsmrajesh/Electric-Bill-Generator)


## Installation
Clone this repository.
This Project uses Lombok library [setup](https://www.baeldung.com/lombok-ide)
Make sure that your eclipse is configured with lombok.
Without lombok this project will not working use above link to download lombok and see the tutorial fix the lombok in your eclipse.
Check out ebilldb.txt file in this repo (Folder) copy all the query in that file and paste in mysql DB command prompt then follow below steps
Go to dbutil folder in that open DButil.java file then change username and password for DB Configuration in 
``` Java
public Connection getConnection() {
try {
    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebill?autoReconnect=true&useSSL=false", dbUserName, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
```
if you have done every above steps correctly then your ready to use this project.

### YouTube Tutorial [click here](https://www.youtube.com/watch?v=y_YxwyYRJek) for JDBC Connection

# Credits:
	M RAJESH (Team Lead and Developer) 
	M V MANJUNATH (Developer)
	
### Contact me [GitHub](https://github.com/itsmrajesh)  [LinkedIn](https://linkedin.com/in/itsmrajesh/) [Twitter](https://github.com/itsmrajesh)


## Contributing
Pull requests are welcome. For any changes, please open an issue first to discuss what you would like to change.
Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
