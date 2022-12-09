package me.devoria.player;

public enum AffinityType {
    DEMIGOD,
    HUMAN,
    KNIGHT,
    MAGE,
    ANGEL,
    AQUAN,
    ASTREAN,
    ELF;

    public static AffinityType fromString(String string) {
        switch (string) {
            case "demigod": return DEMIGOD;
            case "human": return HUMAN;
            case "knight": return KNIGHT;
            case "mage": return MAGE;
            case "angel": return ANGEL;
            case "aquan": return AQUAN;
            case "astrean": return ASTREAN;
            case "elf": return ELF;
            case default: return null;
        }
    }

    public static String toString(AffinityType affinityType) {
        switch (affinityType) {
            case DEMIGOD: return "demigod";
            case HUMAN: return "human";
            case KNIGHT: return "knight";
            case MAGE: return "mage";
            case ANGEL: return "angel";
            case AQUAN: return "aquan";
            case ASTREAN: return "astrean";
            case ELF: return "elf";
            case default: return null;
        }
    }
}
