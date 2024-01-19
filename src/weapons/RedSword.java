package weapons;

import main.Utility;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class RedSword extends SuperWeapon {
    public RedSword() {
        super(0,0,0,9, 9, 0,0,0,48,39); // placeholder hitbox needa figure it out later
        damage = 1;

        try {
            swingUp1 = Utility.scale(ImageIO.read(new File("resources/swordSprites/upAttack1.png")));
            swingUp2 = Utility.scale(ImageIO.read(new File("resources/swordSprites/upAttack2.png")));
            swingUp3 = Utility.scale(ImageIO.read(new File("resources/swordSprites/upAttack3.png")));
            swingUp4 = Utility.scale(ImageIO.read(new File("resources/swordSprites/upAttack4.png")));

            swingDown1 = Utility.scale(ImageIO.read(new File("resources/swordSprites/downAttack1.png")));
            swingDown2 = Utility.scale(ImageIO.read(new File("resources/swordSprites/downAttack2.png")));
            swingDown3 = Utility.scale(ImageIO.read(new File("resources/swordSprites/downAttack3.png")));
            swingDown4 = Utility.scale(ImageIO.read(new File("resources/swordSprites/downAttack4.png")));

            swingLeft1 = Utility.scale(ImageIO.read(new File("resources/swordSprites/leftAttack1.png")));
            swingLeft2 = Utility.scale(ImageIO.read(new File("resources/swordSprites/leftAttack2.png")));
            swingLeft3 = Utility.scale(ImageIO.read(new File("resources/swordSprites/leftAttack3.png")));
            swingLeft4 = Utility.scale(ImageIO.read(new File("resources/swordSprites/leftAttack4.png")));

            swingRight1 = Utility.scale(ImageIO.read(new File("resources/swordSprites/rightAttack1.png")));
            swingRight2 = Utility.scale(ImageIO.read(new File("resources/swordSprites/rightAttack2.png")));
            swingRight3 = Utility.scale(ImageIO.read(new File("resources/swordSprites/rightAttack3.png")));
            swingRight4 = Utility.scale(ImageIO.read(new File("resources/swordSprites/rightAttack4.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
