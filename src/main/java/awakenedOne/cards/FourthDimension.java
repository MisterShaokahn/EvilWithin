package awakenedOne.cards;

import awakenedOne.cards.altDimension.RealityRift;
import awakenedOne.powers.FourthDimensionPower;
import charbosses.cards.purple.EnCarveReality;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Nightmare;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.util.Wiz.*;

public class FourthDimension extends AbstractAwakenedCard {
    public final static String ID = makeID(FourthDimension.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public FourthDimension() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        baseMagicNumber = magicNumber = 3;
        //this is going to need healing tag
        this.tags.add(CardTags.HEALING);
        loadJokeCardImage(this, makeBetaCardPath(FourthDimension.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            for (AbstractCard c : cards) {
                if (!(c instanceof FourthDimension) && !(c instanceof Nightmare)) {
                    shuffleIn(c.makeStatEquivalentCopy(), magicNumber);
                    //applyToSelfTop(new FourthDimensionPower(magicNumber, c.makeStatEquivalentCopy()));
                    att(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                } else {
                    shuffleIn(new RealityRift(), magicNumber);
                    att(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, CardCrawlGame.languagePack.getUIString("awakened:FourthDimensionDupeAttempt").TEXT[0], true));
                }
            }
        }));

    }

    public void upp() {
        upgradeBaseCost(0);
        //upgradeMagicNumber(1);
    }
}