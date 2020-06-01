package it.polito.tdp.food.model;

import java.util.*;

import org.jgrapht.graph.DefaultWeightedEdge;

import com.sun.javafx.geom.Edge;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.*;

import it.polito.tdp.food.db.FoodDao;

public class Model {
private FoodDao dao=new FoodDao();
private Map<Integer, Food> idMap=new TreeMap<>();
private Graph<Food,DefaultWeightedEdge> grafo;

public List<Food> dammiCibo(int k){
	return dao.dammiFood(k);
}
public String creaGrafo(int k) {
	String s="";
	this.grafo= new SimpleWeightedGraph(DefaultWeightedEdge.class);
	for (Food f:this.dammiCibo(k)) {
		idMap.put(f.getFood_code(),f);
	}
	Graphs.addAllVertices(this.grafo,idMap.values());
	for (Coppie c: this.dao.dammiCoppie(k)) {
		if (idMap.containsKey(c.getF1()) && idMap.containsKey(c.getF2())) {
			DefaultWeightedEdge e=this.grafo.addEdge(idMap.get(c.getF1()),idMap.get(c.getF2()));
			this.grafo.setEdgeWeight(e, c.getPeso());
		}
	}
	s="il numero di vertici e' "+this.grafo.vertexSet().size()+" il num di archi e' "+this.grafo.edgeSet().size();
	return s;
}
public String dammiBestFive(Food f) {
	List<Food> vicini=Graphs.neighborListOf(this.grafo,f);
	List<Food> best=new ArrayList<>();
	List<Coppie> coppia=new ArrayList<>();
	for(Food f1:vicini) {
		DefaultWeightedEdge e=	this.grafo.getEdge(f,f1);
		Coppie c=new Coppie(f,f1,this.grafo.getEdgeWeight(e));
		coppia.add(c);
	}
	Collections.sort(coppia);
	for (Coppie c:coppia) {
		if (best.size()<=5) {
		//	best.add(c);
		}
	}
	return null;
}

}
