package org.joml.test;

import junit.framework.TestCase;

import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Tests for the {@link Intersectionf} class.
 * 
 * @author Kai Burjack
 */
public class IntersectionfTest extends TestCase {

    public static void testIntersectRayTriangleFrontPX() {
        Vector3f origin = new Vector3f();
        Vector3f dir = new Vector3f(1, 0, 0);
        Vector3f v0 = new Vector3f(1, -1, -1);
        Vector3f v1 = new Vector3f(1, -1, 1);
        Vector3f v2 = new Vector3f(1, 1, 0);
        float t = Intersectionf.intersectRayTriangleFront(origin, dir, v0, v1, v2, 0.0f);
        assertEquals(1.0f, t, 0.0f);
    }

    public static void testIntersectRaySphere() {
        Vector3f origin = new Vector3f();
        Vector3f dir = new Vector3f(1, 0, 0);
        Vector3f center = new Vector3f(5, 0, 0);
        float radiusSquared = 1.0f;
        Vector2f result = new Vector2f();
        boolean intersect = Intersectionf.intersectRaySphere(origin, dir, center, radiusSquared, result);
        assertTrue(intersect);
        assertEquals(4.0f, result.x, 1E-6f);
        assertEquals(6.0f, result.y, 1E-6f);
    }

    public static void testIntersectRayPlane() {
        Vector3f origin = new Vector3f();
        Vector3f dir = new Vector3f(1, 1, 1);
        Vector3f point = new Vector3f(2, 2, 2);
        Vector3f normal = new Vector3f(-1, -1, -1);
        float t = Intersectionf.intersectRayPlane(origin, dir, point, normal, 0.0f);
        assertTrue(t >= 0.0f);
        Vector3f intersection = new Vector3f(dir).mul(t).add(origin);
        TestUtil.assertVector3fEquals(new Vector3f(2, 2, 2), intersection, 1E-6f);
        normal = new Vector3f(1, 1, 1);
        t = Intersectionf.intersectRayPlane(origin, dir, point, normal, 0.0f);
        assertEquals(-1.0f, t, 1E-6f);
    }

    public static void testNotIntersectRayPlane() {
        Vector3f origin = new Vector3f();
        Vector3f dir = new Vector3f(-1, -1, -1);
        Vector3f point = new Vector3f(2, 2, 2);
        Vector3f normal = new Vector3f(-1, -1, -1);
        float t = Intersectionf.intersectRayPlane(origin, dir, point, normal, 0.0f);
        assertTrue(t == -1.0f);
    }

    public static void testAabPlane() {
        assertTrue(Intersectionf.testAabPlane(-1, -1, -1, 1, 1, 1, 1, 1, 1, 3.0f));
        assertFalse(Intersectionf.testAabPlane(-1, -1, -1, 1, 1, 1, 1, 1, 1, 3.1f));
        assertTrue(Intersectionf.testAabPlane(-1, -1, -1, 1, 1, 1, 1, 1, 1, -3.0f));
        assertFalse(Intersectionf.testAabPlane(-1, -1, -1, 1, 1, 1, 1, 1, 1, -3.1f));
    }

    public static void testSphereSphere() {
        assertTrue(Intersectionf.testSphereSphere(0, 0, 0, 1, 0.5f, 0, 0, 1));
        Vector4f res = new Vector4f();
        assertTrue(Intersectionf.intersectSphereSphere(0, 0, 0, 1, 0.5f, 0, 0, 1, res));
        // intersection point is (0.25, 0, 0) <- middle between both spheres with equal radii
        TestUtil.assertVector3fEquals(new Vector3f(0.25f, 0, 0), new Vector3f(res.x, res.y, res.z), 1E-6f);
        // cos(a) = adjside/hyp
        // cos(a) * hyp = adjside
        // acos(cos(a) * hyp) = acos(adjside)
        // y = sin(acos(adjside))
        float expectedRadius = (float) Math.sin(Math.acos(0.25));
        assertEquals(expectedRadius, res.w, 1E-6f);
    }

    public static void testAabSphere() {
        assertTrue(Intersectionf.testAabSphere(-1, -1, -1, 1, 1, 1, 2, 0, 0, 1.0f));
    }

    public static void testRayTriangleFront() {
        assertTrue(Intersectionf.testRayTriangleFront(0, 0, 0, 1, 0, 0, 1, -1, -1, 1, -1, 1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testRayTriangleFront(0, 0, 0, 1, 0, 0, 1, -1, 1, 1, -1, -1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testRayTriangleFront(0, 0, 0, -1, 0, 0, 1, -1, -1, 1, -1, 1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testRayTriangleFront(0, 0, 0, -1, 0, 0, 1, -1, 1, 1, -1, -1, 1, 1, 0, 1E-6f));
    }

    public static void testRayTriangle() {
        assertTrue(Intersectionf.testRayTriangle(0, 0, 0, 1, 0, 0, 1, -1, -1, 1, -1, 1, 1, 1, 0, 1E-6f));
        assertTrue(Intersectionf.testRayTriangle(0, 0, 0, 1, 0, 0, 1, -1, 1, 1, -1, -1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testRayTriangle(0, 0, 0, -1, 0, 0, 1, -1, -1, 1, -1, 1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testRayTriangle(0, 0, 0, -1, 0, 0, 1, -1, 1, 1, -1, -1, 1, 1, 0, 1E-6f));
    }

    public static void testLineSegmentSphere() {
        assertTrue(Intersectionf.testLineSegmentSphere(-1, 0, 0, 1, 0, 0, 0, 0, 0, 1));
        assertTrue(Intersectionf.testLineSegmentSphere(-1, 1, 0, 1, 1, 0, 0, 0, 0, 1));
        assertFalse(Intersectionf.testLineSegmentSphere(-1, 1.01f, 0, 1, 1, 0, 0, 0, 0, 1));
        assertFalse(Intersectionf.testLineSegmentSphere(1.01f, 0, 0, 2, 0, 0, 0, 0, 0, 1));
        assertFalse(Intersectionf.testLineSegmentSphere(-2, 0, 0, -1.01f, 0, 0, 0, 0, 0, 1));
        assertFalse(Intersectionf.testLineSegmentSphere(2.01f, 0, 0, 3, 0, 0, 0, 0, 0, 2*2));
        assertFalse(Intersectionf.testLineSegmentSphere(-3, 0, 0, -2.01f, 0, 0, 0, 0, 0, 2*2));
        assertTrue(Intersectionf.testLineSegmentSphere(-1, 0, 0, 1, 0, 0, -2, 0, 0, 1));
        assertTrue(Intersectionf.testLineSegmentSphere(-1, 0, 0, 1, 0, 0, 2, 0, 0, 1));
        assertFalse(Intersectionf.testLineSegmentSphere(-1, 0, 0, 1, 0, 0, -4.01f, 0, 0, 3*3));
        assertFalse(Intersectionf.testLineSegmentSphere(-1, 0, 0, 1, 0, 0, 4.01f, 0, 0, 3*3));
    }

    public static void testLineSegmentTriangle() {
        assertTrue(Intersectionf.testLineSegmentTriangle(-1, 0, 0, 1, 0, 0, 1, -1, -1, 1, -1, 1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testLineSegmentTriangle(-1, 0, 0, -5, 0, 0, 1, -1, -1, 1, -1, 1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testLineSegmentTriangle(-5, 0, 0, -1, 0, 0, 1, -1, -1, 1, -1, 1, 1, 1, 0, 1E-6f));
        assertTrue(Intersectionf.testLineSegmentTriangle(1, 0, 0, -1, 0, 0, 1, -1, -1, 1, -1, 1, 1, 1, 0, 1E-6f));

        assertTrue(Intersectionf.testLineSegmentTriangle(-1, 0, 0, 1, 0, 0, 1, -1, 1, 1, -1, -1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testLineSegmentTriangle(-1, 0, 0, -5, 0, 0, 1, -1, 1, 1, -1, -1, 1, 1, 0, 1E-6f));
        assertFalse(Intersectionf.testLineSegmentTriangle(-5, 0, 0, -1, 0, 0, 1, -1, 1, 1, -1, -1, 1, 1, 0, 1E-6f));
        assertTrue(Intersectionf.testLineSegmentTriangle(1, 0, 0, -1, 0, 0, 1, -1, 1, 1, -1, -1, 1, 1, 0, 1E-6f));
    }

    public static void testRayAar() {
        Vector2f p = new Vector2f();
        assertEquals(Intersectionf.AAR_SIDE_MINX, Intersectionf.intersectRayAar(-3, 0, 1, 0, -1, -1, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(-1, 0), new Vector2f(-3 + p.x * 1, 0 + p.x * 0), 1E-6f);
        assertEquals(Intersectionf.AAR_SIDE_MAXX, Intersectionf.intersectRayAar(3, 0, -1, 0, -1, -1, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(1, 0), new Vector2f(3 + p.x * -1, 0 + p.x * 0), 1E-6f);
        assertEquals(Intersectionf.AAR_SIDE_MINY, Intersectionf.intersectRayAar(0, -2, 0, 1, -1, -1, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, -1), new Vector2f(0 + p.x * 0, -2 + p.x * 1), 1E-6f);
        assertEquals(Intersectionf.AAR_SIDE_MAXY, Intersectionf.intersectRayAar(0, 2, 0, -1, -1, -1, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), new Vector2f(0 + p.x * 0, 2 + p.x * -1), 1E-6f);
        assertEquals(Intersectionf.AAR_SIDE_MINX, Intersectionf.intersectRayAar(0, 0, 0, -1, 0, -1, 1, 0, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 0), new Vector2f(0 + p.x * 0, 0 + p.x * -1), 1E-6f);
        assertEquals(Intersectionf.AAR_SIDE_MINX, Intersectionf.intersectRayAar(0, 0, 0, 1, 0, 1, 1, 2, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), new Vector2f(0 + p.x * 0, 0 + p.x * 1), 1E-6f);
        assertEquals(Intersectionf.AAR_SIDE_MINY, Intersectionf.intersectRayAar(0, 0, -1, 0, -1, 0, 0, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 0), new Vector2f(0 + p.x * -1, 0 + p.x * 0), 1E-6f);
        assertEquals(Intersectionf.AAR_SIDE_MINX, Intersectionf.intersectRayAar(0, 0, 1, 0, 1, 0, 2, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(1, 0), new Vector2f(0 + p.x * 1, 0 + p.x * 0), 1E-6f);
    }

    public static void testRayLineSegment() {
        assertEquals(1.0f, Intersectionf.intersectRayLineSegment(0, 0, 1, 0, 1, -1, 1, 1), 1E-6f);
        assertEquals(1.0f, Intersectionf.intersectRayLineSegment(0, 0, 1, 0, 1, 1, 1, -1), 1E-6f);
        assertEquals(1.0f, Intersectionf.intersectRayLineSegment(0, 1, 1, 0, 1, 1, 1, -1), 1E-6f);
        assertEquals(1.0f, Intersectionf.intersectRayLineSegment(0, -1, 1, 0, 1, 1, 1, -1), 1E-6f);
        assertEquals(0.5f, Intersectionf.intersectRayLineSegment(0, -1, 2, 0, 1, 1, 1, -1), 1E-6f);
        assertEquals(1.0f, Intersectionf.intersectRayLineSegment(0, 0, 1, 1, 0, 2, 2, 0), 1E-6f);
        assertEquals(-1.0f, Intersectionf.intersectRayLineSegment(0, 0, -1, -1, 0, 2, 2, 0), 1E-6f);
        assertEquals(-1.0f, Intersectionf.intersectRayLineSegment(0, 0, 1, 0, 1, 1, 1, 2), 1E-6f);
        assertEquals(-1.0f, Intersectionf.intersectRayLineSegment(0, 0, 1, 0, 1, 2, 1, 1), 1E-6f);
    }

    public static void testPolygonRay() {
        Vector2f p = new Vector2f();
        float[] verticesXY = {0, 0, 1, 0, 1, 1, 0, 1};
        assertEquals(3, Intersectionf.intersectPolygonRay(verticesXY, -1, 0.5f, 1, 0, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 0.5f), p, 1E-6f);
        assertEquals(0, Intersectionf.intersectPolygonRay(verticesXY, 0.1f, -0.5f, 0, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.1f, 0), p, 1E-6f);
        assertEquals(-1, Intersectionf.intersectPolygonRay(verticesXY, 0.1f, -1.1f, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.1f, 0), p, 1E-6f);
        assertEquals(-1, Intersectionf.intersectPolygonRay(verticesXY, 1.1f, 0f, -1, -1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.1f, 0), p, 1E-6f);
        assertEquals(-1, Intersectionf.intersectPolygonRay(verticesXY, 1.1f, 1, 0, -1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.1f, 0), p, 1E-6f);
        assertEquals(-1, Intersectionf.intersectPolygonRay(verticesXY, -0.1f, 0, 1, -1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.1f, 0), p, 1E-6f);
    }

    public static void testLineSegmentAar() {
        Vector2f p = new Vector2f();
        assertEquals(Intersectionf.ONE_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 0, 1, 0, 0.5f, -1, 1.5f, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.5f, 0.5f), p, 1E-6f);
        assertEquals(Intersectionf.ONE_INTERSECTION, Intersectionf.intersectLineSegmentAar(1, 0, 0, 0, 0.5f, -1, 1.5f, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.5f, 0.5f), p, 1E-6f);
        assertEquals(Intersectionf.INSIDE, Intersectionf.intersectLineSegmentAar(0, 0, 1, 0, -0.5f, -1, 1.5f, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(-0.5f, 1.5f), p, 1E-6f);
        assertEquals(Intersectionf.INSIDE, Intersectionf.intersectLineSegmentAar(1, 0, 0, 0, -0.5f, -1, 1.5f, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(-0.5f, 1.5f), p, 1E-6f);
        assertEquals(Intersectionf.OUTSIDE, Intersectionf.intersectLineSegmentAar(0, 0, 1, 0, 1.5f, -1, 2.5f, 1, p));
        assertEquals(Intersectionf.OUTSIDE, Intersectionf.intersectLineSegmentAar(1, 0, 0, 0, 1.5f, -1, 2.5f, 1, p));
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 0, 1, 0, 0.5f, -1, 0.75f, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.5f, 0.75f), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(1, 0, 0, 0, 0.5f, -1, 0.75f, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.25f, 0.5f), p, 1E-6f);
        assertEquals(Intersectionf.ONE_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 0, 1, 0, 1, -1, 2, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(1, 1), p, 1E-6f);
        assertEquals(Intersectionf.ONE_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 0, -1, 0, -2, -1, -1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(1, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 0, 1, 0, 0.5f, -1, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0.5f, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 0, 0, 1, -0.5f, 0, 0, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 0, 1, 0, 0, 0, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(1, 0, 0, 0, 0, 0, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 1, 0, 0, 0, 0, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, 0, 0, 1, 0, 0, 1, 1, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, -1, 0, 0, -1, -1, 0, 0, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAar(0, -1, 0, 1, -1, -1, 0, 0, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 0.5f), p, 1E-6f);
    }

    public static void testLineSegmentAab() {
        Vector2f p = new Vector2f();
        assertEquals(Intersectionf.ONE_INTERSECTION, Intersectionf.intersectLineSegmentAab(0, 0, 0, 0, 0, 1, -0.5f, -1, 1, 0.5f, 1, 2, p));
        TestUtil.assertVector2fEquals(new Vector2f(1, 1), p, 1E-6f);
        assertEquals(Intersectionf.TWO_INTERSECTION, Intersectionf.intersectLineSegmentAab(0, 0, -1, 0, 0, 0, 0, -1, -1, 1, 1, 0, p));
        TestUtil.assertVector2fEquals(new Vector2f(0, 1), p, 1E-6f);
    }

}
