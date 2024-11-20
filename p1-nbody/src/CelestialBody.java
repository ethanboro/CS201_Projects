

/**
 * Celestial Body class for NBody
 * Modified from original Planet class
 * used at Princeton and Berkeley
 * @author ola
 *
 * If you add code here, add yourself as @author below
 *
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		// TODO:  constructor
		this.myXPos = xp;
		this.myYPos = yp;
		this.myXVel = xv;
		this.myYVel = yv;
		this.myMass = mass;
		this.myFileName = filename;




	}

	/**
	 *
	 * @return
	 */
	public double getX() {
		return myXPos;
	}

	/**
	 *
	 * @return
	 */
	public double getY() {
		return myYPos;
	}

	/**
	 * Accessor for the x-velocity
	 * @return the value of this object's x-velocity
	 */
	public double getXVel() {
		return myXVel;
	}
	/**
	 * Accessor for the y-velocity.
	 * @return value of this object's y-velocity
	 */
	public double getYVel() {
		return myYVel;
	}

	/**
	 *
	 * @return
	 */
	public double getMass() {
		return myMass;
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double dist = Math.sqrt(((this.myXPos-b.getX())*(this.myXPos-b.getX()))+((this.myYPos-b.getY())*(this.myYPos-b.getY())));
		return dist;
	}

	public double calcForceExertedBy(CelestialBody b) {
		double force = 6.67*1e-11*this.myMass*b.getMass()/calcDistance(b)/calcDistance(b);
		return force;
	}

	public double calcForceExertedByX(CelestialBody b) {
		double fx = (calcForceExertedBy(b) * (b.myXPos - myXPos) / calcDistance(b));
		return fx;
		
	}
	public double calcForceExertedByY(CelestialBody b) {
		double fy = (calcForceExertedBy(b) * (b.myYPos - myYPos) / calcDistance(b));
		return fy;
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (! b.equals(this)) {
				sum += calcForceExertedByX(b);
			}
		}
		
		return sum;
	}

	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (! b.equals(this)) {
				sum += calcForceExertedByY(b);
			}
		}
		
		return sum;
	}

	/**
	 * This is a mutator method, modifies state of a celestial body
	 * (position and velocity)
	 * @param deltaT the time-step used in updating
	 * @param xforce the force in the x-direction
	 * @param yforce the force in the y-direction
	 */
	public void update(double deltaT, 
			           double xforce, double yforce) {
		double ax = xforce / this.getMass();
		double ay = yforce / this.getMass();

		double nvx = myXVel + deltaT*ax;
		double nvy = myYVel + deltaT*ay;

		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;

		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;

	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
