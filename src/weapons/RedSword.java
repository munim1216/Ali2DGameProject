package weapons;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RedSword extends SuperWeapon {
    public RedSword() {
        super(48,48); // placeholder hitbox needa figure it out later
        damage = 1;

        try {
            swingUp1 = ImageIO.read(new File("resources/swordSprites/upAttack1.png"));
            swingUp2 = ImageIO.read(new File("resources/swordSprites/upAttack2.png"));
            swingUp3 = ImageIO.read(new File("resources/swordSprites/upAttack3.png"));
            swingUp4 = ImageIO.read(new File("resources/swordSprites/upAttack4.png"));

            swingDown1 = ImageIO.read(new File("resources/swordSprites/downAttack1.png"));
            swingDown2 = ImageIO.read(new File("resources/swordSprites/downAttack2.png"));
            swingDown3 = ImageIO.read(new File("resources/swordSprites/downAttack3.png"));
            swingDown4 = ImageIO.read(new File("resources/swordSprites/downAttack4.png"));

            swingLeft1 = ImageIO.read(new File("resources/swordSprites/leftAttack1.png"));
            swingLeft2 = ImageIO.read(new File("resources/swordSprites/leftAttack2.png"));
            swingLeft3 = ImageIO.read(new File("resources/swordSprites/leftAttack3.png"));
            swingLeft4 = ImageIO.read(new File("resources/swordSprites/leftAttack4.png"));

            swingRight1 = ImageIO.read(new File("resources/swordSprites/rightAttack1.png"));
            swingRight2 = ImageIO.read(new File("resources/swordSprites/rightAttack2.png"));
            swingRight3 = ImageIO.read(new File("resources/swordSprites/rightAttack3.png"));
            swingRight4 = ImageIO.read(new File("resources/swordSprites/rightAttack4.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
