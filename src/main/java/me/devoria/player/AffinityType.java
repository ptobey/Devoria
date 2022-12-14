package me.devoria.player;

public enum AffinityType {
    DEMIGOD,
    HUMAN,
    KNIGHT,
    MAGE,
    ANGEL,
    AQUAN,
    ASTREAN,
    ELF,
    NONE;

    public static AffinityType fromString(String string) {
        return switch (string) {
            case "demigod" -> DEMIGOD;
            case "human" -> HUMAN;
            case "knight" -> KNIGHT;
            case "mage" -> MAGE;
            case "angel" -> ANGEL;
            case "aquan" -> AQUAN;
            case "astrean" -> ASTREAN;
            case "elf" -> ELF;
            default -> NONE;
        };
    }

    public static String toString(AffinityType affinityType) {
        return switch (affinityType) {
            case DEMIGOD -> "demigod";
            case HUMAN -> "human";
            case KNIGHT -> "knight";
            case MAGE -> "mage";
            case ANGEL -> "angel";
            case AQUAN -> "aquan";
            case ASTREAN -> "astrean";
            case ELF -> "elf";
            case NONE -> null;
        };
    }
}
