import crossroad
[
	
]
node ControllerNode running controller
[
	platform armv8_linux
	main_class "controllernode.ControllerProgram"
]
node PriorANode running priorA, pedestrianB
[
	platform armv8_linux
	main_class "prioranode.PriorityPedestrianAProgram"
]
node SecondaryANode running secondaryA,  pedestrianA
[
	platform armv8_linux
	main_class "secondaryanode.SecondaryPedestrianAProgram"
]
node PriorBNode running priorB
[
	platform armv8_linux
	main_class "priorbnode.PriorityBProgram"
]
node SecondaryBNode running secondaryB
[
	platform armv8_linux
	main_class "secondarybnode.SecondaryBProgram"
]
node MonitorNode running monitor