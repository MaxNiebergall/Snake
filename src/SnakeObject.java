import java.awt.Point;

/**
 * @author { Max Niebergall Date: Febuary 20th, 2015 }
 */
public class SnakeObject {
    private Direction direction;
    private Point point;

    /**
     * 
     * @param Point
     * @param Direction
     */
    SnakeObject(Point point, Direction direction) {
	this.point = point;
	this.direction = direction;
    }

    /**
     * Extends the Snake
     * 
     * @param SnakeObject
     */
    SnakeObject(SnakeObject other) {
	this.point = new Point(other.getPoint().x - ((other.getDirection() == Direction.EAST || other.getDirection() == Direction.WEST) ? other.direction() : 0), other.getPoint().y - ((other.getDirection() == Direction.NORTH || other.getDirection() == Direction.SOUTH) ? other.direction() : 0));// Subtracts direction to
																																				       // extend the snake
	this.direction = other.getDirection();
    }

    /**
     * direction() returns the int value of the enum Direction
     * 
     * @return int NORTH +1, EAST +1, SOUTH -1, WEST -1, default 0
     */
    public int direction() {// NORTH +, EAST +, SOUTH -, WEST -, default 0
			    // the last indicie is actually at the top left, so they all must be inverted
	switch (direction) {
	    case NORTH:
		return -1;

	    case SOUTH:
		return 1;

	    case EAST:
		return 1;

	    case WEST:
		return -1;

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

    public void forward() {// TODO add eating food to this method
	this.point.move(this.point.x + ((this.direction == Direction.EAST || this.direction == Direction.WEST) ? this.direction() : 0), this.point.y + ((this.direction == Direction.NORTH || this.direction == Direction.SOUTH) ? this.direction() : 0));
	System.out.print("Direction Inline Operator: |");
	System.out.println(((this.direction == Direction.EAST || this.direction == Direction.WEST) ? this.direction() : 0));
	System.out.println("Forward");
	System.out.println(point);
	System.out.println(this.direction);
	System.out.println(this.direction());
    }

}
