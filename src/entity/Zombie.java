package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class Zombie extends Entity {

    private int actionLockCounter = 0;
    private Random random = new Random();
    private int attackCooldown = 0; // Cooldown counter

    public Zombie(GamePanel gp) {
        super(gp);

        name = "Zombie";
        speed = 2;
        maxLife = 4;
        life = maxLife;
        type = type_monster;
        pickUpAble = false;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        imageSize = 32;

        direction = "down";
        getImage();
    }

    public void getImage() {
        up1 = setup("/Zombie/right1");
        up2 = setup("/Zombie/right2");
        down1 = setup("/Zombie/right1");
        down2 = setup("/Zombie/right2");
        left1 = setup("/Zombie/left1");
        left2 = setup("/Zombie/left2");
        right1 = setup("/Zombie/right1");
        right2 = setup("/Zombie/right2");
    }

    @Override
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            int i = random.nextInt(4);
            switch (i) {
                case 0: direction = "up"; break;
                case 1: direction = "down"; break;
                case 2: direction = "left"; break;
                case 3: direction = "right"; break;
            }
            actionLockCounter = 0;
        }
    }

    public void update() {
        int dx = gp.player.worldX - worldX;
        int dy = gp.player.worldY - worldY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 8 * gp.tileSize) {
            // Chase
            double angle = Math.atan2(dy, dx);
            int moveX = (int) Math.round(Math.cos(angle) * speed);
            int moveY = (int) Math.round(Math.sin(angle) * speed);

            direction = (moveX > 0) ? "right" : (moveX < 0) ? "left" : direction;
            collisionOn = false;
            worldX += moveX;
            gp.cChecker.checkTile(this);
            if (collisionOn) worldX -= moveX;

            direction = (moveY > 0) ? "down" : (moveY < 0) ? "up" : direction;
            collisionOn = false;
            worldY += moveY;
            gp.cChecker.checkTile(this);
            if (collisionOn) worldY -= moveY;
        } else {
            setAction();
            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
        }

        // ATTACK PLAYER if colliding
        Rectangle zombieHitbox = new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
        Rectangle playerHitbox = new Rectangle(gp.player.worldX + gp.player.solidArea.x, gp.player.worldY + gp.player.solidArea.y, gp.player.solidArea.width, gp.player.solidArea.height);

        if (zombieHitbox.intersects(playerHitbox)) {
            if (attackCooldown == 0) {
                attackPlayer();
                attackCooldown = 60; // 1 second cooldown
            }
        }


        if (attackCooldown > 0) {
            attackCooldown--;
        }

        // Sprite animation
        spriteCounter++;
        if (spriteCounter > 24) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    private void attackPlayer() {
        if (gp.player.invincible == false) {
            gp.player.life -= 1;
            gp.player.invincible = true;
        }
    }
}
