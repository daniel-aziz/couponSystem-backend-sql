
██████╗  █████╗ ███╗   ██╗██╗███████╗██╗          █████╗ ███████╗██╗███████╗
██╔══██╗██╔══██╗████╗  ██║██║██╔════╝██║         ██╔══██╗╚══███╔╝██║╚══███╔╝
██║  ██║███████║██╔██╗ ██║██║█████╗  ██║         ███████║  ███╔╝ ██║  ███╔╝
██║  ██║██╔══██║██║╚██╗██║██║██╔══╝  ██║         ██╔══██║ ███╔╝  ██║ ███╔╝
██████╔╝██║  ██║██║ ╚████║██║███████╗███████╗    ██║  ██║███████╗██║███████╗
╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝╚══════╝╚══════╝    ╚═╝  ╚═╝╚══════╝╚═╝╚══════╝

██████╗ ██████╗  ██████╗      ██╗███████╗ ██████╗████████╗              ██████╗ ██╗  ██╗ █████╗ ███████╗███████╗     ██╗
██╔══██╗██╔══██╗██╔═══██╗     ██║██╔════╝██╔════╝╚══██╔══╝              ██╔══██╗██║  ██║██╔══██╗██╔════╝██╔════╝    ███║
██████╔╝██████╔╝██║   ██║     ██║█████╗  ██║        ██║       █████╗    ██████╔╝███████║███████║███████╗█████╗      ╚██║
██╔═══╝ ██╔══██╗██║   ██║██   ██║██╔══╝  ██║        ██║       ╚════╝    ██╔═══╝ ██╔══██║██╔══██║╚════██║██╔══╝       ██║
██║     ██║  ██║╚██████╔╝╚█████╔╝███████╗╚██████╗   ██║                 ██║     ██║  ██║██║  ██║███████║███████╗     ██║
╚═╝     ╚═╝  ╚═╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝                 ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝     ╚═╝

-----------------------------------
Login details for the SQL Server:

Username: root
password 12345678
server URL: jdbc:mysql://localhost:3306
DataBase name: couponDB

-----------------------------------
How to run the program:

To start the Program, run the Main method in the class 'scr.Program'
this will run the 4 static methods located in class 'scr.Testers.Test'

-----------------------------------
IMPORTANT NOTICE:
1. Test.dBDrop() - will drop a schema by the name 'couponDB'. -Activated by default-

2. Test.dBStart() - Creates, the CouponDB, Tables, Coupons Categories. -Activated by default-

3. Test.testAll() - runs all the methods for required from the Project Guidelines. -Activated by default-

4. Test.createEntriesToDB() - creates pre listed entries to the DB and rand coupon purchase,
- DEACTIVATED by default - as it will cause InvalidLoginCredentialsException ans duplicate entries SQLException


