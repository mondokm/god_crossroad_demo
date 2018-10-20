import crossroad
node ControllerNode running controller
[
	platform armv8_linux
	main_class "controllernode.ControllerProgram"
	address "192.168.1.1"
	username "pi"
	password "raspberry"
]
node PriorANode running priorA, pedestrianB
[
	platform armv8_linux
	main_class "prioranode.PriorityPedestrianAProgram"
	address "192.168.1.26"
	username "pi"
	password "raspberry"
]
node SecondaryANode running secondaryA,  pedestrianA
[
	platform armv8_linux
	main_class "secondaryanode.SecondaryPedestrianAProgram"
	address "192.168.1.25"
	username "pi"
	password "raspberry"
]
node PriorBNode running priorB
[
	platform armv8_linux
	main_class "priorbnode.PriorityBProgram"
	address "192.168.1.24"
	username "pi"
	password "raspberry"
]
node SecondaryBNode running secondaryB
[
	platform armv8_linux
	main_class "secondarybnode.SecondaryBProgram"
	address "192.168.1.27"
	username "pi"
	password "raspberry"
]