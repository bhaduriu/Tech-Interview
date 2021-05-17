## Software Engineer Interivew @ Optiver, Amsterdam

### Question 1: Shapes
Start out by creating an interface Shape, which has an abstract function GetArea() with integer return value. Then create implementations of this interface for three different shapes: Rectangle, Triangle and Circle. The formulas for calculating the area of each of these shapes are:
* Rectangle: height x width
* Triangle: (height x width) / 2
* Circle: 3.14 x radius x radius

The integer values required to calculate the area for each of these shapes will be provided to the constructor of each shape respectively in the order of appearance in the examples below.

Round the result of the calculation to the nearest integer value before returning it.

#### Examples
* Rectangle(height = 4, width = 3) : GetArea() returns 12
* Triangle(height = 5, width = 2) : GetArea() returns 5
* Circle(radius = 5) : GetArea() returns 79

The total area of these shapes is 96.

#### Solution
[/Optiver/Shapes.cpp](/Optiver/Shapes.cpp)
