import crossroad
node ControllerNode running controller
[
	platform amd64_linux
	main_class "controllernode.ControllerProgram"
]
node PriorANode running priorA
[
	platform armv7_linux
	main_class "prioranode.PriorityAProgram"
]
node SecondaryANode running secondaryA
[
	platform armv7_linux
	main_class "secondaryanode.SecondaryAProgram"
]
node PedestrianANode running pedestrianA
[
	platform armv7_linux
	main_class "pedestriananode.PedestrianAProgram"
]
node PriorBNode running priorB
[
	platform armv7_linux
	main_class "priorbnode.PriorityBProgram"
]
node SecondaryBNode running secondaryB
[
	platform armv7_linux
	main_class "secondarybnode.SecondaryBProgram"
]
node PedestrianBNode running pedestrianB
[
	platform armv7_linux
	main_class "pedestrianbnode.PedestrianBProgram"
]
node MonitorNode running monitor