package entity;

public class BigEnemy extends Enemy {
    public BigEnemy() {
        super();
        this.radius = 30;  //Bigger size
        this.health = 10;  //More health
    }

    @Override
    public void moveToward(int targetX, int targetY) {
        int dx = targetX - this.x;
        int dy = targetY - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double moveX = (dx / distance) * 1.5; //Slower speed this time
        double moveY = (dy / distance) * 1.5;
        this.x += moveX;
        this.y += moveY;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }
}