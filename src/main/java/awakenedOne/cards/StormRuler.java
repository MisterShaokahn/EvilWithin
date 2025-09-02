package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.powers.GrimoirePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.*;

public class StormRuler extends AbstractAwakenedCard {
    public final static String ID = makeID(StormRuler.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 9, 1, , , 3, 1

    public StormRuler() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(AwakenedOneMod.DELVE);
        baseMagicNumber = magicNumber = 6;
        loadJokeCardImage(this, makeBetaCardPath(StormRuler.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // Wiz.makeInHand(new Thunderbolt());
        atb(new ConjureAction(false));
        applyToSelf(new GrimoirePower(magicNumber));

    }

    public void upp() {
        //upgradeDamage(2);
        upgradeMagicNumber(3);
    }
}