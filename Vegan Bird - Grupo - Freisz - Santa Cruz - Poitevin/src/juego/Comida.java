package juego;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import entorno.Entorno;

public class Comida {
	private double x;
	private double y;
	private Image i;
	private String tipo;
	private boolean estado = true;
	private Image verdura = new ImageIcon(getClass().getResource("broccoli.png")).getImage();
	private Image carne = new ImageIcon(getClass().getResource("carne.png")).getImage();
	
	Comida(double x, double y){
		this.x = x;
		this.y = y;
		setTipoComida();
	}
	
	void dibujar(Entorno e){
		e.dibujarImagen(this.i,this.x,this.y,0);
	}
	
	void mover() {
		this.x = this.x-1.5;
	}
	
	void recibirDisparo() {
		this.tipo = "verdura";
		this.i = verdura;
	}
	
	private void setTipoComida(){
		Random r = new Random();
		if(r.nextInt(350)%2 ==0){
			this.tipo = "verdura";
			this.i = this.verdura;
		}else{
			this.tipo = "carne";
			this.i = this.carne;
		}
	}
	
	public void reset(double x,double y) {
		
		this.x = x;
		this.y = y;
		this.estado = true;
		setTipoComida();
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
	public void setEstado(boolean b){
		this.estado = b;
	}
	public boolean getEstado(){
		return this.estado;
	}

	public void setImagen(Image i){
		this.i = i;
	}
	public Image getImagen(){
		return this.i;
	}
	public void setTipo(String t){
		this.tipo = t;
	}
	public String getTipo(){
		return this.tipo;
	}
	

}
