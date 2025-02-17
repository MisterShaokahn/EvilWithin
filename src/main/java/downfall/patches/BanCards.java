package downfall.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.colorless.SadisticNature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BanCards {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeCardPools"
    )
    public static class CardPatch {

        public static void Postfix(AbstractDungeon __instance) {
            if (EvilModeCharacterSelect.evilMode) {
                AbstractDungeon.colorlessCardPool.group.remove(SadisticNature.ID);
                AbstractDungeon.srcColorlessCardPool.group.remove(SadisticNature.ID);
            }
        }
    }
}