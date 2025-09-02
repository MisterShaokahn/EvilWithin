package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;


public class TwinSlam extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("TwinSlam");
    public static final String NAME;
    public static final String IMG_PATH = "cards/twinSlam.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int DAMAGE = 7;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 2;
    private static final int SOCKETS = 1;
    private static final boolean SOCKETSAREAFTER = true;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public TwinSlam() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.tags.add(GuardianMod.MULTIHIT);
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        cardsToPreview = new SecondStrikePreviewCard();
        ((AbstractGuardianCard)cardsToPreview).socketCount = 0;
        ((AbstractGuardianCard)cardsToPreview).updateDescription();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("TwinSlam.png"));

        //this.sockets.add(GuardianMod.socketTypes.RED);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractGuardianCard q = new SecondStrike();
        if (upgraded) q.upgrade();
        int i = 0;
        for (GuardianMod.socketTypes gem : sockets) {
            q.sockets.add(gem);
            i++;
        }
        addToBot(new MakeTempCardInHandAction(q, true));
        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new TwinSlam();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            // upgradeDamage(UPGRADE_BONUS);
            if (this.socketCount < 4) {
                this.socketCount++;
                this.saveGemMisc();
            }
            AbstractCard q = new SecondStrikePreviewCard();
            q.upgrade();
            cardsToPreview = q;

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.updateDescription();
        }
    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }
}


