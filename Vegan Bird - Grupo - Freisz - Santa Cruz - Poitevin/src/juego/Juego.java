package juego;


import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Ave ave;
	private Tubo[] tubos;
	private Image bird;
	private Image pipeN;
	private Image pipeS;
	private Image fondo;
	private Image restart;
	private int posicion = 800;
	int valor = 0;
	private boolean estado_juego = false;
	private Comida[] comida;
	private int puntuacion=0;
	private Disparo[] disparo;
	private int contadorDisparo;
	private double x_comida;
	
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Vegan Bird - Grupo - V0.01", 800, 600);
		// Inicializar lo que haga falta para el juego
		// ...
		this.fondo = new ImageIcon(getClass().getResource("fondo.png")).getImage();
		this.restart = new ImageIcon(getClass().getResource("restart.png")).getImage();
		this.bird = new ImageIcon(getClass().getResource("bird.png")).getImage();
		this.pipeN = new ImageIcon(getClass().getResource("pipe-north.png")).getImage();
		this.pipeS = new ImageIcon(getClass().getResource("pipe-south.png")).getImage();
		

		this.tubos = new Tubo[16];
		this.comida = new Comida[3];
		this.disparo = new Disparo[30];
		
		Inicializar();
		
		// Inicia el juego!
		this.entorno.iniciar();
		
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		/**Cuando el estado del juego sea verdadero, el juego va a funcionar. El estado cambia a verdadero
		  *cuando se preciona la tecla enter.
		  */
		if(estado_juego) {
			this.entorno.dibujarImagen(this.fondo,400,300,0);
			this.ave.dibujar(this.entorno);
			
			//Crea la comida y los tubos
			Random r = new Random();
			for (int i = 0; i < this.comida.length; i++) {
				if(this.comida[i].getEstado()){
					if(colisionComida(this.comida[i])){
						this.comida[i].setEstado(false);
					}
					this.comida[i].dibujar(this.entorno);
					this.comida[i].mover();
				}
				if(!this.comida[i].getEstado() || this.comida[i].getX() < -40) {
					int a;
					for (int x = 0; x < this.tubos.length; x++) {
						if(this.tubos[x].getOrientacion().equals("norte")) {
							a = x+2;
							if(a >= this.tubos.length) {
								a = 0;
							}
							if(this.x_comida > this.tubos[x].getX() && this.x_comida < this.tubos[a].getX()) {
								this.x_comida = this.tubos[x].getX() +175;
							}
						}
					}
					this.comida[i].reset(this.x_comida,r.nextInt(300)+201);
					this.x_comida +=175;
				}
			}
			
			for (int i = 0; i < this.tubos.length; i++) {
				this.tubos[i].dibujar(this.entorno);
				this.tubos[i].mover();
				if(this.tubos[i].getX() < -40) {
					
					this.tubos[i].resetX(); 
					
				}
				if(this.tubos[i].getX() < 300 && this.tubos[i].getX() > 200) {
					this.tubos[i].setEstado(1);
				}else {
					this.tubos[i].setEstado(0);
				}
				
			}
			this.ave.caer();
			/** Revisa si los si el usuario presiona la tecla
			 *  Arriba para realizar los saltos 
			 *  */
			if(this.entorno.estaPresionada(this.entorno.TECLA_ARRIBA)){
				if(this.ave.getY()-15 >= 0){ //Para que choque contra la parte superior de la ventana
					this.ave.saltar();
				}
			}
			/** Detecta si el usuario presionó la tecla espacio
			 * Si lo hace realiza un disparo */
			if(this.entorno.sePresiono(this.entorno.TECLA_ESPACIO)){
				
				this.disparo[this.contadorDisparo] = this.ave.disparar();
				this.disparo[this.contadorDisparo].resetEstado();
				//Controla la cantidad de disparos utilizados para el length del arreglo
				this.contadorDisparo++;
				if(this.contadorDisparo >= 30){
					this.contadorDisparo = 0;
				}
			}
			for (int i = 0; i < this.disparo.length; i++) {
				if(this.disparo[i].getEstado()) {
					this.disparo[i].dibujar(this.entorno);
					this.disparo[i].mover();
					for (int j = 0; j < this.comida.length; j++) {
						if(this.comida[j].getTipo().equals("carne") && this.comida[j].getX() < 840)
						if(this.disparo[i].colision(this.comida[j])){
							this.disparo[i].resetEstado();
							this.comida[j].recibirDisparo();
							this.puntuacion+=3;
						}
					}
					if(this.disparo[i].getX() > 800){
						this.disparo[i].resetEstado();
					}
				}
			}
			//Muestra los puntos acomulados por pantalla
			this.entorno.escribirTexto("PUNTOS: "+Integer.toString(this.puntuacion), 50, 50);
			this.estado_juego = colision(); //Si hay colisión cambia de estado el juego
		
		}//fin rama true del estado de juego;
		//Simula como si el juego se hubiese congelado
		else {
			this.entorno.dibujarImagen(this.fondo,400,300,0);
			this.ave.dibujar(this.entorno);
			
			for (int i = 0; i < this.tubos.length; i++) {
				this.tubos[i].dibujar(this.entorno);
			}
			this.entorno.dibujarImagen(this.restart,400,300,0);
			this.entorno.escribirTexto("PUNTOS: "+Integer.toString(this.puntuacion), 50, 50);
			if(this.entorno.estaPresionada(this.entorno.TECLA_ENTER)){
				Inicializar();//Comenzar un nuevo juego
				this.estado_juego = true;
			}
		}
		
	}
	
	/** Método encargada de controlar cuando hay o no coliciones 
	 */
	
	public boolean colision() {
		
		for (int i = 0; i < this.tubos.length; i++) {
			/**Cuando colicione con los tubos de la parte superior del juego;
			  * Cuando colicione, va a cambiar el estado del juego a false
			  */
			if (this.tubos[i].getOrientacion().equals("sur") && this.tubos[i].getEstado() == 1) {
				if(this.ave.getY()+15 > this.tubos[i].getY()-200 
						&& ((this.ave.getX()-45 > this.tubos[i].getX() - 33 && this.ave.getX()-45 < this.tubos[i].getX()+33)
						|| (this.ave.getX()+45 > this.tubos[i].getX() - 33 && this.ave.getX()+45 < this.tubos[i].getX()+33))
					)
					return false;
			}else
			/**Cuando colicione con los tubos de la parte inferior del juego;
			  * Cuando colicione, va a cambiar el estado del juego a false
			  */
			if (this.tubos[i].getOrientacion().equals("norte") && this.tubos[i].getEstado() == 1) {
				if(this.ave.getY()-15 < this.tubos[i].getY()+200
						&& ((this.ave.getX()-45 > this.tubos[i].getX() - 45 && this.ave.getX()-45 < this.tubos[i].getX()+45)
						|| (this.ave.getX()+45 > this.tubos[i].getX() - 45 && this.ave.getX()+45 < this.tubos[i].getX()+45))
					)
					return false;
			}
		}
		if(this.ave.getY()+15 >= 600){
			return false;
		}
		return true;
	}
	
	/** Metodo encargado de controlar las coliciones del ave con la comida*/
	boolean colisionComida(Comida c){
		if((c.getX()-25 < this.ave.getX()+10 && c.getX()+25 > this.ave.getX()-10) 
		&& (c.getY()-25 < this.ave.getY()+10 && c.getY()+25 > this.ave.getY()-10)){
			if(c.getTipo().equals("verdura")){
				this.puntuacion += 5;
			}else{
				this.puntuacion -= 5;
			}
			return true;
		}
		return false;
	}
	
	public void Inicializar(){
		this.x_comida = 1000;
		this.contadorDisparo = 0;
		this.puntuacion = 0;
		this.ave = new Ave(250,150,this.bird);
		this.posicion = 800;
		Random r = new Random();
		this.valor = r.nextInt(350)+1;
		
		for (int i = 0; i < this.disparo.length; i++) {
			this.disparo[i] = new Disparo(0,0);
		}
		
		for (int i = 0; i < this.tubos.length; i++) {
			int tubo1 = -150 + this.valor;
			int tubo2 = 400 + this.valor;
			if(i%2 == 0) {
				this.tubos[i] = new Tubo(this.posicion,tubo1,this.pipeS, "norte");
			}else {
				this.tubos[i] = new Tubo(this.posicion,tubo2,this.pipeN, "sur");
				this.posicion += 350;
				this.valor = r.nextInt(350)+1 ;
			}
		}
		int xComida = 950;
		for (int i = 0; i < this.comida.length; i++) {
			int alturaVerdura = r.nextInt(300)+201; 
			this.comida[i] = new Comida(xComida,alturaVerdura);
			xComida +=350;
		}

	}
	

//	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
