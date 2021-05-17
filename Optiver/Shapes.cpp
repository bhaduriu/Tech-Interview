#include <iostream>
#include <memory>
#include <numeric>
#include <vector>
#include <cmath>

/* OOP factory pattern */

class Shape
{
    public:
        Shape();
        virtual int GetArea() = 0;
        virtual ~Shape() = 0;
};
Shape::Shape() { } // nothing to here
Shape::~Shape(){  /*std::cout << "Rest in peace" << std::endl;*/ }


class Rectangle : public Shape
{
    public:
        Rectangle(const int &height, const int &width);
        ~Rectangle();
    private:
        int GetArea();
        int _height;
        int _width;
};

Rectangle::Rectangle(const int &height, const int &width)
    : _height(height), _width(width) { }

Rectangle::~Rectangle() = default;

int Rectangle::GetArea()
{
    return _height * _width;
}


class Triangle : public Shape
{
    public:
        Triangle(const int &height, const int &width);
        ~Triangle();
    private:
        int GetArea();
        int _height;
        int _width;
};

Triangle::Triangle(const int &height, const int &width)
    : _height(height), _width(width) { }

Triangle::~Triangle() = default;

int Triangle::GetArea()
{
    return round(0.5 * _height * _width);
}


class Circle : public Shape
{
    public:
        Circle(const int &radius);
        ~Circle();
    private:
        int GetArea();
        int _radius;
};

Circle::Circle(const int &radius)
    : _radius(radius) { }

Circle::~Circle() = default;

int Circle::GetArea()
{
    return round(3.14 * _radius * _radius);
}


int main()
{
    int rectangleHeight = 0, rectangleWidth = 0;
    int triangleHeight = 0, triangleWidth = 0;
    int circleRadius = 0;

    std::cin >> rectangleHeight >> rectangleWidth >> triangleHeight >> triangleWidth >> circleRadius;

    std::vector<std::unique_ptr<Shape>> shapes;
    shapes.emplace_back(std::make_unique<Rectangle>(rectangleHeight, rectangleWidth));
    shapes.emplace_back(std::make_unique<Triangle>(triangleHeight, triangleWidth));
    shapes.emplace_back(std::make_unique<Circle>(circleRadius));

    const int totalArea = std::accumulate(shapes.begin(), shapes.end(), 0, [](int total, const auto& shape)
            { return total + shape->GetArea(); });
    std::cout << totalArea << "\n";

    return 0;
}
