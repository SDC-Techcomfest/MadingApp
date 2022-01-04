Setting API nya dlu :
Untuk Android ada di Helper -> BaseUrl
Untuk API ada di Properties->applicationUrl
Untuk Database kita menggunakan SqlServer, jadi tinggal membuat database baru dengan nama "MadingApp", dan jalankan API maka table databasenya akan termigrasi sendiri 

Untuk menghubungkan API dengan database cukup edit 

"ConnectionStrings": {
    "db": "Server=.\\{Nama Server};Database=MadingApp;Trusted_Connection=True",
    di API dibagian appsettings.json
Setting juga dibagian MadingAppContext di API  optionsBuilder.UseSqlServer("Server=.\\{Nama Server};Database=MadingApp;Trusted_Connection=True");
