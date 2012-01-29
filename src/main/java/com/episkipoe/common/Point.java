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
		if(other==null) return false;
		return (x==other.x && y==other.y);
	}
	
	public Point scale(double factor) {
		return new Point(x*factor, y*factor);
	}
	public Point add(Point other) {
		if(other==null) return this;
		return new Point(x+other.x, y+other.y);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		float result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return (int) result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if(Math.abs(x-other.x)>0.1) return false;
		if(Math.abs(y-other.y)>0.1) return false;
		return true;
	}
}
