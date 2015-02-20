import java.awt.Point;

/**
 * @author {
 * 			Max Niebergall
 *			Date: Febuary 20th, 2015
 *		}
 */
public class SnakeObject {
	Direction direction;
	Point point;
	/**
	 * 
	 * @param Point
	 * @param Direction
	 */
	SnakeObject(Point point, Direction direction){
		this.point=point;
		this.direction=direction;
	}
	/**
	 * Extends the Snake
	 * @param SnakeObject
	 */
	SnakeObject(SnakeObject other){
		this.point= new Point(other.getPoint().x-other.direction(), other.getPoint().y-other.direction());//Subtracts direction to extend the snake
		this.direction=other.getDirection();
	}
	/**
	 * direction()
	 * returns the int value of the enum Direction
	 * @return int NORTH +1, EAST +1, SOUTH -1, WEST -1, default 0
	 */
	public int direction(){//NORTH +, EAST +, SOUTH -, WEST -, default 0
		switch (direction) {
        case NORTH:
            return 1;
                
        case SOUTH:
            return -1;
                     
        case EAST:
        	return +1;

        case WEST:
            return 1;
                    
        default:
            return 0;
    }
		
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	
}
