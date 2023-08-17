package me.devoria.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleUtils {
    public static List<Vector> getCirclePoints(int radius) {
        List<Vector> points = new ArrayList<>();
        for (int d = 0; d <= 90; d += 1) {
            // Cosine for X
            Vector vector = new Vector(Math.cos(d) * radius, 0, Math.sin(d) * radius);
            points.add(vector);
        }

        return points;
    }

    public static List<Vector> getSpiralPoints(int radius, double radiusIncrease) {
        List<Vector> points = new ArrayList<>();
        for (int d = 0; d <= 60; d += 1) {
            radius += radiusIncrease;
            Vector vector = new Vector(Math.cos(d) * radius, 0, Math.sin(d) * radius);

            points.add(vector);
        }

        return points;
    }

    // Made by Team Monumenta!
    public static List<Vector> getFilledCirclePoints(int radius, int partialCount) {
        List<Vector> points = new ArrayList<>();

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

            Vector vector = new Vector(offsetX, 0, offsetZ);
            points.add(vector);
        }

        return points;
    }

    public static List<Vector> getSpherePoints(int radius) {
        List<Vector> points = new ArrayList<>();

        double increment = Math.PI / 20;
        for (double theta = 0; theta <= Math.PI; theta += increment) {
            double sinTheta = Math.sin(theta);
            double cosTheta = Math.cos(theta);
            for (double phi = 0; phi <= 2 * Math.PI; phi += increment) {
                double sinPhi = Math.sin(phi);
                double cosPhi = Math.cos(phi);
                Vector vector = new Vector(radius * sinTheta * cosPhi, radius * sinTheta * sinPhi, radius * cosTheta);

                points.add(vector);
            }
        }

        return points;
    }

    public static Vector getQuadraticBezierPoint(Vector A, Vector B, Vector C, double t) {
        double u = 1 - t;
        return A.multiply(u * u).add(B.clone().multiply(2 * u * t)).add(C.clone().multiply(t * t));
    }

    public static List<Vector> getPolygonPoints(Vector[] vertices, int numParticles) {
        List<Vector> points = new ArrayList<>();

        for (int i = 0; i < vertices.length; i++) {
            int length = vertices.length + 1;
            Vector from = vertices[i];
            Vector to;
            if (i + 1 == vertices.length) {
                to = vertices[0];
            } else {
                to = vertices[i + 1];
            }
            points.addAll(getLinePoints(from, to, numParticles / vertices.length));
        }

        return points;
    }

    public static List<Vector> getLinePoints(Vector start, Vector end, int numParticles) {
        List<Vector> points = new ArrayList<>();

        Vector direction = end.clone().subtract(start).normalize();
        double distance = start.distance(end);
        double step = distance / (numParticles - 1);
        for (int i = 0; i < numParticles; i++) {
            Vector point = start.clone().add(direction.clone().multiply(step * i));
            points.add(point);
        }
        return points;
    }

    public static List<Vector> getConePoints(Vector start, Vector direction, double radius, double height, int points) {
        List<Vector> conePoints = new ArrayList<>();

        Vector up = new Vector(0, 1, 0);
        Vector right = direction.crossProduct(up).normalize();
        up = right.crossProduct(direction).normalize();

        Vector apex = start.add(direction.clone().multiply(height));

        for (int i = 0; i < points; i++) {
            double t = (double) i / (points - 1);
            double angle = t * 2 * Math.PI;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);

            Vector pointOnBase = start.add(right.multiply(x)).add(up.multiply(y));
            Vector directionToApex = apex.clone().subtract(pointOnBase);
            Vector point = pointOnBase.clone().add(directionToApex.multiply(height / directionToApex.length()));

            conePoints.add(point);
        }

        return conePoints;
    }


    public static List<Vector> getHelixPoints(Vector direction, double radius, double length, int pointCount, boolean doubleHelix) {
        List<Vector> points = new ArrayList<>();
        Vector x = direction.clone().normalize();
        Vector y = new Vector(0, 1, 0);
        if (x.getX() == 0 && x.getZ() == 0) {
            y = new Vector(0, 0, 1);
        }
        Vector z = y.clone().crossProduct(x).normalize().multiply(radius);
        y = x.clone().crossProduct(z).normalize().multiply(radius);
        double angle = 2 * Math.PI / pointCount;
        double pointSpacing = length / pointCount;
        for (int i = 0; i < pointCount; i++) {
            double xCoord = radius * Math.cos(i * angle);
            double yCoord = radius * Math.sin(i * angle);
            Vector point = x.clone().multiply(i * pointSpacing).add(y.clone().multiply(yCoord)).add(z.clone().multiply(xCoord));
            points.add(point);
            if (doubleHelix) {
                xCoord = -radius * Math.cos(i * angle);
                yCoord = -radius * Math.sin(i * angle);
                point = x.clone().multiply(i * pointSpacing).add(y.clone().multiply(yCoord)).add(z.clone().multiply(xCoord));
                points.add(point);
            }
        }
        return points;
    }


    // Also taken from Team Monumenta.
    public static Vector[] getStarVertices(int vertices, double size, double pointiness) {
        Vector[] starPoints = new Vector[2 * vertices];

        for (int i = 0; i < vertices; i++) {
            double angle = 2 * i * Math.PI / vertices;
            double cosAngle = FastUtils.cos(angle);
            double sinAngle = FastUtils.sin(angle);
            double cosAnglePlus = FastUtils.cos(angle + Math.PI / vertices);
            double sinAnglePlus = FastUtils.sin(angle + Math.PI / vertices);

            starPoints[2 * i] = new Vector(sinAngle, 0, cosAngle).multiply(size);
            starPoints[2 * i + 1] = new Vector(sinAnglePlus, 0, cosAnglePlus).multiply(pointiness * size);
        }

        return starPoints;
    }


    // Rotate functions made by Team Monumenta
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

    public static Vector rotateYAxis(Vector position, double degrees) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        double cos = FastUtils.cosDeg(degrees);
        double sin = FastUtils.sinDeg(degrees);
        return new Vector(x * cos - z * sin, y, z * cos + x * sin);
    }

    public static Vector rotateZAxis(Vector position, double degrees) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        double cos = FastUtils.cosDeg(degrees);
        double sin = FastUtils.sinDeg(degrees);
        return new Vector(x * cos + y * sin, y * cos - x * sin, z);
    }

    public static Vector getDirection(Location from, Location to) {
        return to.toVector().subtract(from.toVector()).normalize();
    }
}
