run: FlightManagementSystem.class
	java FlightManagementSystem

FlightManagementSystem.class: FlightManagementSystem.java
	javac FlightManagementSystem.java

.PHONY: doc

doc:
	javadoc -private -author -version -d doc au.edu.uow.Collection au.edu.uow.CollectionDB au.edu.uow.UserInterface au.edu.uow.Networking

clean:
	rm *.class
	rm com/*/*.class