package safro.saflib.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class MathUtil {

    // Returns a list which contains a sphere of BlockPos in the designated radius
    public static List<BlockPos> getPosSphere(BlockPos origin, int radius) {
        List<BlockPos> blocks = new ArrayList<>();
        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                for (int z = -radius; z < radius; z++) {
                    if (x * x + y * y + z * z < radius * radius) {
                        BlockPos pos = origin.add(x, y, z);
                        blocks.add(pos);
                    }
                }
            }
        }
        return blocks;
    }

    // Returns a list which contains a circle of vectors in the designated radius and frequency (amount)
    public static List<Vec3d> getCircle(double x, double y, double z, double radius, int amount, Direction.Axis axis) {
        List<Vec3d> list = new ArrayList<>();
        int count = (int) (2 * Math.PI * radius * amount);
        double degree = 360 / (double) count;
        double add = 0;
        for (double i = 0 + add; i < 360 + add; i += degree) {
            Vec3d vec3d;
            double value = Math.toRadians(i);
            vec3d = switch (axis) {
                case Y -> new Vec3d(Math.cos(value) * radius, 0, Math.sin(value) * radius);
                case X -> new Vec3d(0, Math.cos(value) * radius, Math.sin(value) * radius);
                case Z -> new Vec3d(Math.cos(value) * radius, Math.sin(value) * radius, 0);
            };
            list.add(new Vec3d(x + vec3d.x, y + vec3d.y, z + vec3d.z));
        }
        return list;
    }

    // Returns a list which contains a line of vectors with the desired frequency (spacing)
    public static List<Vec3d> getLine(Vec3d point1, Vec3d point2, double spacing) {
        List<Vec3d> list = new ArrayList<>();
        double distance = point1.distanceTo(point2);
        Vec3d disVec = new Vec3d(point2.getX(), point2.getY(), point2.getZ()).subtract(point1).normalize().multiply(spacing);
        for (double length = 0.0D; length < distance; point1.add(disVec)) {
            list.add(new Vec3d(point1.getX(), point1.getY(), point1.getZ()));
            length += spacing;
        }
        return list;
    }
}
