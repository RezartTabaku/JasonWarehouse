// Agent forklift in project Warehouse

/* Initial beliefs and rules */

weightCanCarry(500).
staying_in(restArea).

/* Initial goals */

!start.

/* Plans */

+!newItem(weight) : carrying | weight > weightCanCarry
	<- true.

+!newItem(weight) : not carrying & item < weightCanCarry
	<- !go(supplier);
	   get(item);
	   !go(shelf,item).
	   
+!start :true <- .print("Started").

// Testing
