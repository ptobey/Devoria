package me.devoria.player;

public enum FactionType {
    IMANITY,
    LIGHTSEEKER,
    HELLSCAPER,
    CAVEDWELLER,
    NONE;

    public static FactionType fromString(String string) {
        switch (string) {
            case "imanity": return IMANITY;
            case "lightseeker": return LIGHTSEEKER;
            case "hellscaper": return HELLSCAPER;
            case "cavedweller": return CAVEDWELLER;
            default: return NONE;
        }
    }

    public static String toString(FactionType factionType) {
        switch (factionType) {
            case IMANITY: return "imanity";
            case LIGHTSEEKER: return "lightseeker";
            case HELLSCAPER: return "hellscaper";
            case CAVEDWELLER: return "cavedweller";
            default: return "none";
        }
    }
}
