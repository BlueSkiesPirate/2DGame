package entity;

public class Enemy {
    public int x, y;
    public int radius = 10;  // size of circle/enemy
    public int health = 3;   //health

    public Enemy() {
        // Randomize the position of the enemy
        this.x = (int) (Math.random() * 800);  //width of screen
        this.y = (int) (Math.random() * 600);  //height of screen
    }

    //Based on position for target, move accordingly to close the distance.
    public void moveToward(int targetX, int targetY) {
        int dx = targetX - this.x;
        int dy = targetY - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double moveX = (dx / distance) * 3;  //3 is the speed, since more larger movement
        double moveY = (dy / distance) * 3;
        this.x += moveX;
        this.y += moveY;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }
} 
