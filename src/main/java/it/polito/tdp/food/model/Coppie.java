package it.polito.tdp.food.model;

public class Coppie implements Comparable<Coppie> {
private int f1;
private int f2;
private double peso;
private int numCond;

public int getF1() {
	return f1;
}
public void setF1(int f1) {
	this.f1 = f1;
}
public int getF2() {
	return f2;
}
public void setF2(int f2) {
	this.f2 = f2;
}
public double getPeso() {
	return peso;
}
public void setPeso(double peso) {
	this.peso = peso;
}
public Coppie(int f1, int f2, double peso) {
	super();
	this.f1 = f1;
	this.f2 = f2;
	this.peso = peso;
	this.setNumCond(numCond);
}
public Coppie(Food food, Food food2, double edgeWeight) {
	// TODO Auto-generated constructor stub
}
public int getNumCond() {
	return numCond;
}
public void setNumCond(int numCond) {
	this.numCond = numCond;
}
@Override
public int compareTo(Coppie o) {
	// TODO Auto-generated method stub
	return this.numCond-o.getNumCond();
}

}
