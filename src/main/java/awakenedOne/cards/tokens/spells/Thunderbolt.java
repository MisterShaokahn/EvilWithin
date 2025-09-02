package awakenedOne.cards.tokens.spells;

import awakenedOne.powers.GrimoirePower;
import awakenedOne.relics.EyeOfTheOccult;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;
import hermit.actions.SoundAction;
import slimebound.powers.TackleBuffPower;
import slimebound.powers.TackleDebuffPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.vfx;

public class Thunderbolt extends AbstractSpellCard {
    public final static String ID = makeID(Thunderbolt.class.getSimpleName());
    // intellij stuff attack, enemy, 13, 6, , , , 

    public Thunderbolt() {
        super(ID, 1, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 12;
        this.setBackgroundTexture("awakenedResources/images/512/bg_attack_awakened.png", "awakenedResources/images/1024/bg_attack_awakened.png");
        loadJokeCardImage(this, makeBetaCardPath(Thunderbolt.class.getSimpleName() + ".png"));
        if (CardCrawlGame.isInARun()) {
            if (AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
                isMultiDamage = true;
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            target = CardTarget.ALL_ENEMY;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new SoundAction(makeID("THUNDERSPELL")));
        if (!AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            vfx(new LightningEffect(m.hb.cX, m.hb.cY));
            dmg(m, AbstractGameAction.AttackEffect.NONE);
        } else {
            this.addToBot(new VFXAction(new ReaperEffect()));
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }

    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        if (player.hasPower(GrimoirePower.POWER_ID)) {
            bonus = player.getPower(GrimoirePower.POWER_ID).amount;
        }
        return tmp + bonus;
    }


    public void upp() {
        upgradeDamage(6);
    }
}