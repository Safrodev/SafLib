package safro.saflib.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class MathUtil {

    /**
     * @param origin the center of the sphere
     * @param radius the radius of the sphere
     * @return a list of BlockPos that contains all the blocks inside the sphere
     */
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

    /**
     * @param x center-x
     * @param y center-y
     * @param z center-z
     * @param radius radius of the circle
     * @param amount frequency of particles
     * @param axis rotation of the circle by axis
     * @return Returns a list of vectors that make up the circle
     */
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

    /**
     * @param start first endpoint
     * @param end second endpoint
     * @param spacing the spacing between particles
     *                Use a smaller value (Ex. 0.1) for closer, more frequent particles
     *                and a larger value (Ex. 1) for farther, less frequent particles
     * @return Returns a list of vectors that make up a line
     */
    public static List<Vec3d> getLine(Vec3d start, Vec3d end, double spacing) {
        List<Vec3d> list = new ArrayList<>();
        double deltaX = end.getX() - start.getX();
        double deltaY = end.getY() - start.getY();
        double deltaZ = end.getZ() - start.getZ();
        double distance = Math.max(Math.abs(deltaX), Math.max(Math.abs(deltaY), Math.abs(deltaZ)));
        double stepX = deltaX / distance;
        double stepY = deltaY / distance;
        double stepZ = deltaZ / distance;

        for (double i = 0.0; i < distance; i += spacing) {
            double x = start.getX() + stepX * i;
            double y = start.getY() + stepY * i;
            double z = start.getZ() + stepZ * i;
            list.add(new Vec3d(x, y, z));
        }
        return list;
    }
}
