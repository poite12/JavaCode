package juego;

import entorno.Entorno;
import java.awt.Image;

public class Tubo {
	private double x;
	private double y;
	private Image imagen;
	private String orientacion;
	private double estado = 0;

	
	Tubo(double x, double y, Image c, String t){
		this.x=x;
		this.y=y;
		this.imagen=c;
		this.orientacion = t;
	}
	
	void dibujar(Entorno e){
		e.dibujarImagen(this.imagen,this.x,this.y,0);
	}
	
	void mover(){
		this.x = this.x-1.5;
	}
	
	public void resetX() {
		this.x = 2750;
	}
	
	public void resetY(double y) {
		this.y = this.y + y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	public double getX(){
		return this.x;
	}
	public void setY(double y){
		this.y = y;
	}
	public double getY(){
		return this.y;
	}
	public void setOrientacion(String orientacion){
		this.orientacion = orientacion;
	}
	public String getOrientacion(){
		return this.orientacion;
	}
	public void setEstado(double e){
		this.estado = e;
	}
	public double getEstado(){
		return this.estado;
	}
}
