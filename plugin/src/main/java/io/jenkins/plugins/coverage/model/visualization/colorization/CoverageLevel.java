package io.jenkins.plugins.coverage.model.visualization.colorization;

import edu.umd.cs.findbugs.annotations.NonNull;

import io.jenkins.plugins.coverage.model.visualization.colorization.ColorProvider.DisplayColors;

/**
 * Provides the colorization for different coverage levels.
 *
 * @author Florian Orendi
 */
public enum CoverageLevel {

    LVL_95(95.0, ColorId.DARK_GREEN),
    LVL_90(90.0, ColorId.GREEN),
    LVL_85(85.0, ColorId.LIGHT_GREEN),
    LVL_80(80.0, ColorId.LIGHT_YELLOW),
    LVL_75(75.0, ColorId.YELLOW),
    LVL_70(70.0, ColorId.DARK_YELLOW),
    LVL_65(65.0, ColorId.LIGHT_ORANGE),
    LVL_60(60.0, ColorId.DARK_ORANGE),
    LVL_50(50.0, ColorId.LIGHT_RED),
    LVL_0(0.0, ColorId.DARK_RED),
    NA(-1.0, ColorId.WHITE);

    private final double level;
    private final ColorId colorizationId;

    CoverageLevel(final double level, final ColorId colorizationId) {
        this.level = level;
        this.colorizationId = colorizationId;
    }

    /**
     * Gets the {@link DisplayColors display colors} for representing the passed coverage amount. If the value is placed
     * between two levels, the fill colors are blended.
     *
     * @param coveragePercentage
     *         The coverage percentage
     *
     * @return the display colors
     */
    public static DisplayColors getDisplayColorsOfCoverageLevel(final Double coveragePercentage,
            @NonNull final ColorProvider colorProvider) {
        if (coveragePercentage >= 0) {
            return getBlendedColors(coveragePercentage, colorProvider);
        }
        return colorProvider.getDisplayColorsOf(NA.colorizationId);
    }

    /**
     * Gets the blended {@link DisplayColors display colors} for representing the passed coverage amount.
     *
     * @param coveragePercentage
     *         The coverage percentage
     *
     * @return the blended display colors
     */
    private static DisplayColors getBlendedColors(final Double coveragePercentage,
            @NonNull final ColorProvider colorProvider) {
        for (int i = 0; i < values().length - 1; i++) {
            CoverageLevel level = values()[i];
            if (coveragePercentage >= level.level) {
                if (i == 0) {
                    return colorProvider.getDisplayColorsOf(level.colorizationId);
                }
                CoverageLevel upperLevel = values()[i - 1];
                double distanceLevel = coveragePercentage - level.level;
                double distanceUpper = upperLevel.level - coveragePercentage;
                if (distanceLevel == 0) {
                    return colorProvider.getDisplayColorsOf(level.colorizationId);
                }
                return colorProvider.getBlendedDisplayColors(
                        distanceLevel, distanceUpper,
                        upperLevel.colorizationId,
                        level.colorizationId);
            }
        }
        return colorProvider.getDisplayColorsOf(NA.colorizationId);
    }

    public double getLevel() {
        return level;
    }

    public ColorId getColorizationId() {
        return colorizationId;
    }
}
