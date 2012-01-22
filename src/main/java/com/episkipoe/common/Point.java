package com.episkipoe.common;

public class Point {
	public float x, y;
	public Point() { x = y = (float)0.0; }
	public Point(float x, float y) { 
		this.x = x;
		this.y = y;
	}
	public Point(double x, double y) { 
		this.x = (float)x;
		this.y = (float)y;
	}
	
	public String toString() { return x + ", " + y; }
	
	public boolean inRange(Point rhs, double range) {
		return (distance(rhs)<range);
	}
	public double distance(Point rhs) {
		return Math.sqrt(Math.pow(x-rhs.x,2)+Math.pow(y-rhs.y,2));
	}
	public double angle(Point other) {
		return Math.atan2((other.y-y),(other.x-x));
	}
	public void addVector(double angle, double magnitude) {
		x += Math.cos(angle) * magnitude;
		y += Math.sin(angle) * magnitude;
	}
	public void normalize() {
		double dx = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
		if(Math.abs(dx)<1e-5) return;
		x/=dx;
		y/=dx;
	}

	public boolean equals(Point other) {
		return (x==other.x && y==other.y);
	}
	
	public Point scale(double factor) {
		return new Point(x*factor, y*factor);
	}
	public Point add(Point other) {
		return new Point(x+other.x, y+other.y);
	}

}
