package me.devoria.utils;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class ParticleUtils {
    public static void summonCircle(Location location, int radius, Particle particle) {
        for (int d = 0; d <= 90; d += 1) {
            Location particleLoc = location.clone();
            // Cosine for X
            particleLoc.setX(location.getX() + Math.cos(d) * radius);
            // Sine for Z
            particleLoc.setZ(location.getZ() + Math.sin(d) * radius);
            location.getWorld().spawnParticle(particle, particleLoc, 1);
        }
    }

    public static void summonCircle(Location location, int radius, Particle.DustOptions options) {
        for (int d = 0; d <= 90; d += 1) {
            Location particleLoc = location.clone();
            // Cosine for X
            particleLoc.setX(location.getX() + Math.cos(d) * radius);
            // Sine for Z
            particleLoc.setZ(location.getZ() + Math.sin(d) * radius);
            location.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, options);
        }
    }

    // filledCircle() was created by the friends over at Team Monumenta
    public static void filledCircle(ParticleBuilder packagedValues, int radius) {
        int partialCount = packagedValues.count();
        Location centerLocation = packagedValues.location();
        // Spawning one by one, looping manually by partialCount times.
        // spawnWithSettings() will handle whether count should be 0 for
        // directional mode
        packagedValues.count(1);

        int revolutionDegrees = 360;
        double currentDegrees = 0;
        for (int i = 0; i < partialCount; i++) {
            // Always rerandomise rotation
            currentDegrees = FastUtils.randomDoubleInRange(0, revolutionDegrees);

            double offsetX = FastUtils.sinDeg(currentDegrees) * radius;
            double offsetZ = FastUtils.cosDeg(currentDegrees) * radius;
            // Randomly move inwards
            double inwardFactor = Math.sqrt(FastUtils.RANDOM.nextDouble());
            offsetX *= inwardFactor;
            offsetZ *= inwardFactor;

            Location currentLocation = centerLocation.clone();
            currentLocation.add(offsetX, 0, offsetZ);
            packagedValues.location(currentLocation);

            packagedValues.spawn();
        }
    }

    // rotate vector "position" by angle "degrees" on the y-z-(2D)-plane (pitch; looking up/down)
    public static Vector rotateXAxis(Vector position, double degrees) {
        // Angle is negative, since:
        // This is 1:1 with Minecraft; -90 is up, 90 is down, 0 is straight ahead.
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        double cos = FastUtils.cosDeg(degrees);
        double sin = FastUtils.sinDeg(degrees);
        return new Vector(x, y * cos - z * sin, z * cos + y * sin);
    }

    // rotate vector "position" by angle "degrees" on the x-z-(2D)-plane (yaw; compass direction)
    public static Vector rotateYAxis(Vector position, double degrees) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        double cos = FastUtils.cosDeg(degrees);
        double sin = FastUtils.sinDeg(degrees);
        return new Vector(x * cos - z * sin, y, z * cos + x * sin);
    }

    // rotate vector "position" by angle "degrees" on the x-y-(2D)-plane (roll; turn your screen, basically)
    public static Vector rotateZAxis(Vector position, double degrees) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        double cos = FastUtils.cosDeg(degrees);
        double sin = FastUtils.sinDeg(degrees);
        return new Vector(x * cos + y * sin, y * cos - x * sin, z);
    }


}
