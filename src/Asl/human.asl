// Agent human in project warehouse

/* Initial beliefs and rules */

weightCanCarry(100).
staying_in(restArea).

/* Initial goals */

!newItem(23).

/* Plans */

+!newItem(Weight) : carrying | Weight > weightCanCarry
	<- .print("Cannot carry this item").

+!newItem(Weight): true
	<- go(supplier)
//	   get(item);
//	   go(3,3)
	   .
	   
-!newItem(Weight) :true <- .print("Cannot Carry item").


+!start :true <- .print("Started").
