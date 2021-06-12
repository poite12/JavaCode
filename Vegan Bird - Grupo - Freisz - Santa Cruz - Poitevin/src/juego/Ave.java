package juego;

import entorno.Entorno;
import java.awt.Image;


public class Ave {
	private double x;
	private double y;
	private Image imagen;
	private Disparo tiro;
	
	Ave(double x, double y, Image c){
		this.x=x;
		this.y=y;
		this.imagen=c;
	}
	
	void dibujar(Entorno e){
		e.dibujarImagen(this.imagen,this.x,this.y,0);
	}

	void caer(){
		this.y = this.y+2.2;
	}
	
	void saltar(){
		this.y = this.y-4;
	}
	
	public Disparo disparar() {
		this.tiro = new Disparo(this.x,this.y);
		return tiro;
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
	public void setImagen(Image c){
		this.imagen = c;
	}
	public Image getImagen(){
		return this.imagen;
	}
	
	
}
