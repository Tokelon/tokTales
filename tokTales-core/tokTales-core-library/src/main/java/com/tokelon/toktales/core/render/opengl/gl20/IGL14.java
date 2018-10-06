package com.tokelon.toktales.core.render.opengl.gl20;

/** OGL2 v1.4 definition of OpenGL CompatWrapper.
 * <p>
 * Credit to LWJGL (https://www.lwjgl.org) which was used as the base.
 */
public interface IGL14 extends IGL {
	/**
	 * The core OpenGL 1.4 functionality.
	 * 
	 * <p>Extensions promoted to core in this release:</p>
	 * 
	 * <ul>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/SGIS/SGIS_generate_mipmap.txt">SGIS_generate_mipmap</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/NV/NV_blend_square.txt">NV_blend_square</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/ARB/ARB_depth_texture.txt">ARB_depth_texture</a> and <a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/ARB/ARB_shadow.txt">ARB_shadow</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_fog_coord.txt">EXT_fog_coord</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_multi_draw_arrays.txt">EXT_multi_draw_arrays</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/ARB/ARB_point_parameters.txt">ARB_point_parameters</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_secondary_color.txt">EXT_secondary_color</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_blend_func_separate.txt">EXT_blend_func_separate</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_stencil_wrap.txt">EXT_stencil_wrap</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/ARB/ARB_texture_env_crossbar.txt">ARB_texture_env_crossbar</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_texture_lod_bias.txt">EXT_texture_lod_bias</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/ARB/ARB_texture_mirrored_repeat.txt">ARB_texture_mirrored_repeat</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/ARB/ARB_window_pos.txt">ARB_window_pos</a></li>
	 * </ul>
	 */

	
    /** Accepted by the {@code pname} parameter of TexParameteri, TexParameterf, TexParameteriv, TexParameterfv, GetTexParameteriv, and GetTexParameterfv. */
    //public static final int GL_GENERATE_MIPMAP = 0x8191;

    /** Accepted by the {@code target} parameter of Hint, and by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
    public static final int GL_GENERATE_MIPMAP_HINT = 0x8192;

    /** Accepted by the {@code sfactor} and {@code dfactor} parameters of BlendFunc. */
    public static final int
        GL_CONSTANT_COLOR           = 0x8001,
        GL_ONE_MINUS_CONSTANT_COLOR = 0x8002,
        GL_CONSTANT_ALPHA           = 0x8003,
        GL_ONE_MINUS_CONSTANT_ALPHA = 0x8004;

    /** Accepted by the {@code mode} parameter of BlendEquation. */
    public static final int
        GL_FUNC_ADD = 0x8006;
        //GL_MIN      = 0x8007,
        //GL_MAX      = 0x8008;

    /** Accepted by the {@code mode} parameter of BlendEquation. */
    public static final int
        GL_FUNC_SUBTRACT         = 0x800A,
        GL_FUNC_REVERSE_SUBTRACT = 0x800B;

    /** Accepted by the {@code internalFormat} parameter of TexImage1D, TexImage2D, CopyTexImage1D and CopyTexImage2D. */
    public static final int
        GL_DEPTH_COMPONENT16 = 0x81A5;
        //GL_DEPTH_COMPONENT24 = 0x81A6,
        //GL_DEPTH_COMPONENT32 = 0x81A7;

    /** Accepted by the {@code pname} parameter of GetTexLevelParameterfv and GetTexLevelParameteriv. */
    //public static final int GL_TEXTURE_DEPTH_SIZE = 0x884A;

    /** Accepted by the {@code pname} parameter of TexParameterf, TexParameteri, TexParameterfv, TexParameteriv, GetTexParameterfv, and GetTexParameteriv. */
    //public static final int GL_DEPTH_TEXTURE_MODE = 0x884B;

    /** Accepted by the {@code pname} parameter of TexParameterf, TexParameteri, TexParameterfv, TexParameteriv, GetTexParameterfv, and GetTexParameteriv. *//*
    public static final int
        GL_TEXTURE_COMPARE_MODE = 0x884C,
        GL_TEXTURE_COMPARE_FUNC = 0x884D;
	*/
    
    /**
     * Accepted by the {@code param} parameter of TexParameterf, TexParameteri, TexParameterfv, and TexParameteriv when the {@code pname} parameter is
     * TEXTURE_COMPARE_MODE.
     */
    //public static final int GL_COMPARE_R_TO_TEXTURE = 0x884E;

    /** Accepted by the {@code pname} parameter of Fogi and Fogf. */
    //public static final int GL_FOG_COORDINATE_SOURCE = 0x8450;

    /** Accepted by the {@code param} parameter of Fogi and Fogf. *//*
    public static final int
        GL_FOG_COORDINATE = 0x8451,
        GL_FRAGMENT_DEPTH = 0x8452;
	*/
    
    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. *//*
    public static final int
        GL_CURRENT_FOG_COORDINATE      = 0x8453,
        GL_FOG_COORDINATE_ARRAY_TYPE   = 0x8454,
        GL_FOG_COORDINATE_ARRAY_STRIDE = 0x8455;
	*/
    
    /** Accepted by the {@code pname} parameter of GetPointerv. */
    //public static final int GL_FOG_COORDINATE_ARRAY_POINTER = 0x8456;

    /** Accepted by the {@code array} parameter of EnableClientState and DisableClientState. */
    //public static final int GL_FOG_COORDINATE_ARRAY = 0x8457;

    /** Accepted by the {@code pname} parameter of PointParameterfARB, and the {@code pname} of Get. *//*
    public static final int
        GL_POINT_SIZE_MIN             = 0x8126,
        GL_POINT_SIZE_MAX             = 0x8127,
        GL_POINT_FADE_THRESHOLD_SIZE  = 0x8128,
        GL_POINT_DISTANCE_ATTENUATION = 0x8129;
	*/
    
    /**
     * Accepted by the {@code cap} parameter of Enable, Disable, and IsEnabled, and by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and
     * GetDoublev.
     */
    //public static final int GL_COLOR_SUM = 0x8458;

    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. *//*
    public static final int
        GL_CURRENT_SECONDARY_COLOR      = 0x8459,
        GL_SECONDARY_COLOR_ARRAY_SIZE   = 0x845A,
        GL_SECONDARY_COLOR_ARRAY_TYPE   = 0x845B,
        GL_SECONDARY_COLOR_ARRAY_STRIDE = 0x845C;
	*/
    
    /** Accepted by the {@code pname} parameter of GetPointerv. */
    //public static final int GL_SECONDARY_COLOR_ARRAY_POINTER = 0x845D;

    /** Accepted by the {@code array} parameter of EnableClientState and DisableClientState. */
    //public static final int GL_SECONDARY_COLOR_ARRAY = 0x845E;

    /** Accepted by the {@code pname} parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
    public static final int
        GL_BLEND_DST_RGB   = 0x80C8,
        GL_BLEND_SRC_RGB   = 0x80C9,
        GL_BLEND_DST_ALPHA = 0x80CA,
        GL_BLEND_SRC_ALPHA = 0x80CB;

    /** Accepted by the {@code sfail}, {@code dpfail}, and {@code dppass} parameter of StencilOp. */
    public static final int
        GL_INCR_WRAP = 0x8507,
        GL_DECR_WRAP = 0x8508;

    /** Accepted by the {@code target} parameters of GetTexEnvfv, GetTexEnviv, TexEnvi, TexEnvf, Texenviv, and TexEnvfv. */
    //public static final int GL_TEXTURE_FILTER_CONTROL = 0x8500;

    /**
     * When the {@code target} parameter of GetTexEnvfv, GetTexEnviv, TexEnvi, TexEnvf, TexEnviv, and TexEnvfv is TEXTURE_FILTER_CONTROL, then the value of
     * {@code pname} may be.
     */
    //public static final int GL_TEXTURE_LOD_BIAS = 0x8501;

    /** Accepted by the {@code pname} parameters of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev. */
    //public static final int GL_MAX_TEXTURE_LOD_BIAS = 0x84FD;

    /**
     * Accepted by the {@code param} parameter of TexParameteri and TexParameterf, and by the {@code params} parameter of TexParameteriv and TexParameterfv,
     * when their {@code pname} parameter is TEXTURE_WRAP_S, TEXTURE_WRAP_T, or TEXTURE_WRAP_R.
     */
    public static final int GL_MIRRORED_REPEAT = 0x8370;

    
    // --- [ glBlendColor ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glBlendColor">Reference Page</a></p>
     * 
     * Specifies the constant color C<sub>c</sub> to be used in blending.
     *
     * @param red   the red color component
     * @param green the green color component
     * @param blue  the blue color component
     * @param alpha the alpha color component
     */
    public void glBlendColor(float red, float green, float blue, float alpha);

    // --- [ glBlendEquation ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glBlendEquation">Reference Page</a></p>
     * 
     * Controls the blend equations used for per-fragment blending.
     *
     * @param mode the blend equation. One of:<br><table><tr><td>{@link #GL_FUNC_ADD FUNC_ADD}</td><td>{@link #GL_FUNC_SUBTRACT FUNC_SUBTRACT}</td><td>{@link #GL_FUNC_REVERSE_SUBTRACT FUNC_REVERSE_SUBTRACT}</td><td>{@link #GL_MIN MIN}</td><td>{@link #GL_MAX MAX}</td></tr></table>
     */
    public void glBlendEquation(int mode);

    // --- [ glMultiDrawArrays ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glMultiDrawArrays">Reference Page</a></p>
     * 
     * Renders multiple sets of primitives from array data.
     *
     * @param mode  the kind of primitives to render. One of:<br><table><tr><td>{@link GL11#GL_POINTS POINTS}</td><td>{@link GL11#GL_LINE_STRIP LINE_STRIP}</td><td>{@link GL11#GL_LINE_LOOP LINE_LOOP}</td><td>{@link GL11#GL_LINES LINES}</td><td>{@link GL11#GL_POLYGON POLYGON}</td><td>{@link GL11#GL_TRIANGLE_STRIP TRIANGLE_STRIP}</td><td>{@link GL11#GL_TRIANGLE_FAN TRIANGLE_FAN}</td></tr><tr><td>{@link GL11#GL_TRIANGLES TRIANGLES}</td><td>{@link GL11#GL_QUAD_STRIP QUAD_STRIP}</td><td>{@link GL11#GL_QUADS QUADS}</td><td>{@link GL32#GL_LINES_ADJACENCY LINES_ADJACENCY}</td><td>{@link GL32#GL_LINE_STRIP_ADJACENCY LINE_STRIP_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLES_ADJACENCY TRIANGLES_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLE_STRIP_ADJACENCY TRIANGLE_STRIP_ADJACENCY}</td></tr><tr><td>{@link GL40#GL_PATCHES PATCHES}</td></tr></table>
     * @param first an array of starting indices in the enabled arrays
     * @param count an array of the number of indices to be rendered
     */
    //public void glMultiDrawArrays(int mode, IntBuffer first, IntBuffer count);

    // --- [ glMultiDrawElements ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glMultiDrawElements">Reference Page</a></p>
     * 
     * Renders multiple sets of primitives by specifying indices of array data elements.
     * 
     * <p><b>LWJGL note</b>: Use {@link org.lwjgl.system.MemoryUtil#memAddress} to retrieve pointers to the index buffers.</p>
     *
     * @param mode    the kind of primitives to render. One of:<br><table><tr><td>{@link GL11#GL_POINTS POINTS}</td><td>{@link GL11#GL_LINE_STRIP LINE_STRIP}</td><td>{@link GL11#GL_LINE_LOOP LINE_LOOP}</td><td>{@link GL11#GL_LINES LINES}</td><td>{@link GL11#GL_POLYGON POLYGON}</td><td>{@link GL11#GL_TRIANGLE_STRIP TRIANGLE_STRIP}</td><td>{@link GL11#GL_TRIANGLE_FAN TRIANGLE_FAN}</td></tr><tr><td>{@link GL11#GL_TRIANGLES TRIANGLES}</td><td>{@link GL11#GL_QUAD_STRIP QUAD_STRIP}</td><td>{@link GL11#GL_QUADS QUADS}</td><td>{@link GL32#GL_LINES_ADJACENCY LINES_ADJACENCY}</td><td>{@link GL32#GL_LINE_STRIP_ADJACENCY LINE_STRIP_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLES_ADJACENCY TRIANGLES_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLE_STRIP_ADJACENCY TRIANGLE_STRIP_ADJACENCY}</td></tr><tr><td>{@link GL40#GL_PATCHES PATCHES}</td></tr></table>
     * @param count   an array of the elements counts
     * @param type    the type of the values in indices. One of:<br><table><tr><td>{@link GL11#GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link GL11#GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link GL11#GL_UNSIGNED_INT UNSIGNED_INT}</td></tr></table>
     * @param indices a pointer to the location where the indices are stored
     */
    //public void glMultiDrawElements(int mode, IntBuffer count, int type, PointerBuffer indices);

    // --- [ glPointParameterf ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPointParameterf">Reference Page</a></p>
     * 
     * Sets the float value of a pointer parameter.
     *
     * @param pname the parameter to set. One of:<br><table><tr><td>{@link #GL_POINT_SIZE_MIN POINT_SIZE_MIN}</td><td>{@link #GL_POINT_SIZE_MAX POINT_SIZE_MAX}</td><td>{@link #GL_POINT_FADE_THRESHOLD_SIZE POINT_FADE_THRESHOLD_SIZE}</td></tr></table>
     * @param param the parameter value
     */
    //public void glPointParameterf(int pname, float param);

    // --- [ glPointParameteri ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPointParameteri">Reference Page</a></p>
     * 
     * Integer version of {@link #glPointParameterf PointParameterf}.
     *
     * @param pname the parameter to set. One of:<br><table><tr><td>{@link #GL_POINT_SIZE_MIN POINT_SIZE_MIN}</td><td>{@link #GL_POINT_SIZE_MAX POINT_SIZE_MAX}</td><td>{@link #GL_POINT_FADE_THRESHOLD_SIZE POINT_FADE_THRESHOLD_SIZE}</td></tr></table>
     * @param param the parameter value
     */
    //public void glPointParameteri(int pname, int param);

    // --- [ glPointParameterfv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPointParameter">Reference Page</a></p>
     * 
     * Pointer version of {@link #glPointParameterf PointParameterf}.
     *
     * @param pname  the parameter to set. Must be:<br><table><tr><td>{@link #GL_POINT_DISTANCE_ATTENUATION POINT_DISTANCE_ATTENUATION}</td></tr></table>
     * @param params the parameter value
     */
    //public void glPointParameterfv(int pname, FloatBuffer params);

    // --- [ glPointParameteriv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPointParameter">Reference Page</a></p>
     * 
     * Pointer version of {@link #glPointParameteri PointParameteri}.
     *
     * @param pname  the parameter to set. Must be:<br><table><tr><td>{@link #GL_POINT_DISTANCE_ATTENUATION POINT_DISTANCE_ATTENUATION}</td></tr></table>
     * @param params the parameter value
     */
    //public void glPointParameteriv(int pname, IntBuffer params);

    // --- [ glBlendFuncSeparate ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glBlendFuncSeparate">Reference Page</a></p>
     * 
     * Specifies pixel arithmetic for RGB and alpha components separately.
     *
     * @param sfactorRGB   how the red, green, and blue blending factors are computed. The initial value is GL_ONE.
     * @param dfactorRGB   how the red, green, and blue destination blending factors are computed. The initial value is GL_ZERO.
     * @param sfactorAlpha how the alpha source blending factor is computed. The initial value is GL_ONE.
     * @param dfactorAlpha how the alpha destination blending factor is computed. The initial value is GL_ZERO.
     */
    public void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glMultiDrawArrays">Reference Page</a></p>
     * 
     * Array version of: {@link #glMultiDrawArrays MultiDrawArrays}
     */
    //public void glMultiDrawArrays(int mode, int[] first, int[] count);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glMultiDrawElements">Reference Page</a></p>
     * 
     * Array version of: {@link #glMultiDrawElements MultiDrawElements}
     */
    //public void glMultiDrawElements(int mode, int[] count, int type, PointerBuffer indices);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPointParameter">Reference Page</a></p>
     * 
     * Array version of: {@link #glPointParameterfv PointParameterfv}
     */
    //public void glPointParameterfv(int pname, float[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPointParameter">Reference Page</a></p>
     * 
     * Array version of: {@link #glPointParameteriv PointParameteriv}
     */
    //public void glPointParameteriv(int pname, int[] params);

}
