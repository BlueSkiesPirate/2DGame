package object;


import entity.Entity;
import main.GamePanel;

public class OBJ_Bullet extends Entity {
    private GamePanel gp;

    double angle;
    int distanceTraveled;
    int maxDistance;
    
    public OBJ_Bullet(GamePanel gp, int startX, int startY, double angle) {
        super(gp);
        this.gp = gp; // store it locally
        this.worldX = startX;
        this.worldY = startY;
        this.angle = angle;
        this.speed = 10;
        this.distanceTraveled = 0;
        this.maxDistance = gp.tileSize * 10;

        // Set up sprite/image if needed
        this.down1 = setup("/objects/bullet"); 
    }

    public void update() {
        // Use local gp instead of inherited one
        worldX += (int)(Math.cos(angle) * speed);
        worldY += (int)(Math.sin(angle) * speed);
        distanceTraveled += speed;

        if (distanceTraveled >= maxDistance || collisionDetected()) {
            gp.projectiles.remove(this);
        }
    }

    private boolean collisionDetected() {
        gp.cChecker.checkTile(this);
        return this.collisionOn;
    }
}
