What does Spring batch do?

If well planned, can help to migrate lots of data thorugh lot of datasources, JSON, csv and xml. For medatata files you would have to built a custom reader, 
or another approach derivated from spring batch. Its like a framework for etls.

![image](https://github.com/user-attachments/assets/8f8f7766-2574-42ef-9997-433bde0f28b7)



Essentially, thats what it does. You can also read multiple files at the time, process data concurrently, asyncronously, many variations depending of the system resources and the ammount
of running processes being orchestrated in server.


My objective is just to have a shorthand so when i use this tool, i can remember faster, in this excercise
I migrate this batch of jsons: 
![image](https://github.com/user-attachments/assets/8871128d-60a9-4e58-9121-1a78f1f317cb)

to this: 
![image](https://github.com/user-attachments/assets/d68506e7-f2fe-43f1-b6f2-db3d08844922)
