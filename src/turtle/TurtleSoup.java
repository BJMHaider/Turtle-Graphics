/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

/**
 * TurtleSoup Class
 * 
 * Allows programmers to issue a series of commands to an on-screen “turtle”
 * that moves, drawing a line as it goes
 * 
 */

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sideLength
	 *            length of each side is the number of steps to move in the current
	 *            direction; must be positive
	 */
	public static void drawSquare(final Turtle turtle, final int sideLength) {
		final int numSides = 4; // Number of square sides
		final int dgreeAmount = 90; // The degrees amount of change in angle, with positive being clockwise

		for (int i = 0; i < numSides; i++) {
			turtle.forward(sideLength); // Moves the turtle in the current direction by units pixels, where units is an
										// integer
			turtle.turn(dgreeAmount); // Rotates the turtle by angle degrees to the right (clockwise)
		}
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon; you
	 * should derive it and use it here.
	 * 
	 * @param sides
	 *            number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(final int sides) {
		double angle = 0.0;
		final double straightAngle = 180.0;
		if (sides > 2)
			angle = (sides - 2) * straightAngle / sides;
		return angle;
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior angles.
	 * 
	 * @param angle
	 *            size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(final double angle) {
		final double straightAngle = 180.0;
		final double completeAngle = 360.0;
		return (int) Math.round(completeAngle / (straightAngle - angle));
	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
	 * draw.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sides
	 *            number of sides of the polygon to draw
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawRegularPolygon(final Turtle turtle, final int sides, final int sideLength) {
		final double angle = calculateRegularPolygonAngle(sides);
		final double straightAngle = 180.0;
		for (int i = sides; i > 0; i--) {
			turtle.forward(sideLength);
			turtle.turn(straightAngle - angle);
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the heading towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle in
	 * the direction of the target point (targetX,targetY), given that the turtle is
	 * already at the point (currentX,currentY) and is facing at angle
	 * currentHeading. The angle must be expressed in degrees, where 0 <= angle <
	 * 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
	 * 
	 * @param currentHeading
	 *            current direction as clockwise from north
	 * @param currentX
	 *            current location x-coordinate
	 * @param currentY
	 *            current location y-coordinate
	 * @param targetX
	 *            target point x-coordinate
	 * @param targetY
	 *            target point y-coordinate
	 * @return adjustment to heading (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateHeadingToPoint(final double currentHeading, final int currentX, final int currentY,
			final int targetX, final int targetY) {
		final double xCoords = targetX - currentX;
		final double yCoords = targetY - currentY;
		final double completeAngle = 360.0;
		double angle = Math.atan(xCoords / yCoords) * completeAngle / (2 * Math.PI) - currentHeading;
		return angle < 0.0 ? completeAngle + angle : angle;

	}

	/**
	 * Given a sequence of points, calculate the heading adjustments needed to get
	 * from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e. 0
	 * degrees). For each subsequent point, assumes that the turtle is still facing
	 * in the direction it was facing when it moved to the previous point. You
	 * should use calculateHeadingToPoint() to implement this function.
	 * 
	 * @param xCoords
	 *            list of x-coordinates (must be same length as yCoords)
	 * @param yCoords
	 *            list of y-coordinates (must be same length as xCoords)
	 * @return list of heading adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1
	 */
	public static List<Double> calculateHeadings(final List<Integer> xCoords, final List<Integer> yCoords) {

		List<Double> headings = new ArrayList<Double>();
		double relHeading = 0.0;
		for (int x = 0; x < xCoords.size() - 1; x++) {
			relHeading = calculateHeadingToPoint(relHeading, xCoords.get(x), yCoords.get(x), xCoords.get(x + 1),
					yCoords.get(x + 1));
			headings.add(relHeading);
		}
		return headings;
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can be
	 * as little or as much as you want.
	 * 
	 * @param turtle
	 *            the turtle context
	 */
	public static void drawPersonalArt(final Turtle turtle) {
		final int numSides = 40;
		final int sideLength = 30;
		for (int i = 0; i < numSides; i++) {
			for (int j = 0; j < numSides; j++) {
				turtle.forward(sideLength - (i - j));
				turtle.turn(sideLength);
				if (i % 2 == 0)
					turtle.color(PenColor.RED);
				else
					turtle.color(PenColor.GREEN);
			}
		}

	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		// drawSquare(turtle, 100);
		// drawRegularPolygon(turtle, 8, 100);
		 drawPersonalArt(turtle);
		turtle.draw();
	}

}
