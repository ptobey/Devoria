package me.devoria.spells;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class CastTargetLedgerTest {

    @Test
    void claimsATargetOnlyOncePerCast() {
        CastTargetLedger ledger = new CastTargetLedger();
        UUID target = UUID.randomUUID();

        assertTrue(ledger.claim(target));
        assertFalse(ledger.claim(target));
    }

    @Test
    void tracksDifferentTargetsIndependently() {
        CastTargetLedger ledger = new CastTargetLedger();

        assertTrue(ledger.claim(UUID.randomUUID()));
        assertTrue(ledger.claim(UUID.randomUUID()));
    }

    @Test
    void separateCastsDoNotShareTargetState() {
        UUID target = UUID.randomUUID();

        assertTrue(new CastTargetLedger().claim(target));
        assertTrue(new CastTargetLedger().claim(target));
    }

    @Test
    void rejectsMissingTargetIdentity() {
        CastTargetLedger ledger = new CastTargetLedger();

        assertThrows(NullPointerException.class, () -> ledger.claim(null));
    }
}
