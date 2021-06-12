package juego;

import java.awt.Color;

import entorno.Entorno;

public class Disparo {
	private double x;
	private double y;
	private boolean estado;
	

	Disparo(double x, double y){
		this.x=x;
		this.y=y;
		this.estado = false;
	}

	void dibujar(Entorno e){
		e.dibujarCirculo(this.x, this.y, 10, Color.MAGENTA);
	}
	
	public boolean colision(Comida c) {
		if((c.getX()-25 < this.x+5 && c.getX()+25 > this.x-5) 
				&& (c.getY()-25 < this.y+5 && c.getY()+25 > this.y-5)){
					return true;
				}
				return false;
	}

	void mover() {
		this.x = this.x+5;
	}

	void resetEstado() {
		this.estado = !this.estado;
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

	
	public boolean getEstado(){
		return this.estado;
	}
}
