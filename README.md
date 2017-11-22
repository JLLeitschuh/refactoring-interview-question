# Refactoring Interview Problem

The goal of this problem is to try to see how you tackle refactoring code 

## Hints

 - Are there methods that are instance methods that could be made `static`?
   - If a method has a dependency on a field, could that field be passed as an argument instead?
 - Are there methods that can be made `private`?
 - Are there fields in classes that are not being used?
 - In general, constructors should "do no work". Instead, try to make it so that constructors are
 simply assigning their parameters to fields.
 - In IntelliJ pressing `F6` when a class is selected in the project view will pull up a UI to help 
 moving classes.