package com.tokelon.toktales.core.render.opengl.gl20;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/** OGL2 v1.1 definition of OpenGL CompatWrapper.
 * <p>
 * Credit to LWJGL (https://www.lwjgl.org) which was used as the base.
 */
public interface IGL11 extends IGL {
	/**
	 * The core OpenGL 1.1 functionality.
	 * 
	 * <p>Extensions promoted to core in this release:</p>
	 * 
	 * <ul>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_vertex_array.txt">EXT_vertex_array</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_polygon_offset.txt">EXT_polygon_offset</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_blend_logic_op.txt">EXT_blend_logic_op</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_texture.txt">EXT_texture</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_copy_texture.txt">EXT_copy_texture</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_subtexture.txt">EXT_subtexture</a></li>
	 * <li><a target="_blank" href="https://www.khronos.org/registry/OpenGL/extensions/EXT/EXT_texture_object.txt">EXT_texture_object</a></li>
	 * </ul>
	 */
	
	
    /** AccumOp *//*
    public static final int
        GL_ACCUM  = 0x100,
        GL_LOAD   = 0x101,
        GL_RETURN = 0x102,
        GL_MULT   = 0x103,
        GL_ADD    = 0x104;
	*/
	
    /** AlphaFunction */
    public static final int
        GL_NEVER    = 0x200,
        GL_LESS     = 0x201,
        GL_EQUAL    = 0x202,
        GL_LEQUAL   = 0x203,
        GL_GREATER  = 0x204,
        GL_NOTEQUAL = 0x205,
        GL_GEQUAL   = 0x206,
        GL_ALWAYS   = 0x207;

    /** AttribMask */
    public static final int
//        GL_CURRENT_BIT         = 0x1,
//        GL_POINT_BIT           = 0x2,
//        GL_LINE_BIT            = 0x4,
//        GL_POLYGON_BIT         = 0x8,
//        GL_POLYGON_STIPPLE_BIT = 0x10,
//        GL_PIXEL_MODE_BIT      = 0x20,
//        GL_LIGHTING_BIT        = 0x40,
//        GL_FOG_BIT             = 0x80,
        GL_DEPTH_BUFFER_BIT    = 0x100,
//        GL_ACCUM_BUFFER_BIT    = 0x200,
        GL_STENCIL_BUFFER_BIT  = 0x400,
//        GL_VIEWPORT_BIT        = 0x800,
//        GL_TRANSFORM_BIT       = 0x1000,
//        GL_ENABLE_BIT          = 0x2000,
        GL_COLOR_BUFFER_BIT    = 0x4000;
//        GL_HINT_BIT            = 0x8000,
//        GL_EVAL_BIT            = 0x10000,
//        GL_LIST_BIT            = 0x20000,
//        GL_TEXTURE_BIT         = 0x40000,
//        GL_SCISSOR_BIT         = 0x80000,
//        GL_ALL_ATTRIB_BITS     = 0xFFFFF;

    /** BeginMode */
    public static final int
        GL_POINTS         = 0x0,
        GL_LINES          = 0x1,
        GL_LINE_LOOP      = 0x2,
        GL_LINE_STRIP     = 0x3,
        GL_TRIANGLES      = 0x4,
        GL_TRIANGLE_STRIP = 0x5,
        GL_TRIANGLE_FAN   = 0x6;
//        GL_QUADS          = 0x7,
//        GL_QUAD_STRIP     = 0x8,
//        GL_POLYGON        = 0x9;

    /** BlendingFactorDest */
    public static final int
        GL_ZERO                = 0,
        GL_ONE                 = 1,
        GL_SRC_COLOR           = 0x300,
        GL_ONE_MINUS_SRC_COLOR = 0x301,
        GL_SRC_ALPHA           = 0x302,
        GL_ONE_MINUS_SRC_ALPHA = 0x303,
        GL_DST_ALPHA           = 0x304,
        GL_ONE_MINUS_DST_ALPHA = 0x305;

    /** BlendingFactorSrc */
    public static final int
        GL_DST_COLOR           = 0x306,
        GL_ONE_MINUS_DST_COLOR = 0x307,
        GL_SRC_ALPHA_SATURATE  = 0x308;

    /** Boolean */
    public static final int
        GL_TRUE  = 1,
        GL_FALSE = 0;

    /** ClipPlaneName *//*
    public static final int
        GL_CLIP_PLANE0 = 0x3000,
        GL_CLIP_PLANE1 = 0x3001,
        GL_CLIP_PLANE2 = 0x3002,
        GL_CLIP_PLANE3 = 0x3003,
        GL_CLIP_PLANE4 = 0x3004,
        GL_CLIP_PLANE5 = 0x3005;
	*/
    
    /** DataType */
    public static final int
        GL_BYTE           = 0x1400,
        GL_UNSIGNED_BYTE  = 0x1401,
        GL_SHORT          = 0x1402,
        GL_UNSIGNED_SHORT = 0x1403,
        GL_INT            = 0x1404,
        GL_UNSIGNED_INT   = 0x1405,
        GL_FLOAT          = 0x1406;
//        GL_2_BYTES        = 0x1407,
//        GL_3_BYTES        = 0x1408,
//        GL_4_BYTES        = 0x1409,
//        GL_DOUBLE         = 0x140A;

    /** DrawBufferMode */
    public static final int
        GL_NONE           = 0,
//        GL_FRONT_LEFT     = 0x400,
//        GL_FRONT_RIGHT    = 0x401,
//        GL_BACK_LEFT      = 0x402,
//        GL_BACK_RIGHT     = 0x403,
        GL_FRONT          = 0x404,
        GL_BACK           = 0x405,
//        GL_LEFT           = 0x406,
//        GL_RIGHT          = 0x407,
        GL_FRONT_AND_BACK = 0x408;
//        GL_AUX0           = 0x409,
//        GL_AUX1           = 0x40A,
//        GL_AUX2           = 0x40B,
//        GL_AUX3           = 0x40C;

    /** ErrorCode */
    public static final int
        GL_NO_ERROR          = 0,
        GL_INVALID_ENUM      = 0x500,
        GL_INVALID_VALUE     = 0x501,
        GL_INVALID_OPERATION = 0x502,
//        GL_STACK_OVERFLOW    = 0x503,
//        GL_STACK_UNDERFLOW   = 0x504,
        GL_OUT_OF_MEMORY     = 0x505;

    /** FeedBackMode *//*
    public static final int
        GL_2D               = 0x600,
        GL_3D               = 0x601,
        GL_3D_COLOR         = 0x602,
        GL_3D_COLOR_TEXTURE = 0x603,
        GL_4D_COLOR_TEXTURE = 0x604;
	*/
    
    /** FeedBackToken *//*
    public static final int
        GL_PASS_THROUGH_TOKEN = 0x700,
        GL_POINT_TOKEN        = 0x701,
        GL_LINE_TOKEN         = 0x702,
        GL_POLYGON_TOKEN      = 0x703,
        GL_BITMAP_TOKEN       = 0x704,
        GL_DRAW_PIXEL_TOKEN   = 0x705,
        GL_COPY_PIXEL_TOKEN   = 0x706,
        GL_LINE_RESET_TOKEN   = 0x707;
	*/
    
    /** FogMode *//*
    public static final int
        GL_EXP  = 0x800,
        GL_EXP2 = 0x801;
    */

    /** FrontFaceDirection */
    public static final int
        GL_CW  = 0x900,
        GL_CCW = 0x901;

    /** GetMapTarget *//*
    public static final int
        GL_COEFF  = 0xA00,
        GL_ORDER  = 0xA01,
        GL_DOMAIN = 0xA02;
    */

    /** GetTarget */
    public static final int
//        GL_CURRENT_COLOR                 = 0xB00,
//        GL_CURRENT_INDEX                 = 0xB01,
//        GL_CURRENT_NORMAL                = 0xB02,
//        GL_CURRENT_TEXTURE_COORDS        = 0xB03,
//        GL_CURRENT_RASTER_COLOR          = 0xB04,
//        GL_CURRENT_RASTER_INDEX          = 0xB05,
//        GL_CURRENT_RASTER_TEXTURE_COORDS = 0xB06,
//        GL_CURRENT_RASTER_POSITION       = 0xB07,
//        GL_CURRENT_RASTER_POSITION_VALID = 0xB08,
//        GL_CURRENT_RASTER_DISTANCE       = 0xB09,
//        GL_POINT_SMOOTH                  = 0xB10,
//        GL_POINT_SIZE                    = 0xB11,
//        GL_POINT_SIZE_RANGE              = 0xB12,
//        GL_POINT_SIZE_GRANULARITY        = 0xB13,
//        GL_LINE_SMOOTH                   = 0xB20,
        GL_LINE_WIDTH                    = 0xB21,
//        GL_LINE_WIDTH_RANGE              = 0xB22,
//        GL_LINE_WIDTH_GRANULARITY        = 0xB23,
//        GL_LINE_STIPPLE                  = 0xB24,
//        GL_LINE_STIPPLE_PATTERN          = 0xB25,
//        GL_LINE_STIPPLE_REPEAT           = 0xB26,
//        GL_LIST_MODE                     = 0xB30,
//        GL_MAX_LIST_NESTING              = 0xB31,
//        GL_LIST_BASE                     = 0xB32,
//        GL_LIST_INDEX                    = 0xB33,
//        GL_POLYGON_MODE                  = 0xB40,
//        GL_POLYGON_SMOOTH                = 0xB41,
//        GL_POLYGON_STIPPLE               = 0xB42,
//        GL_EDGE_FLAG                     = 0xB43,
        GL_CULL_FACE                     = 0xB44,
        GL_CULL_FACE_MODE                = 0xB45,
        GL_FRONT_FACE                    = 0xB46,
//        GL_LIGHTING                      = 0xB50,
//        GL_LIGHT_MODEL_LOCAL_VIEWER      = 0xB51,
//        GL_LIGHT_MODEL_TWO_SIDE          = 0xB52,
//        GL_LIGHT_MODEL_AMBIENT           = 0xB53,
//        GL_SHADE_MODEL                   = 0xB54,
//        GL_COLOR_MATERIAL_FACE           = 0xB55,
//        GL_COLOR_MATERIAL_PARAMETER      = 0xB56,
//        GL_COLOR_MATERIAL                = 0xB57,
//        GL_FOG                           = 0xB60,
//        GL_FOG_INDEX                     = 0xB61,
//        GL_FOG_DENSITY                   = 0xB62,
//        GL_FOG_START                     = 0xB63,
//        GL_FOG_END                       = 0xB64,
//        GL_FOG_MODE                      = 0xB65,
//        GL_FOG_COLOR                     = 0xB66,
        GL_DEPTH_RANGE                   = 0xB70,
        GL_DEPTH_TEST                    = 0xB71,
        GL_DEPTH_WRITEMASK               = 0xB72,
        GL_DEPTH_CLEAR_VALUE             = 0xB73,
        GL_DEPTH_FUNC                    = 0xB74,
//        GL_ACCUM_CLEAR_VALUE             = 0xB80,
        GL_STENCIL_TEST                  = 0xB90,
        GL_STENCIL_CLEAR_VALUE           = 0xB91,
        GL_STENCIL_FUNC                  = 0xB92,
        GL_STENCIL_VALUE_MASK            = 0xB93,
        GL_STENCIL_FAIL                  = 0xB94,
        GL_STENCIL_PASS_DEPTH_FAIL       = 0xB95,
        GL_STENCIL_PASS_DEPTH_PASS       = 0xB96,
        GL_STENCIL_REF                   = 0xB97,
        GL_STENCIL_WRITEMASK             = 0xB98,
//        GL_MATRIX_MODE                   = 0xBA0,
//        GL_NORMALIZE                     = 0xBA1,
        GL_VIEWPORT                      = 0xBA2,
//        GL_MODELVIEW_STACK_DEPTH         = 0xBA3,
//        GL_PROJECTION_STACK_DEPTH        = 0xBA4,
//        GL_TEXTURE_STACK_DEPTH           = 0xBA5,
//        GL_MODELVIEW_MATRIX              = 0xBA6,
//        GL_PROJECTION_MATRIX             = 0xBA7,
//        GL_TEXTURE_MATRIX                = 0xBA8,
//        GL_ATTRIB_STACK_DEPTH            = 0xBB0,
//        GL_CLIENT_ATTRIB_STACK_DEPTH     = 0xBB1,
//        GL_ALPHA_TEST                    = 0xBC0,
//        GL_ALPHA_TEST_FUNC               = 0xBC1,
//        GL_ALPHA_TEST_REF                = 0xBC2,
        GL_DITHER                        = 0xBD0,
//        GL_BLEND_DST                     = 0xBE0,
//        GL_BLEND_SRC                     = 0xBE1,
        GL_BLEND                         = 0xBE2,
//        GL_LOGIC_OP_MODE                 = 0xBF0,
//        GL_INDEX_LOGIC_OP                = 0xBF1,
//        GL_LOGIC_OP                      = 0xBF1,
//        GL_COLOR_LOGIC_OP                = 0xBF2,
//        GL_AUX_BUFFERS                   = 0xC00,
//        GL_DRAW_BUFFER                   = 0xC01,
//        GL_READ_BUFFER                   = 0xC02,
        GL_SCISSOR_BOX                   = 0xC10,
        GL_SCISSOR_TEST                  = 0xC11,
//        GL_INDEX_CLEAR_VALUE             = 0xC20,
//        GL_INDEX_WRITEMASK               = 0xC21,
        GL_COLOR_CLEAR_VALUE             = 0xC22,
        GL_COLOR_WRITEMASK               = 0xC23,
//        GL_INDEX_MODE                    = 0xC30,
//        GL_RGBA_MODE                     = 0xC31,
//        GL_DOUBLEBUFFER                  = 0xC32,
//        GL_STEREO                        = 0xC33,
//        GL_RENDER_MODE                   = 0xC40,
//        GL_PERSPECTIVE_CORRECTION_HINT   = 0xC50,
//        GL_POINT_SMOOTH_HINT             = 0xC51,
//        GL_LINE_SMOOTH_HINT              = 0xC52,
//        GL_POLYGON_SMOOTH_HINT           = 0xC53,
//        GL_FOG_HINT                      = 0xC54,
//        GL_TEXTURE_GEN_S                 = 0xC60,
//        GL_TEXTURE_GEN_T                 = 0xC61,
//        GL_TEXTURE_GEN_R                 = 0xC62,
//        GL_TEXTURE_GEN_Q                 = 0xC63,
//        GL_PIXEL_MAP_I_TO_I              = 0xC70,
//        GL_PIXEL_MAP_S_TO_S              = 0xC71,
//        GL_PIXEL_MAP_I_TO_R              = 0xC72,
//        GL_PIXEL_MAP_I_TO_G              = 0xC73,
//        GL_PIXEL_MAP_I_TO_B              = 0xC74,
//        GL_PIXEL_MAP_I_TO_A              = 0xC75,
//        GL_PIXEL_MAP_R_TO_R              = 0xC76,
//        GL_PIXEL_MAP_G_TO_G              = 0xC77,
//        GL_PIXEL_MAP_B_TO_B              = 0xC78,
//        GL_PIXEL_MAP_A_TO_A              = 0xC79,
//        GL_PIXEL_MAP_I_TO_I_SIZE         = 0xCB0,
//        GL_PIXEL_MAP_S_TO_S_SIZE         = 0xCB1,
//        GL_PIXEL_MAP_I_TO_R_SIZE         = 0xCB2,
//        GL_PIXEL_MAP_I_TO_G_SIZE         = 0xCB3,
//        GL_PIXEL_MAP_I_TO_B_SIZE         = 0xCB4,
//        GL_PIXEL_MAP_I_TO_A_SIZE         = 0xCB5,
//        GL_PIXEL_MAP_R_TO_R_SIZE         = 0xCB6,
//        GL_PIXEL_MAP_G_TO_G_SIZE         = 0xCB7,
//        GL_PIXEL_MAP_B_TO_B_SIZE         = 0xCB8,
//        GL_PIXEL_MAP_A_TO_A_SIZE         = 0xCB9,
//        GL_UNPACK_SWAP_BYTES             = 0xCF0,
//        GL_UNPACK_LSB_FIRST              = 0xCF1,
//        GL_UNPACK_ROW_LENGTH             = 0xCF2,
//        GL_UNPACK_SKIP_ROWS              = 0xCF3,
//        GL_UNPACK_SKIP_PIXELS            = 0xCF4,
        GL_UNPACK_ALIGNMENT              = 0xCF5,
//        GL_PACK_SWAP_BYTES               = 0xD00,
//        GL_PACK_LSB_FIRST                = 0xD01,
//        GL_PACK_ROW_LENGTH               = 0xD02,
//        GL_PACK_SKIP_ROWS                = 0xD03,
//        GL_PACK_SKIP_PIXELS              = 0xD04,
        GL_PACK_ALIGNMENT                = 0xD05,
//        GL_MAP_COLOR                     = 0xD10,
//        GL_MAP_STENCIL                   = 0xD11,
//        GL_INDEX_SHIFT                   = 0xD12,
//        GL_INDEX_OFFSET                  = 0xD13,
//        GL_RED_SCALE                     = 0xD14,
//        GL_RED_BIAS                      = 0xD15,
//        GL_ZOOM_X                        = 0xD16,
//        GL_ZOOM_Y                        = 0xD17,
//        GL_GREEN_SCALE                   = 0xD18,
//        GL_GREEN_BIAS                    = 0xD19,
//        GL_BLUE_SCALE                    = 0xD1A,
//        GL_BLUE_BIAS                     = 0xD1B,
//        GL_ALPHA_SCALE                   = 0xD1C,
//        GL_ALPHA_BIAS                    = 0xD1D,
//        GL_DEPTH_SCALE                   = 0xD1E,
//        GL_DEPTH_BIAS                    = 0xD1F,
//        GL_MAX_EVAL_ORDER                = 0xD30,
//        GL_MAX_LIGHTS                    = 0xD31,
//        GL_MAX_CLIP_PLANES               = 0xD32,
        GL_MAX_TEXTURE_SIZE              = 0xD33,
//        GL_MAX_PIXEL_MAP_TABLE           = 0xD34,
//        GL_MAX_ATTRIB_STACK_DEPTH        = 0xD35,
//        GL_MAX_MODELVIEW_STACK_DEPTH     = 0xD36,
//        GL_MAX_NAME_STACK_DEPTH          = 0xD37,
//        GL_MAX_PROJECTION_STACK_DEPTH    = 0xD38,
//        GL_MAX_TEXTURE_STACK_DEPTH       = 0xD39,
        GL_MAX_VIEWPORT_DIMS             = 0xD3A,
//        GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 0xD3B,
        GL_SUBPIXEL_BITS                 = 0xD50,
//        GL_INDEX_BITS                    = 0xD51,
        GL_RED_BITS                      = 0xD52,
        GL_GREEN_BITS                    = 0xD53,
        GL_BLUE_BITS                     = 0xD54,
        GL_ALPHA_BITS                    = 0xD55,
        GL_DEPTH_BITS                    = 0xD56,
        GL_STENCIL_BITS                  = 0xD57,
//        GL_ACCUM_RED_BITS                = 0xD58,
//        GL_ACCUM_GREEN_BITS              = 0xD59,
//        GL_ACCUM_BLUE_BITS               = 0xD5A,
//        GL_ACCUM_ALPHA_BITS              = 0xD5B,
//        GL_NAME_STACK_DEPTH              = 0xD70,
//        GL_AUTO_NORMAL                   = 0xD80,
//        GL_MAP1_COLOR_4                  = 0xD90,
//        GL_MAP1_INDEX                    = 0xD91,
//        GL_MAP1_NORMAL                   = 0xD92,
//        GL_MAP1_TEXTURE_COORD_1          = 0xD93,
//        GL_MAP1_TEXTURE_COORD_2          = 0xD94,
//        GL_MAP1_TEXTURE_COORD_3          = 0xD95,
//        GL_MAP1_TEXTURE_COORD_4          = 0xD96,
//        GL_MAP1_VERTEX_3                 = 0xD97,
//        GL_MAP1_VERTEX_4                 = 0xD98,
//        GL_MAP2_COLOR_4                  = 0xDB0,
//        GL_MAP2_INDEX                    = 0xDB1,
//        GL_MAP2_NORMAL                   = 0xDB2,
//        GL_MAP2_TEXTURE_COORD_1          = 0xDB3,
//        GL_MAP2_TEXTURE_COORD_2          = 0xDB4,
//        GL_MAP2_TEXTURE_COORD_3          = 0xDB5,
//        GL_MAP2_TEXTURE_COORD_4          = 0xDB6,
//        GL_MAP2_VERTEX_3                 = 0xDB7,
//        GL_MAP2_VERTEX_4                 = 0xDB8,
//        GL_MAP1_GRID_DOMAIN              = 0xDD0,
//        GL_MAP1_GRID_SEGMENTS            = 0xDD1,
//        GL_MAP2_GRID_DOMAIN              = 0xDD2,
//        GL_MAP2_GRID_SEGMENTS            = 0xDD3,
//        GL_TEXTURE_1D                    = 0xDE0,
        GL_TEXTURE_2D                    = 0xDE1;
//        GL_FEEDBACK_BUFFER_POINTER       = 0xDF0,
//        GL_FEEDBACK_BUFFER_SIZE          = 0xDF1,
//        GL_FEEDBACK_BUFFER_TYPE          = 0xDF2,
//        GL_SELECTION_BUFFER_POINTER      = 0xDF3,
//        GL_SELECTION_BUFFER_SIZE         = 0xDF4;

    /** GetTextureParameter *//*
    public static final int
        GL_TEXTURE_WIDTH           = 0x1000,
        GL_TEXTURE_HEIGHT          = 0x1001,
        GL_TEXTURE_INTERNAL_FORMAT = 0x1003,
        GL_TEXTURE_COMPONENTS      = 0x1003,
        GL_TEXTURE_BORDER_COLOR    = 0x1004,
        GL_TEXTURE_BORDER          = 0x1005;
	*/
    
    /** HintMode */
    public static final int
        GL_DONT_CARE = 0x1100,
        GL_FASTEST   = 0x1101,
        GL_NICEST    = 0x1102;

    /** LightName *//*
    public static final int
        GL_LIGHT0 = 0x4000,
        GL_LIGHT1 = 0x4001,
        GL_LIGHT2 = 0x4002,
        GL_LIGHT3 = 0x4003,
        GL_LIGHT4 = 0x4004,
        GL_LIGHT5 = 0x4005,
        GL_LIGHT6 = 0x4006,
        GL_LIGHT7 = 0x4007;
	*/
    
    /** LightParameter *//*
    public static final int
        GL_AMBIENT               = 0x1200,
        GL_DIFFUSE               = 0x1201,
        GL_SPECULAR              = 0x1202,
        GL_POSITION              = 0x1203,
        GL_SPOT_DIRECTION        = 0x1204,
        GL_SPOT_EXPONENT         = 0x1205,
        GL_SPOT_CUTOFF           = 0x1206,
        GL_CONSTANT_ATTENUATION  = 0x1207,
        GL_LINEAR_ATTENUATION    = 0x1208,
        GL_QUADRATIC_ATTENUATION = 0x1209;
	*/
    
    /** ListMode *//*
    public static final int
        GL_COMPILE             = 0x1300,
        GL_COMPILE_AND_EXECUTE = 0x1301;
	*/
    
    /** LogicOp */
    public static final int
//        GL_CLEAR         = 0x1500,
//        GL_AND           = 0x1501,
//        GL_AND_REVERSE   = 0x1502,
//        GL_COPY          = 0x1503,
//        GL_AND_INVERTED  = 0x1504,
//        GL_NOOP          = 0x1505,
//        GL_XOR           = 0x1506,
//        GL_OR            = 0x1507,
//        GL_NOR           = 0x1508,
//        GL_EQUIV         = 0x1509,
        GL_INVERT        = 0x150A;
//        GL_OR_REVERSE    = 0x150B,
//        GL_COPY_INVERTED = 0x150C,
//        GL_OR_INVERTED   = 0x150D,
//        GL_NAND          = 0x150E,
//        GL_SET           = 0x150F;

    /** MaterialParameter *//*
    public static final int
        GL_EMISSION            = 0x1600,
        GL_SHININESS           = 0x1601,
        GL_AMBIENT_AND_DIFFUSE = 0x1602,
        GL_COLOR_INDEXES       = 0x1603;
	*/
    
    /** MatrixMode *//*
    public static final int
        GL_MODELVIEW  = 0x1700,
        GL_PROJECTION = 0x1701,
        GL_TEXTURE    = 0x1702;
	*/
    
    /** PixelCopyType *//*
    public static final int
        GL_COLOR   = 0x1800,
        GL_DEPTH   = 0x1801,
        GL_STENCIL = 0x1802;
	*/
    
    /** PixelFormat */
    public static final int
//        GL_COLOR_INDEX     = 0x1900,
        GL_STENCIL_INDEX   = 0x1901,
        GL_DEPTH_COMPONENT = 0x1902,
//        GL_RED             = 0x1903,
//        GL_GREEN           = 0x1904,
//        GL_BLUE            = 0x1905,
        GL_ALPHA           = 0x1906,
        GL_RGB             = 0x1907,
        GL_RGBA            = 0x1908,
        GL_LUMINANCE       = 0x1909,
        GL_LUMINANCE_ALPHA = 0x190A;

    /** PixelType */
    //public static final int GL_BITMAP = 0x1A00;

    /** PolygonMode *//*
    public static final int
        GL_POINT = 0x1B00,
        GL_LINE  = 0x1B01,
        GL_FILL  = 0x1B02;
	*/
    
    /** RenderingMode *//*
    public static final int
        GL_RENDER   = 0x1C00,
        GL_FEEDBACK = 0x1C01,
        GL_SELECT   = 0x1C02;
	*/
    
    /** ShadingModel *//*
    public static final int
        GL_FLAT   = 0x1D00,
        GL_SMOOTH = 0x1D01;
	*/
    
    /** StencilOp */
    public static final int
        GL_KEEP    = 0x1E00,
        GL_REPLACE = 0x1E01,
        GL_INCR    = 0x1E02,
        GL_DECR    = 0x1E03;

    /** StringName */
    public static final int
        GL_VENDOR     = 0x1F00,
        GL_RENDERER   = 0x1F01,
        GL_VERSION    = 0x1F02,
        GL_EXTENSIONS = 0x1F03;

    /** TextureCoordName *//*
    public static final int
        GL_S = 0x2000,
        GL_T = 0x2001,
        GL_R = 0x2002,
        GL_Q = 0x2003;
	*/
    
    /** TextureEnvMode *//*
    public static final int
        GL_MODULATE = 0x2100,
        GL_DECAL    = 0x2101;
	*/
	
    /** TextureEnvParameter *//*
    public static final int
        GL_TEXTURE_ENV_MODE  = 0x2200,
        GL_TEXTURE_ENV_COLOR = 0x2201;
	*/
	
    /** TextureEnvTarget */
    //public static final int GL_TEXTURE_ENV = 0x2300;

    /** TextureGenMode *//*
    public static final int
        GL_EYE_LINEAR    = 0x2400,
        GL_OBJECT_LINEAR = 0x2401,
        GL_SPHERE_MAP    = 0x2402;
	*/
    
    /** TextureGenParameter *//*
    public static final int
        GL_TEXTURE_GEN_MODE = 0x2500,
        GL_OBJECT_PLANE     = 0x2501,
        GL_EYE_PLANE        = 0x2502;
	*/
    
    /** TextureMagFilter */
    public static final int
        GL_NEAREST = 0x2600,
        GL_LINEAR  = 0x2601;

    /** TextureMinFilter */
    public static final int
        GL_NEAREST_MIPMAP_NEAREST = 0x2700,
        GL_LINEAR_MIPMAP_NEAREST  = 0x2701,
        GL_NEAREST_MIPMAP_LINEAR  = 0x2702,
        GL_LINEAR_MIPMAP_LINEAR   = 0x2703;

    /** TextureParameterName */
    public static final int
        GL_TEXTURE_MAG_FILTER = 0x2800,
        GL_TEXTURE_MIN_FILTER = 0x2801,
        GL_TEXTURE_WRAP_S     = 0x2802,
        GL_TEXTURE_WRAP_T     = 0x2803;

    /** TextureWrapMode */
    public static final int
        //GL_CLAMP  = 0x2900,
        GL_REPEAT = 0x2901;

    /** ClientAttribMask */
    public static final int
        GL_CLIENT_PIXEL_STORE_BIT  = 0x1,
        GL_CLIENT_VERTEX_ARRAY_BIT = 0x2,
        GL_CLIENT_ALL_ATTRIB_BITS  = 0xFFFFFFFF;

    /** polygon_offset */
    public static final int
        GL_POLYGON_OFFSET_FACTOR = 0x8038,
        GL_POLYGON_OFFSET_UNITS  = 0x2A00,
//        GL_POLYGON_OFFSET_POINT  = 0x2A01,
//        GL_POLYGON_OFFSET_LINE   = 0x2A02,
        GL_POLYGON_OFFSET_FILL   = 0x8037;

    /** texture */
    public static final int
//        GL_ALPHA4                 = 0x803B,
//        GL_ALPHA8                 = 0x803C,
//        GL_ALPHA12                = 0x803D,
//        GL_ALPHA16                = 0x803E,
//        GL_LUMINANCE4             = 0x803F,
//        GL_LUMINANCE8             = 0x8040,
//        GL_LUMINANCE12            = 0x8041,
//        GL_LUMINANCE16            = 0x8042,
//        GL_LUMINANCE4_ALPHA4      = 0x8043,
//        GL_LUMINANCE6_ALPHA2      = 0x8044,
//        GL_LUMINANCE8_ALPHA8      = 0x8045,
//        GL_LUMINANCE12_ALPHA4     = 0x8046,
//        GL_LUMINANCE12_ALPHA12    = 0x8047,
//        GL_LUMINANCE16_ALPHA16    = 0x8048,
//        GL_INTENSITY              = 0x8049,
//        GL_INTENSITY4             = 0x804A,
//        GL_INTENSITY8             = 0x804B,
//        GL_INTENSITY12            = 0x804C,
//        GL_INTENSITY16            = 0x804D,
//        GL_R3_G3_B2               = 0x2A10,
//        GL_RGB4                   = 0x804F,
//        GL_RGB5                   = 0x8050,
//        GL_RGB8                   = 0x8051,
//        GL_RGB10                  = 0x8052,
//        GL_RGB12                  = 0x8053,
//        GL_RGB16                  = 0x8054,
//        GL_RGBA2                  = 0x8055,
        GL_RGBA4                  = 0x8056,
        GL_RGB5_A1                = 0x8057;
//        GL_RGBA8                  = 0x8058,
//        GL_RGB10_A2               = 0x8059,
//        GL_RGBA12                 = 0x805A,
//        GL_RGBA16                 = 0x805B,
//        GL_TEXTURE_RED_SIZE       = 0x805C,
//        GL_TEXTURE_GREEN_SIZE     = 0x805D,
//        GL_TEXTURE_BLUE_SIZE      = 0x805E,
//        GL_TEXTURE_ALPHA_SIZE     = 0x805F,
//        GL_TEXTURE_LUMINANCE_SIZE = 0x8060,
//        GL_TEXTURE_INTENSITY_SIZE = 0x8061,
//        GL_PROXY_TEXTURE_1D       = 0x8063,
//        GL_PROXY_TEXTURE_2D       = 0x8064;

    /** texture_object */
    public static final int
//        GL_TEXTURE_PRIORITY   = 0x8066,
//        GL_TEXTURE_RESIDENT   = 0x8067,
//        GL_TEXTURE_BINDING_1D = 0x8068,
        GL_TEXTURE_BINDING_2D = 0x8069;

    /** vertex_array *//*
    public static final int
        GL_VERTEX_ARRAY                = 0x8074,
        GL_NORMAL_ARRAY                = 0x8075,
        GL_COLOR_ARRAY                 = 0x8076,
        GL_INDEX_ARRAY                 = 0x8077,
        GL_TEXTURE_COORD_ARRAY         = 0x8078,
        GL_EDGE_FLAG_ARRAY             = 0x8079,
        GL_VERTEX_ARRAY_SIZE           = 0x807A,
        GL_VERTEX_ARRAY_TYPE           = 0x807B,
        GL_VERTEX_ARRAY_STRIDE         = 0x807C,
        GL_NORMAL_ARRAY_TYPE           = 0x807E,
        GL_NORMAL_ARRAY_STRIDE         = 0x807F,
        GL_COLOR_ARRAY_SIZE            = 0x8081,
        GL_COLOR_ARRAY_TYPE            = 0x8082,
        GL_COLOR_ARRAY_STRIDE          = 0x8083,
        GL_INDEX_ARRAY_TYPE            = 0x8085,
        GL_INDEX_ARRAY_STRIDE          = 0x8086,
        GL_TEXTURE_COORD_ARRAY_SIZE    = 0x8088,
        GL_TEXTURE_COORD_ARRAY_TYPE    = 0x8089,
        GL_TEXTURE_COORD_ARRAY_STRIDE  = 0x808A,
        GL_EDGE_FLAG_ARRAY_STRIDE      = 0x808C,
        GL_VERTEX_ARRAY_POINTER        = 0x808E,
        GL_NORMAL_ARRAY_POINTER        = 0x808F,
        GL_COLOR_ARRAY_POINTER         = 0x8090,
        GL_INDEX_ARRAY_POINTER         = 0x8091,
        GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092,
        GL_EDGE_FLAG_ARRAY_POINTER     = 0x8093,
        GL_V2F                         = 0x2A20,
        GL_V3F                         = 0x2A21,
        GL_C4UB_V2F                    = 0x2A22,
        GL_C4UB_V3F                    = 0x2A23,
        GL_C3F_V3F                     = 0x2A24,
        GL_N3F_V3F                     = 0x2A25,
        GL_C4F_N3F_V3F                 = 0x2A26,
        GL_T2F_V3F                     = 0x2A27,
        GL_T4F_V4F                     = 0x2A28,
        GL_T2F_C4UB_V3F                = 0x2A29,
        GL_T2F_C3F_V3F                 = 0x2A2A,
        GL_T2F_N3F_V3F                 = 0x2A2B,
        GL_T2F_C4F_N3F_V3F             = 0x2A2C,
        GL_T4F_C4F_N3F_V4F             = 0x2A2D;
	*/
    
    
    // --- [ glEnable ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glEnable">Reference Page</a></p>
     * 
     * Enables the specified OpenGL state.
     *
     * @param target the OpenGL state to enable
     */
    public void glEnable(int target);

    // --- [ glDisable ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDisable">Reference Page</a></p>
     * 
     * Disables the specified OpenGL state.
     *
     * @param target the OpenGL state to disable
     */
    public void glDisable(int target);

    // --- [ glArrayElement ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glArrayElement">Reference Page</a></p>
     * 
     * Transfers the ith element of every enabled, non-instanced array, and the first element of every enabled, instanced array to the GL.
     *
     * @param i the element to transfer
     */
    //public void glArrayElement(int i);

    // --- [ glBindTexture ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glBindTexture">Reference Page</a></p>
     * 
     * Binds the a texture to a texture target.
     * 
     * <p>While a texture object is bound, GL operations on the target to which it is bound affect the bound object, and queries of the target to which it is
     * bound return state from the bound object. If texture mapping of the dimensionality of the target to which a texture object is bound is enabled, the
     * state of the bound texture object directs the texturing operation.</p>
     *
     * @param target  the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL40#GL_TEXTURE_CUBE_MAP_ARRAY TEXTURE_CUBE_MAP_ARRAY}</td><td>{@link GL31#GL_TEXTURE_BUFFER TEXTURE_BUFFER}</td><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE TEXTURE_2D_MULTISAMPLE}</td></tr><tr><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE_ARRAY TEXTURE_2D_MULTISAMPLE_ARRAY}</td></tr></table>
     * @param texture the texture object to bind
     */
    public void glBindTexture(int target, int texture);

    // --- [ glBlendFunc ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glBlendFunc">Reference Page</a></p>
     * 
     * Specifies the weighting factors used by the blend equation, for both RGB and alpha functions and for all draw buffers.
     *
     * @param sfactor the source weighting factor. One of:<br><table><tr><td>{@link #GL_ZERO ZERO}</td><td>{@link #GL_ONE ONE}</td><td>{@link #GL_SRC_COLOR SRC_COLOR}</td><td>{@link #GL_ONE_MINUS_SRC_COLOR ONE_MINUS_SRC_COLOR}</td><td>{@link #GL_DST_COLOR DST_COLOR}</td></tr><tr><td>{@link #GL_ONE_MINUS_DST_COLOR ONE_MINUS_DST_COLOR}</td><td>{@link #GL_SRC_ALPHA SRC_ALPHA}</td><td>{@link #GL_ONE_MINUS_SRC_ALPHA ONE_MINUS_SRC_ALPHA}</td><td>{@link #GL_DST_ALPHA DST_ALPHA}</td><td>{@link #GL_ONE_MINUS_DST_ALPHA ONE_MINUS_DST_ALPHA}</td></tr><tr><td>{@link GL14#GL_CONSTANT_COLOR CONSTANT_COLOR}</td><td>{@link GL14#GL_ONE_MINUS_CONSTANT_COLOR ONE_MINUS_CONSTANT_COLOR}</td><td>{@link GL14#GL_CONSTANT_ALPHA CONSTANT_ALPHA}</td><td>{@link GL14#GL_ONE_MINUS_CONSTANT_ALPHA ONE_MINUS_CONSTANT_ALPHA}</td><td>{@link #GL_SRC_ALPHA_SATURATE SRC_ALPHA_SATURATE}</td></tr><tr><td>{@link GL33#GL_SRC1_COLOR SRC1_COLOR}</td><td>{@link GL33#GL_ONE_MINUS_SRC1_COLOR ONE_MINUS_SRC1_COLOR}</td><td>{@link GL15#GL_SRC1_ALPHA SRC1_ALPHA}</td><td>{@link GL33#GL_ONE_MINUS_SRC1_ALPHA ONE_MINUS_SRC1_ALPHA}</td></tr></table>
     * @param dfactor the destination weighting factor
     */
    public void glBlendFunc(int sfactor, int dfactor);

    // --- [ glClear ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glClear">Reference Page</a></p>
     * 
     * Sets portions of every pixel in a particular buffer to the same value. The value to which each buffer is cleared depends on the setting of the clear
     * value for that buffer.
     *
     * @param mask Zero or the bitwise OR of one or more values indicating which buffers are to be cleared. One or more of:<br><table><tr><td>{@link #GL_ACCUM_BUFFER_BIT ACCUM_BUFFER_BIT}</td><td>{@link #GL_COLOR_BUFFER_BIT COLOR_BUFFER_BIT}</td><td>{@link #GL_DEPTH_BUFFER_BIT DEPTH_BUFFER_BIT}</td><td>{@link #GL_STENCIL_BUFFER_BIT STENCIL_BUFFER_BIT}</td></tr></table>
     */
    public void glClear(int mask);

    // --- [ glClearColor ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glClearColor">Reference Page</a></p>
     * 
     * Sets the clear value for fixed-point and floating-point color buffers in RGBA mode. The specified components are stored as floating-point values.
     *
     * @param red   the value to which to clear the R channel of the color buffer
     * @param green the value to which to clear the G channel of the color buffer
     * @param blue  the value to which to clear the B channel of the color buffer
     * @param alpha the value to which to clear the A channel of the color buffer
     */
    public void glClearColor(float red, float green, float blue, float alpha);

    // --- [ glClearDepth ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glClearDepth">Reference Page</a></p>
     * 
     * Sets the depth value used when clearing the depth buffer. When clearing a fixedpoint depth buffer, {@code depth} is clamped to the range [0,1] and
     * converted to fixed-point. No conversion is applied when clearing a floating-point depth buffer.
     *
     * @param depth the value to which to clear the depth buffer
     */
    public void glClearDepth(double depth);

    // --- [ glClearStencil ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glClearStencil">Reference Page</a></p>
     * 
     * Sets the value to which to clear the stencil buffer. {@code s} is masked to the number of bitplanes in the stencil buffer.
     *
     * @param s the value to which to clear the stencil buffer
     */
    public void glClearStencil(int s);

    // --- [ glClipPlane ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glClipPlane">Reference Page</a></p>
     * 
     * Specifies a client-defined clip plane.
     * 
     * <p>The value of the first argument, {@code plane}, is a symbolic constant, CLIP_PLANEi, where i is an integer between 0 and n &ndash; 1, indicating one of
     * n client-defined clip planes. {@code equation} is an array of four double-precision floating-point values. These are the coefficients of a plane
     * equation in object coordinates: p1, p2, p3, and p4 (in that order).</p>
     *
     * @param plane    the clip plane to define
     * @param equation the clip plane coefficients
     */
    //public void glClipPlane(int plane, DoubleBuffer equation);

    // --- [ glColorMask ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glColorMask">Reference Page</a></p>
     * 
     * Masks the writing of R, G, B and A values to all draw buffers. In the initial state, all color values are enabled for writing for all draw buffers.
     *
     * @param red   whether R values are written or not
     * @param green whether G values are written or not
     * @param blue  whether B values are written or not
     * @param alpha whether A values are written or not
     */
    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha);

    // --- [ glCopyPixels ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glCopyPixels">Reference Page</a></p>
     * 
     * Transfers a rectangle of pixel values from one region of the read framebuffer to another in the draw framebuffer
     *
     * @param x      the left framebuffer pixel coordinate
     * @param y      the lower framebuffer pixel coordinate
     * @param width  the rectangle width
     * @param height the rectangle height
     * @param type   Indicates the type of values to be transfered. One of:<br><table><tr><td>{@link #GL_COLOR COLOR}</td><td>{@link #GL_STENCIL STENCIL}</td><td>{@link #GL_DEPTH DEPTH}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td></tr></table>
     */
    //public void glCopyPixels(int x, int y, int width, int height, int type);

    // --- [ glCullFace ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glCullFace">Reference Page</a></p>
     * 
     * Specifies which polygon faces are culled if {@link #GL_CULL_FACE CULL_FACE} is enabled. Front-facing polygons are rasterized if either culling is disabled or the
     * CullFace mode is {@link #GL_BACK BACK} while back-facing polygons are rasterized only if either culling is disabled or the CullFace mode is
     * {@link #GL_FRONT FRONT}. The initial setting of the CullFace mode is {@link #GL_BACK BACK}. Initially, culling is disabled.
     *
     * @param mode the CullFace mode. One of:<br><table><tr><td>{@link #GL_FRONT FRONT}</td><td>{@link #GL_BACK BACK}</td><td>{@link #GL_FRONT_AND_BACK FRONT_AND_BACK}</td></tr></table>
     */
    public void glCullFace(int mode);

    // --- [ glDepthFunc ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDepthFunc">Reference Page</a></p>
     * 
     * Specifies the comparison that takes place during the depth buffer test (when {@link #GL_DEPTH_TEST DEPTH_TEST} is enabled).
     *
     * @param func the depth test comparison. One of:<br><table><tr><td>{@link #GL_NEVER NEVER}</td><td>{@link #GL_ALWAYS ALWAYS}</td><td>{@link #GL_LESS LESS}</td><td>{@link #GL_LEQUAL LEQUAL}</td><td>{@link #GL_EQUAL EQUAL}</td><td>{@link #GL_GREATER GREATER}</td><td>{@link #GL_GEQUAL GEQUAL}</td><td>{@link #GL_NOTEQUAL NOTEQUAL}</td></tr></table>
     */
    public void glDepthFunc(int func);

    // --- [ glDepthMask ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDepthMask">Reference Page</a></p>
     * 
     * Masks the writing of depth values to the depth buffer. In the initial state, the depth buffer is enabled for writing.
     *
     * @param flag whether depth values are written or not.
     */
    public void glDepthMask(boolean flag);

    // --- [ glDepthRange ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDepthRange">Reference Page</a></p>
     * 
     * Sets the depth range for all viewports to the same values.
     *
     * @param zNear the near depth range
     * @param zFar  the far depth range
     */
    public void glDepthRange(double zNear, double zFar);

    // --- [ glDrawArrays ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDrawArrays">Reference Page</a></p>
     * 
     * Constructs a sequence of geometric primitives by successively transferring elements for {@code count} vertices. Elements {@code first} through
     * <code>first + count &ndash; 1</code> of each enabled non-instanced array are transferred to the GL.
     * 
     * <p>If an array corresponding to an attribute required by a vertex shader is not enabled, then the corresponding element is taken from the current attribute
     * state. If an array is enabled, the corresponding current vertex attribute value is unaffected by the execution of this function.</p>
     *
     * @param mode  the kind of primitives being constructed
     * @param first the first vertex to transfer to the GL
     * @param count the number of vertices after {@code first} to transfer to the GL
     */
    public void glDrawArrays(int mode, int first, int count);

    // --- [ glDrawBuffer ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDrawBuffer">Reference Page</a></p>
     * 
     * Defines the color buffer to which fragment color zero is written.
     * 
     * <p>Acceptable values for {@code buf} depend on whether the GL is using the default framebuffer (i.e., {@link GL30#GL_DRAW_FRAMEBUFFER_BINDING DRAW_FRAMEBUFFER_BINDING} is zero), or
     * a framebuffer object (i.e., {@link GL30#GL_DRAW_FRAMEBUFFER_BINDING DRAW_FRAMEBUFFER_BINDING} is non-zero). In the initial state, the GL is bound to the default framebuffer.</p>
     *
     * @param buf the color buffer to draw to. One of:<br><table><tr><td>{@link #GL_NONE NONE}</td><td>{@link #GL_FRONT_LEFT FRONT_LEFT}</td><td>{@link #GL_FRONT_RIGHT FRONT_RIGHT}</td><td>{@link #GL_BACK_LEFT BACK_LEFT}</td><td>{@link #GL_BACK_RIGHT BACK_RIGHT}</td><td>{@link #GL_FRONT FRONT}</td><td>{@link #GL_BACK BACK}</td><td>{@link #GL_LEFT LEFT}</td></tr><tr><td>{@link #GL_RIGHT RIGHT}</td><td>{@link #GL_FRONT_AND_BACK FRONT_AND_BACK}</td><td>{@link #GL_AUX0 AUX0}</td><td>{@link #GL_AUX1 AUX1}</td><td>{@link #GL_AUX2 AUX2}</td><td>{@link #GL_AUX3 AUX3}</td><td>{@link GL30#GL_COLOR_ATTACHMENT0 COLOR_ATTACHMENT0}</td><td>GL30.GL_COLOR_ATTACHMENT[1-15]</td></tr></table>
     */
    //public void glDrawBuffer(int buf);

    // --- [ glDrawElements ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDrawElements">Reference Page</a></p>
     * 
     * Constructs a sequence of geometric primitives by successively transferring elements for {@code count} vertices to the GL.
     * The i<sup>th</sup> element transferred by {@code DrawElements} will be taken from element {@code indices[i]} (if no element array buffer is bound), or
     * from the element whose index is stored in the currently bound element array buffer at offset {@code indices + i}.
     *
     * @param mode    the kind of primitives being constructed. One of:<br><table><tr><td>{@link #GL_POINTS POINTS}</td><td>{@link #GL_LINE_STRIP LINE_STRIP}</td><td>{@link #GL_LINE_LOOP LINE_LOOP}</td><td>{@link #GL_LINES LINES}</td><td>{@link #GL_POLYGON POLYGON}</td><td>{@link #GL_TRIANGLE_STRIP TRIANGLE_STRIP}</td><td>{@link #GL_TRIANGLE_FAN TRIANGLE_FAN}</td></tr><tr><td>{@link #GL_TRIANGLES TRIANGLES}</td><td>{@link #GL_QUAD_STRIP QUAD_STRIP}</td><td>{@link #GL_QUADS QUADS}</td><td>{@link GL32#GL_LINES_ADJACENCY LINES_ADJACENCY}</td><td>{@link GL32#GL_LINE_STRIP_ADJACENCY LINE_STRIP_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLES_ADJACENCY TRIANGLES_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLE_STRIP_ADJACENCY TRIANGLE_STRIP_ADJACENCY}</td></tr><tr><td>{@link GL40#GL_PATCHES PATCHES}</td></tr></table>
     * @param count   the number of vertices to transfer to the GL
     * @param type    indicates the type of index values in {@code indices}. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td></tr></table>
     * @param indices the index values
     */
    //public void glDrawElements(int mode, int count, int type, long indices);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDrawElements">Reference Page</a></p>
     * 
     * Constructs a sequence of geometric primitives by successively transferring elements for {@code count} vertices to the GL.
     * The i<sup>th</sup> element transferred by {@code DrawElements} will be taken from element {@code indices[i]} (if no element array buffer is bound), or
     * from the element whose index is stored in the currently bound element array buffer at offset {@code indices + i}.
     *
     * @param mode    the kind of primitives being constructed. One of:<br><table><tr><td>{@link #GL_POINTS POINTS}</td><td>{@link #GL_LINE_STRIP LINE_STRIP}</td><td>{@link #GL_LINE_LOOP LINE_LOOP}</td><td>{@link #GL_LINES LINES}</td><td>{@link #GL_POLYGON POLYGON}</td><td>{@link #GL_TRIANGLE_STRIP TRIANGLE_STRIP}</td><td>{@link #GL_TRIANGLE_FAN TRIANGLE_FAN}</td></tr><tr><td>{@link #GL_TRIANGLES TRIANGLES}</td><td>{@link #GL_QUAD_STRIP QUAD_STRIP}</td><td>{@link #GL_QUADS QUADS}</td><td>{@link GL32#GL_LINES_ADJACENCY LINES_ADJACENCY}</td><td>{@link GL32#GL_LINE_STRIP_ADJACENCY LINE_STRIP_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLES_ADJACENCY TRIANGLES_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLE_STRIP_ADJACENCY TRIANGLE_STRIP_ADJACENCY}</td></tr><tr><td>{@link GL40#GL_PATCHES PATCHES}</td></tr></table>
     * @param type    indicates the type of index values in {@code indices}. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td></tr></table>
     * @param indices the index values
     */
    public void glDrawElements(int mode, int type, ByteBuffer indices);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDrawElements">Reference Page</a></p>
     * 
     * Constructs a sequence of geometric primitives by successively transferring elements for {@code count} vertices to the GL.
     * The i<sup>th</sup> element transferred by {@code DrawElements} will be taken from element {@code indices[i]} (if no element array buffer is bound), or
     * from the element whose index is stored in the currently bound element array buffer at offset {@code indices + i}.
     *
     * @param mode    the kind of primitives being constructed. One of:<br><table><tr><td>{@link #GL_POINTS POINTS}</td><td>{@link #GL_LINE_STRIP LINE_STRIP}</td><td>{@link #GL_LINE_LOOP LINE_LOOP}</td><td>{@link #GL_LINES LINES}</td><td>{@link #GL_POLYGON POLYGON}</td><td>{@link #GL_TRIANGLE_STRIP TRIANGLE_STRIP}</td><td>{@link #GL_TRIANGLE_FAN TRIANGLE_FAN}</td></tr><tr><td>{@link #GL_TRIANGLES TRIANGLES}</td><td>{@link #GL_QUAD_STRIP QUAD_STRIP}</td><td>{@link #GL_QUADS QUADS}</td><td>{@link GL32#GL_LINES_ADJACENCY LINES_ADJACENCY}</td><td>{@link GL32#GL_LINE_STRIP_ADJACENCY LINE_STRIP_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLES_ADJACENCY TRIANGLES_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLE_STRIP_ADJACENCY TRIANGLE_STRIP_ADJACENCY}</td></tr><tr><td>{@link GL40#GL_PATCHES PATCHES}</td></tr></table>
     * @param indices the index values
     */
    public void glDrawElements(int mode, ByteBuffer indices);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDrawElements">Reference Page</a></p>
     * 
     * Constructs a sequence of geometric primitives by successively transferring elements for {@code count} vertices to the GL.
     * The i<sup>th</sup> element transferred by {@code DrawElements} will be taken from element {@code indices[i]} (if no element array buffer is bound), or
     * from the element whose index is stored in the currently bound element array buffer at offset {@code indices + i}.
     *
     * @param mode    the kind of primitives being constructed. One of:<br><table><tr><td>{@link #GL_POINTS POINTS}</td><td>{@link #GL_LINE_STRIP LINE_STRIP}</td><td>{@link #GL_LINE_LOOP LINE_LOOP}</td><td>{@link #GL_LINES LINES}</td><td>{@link #GL_POLYGON POLYGON}</td><td>{@link #GL_TRIANGLE_STRIP TRIANGLE_STRIP}</td><td>{@link #GL_TRIANGLE_FAN TRIANGLE_FAN}</td></tr><tr><td>{@link #GL_TRIANGLES TRIANGLES}</td><td>{@link #GL_QUAD_STRIP QUAD_STRIP}</td><td>{@link #GL_QUADS QUADS}</td><td>{@link GL32#GL_LINES_ADJACENCY LINES_ADJACENCY}</td><td>{@link GL32#GL_LINE_STRIP_ADJACENCY LINE_STRIP_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLES_ADJACENCY TRIANGLES_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLE_STRIP_ADJACENCY TRIANGLE_STRIP_ADJACENCY}</td></tr><tr><td>{@link GL40#GL_PATCHES PATCHES}</td></tr></table>
     * @param indices the index values
     */
    public void glDrawElements(int mode, ShortBuffer indices);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDrawElements">Reference Page</a></p>
     * 
     * Constructs a sequence of geometric primitives by successively transferring elements for {@code count} vertices to the GL.
     * The i<sup>th</sup> element transferred by {@code DrawElements} will be taken from element {@code indices[i]} (if no element array buffer is bound), or
     * from the element whose index is stored in the currently bound element array buffer at offset {@code indices + i}.
     *
     * @param mode    the kind of primitives being constructed. One of:<br><table><tr><td>{@link #GL_POINTS POINTS}</td><td>{@link #GL_LINE_STRIP LINE_STRIP}</td><td>{@link #GL_LINE_LOOP LINE_LOOP}</td><td>{@link #GL_LINES LINES}</td><td>{@link #GL_POLYGON POLYGON}</td><td>{@link #GL_TRIANGLE_STRIP TRIANGLE_STRIP}</td><td>{@link #GL_TRIANGLE_FAN TRIANGLE_FAN}</td></tr><tr><td>{@link #GL_TRIANGLES TRIANGLES}</td><td>{@link #GL_QUAD_STRIP QUAD_STRIP}</td><td>{@link #GL_QUADS QUADS}</td><td>{@link GL32#GL_LINES_ADJACENCY LINES_ADJACENCY}</td><td>{@link GL32#GL_LINE_STRIP_ADJACENCY LINE_STRIP_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLES_ADJACENCY TRIANGLES_ADJACENCY}</td><td>{@link GL32#GL_TRIANGLE_STRIP_ADJACENCY TRIANGLE_STRIP_ADJACENCY}</td></tr><tr><td>{@link GL40#GL_PATCHES PATCHES}</td></tr></table>
     * @param indices the index values
     */
    public void glDrawElements(int mode, IntBuffer indices);

    // --- [ glFinish ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glFinish">Reference Page</a></p>
     * 
     * Forces all previously issued GL commands to complete. {@code Finish} does not return until all effects from such commands on GL client and server
     * state and the framebuffer are fully realized.
     */
    public void glFinish();

    // --- [ glFlush ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glFlush">Reference Page</a></p>
     * 
     * Causes all previously issued GL commands to complete in finite time (although such commands may still be executing when {@code Flush} returns).
     */
    public void glFlush();

    // --- [ glFrontFace ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glFrontFace">Reference Page</a></p>
     * 
     * The first step of polygon rasterization is to determine if the polygon is back-facing or front-facing. This determination is made based on the sign of
     * the (clipped or unclipped) polygon's area computed in window coordinates. The interpretation of the sign of this value is controlled with this function.
     * In the initial state, the front face direction is set to {@link #GL_CCW CCW}.
     *
     * @param dir the front face direction. One of:<br><table><tr><td>{@link #GL_CCW CCW}</td><td>{@link #GL_CW CW}</td></tr></table>
     */
    public void glFrontFace(int dir);

    // --- [ glGenTextures ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGenTextures">Reference Page</a></p>
     * 
     * Returns n previously unused texture names in textures. These names are marked as used, for the purposes of GenTextures only, but they acquire texture
     * state and a dimensionality only when they are first bound, just as if they were unused.
     *
     * @param textures a scalar or buffer in which to place the returned texture names
     */
    public void glGenTextures(IntBuffer textures);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGenTextures">Reference Page</a></p>
     * 
     * Returns n previously unused texture names in textures. These names are marked as used, for the purposes of GenTextures only, but they acquire texture
     * state and a dimensionality only when they are first bound, just as if they were unused.
     */
    public int glGenTextures();

    // --- [ glDeleteTextures ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDeleteTextures">Reference Page</a></p>
     * 
     * Deletes texture objects. After a texture object is deleted, it has no contents or dimensionality, and its name is again unused. If a texture that is
     * currently bound to any of the target bindings of {@link #glBindTexture BindTexture} is deleted, it is as though {@link #glBindTexture BindTexture} had been executed with the
     * same target and texture zero. Additionally, special care must be taken when deleting a texture if any of the images of the texture are attached to a
     * framebuffer object.
     * 
     * <p>Unused names in textures that have been marked as used for the purposes of {@link #glGenTextures GenTextures} are marked as unused again. Unused names in textures are
     * silently ignored, as is the name zero.</p>
     *
     * @param textures contains {@code n} names of texture objects to be deleted
     */
    public void glDeleteTextures(IntBuffer textures);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDeleteTextures">Reference Page</a></p>
     * 
     * Deletes texture objects. After a texture object is deleted, it has no contents or dimensionality, and its name is again unused. If a texture that is
     * currently bound to any of the target bindings of {@link #glBindTexture BindTexture} is deleted, it is as though {@link #glBindTexture BindTexture} had been executed with the
     * same target and texture zero. Additionally, special care must be taken when deleting a texture if any of the images of the texture are attached to a
     * framebuffer object.
     * 
     * <p>Unused names in textures that have been marked as used for the purposes of {@link #glGenTextures GenTextures} are marked as unused again. Unused names in textures are
     * silently ignored, as is the name zero.</p>
     */
    public void glDeleteTextures(int texture);

    // --- [ glGetClipPlane ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetClipPlane">Reference Page</a></p>
     * 
     * Returns four double-precision values in {@code equation}; these are the coefficients of the plane equation of plane in eye coordinates (these
     * coordinates are those that were computed when the plane was specified).
     *
     * @param plane    the clip plane
     * @param equation a buffer in which to place the returned values
     */
    //public void glGetClipPlane(int plane, DoubleBuffer equation);

    // --- [ glGetBooleanv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetBooleanv">Reference Page</a></p>
     * 
     * Returns the current boolean value of the specified state variable.
     * 
     * <p><b>LWJGL note</b>: The state that corresponds to the state variable may be a single value or an array of values. In the case of an array of values,
     * LWJGL will <b>not</b> validate if {@code params} has enough space to store that array. Doing so would introduce significant overhead, as the
     * OpenGL state variables are too many. It is the user's responsibility to avoid JVM crashes by ensuring enough space for the returned values.</p>
     *
     * @param pname  the state variable
     * @param params a scalar or buffer in which to place the returned data
     */
    //public void glGetBooleanv(int pname, ByteBuffer params); // TODO: Incompatible with Android - Needs IntBuffer

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetBooleanv">Reference Page</a></p>
     * 
     * Returns the current boolean value of the specified state variable.
     * 
     * <p><b>LWJGL note</b>: The state that corresponds to the state variable may be a single value or an array of values. In the case of an array of values,
     * LWJGL will <b>not</b> validate if {@code params} has enough space to store that array. Doing so would introduce significant overhead, as the
     * OpenGL state variables are too many. It is the user's responsibility to avoid JVM crashes by ensuring enough space for the returned values.</p>
     *
     * @param pname the state variable
     */
    public boolean glGetBoolean(int pname);

    // --- [ glGetFloatv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetFloatv">Reference Page</a></p>
     * 
     * Returns the current float value of the specified state variable.
     * 
     * <p><b>LWJGL note</b>: The state that corresponds to the state variable may be a single value or an array of values. In the case of an array of values,
     * LWJGL will <b>not</b> validate if {@code params} has enough space to store that array. Doing so would introduce significant overhead, as the
     * OpenGL state variables are too many. It is the user's responsibility to avoid JVM crashes by ensuring enough space for the returned values.</p>
     *
     * @param pname  the state variable
     * @param params a scalar or buffer in which to place the returned data
     */
    public void glGetFloatv(int pname, FloatBuffer params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetFloatv">Reference Page</a></p>
     * 
     * Returns the current float value of the specified state variable.
     * 
     * <p><b>LWJGL note</b>: The state that corresponds to the state variable may be a single value or an array of values. In the case of an array of values,
     * LWJGL will <b>not</b> validate if {@code params} has enough space to store that array. Doing so would introduce significant overhead, as the
     * OpenGL state variables are too many. It is the user's responsibility to avoid JVM crashes by ensuring enough space for the returned values.</p>
     *
     * @param pname the state variable
     */
    public float glGetFloat(int pname);

    // --- [ glGetIntegerv ] ---
    
    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetIntegerv">Reference Page</a></p>
     * 
     * Returns the current integer value of the specified state variable.
     * 
     * <p><b>LWJGL note</b>: The state that corresponds to the state variable may be a single value or an array of values. In the case of an array of values,
     * LWJGL will <b>not</b> validate if {@code params} has enough space to store that array. Doing so would introduce significant overhead, as the
     * OpenGL state variables are too many. It is the user's responsibility to avoid JVM crashes by ensuring enough space for the returned values.</p>
     *
     * @param pname  the state variable
     * @param params a scalar or buffer in which to place the returned data
     */
    public void glGetIntegerv(int pname, IntBuffer params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetIntegerv">Reference Page</a></p>
     * 
     * Returns the current integer value of the specified state variable.
     * 
     * <p><b>LWJGL note</b>: The state that corresponds to the state variable may be a single value or an array of values. In the case of an array of values,
     * LWJGL will <b>not</b> validate if {@code params} has enough space to store that array. Doing so would introduce significant overhead, as the
     * OpenGL state variables are too many. It is the user's responsibility to avoid JVM crashes by ensuring enough space for the returned values.</p>
     *
     * @param pname the state variable
     */
    public int glGetInteger(int pname);

    // --- [ glGetDoublev ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetDoublev">Reference Page</a></p>
     * 
     * Returns the current double value of the specified state variable.
     * 
     * <p><b>LWJGL note</b>: The state that corresponds to the state variable may be a single value or an array of values. In the case of an array of values,
     * LWJGL will <b>not</b> validate if {@code params} has enough space to store that array. Doing so would introduce significant overhead, as the
     * OpenGL state variables are too many. It is the user's responsibility to avoid JVM crashes by ensuring enough space for the returned values.</p>
     *
     * @param pname  the state variable
     * @param params a scalar or buffer in which to place the returned data
     */
    //public void glGetDoublev(int pname, DoubleBuffer params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetDoublev">Reference Page</a></p>
     * 
     * Returns the current double value of the specified state variable.
     * 
     * <p><b>LWJGL note</b>: The state that corresponds to the state variable may be a single value or an array of values. In the case of an array of values,
     * LWJGL will <b>not</b> validate if {@code params} has enough space to store that array. Doing so would introduce significant overhead, as the
     * OpenGL state variables are too many. It is the user's responsibility to avoid JVM crashes by ensuring enough space for the returned values.</p>
     *
     * @param pname the state variable
     */
    //public double glGetDouble(int pname);

    // --- [ glGetError ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetError">Reference Page</a></p>
     * 
     * Returns error information. Each detectable error is assigned a numeric code. When an error is detected, a flag is set and the code is recorded. Further
     * errors, if they occur, do not affect this recorded code. When {@code GetError} is called, the code is returned and the flag is cleared, so that a
     * further error will again record its code. If a call to {@code GetError} returns {@link #GL_NO_ERROR NO_ERROR}, then there has been no detectable error since
     * the last call to {@code GetError} (or since the GL was initialized).
     */
    public int glGetError();

    // --- [ glGetPointerv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetPointerv">Reference Page</a></p>
     * 
     * Returns a pointer in the current GL context.
     *
     * @param pname  the pointer to return. One of:<br><table><tr><td>{@link #GL_FEEDBACK_BUFFER_POINTER FEEDBACK_BUFFER_POINTER}</td><td>{@link #GL_SELECTION_BUFFER_POINTER SELECTION_BUFFER_POINTER}</td><td>{@link #GL_VERTEX_ARRAY_POINTER VERTEX_ARRAY_POINTER}</td></tr><tr><td>{@link #GL_NORMAL_ARRAY_POINTER NORMAL_ARRAY_POINTER}</td><td>{@link #GL_COLOR_ARRAY_POINTER COLOR_ARRAY_POINTER}</td><td>{@link #GL_INDEX_ARRAY_POINTER INDEX_ARRAY_POINTER}</td></tr><tr><td>{@link #GL_TEXTURE_COORD_ARRAY_POINTER TEXTURE_COORD_ARRAY_POINTER}</td><td>{@link #GL_EDGE_FLAG_ARRAY_POINTER EDGE_FLAG_ARRAY_POINTER}</td><td>{@link GL14#GL_SECONDARY_COLOR_ARRAY_POINTER SECONDARY_COLOR_ARRAY_POINTER}</td></tr><tr><td>{@link GL15#GL_FOG_COORD_ARRAY_POINTER FOG_COORD_ARRAY_POINTER}</td><td>{@link GL43#GL_DEBUG_CALLBACK_FUNCTION DEBUG_CALLBACK_FUNCTION}</td><td>{@link GL43#GL_DEBUG_CALLBACK_USER_PARAM DEBUG_CALLBACK_USER_PARAM}</td></tr></table>
     * @param params a buffer in which to place the returned pointer
     */
    //public void glGetPointerv(int pname, PointerBuffer params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetPointerv">Reference Page</a></p>
     * 
     * Returns a pointer in the current GL context.
     *
     * @param pname the pointer to return. One of:<br><table><tr><td>{@link #GL_FEEDBACK_BUFFER_POINTER FEEDBACK_BUFFER_POINTER}</td><td>{@link #GL_SELECTION_BUFFER_POINTER SELECTION_BUFFER_POINTER}</td><td>{@link #GL_VERTEX_ARRAY_POINTER VERTEX_ARRAY_POINTER}</td></tr><tr><td>{@link #GL_NORMAL_ARRAY_POINTER NORMAL_ARRAY_POINTER}</td><td>{@link #GL_COLOR_ARRAY_POINTER COLOR_ARRAY_POINTER}</td><td>{@link #GL_INDEX_ARRAY_POINTER INDEX_ARRAY_POINTER}</td></tr><tr><td>{@link #GL_TEXTURE_COORD_ARRAY_POINTER TEXTURE_COORD_ARRAY_POINTER}</td><td>{@link #GL_EDGE_FLAG_ARRAY_POINTER EDGE_FLAG_ARRAY_POINTER}</td><td>{@link GL14#GL_SECONDARY_COLOR_ARRAY_POINTER SECONDARY_COLOR_ARRAY_POINTER}</td></tr><tr><td>{@link GL15#GL_FOG_COORD_ARRAY_POINTER FOG_COORD_ARRAY_POINTER}</td><td>{@link GL43#GL_DEBUG_CALLBACK_FUNCTION DEBUG_CALLBACK_FUNCTION}</td><td>{@link GL43#GL_DEBUG_CALLBACK_USER_PARAM DEBUG_CALLBACK_USER_PARAM}</td></tr></table>
     */
    //public long glGetPointer(int pname);

    // --- [ glGetString ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetString">Reference Page</a></p>
     * 
     * Return strings describing properties of the current GL context.
     *
     * @param name the property to query. One of:<br><table><tr><td>{@link #GL_RENDERER RENDERER}</td><td>{@link #GL_VENDOR VENDOR}</td><td>{@link #GL_EXTENSIONS EXTENSIONS}</td><td>{@link #GL_VERSION VERSION}</td><td>{@link GL20#GL_SHADING_LANGUAGE_VERSION SHADING_LANGUAGE_VERSION}</td></tr></table>
     */
    /* @Nullable */
    public String glGetString(int name);

    // --- [ glGetTexEnviv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexEnv">Reference Page</a></p>
     * 
     * Returns integer information about {@code pname} for {@code env} in {@code data}.
     *
     * @param env   the texture environment to query. One of:<br><table><tr><td>{@link GL20#GL_POINT_SPRITE POINT_SPRITE}</td><td>{@link #GL_TEXTURE_ENV TEXTURE_ENV}</td><td>{@link GL14#GL_TEXTURE_FILTER_CONTROL TEXTURE_FILTER_CONTROL}</td></tr></table>
     * @param pname the parameter to query. One of:<br><table><tr><td>{@link GL20#GL_COORD_REPLACE COORD_REPLACE}</td><td>{@link #GL_TEXTURE_ENV_MODE TEXTURE_ENV_MODE}</td><td>{@link #GL_TEXTURE_ENV_COLOR TEXTURE_ENV_COLOR}</td><td>{@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}</td><td>{@link GL13#GL_COMBINE_RGB COMBINE_RGB}</td><td>{@link GL13#GL_COMBINE_ALPHA COMBINE_ALPHA}</td></tr><tr><td>{@link GL15#GL_SRC0_RGB SRC0_RGB}</td><td>{@link GL15#GL_SRC1_RGB SRC1_RGB}</td><td>{@link GL15#GL_SRC2_RGB SRC2_RGB}</td><td>{@link GL15#GL_SRC0_ALPHA SRC0_ALPHA}</td><td>{@link GL15#GL_SRC1_ALPHA SRC1_ALPHA}</td><td>{@link GL15#GL_SRC2_ALPHA SRC2_ALPHA}</td></tr><tr><td>{@link GL13#GL_OPERAND0_RGB OPERAND0_RGB}</td><td>{@link GL13#GL_OPERAND1_RGB OPERAND1_RGB}</td><td>{@link GL13#GL_OPERAND2_RGB OPERAND2_RGB}</td><td>{@link GL13#GL_OPERAND0_ALPHA OPERAND0_ALPHA}</td><td>{@link GL13#GL_OPERAND1_ALPHA OPERAND1_ALPHA}</td><td>{@link GL13#GL_OPERAND2_ALPHA OPERAND2_ALPHA}</td></tr><tr><td>{@link GL13#GL_RGB_SCALE RGB_SCALE}</td><td>{@link #GL_ALPHA_SCALE ALPHA_SCALE}</td></tr></table>
     * @param data  a scalar or buffer in which to place the returned data
     */
    //public void glGetTexEnviv(int env, int pname, IntBuffer data);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexEnv">Reference Page</a></p>
     * 
     * Returns integer information about {@code pname} for {@code env} in {@code data}.
     *
     * @param env   the texture environment to query. One of:<br><table><tr><td>{@link GL20#GL_POINT_SPRITE POINT_SPRITE}</td><td>{@link #GL_TEXTURE_ENV TEXTURE_ENV}</td><td>{@link GL14#GL_TEXTURE_FILTER_CONTROL TEXTURE_FILTER_CONTROL}</td></tr></table>
     * @param pname the parameter to query. One of:<br><table><tr><td>{@link GL20#GL_COORD_REPLACE COORD_REPLACE}</td><td>{@link #GL_TEXTURE_ENV_MODE TEXTURE_ENV_MODE}</td><td>{@link #GL_TEXTURE_ENV_COLOR TEXTURE_ENV_COLOR}</td><td>{@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}</td><td>{@link GL13#GL_COMBINE_RGB COMBINE_RGB}</td><td>{@link GL13#GL_COMBINE_ALPHA COMBINE_ALPHA}</td></tr><tr><td>{@link GL15#GL_SRC0_RGB SRC0_RGB}</td><td>{@link GL15#GL_SRC1_RGB SRC1_RGB}</td><td>{@link GL15#GL_SRC2_RGB SRC2_RGB}</td><td>{@link GL15#GL_SRC0_ALPHA SRC0_ALPHA}</td><td>{@link GL15#GL_SRC1_ALPHA SRC1_ALPHA}</td><td>{@link GL15#GL_SRC2_ALPHA SRC2_ALPHA}</td></tr><tr><td>{@link GL13#GL_OPERAND0_RGB OPERAND0_RGB}</td><td>{@link GL13#GL_OPERAND1_RGB OPERAND1_RGB}</td><td>{@link GL13#GL_OPERAND2_RGB OPERAND2_RGB}</td><td>{@link GL13#GL_OPERAND0_ALPHA OPERAND0_ALPHA}</td><td>{@link GL13#GL_OPERAND1_ALPHA OPERAND1_ALPHA}</td><td>{@link GL13#GL_OPERAND2_ALPHA OPERAND2_ALPHA}</td></tr><tr><td>{@link GL13#GL_RGB_SCALE RGB_SCALE}</td><td>{@link #GL_ALPHA_SCALE ALPHA_SCALE}</td></tr></table>
     */
    //public int glGetTexEnvi(int env, int pname);

    // --- [ glGetTexEnvfv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexEnv">Reference Page</a></p>
     * 
     * Float version of {@link #glGetTexEnviv GetTexEnviv}.
     *
     * @param env   the texture environment to query
     * @param pname the parameter to query
     * @param data  a scalar or buffer in which to place the returned data
     */
    //public void glGetTexEnvfv(int env, int pname, FloatBuffer data);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexEnv">Reference Page</a></p>
     * 
     * Float version of {@link #glGetTexEnviv GetTexEnviv}.
     *
     * @param env   the texture environment to query
     * @param pname the parameter to query
     */
    //public float glGetTexEnvf(int env, int pname);

    // --- [ glGetTexImage ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Obtains texture images.
     *
     * @param tex    the texture (or texture face) to be obtained. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_X TEXTURE_CUBE_MAP_POSITIVE_X}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_X TEXTURE_CUBE_MAP_NEGATIVE_X}</td></tr><tr><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Y TEXTURE_CUBE_MAP_POSITIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Y TEXTURE_CUBE_MAP_NEGATIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Z TEXTURE_CUBE_MAP_POSITIVE_Z}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Z TEXTURE_CUBE_MAP_NEGATIVE_Z}</td></tr></table>
     * @param level  the level-of-detail number
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels the buffer in which to place the returned data
     */
    //public void glGetTexImage(int tex, int level, int format, int type, ByteBuffer pixels);
    
    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Obtains texture images.
     *
     * @param tex    the texture (or texture face) to be obtained. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_X TEXTURE_CUBE_MAP_POSITIVE_X}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_X TEXTURE_CUBE_MAP_NEGATIVE_X}</td></tr><tr><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Y TEXTURE_CUBE_MAP_POSITIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Y TEXTURE_CUBE_MAP_NEGATIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Z TEXTURE_CUBE_MAP_POSITIVE_Z}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Z TEXTURE_CUBE_MAP_NEGATIVE_Z}</td></tr></table>
     * @param level  the level-of-detail number
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels the buffer in which to place the returned data
     */
    //public void glGetTexImage(int tex, int level, int format, int type, long pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Obtains texture images.
     *
     * @param tex    the texture (or texture face) to be obtained. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_X TEXTURE_CUBE_MAP_POSITIVE_X}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_X TEXTURE_CUBE_MAP_NEGATIVE_X}</td></tr><tr><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Y TEXTURE_CUBE_MAP_POSITIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Y TEXTURE_CUBE_MAP_NEGATIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Z TEXTURE_CUBE_MAP_POSITIVE_Z}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Z TEXTURE_CUBE_MAP_NEGATIVE_Z}</td></tr></table>
     * @param level  the level-of-detail number
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels the buffer in which to place the returned data
     */
    //public void glGetTexImage(int tex, int level, int format, int type, ShortBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Obtains texture images.
     *
     * @param tex    the texture (or texture face) to be obtained. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_X TEXTURE_CUBE_MAP_POSITIVE_X}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_X TEXTURE_CUBE_MAP_NEGATIVE_X}</td></tr><tr><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Y TEXTURE_CUBE_MAP_POSITIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Y TEXTURE_CUBE_MAP_NEGATIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Z TEXTURE_CUBE_MAP_POSITIVE_Z}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Z TEXTURE_CUBE_MAP_NEGATIVE_Z}</td></tr></table>
     * @param level  the level-of-detail number
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels the buffer in which to place the returned data
     */
    //public void glGetTexImage(int tex, int level, int format, int type, IntBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Obtains texture images.
     *
     * @param tex    the texture (or texture face) to be obtained. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_X TEXTURE_CUBE_MAP_POSITIVE_X}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_X TEXTURE_CUBE_MAP_NEGATIVE_X}</td></tr><tr><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Y TEXTURE_CUBE_MAP_POSITIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Y TEXTURE_CUBE_MAP_NEGATIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Z TEXTURE_CUBE_MAP_POSITIVE_Z}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Z TEXTURE_CUBE_MAP_NEGATIVE_Z}</td></tr></table>
     * @param level  the level-of-detail number
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels the buffer in which to place the returned data
     */
    //public void glGetTexImage(int tex, int level, int format, int type, FloatBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Obtains texture images.
     *
     * @param tex    the texture (or texture face) to be obtained. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_X TEXTURE_CUBE_MAP_POSITIVE_X}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_X TEXTURE_CUBE_MAP_NEGATIVE_X}</td></tr><tr><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Y TEXTURE_CUBE_MAP_POSITIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Y TEXTURE_CUBE_MAP_NEGATIVE_Y}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_POSITIVE_Z TEXTURE_CUBE_MAP_POSITIVE_Z}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP_NEGATIVE_Z TEXTURE_CUBE_MAP_NEGATIVE_Z}</td></tr></table>
     * @param level  the level-of-detail number
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels the buffer in which to place the returned data
     */
    //public void glGetTexImage(int tex, int level, int format, int type, DoubleBuffer pixels);

    // --- [ glGetTexLevelParameteriv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexLevelParameter">Reference Page</a></p>
     * 
     * Places integer information about texture image parameter {@code pname} for level-of-detail {@code level} of the specified {@code target} into {@code params}.
     *
     * @param target the texture image target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_PROXY_TEXTURE_2D PROXY_TEXTURE_2D}</td><td>{@link GL30#GL_PROXY_TEXTURE_1D_ARRAY PROXY_TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_PROXY_TEXTURE_RECTANGLE PROXY_TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_PROXY_TEXTURE_CUBE_MAP PROXY_TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL40#GL_TEXTURE_CUBE_MAP_ARRAY TEXTURE_CUBE_MAP_ARRAY}</td></tr><tr><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE TEXTURE_2D_MULTISAMPLE}</td><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE_ARRAY TEXTURE_2D_MULTISAMPLE_ARRAY}</td><td>{@link #GL_PROXY_TEXTURE_1D PROXY_TEXTURE_1D}</td><td>{@link GL12#GL_PROXY_TEXTURE_3D PROXY_TEXTURE_3D}</td></tr><tr><td>{@link GL30#GL_PROXY_TEXTURE_2D_ARRAY PROXY_TEXTURE_2D_ARRAY}</td><td>{@link GL40#GL_PROXY_TEXTURE_CUBE_MAP_ARRAY PROXY_TEXTURE_CUBE_MAP_ARRAY}</td><td>{@link GL32#GL_PROXY_TEXTURE_2D_MULTISAMPLE PROXY_TEXTURE_2D_MULTISAMPLE}</td><td>{@link GL32#GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY}</td></tr></table>
     * @param level  the level-of-detail number
     * @param pname  the parameter to query. One of:<br><table><tr><td>{@link #GL_TEXTURE_WIDTH TEXTURE_WIDTH}</td><td>{@link #GL_TEXTURE_HEIGHT TEXTURE_HEIGHT}</td><td>{@link GL12#GL_TEXTURE_DEPTH TEXTURE_DEPTH}</td><td>{@link #GL_TEXTURE_BORDER TEXTURE_BORDER}</td></tr><tr><td>{@link GL32#GL_TEXTURE_SAMPLES TEXTURE_SAMPLES}</td><td>{@link GL32#GL_TEXTURE_FIXED_SAMPLE_LOCATIONS TEXTURE_FIXED_SAMPLE_LOCATIONS}</td><td>{@link #GL_TEXTURE_INTERNAL_FORMAT TEXTURE_INTERNAL_FORMAT}</td><td>{@link #GL_TEXTURE_RED_SIZE TEXTURE_RED_SIZE}</td></tr><tr><td>{@link #GL_TEXTURE_GREEN_SIZE TEXTURE_GREEN_SIZE}</td><td>{@link #GL_TEXTURE_BLUE_SIZE TEXTURE_BLUE_SIZE}</td><td>{@link #GL_TEXTURE_ALPHA_SIZE TEXTURE_ALPHA_SIZE}</td><td>{@link #GL_TEXTURE_LUMINANCE_SIZE TEXTURE_LUMINANCE_SIZE}</td></tr><tr><td>{@link #GL_TEXTURE_INTENSITY_SIZE TEXTURE_INTENSITY_SIZE}</td><td>{@link GL14#GL_TEXTURE_DEPTH_SIZE TEXTURE_DEPTH_SIZE}</td><td>{@link GL30#GL_TEXTURE_STENCIL_SIZE TEXTURE_STENCIL_SIZE}</td><td>{@link GL30#GL_TEXTURE_SHARED_SIZE TEXTURE_SHARED_SIZE}</td></tr><tr><td>{@link GL30#GL_TEXTURE_RED_TYPE TEXTURE_RED_TYPE}</td><td>{@link GL30#GL_TEXTURE_GREEN_TYPE TEXTURE_GREEN_TYPE}</td><td>{@link GL30#GL_TEXTURE_BLUE_TYPE TEXTURE_BLUE_TYPE}</td><td>{@link GL30#GL_TEXTURE_ALPHA_TYPE TEXTURE_ALPHA_TYPE}</td></tr><tr><td>{@link GL30#GL_TEXTURE_LUMINANCE_TYPE TEXTURE_LUMINANCE_TYPE}</td><td>{@link GL30#GL_TEXTURE_INTENSITY_TYPE TEXTURE_INTENSITY_TYPE}</td><td>{@link GL30#GL_TEXTURE_DEPTH_TYPE TEXTURE_DEPTH_TYPE}</td><td>{@link GL13#GL_TEXTURE_COMPRESSED TEXTURE_COMPRESSED}</td></tr><tr><td>{@link GL13#GL_TEXTURE_COMPRESSED_IMAGE_SIZE TEXTURE_COMPRESSED_IMAGE_SIZE}</td><td>{@link GL31#GL_TEXTURE_BUFFER_DATA_STORE_BINDING TEXTURE_BUFFER_DATA_STORE_BINDING}</td><td>{@link GL43#GL_TEXTURE_BUFFER_OFFSET TEXTURE_BUFFER_OFFSET}</td><td>{@link GL43#GL_TEXTURE_BUFFER_SIZE TEXTURE_BUFFER_SIZE}</td></tr></table>
     * @param params a scalar or buffer in which to place the returned data
     */
    //public void glGetTexLevelParameteriv(int target, int level, int pname, IntBuffer params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexLevelParameter">Reference Page</a></p>
     * 
     * Places integer information about texture image parameter {@code pname} for level-of-detail {@code level} of the specified {@code target} into {@code params}.
     *
     * @param target the texture image target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_PROXY_TEXTURE_2D PROXY_TEXTURE_2D}</td><td>{@link GL30#GL_PROXY_TEXTURE_1D_ARRAY PROXY_TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_PROXY_TEXTURE_RECTANGLE PROXY_TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_PROXY_TEXTURE_CUBE_MAP PROXY_TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL40#GL_TEXTURE_CUBE_MAP_ARRAY TEXTURE_CUBE_MAP_ARRAY}</td></tr><tr><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE TEXTURE_2D_MULTISAMPLE}</td><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE_ARRAY TEXTURE_2D_MULTISAMPLE_ARRAY}</td><td>{@link #GL_PROXY_TEXTURE_1D PROXY_TEXTURE_1D}</td><td>{@link GL12#GL_PROXY_TEXTURE_3D PROXY_TEXTURE_3D}</td></tr><tr><td>{@link GL30#GL_PROXY_TEXTURE_2D_ARRAY PROXY_TEXTURE_2D_ARRAY}</td><td>{@link GL40#GL_PROXY_TEXTURE_CUBE_MAP_ARRAY PROXY_TEXTURE_CUBE_MAP_ARRAY}</td><td>{@link GL32#GL_PROXY_TEXTURE_2D_MULTISAMPLE PROXY_TEXTURE_2D_MULTISAMPLE}</td><td>{@link GL32#GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY}</td></tr></table>
     * @param level  the level-of-detail number
     * @param pname  the parameter to query. One of:<br><table><tr><td>{@link #GL_TEXTURE_WIDTH TEXTURE_WIDTH}</td><td>{@link #GL_TEXTURE_HEIGHT TEXTURE_HEIGHT}</td><td>{@link GL12#GL_TEXTURE_DEPTH TEXTURE_DEPTH}</td><td>{@link #GL_TEXTURE_BORDER TEXTURE_BORDER}</td></tr><tr><td>{@link GL32#GL_TEXTURE_SAMPLES TEXTURE_SAMPLES}</td><td>{@link GL32#GL_TEXTURE_FIXED_SAMPLE_LOCATIONS TEXTURE_FIXED_SAMPLE_LOCATIONS}</td><td>{@link #GL_TEXTURE_INTERNAL_FORMAT TEXTURE_INTERNAL_FORMAT}</td><td>{@link #GL_TEXTURE_RED_SIZE TEXTURE_RED_SIZE}</td></tr><tr><td>{@link #GL_TEXTURE_GREEN_SIZE TEXTURE_GREEN_SIZE}</td><td>{@link #GL_TEXTURE_BLUE_SIZE TEXTURE_BLUE_SIZE}</td><td>{@link #GL_TEXTURE_ALPHA_SIZE TEXTURE_ALPHA_SIZE}</td><td>{@link #GL_TEXTURE_LUMINANCE_SIZE TEXTURE_LUMINANCE_SIZE}</td></tr><tr><td>{@link #GL_TEXTURE_INTENSITY_SIZE TEXTURE_INTENSITY_SIZE}</td><td>{@link GL14#GL_TEXTURE_DEPTH_SIZE TEXTURE_DEPTH_SIZE}</td><td>{@link GL30#GL_TEXTURE_STENCIL_SIZE TEXTURE_STENCIL_SIZE}</td><td>{@link GL30#GL_TEXTURE_SHARED_SIZE TEXTURE_SHARED_SIZE}</td></tr><tr><td>{@link GL30#GL_TEXTURE_RED_TYPE TEXTURE_RED_TYPE}</td><td>{@link GL30#GL_TEXTURE_GREEN_TYPE TEXTURE_GREEN_TYPE}</td><td>{@link GL30#GL_TEXTURE_BLUE_TYPE TEXTURE_BLUE_TYPE}</td><td>{@link GL30#GL_TEXTURE_ALPHA_TYPE TEXTURE_ALPHA_TYPE}</td></tr><tr><td>{@link GL30#GL_TEXTURE_LUMINANCE_TYPE TEXTURE_LUMINANCE_TYPE}</td><td>{@link GL30#GL_TEXTURE_INTENSITY_TYPE TEXTURE_INTENSITY_TYPE}</td><td>{@link GL30#GL_TEXTURE_DEPTH_TYPE TEXTURE_DEPTH_TYPE}</td><td>{@link GL13#GL_TEXTURE_COMPRESSED TEXTURE_COMPRESSED}</td></tr><tr><td>{@link GL13#GL_TEXTURE_COMPRESSED_IMAGE_SIZE TEXTURE_COMPRESSED_IMAGE_SIZE}</td><td>{@link GL31#GL_TEXTURE_BUFFER_DATA_STORE_BINDING TEXTURE_BUFFER_DATA_STORE_BINDING}</td><td>{@link GL43#GL_TEXTURE_BUFFER_OFFSET TEXTURE_BUFFER_OFFSET}</td><td>{@link GL43#GL_TEXTURE_BUFFER_SIZE TEXTURE_BUFFER_SIZE}</td></tr></table>
     */
    //public int glGetTexLevelParameteri(int target, int level, int pname);

    // --- [ glGetTexLevelParameterfv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexLevelParameter">Reference Page</a></p>
     * 
     * Float version of {@link #glGetTexLevelParameteriv GetTexLevelParameteriv}.
     *
     * @param target the texture image target
     * @param level  the level-of-detail number
     * @param pname  the parameter to query
     * @param params a scalar or buffer in which to place the returned data
     */
    //public void glGetTexLevelParameterfv(int target, int level, int pname, FloatBuffer params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexLevelParameter">Reference Page</a></p>
     * 
     * Float version of {@link #glGetTexLevelParameteriv GetTexLevelParameteriv}.
     *
     * @param target the texture image target
     * @param level  the level-of-detail number
     * @param pname  the parameter to query
     */
    //public float glGetTexLevelParameterf(int target, int level, int pname);

    // --- [ glGetTexParameteriv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexParameter">Reference Page</a></p>
     * 
     * Place integer information about texture parameter {@code pname} for the specified {@code target} into {@code params}.
     *
     * @param target the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td><td>{@link GL40#GL_TEXTURE_CUBE_MAP_ARRAY TEXTURE_CUBE_MAP_ARRAY}</td></tr><tr><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE TEXTURE_2D_MULTISAMPLE}</td><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE_ARRAY TEXTURE_2D_MULTISAMPLE_ARRAY}</td></tr></table>
     * @param pname  the parameter to query. One of:<br><table><tr><td>{@link GL12#GL_TEXTURE_BASE_LEVEL TEXTURE_BASE_LEVEL}</td><td>{@link #GL_TEXTURE_BORDER_COLOR TEXTURE_BORDER_COLOR}</td><td>{@link GL14#GL_TEXTURE_COMPARE_MODE TEXTURE_COMPARE_MODE}</td><td>{@link GL14#GL_TEXTURE_COMPARE_FUNC TEXTURE_COMPARE_FUNC}</td></tr><tr><td>{@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}</td><td>{@link #GL_TEXTURE_MAG_FILTER TEXTURE_MAG_FILTER}</td><td>{@link GL12#GL_TEXTURE_MAX_LEVEL TEXTURE_MAX_LEVEL}</td><td>{@link GL12#GL_TEXTURE_MAX_LOD TEXTURE_MAX_LOD}</td></tr><tr><td>{@link #GL_TEXTURE_MIN_FILTER TEXTURE_MIN_FILTER}</td><td>{@link GL12#GL_TEXTURE_MIN_LOD TEXTURE_MIN_LOD}</td><td>{@link #GL_TEXTURE_PRIORITY TEXTURE_PRIORITY}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_R TEXTURE_SWIZZLE_R}</td></tr><tr><td>{@link GL33#GL_TEXTURE_SWIZZLE_G TEXTURE_SWIZZLE_G}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_B TEXTURE_SWIZZLE_B}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_A TEXTURE_SWIZZLE_A}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_RGBA TEXTURE_SWIZZLE_RGBA}</td></tr><tr><td>{@link #GL_TEXTURE_WRAP_S TEXTURE_WRAP_S}</td><td>{@link #GL_TEXTURE_WRAP_T TEXTURE_WRAP_T}</td><td>{@link GL12#GL_TEXTURE_WRAP_R TEXTURE_WRAP_R}</td><td>{@link GL14#GL_DEPTH_TEXTURE_MODE DEPTH_TEXTURE_MODE}</td></tr><tr><td>{@link GL14#GL_GENERATE_MIPMAP GENERATE_MIPMAP}</td><td>{@link GL42#GL_IMAGE_FORMAT_COMPATIBILITY_TYPE IMAGE_FORMAT_COMPATIBILITY_TYPE}</td><td>{@link GL42#GL_TEXTURE_IMMUTABLE_FORMAT TEXTURE_IMMUTABLE_FORMAT}</td><td>{@link GL43#GL_TEXTURE_IMMUTABLE_LEVELS TEXTURE_IMMUTABLE_LEVELS}</td></tr><tr><td>{@link GL43#GL_TEXTURE_VIEW_MIN_LEVEL TEXTURE_VIEW_MIN_LEVEL}</td><td>{@link GL43#GL_TEXTURE_VIEW_NUM_LEVELS TEXTURE_VIEW_NUM_LEVELS}</td><td>{@link GL43#GL_TEXTURE_VIEW_MIN_LAYER TEXTURE_VIEW_MIN_LAYER}</td><td>{@link GL43#GL_TEXTURE_VIEW_NUM_LAYERS TEXTURE_VIEW_NUM_LAYERS}</td></tr><tr><td>{@link #GL_TEXTURE_RESIDENT TEXTURE_RESIDENT}</td></tr></table>
     * @param params a scalar or buffer in which to place the returned data
     */
    public void glGetTexParameteriv(int target, int pname, IntBuffer params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexParameter">Reference Page</a></p>
     * 
     * Place integer information about texture parameter {@code pname} for the specified {@code target} into {@code params}.
     *
     * @param target the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td><td>{@link GL40#GL_TEXTURE_CUBE_MAP_ARRAY TEXTURE_CUBE_MAP_ARRAY}</td></tr><tr><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE TEXTURE_2D_MULTISAMPLE}</td><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE_ARRAY TEXTURE_2D_MULTISAMPLE_ARRAY}</td></tr></table>
     * @param pname  the parameter to query. One of:<br><table><tr><td>{@link GL12#GL_TEXTURE_BASE_LEVEL TEXTURE_BASE_LEVEL}</td><td>{@link #GL_TEXTURE_BORDER_COLOR TEXTURE_BORDER_COLOR}</td><td>{@link GL14#GL_TEXTURE_COMPARE_MODE TEXTURE_COMPARE_MODE}</td><td>{@link GL14#GL_TEXTURE_COMPARE_FUNC TEXTURE_COMPARE_FUNC}</td></tr><tr><td>{@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}</td><td>{@link #GL_TEXTURE_MAG_FILTER TEXTURE_MAG_FILTER}</td><td>{@link GL12#GL_TEXTURE_MAX_LEVEL TEXTURE_MAX_LEVEL}</td><td>{@link GL12#GL_TEXTURE_MAX_LOD TEXTURE_MAX_LOD}</td></tr><tr><td>{@link #GL_TEXTURE_MIN_FILTER TEXTURE_MIN_FILTER}</td><td>{@link GL12#GL_TEXTURE_MIN_LOD TEXTURE_MIN_LOD}</td><td>{@link #GL_TEXTURE_PRIORITY TEXTURE_PRIORITY}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_R TEXTURE_SWIZZLE_R}</td></tr><tr><td>{@link GL33#GL_TEXTURE_SWIZZLE_G TEXTURE_SWIZZLE_G}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_B TEXTURE_SWIZZLE_B}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_A TEXTURE_SWIZZLE_A}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_RGBA TEXTURE_SWIZZLE_RGBA}</td></tr><tr><td>{@link #GL_TEXTURE_WRAP_S TEXTURE_WRAP_S}</td><td>{@link #GL_TEXTURE_WRAP_T TEXTURE_WRAP_T}</td><td>{@link GL12#GL_TEXTURE_WRAP_R TEXTURE_WRAP_R}</td><td>{@link GL14#GL_DEPTH_TEXTURE_MODE DEPTH_TEXTURE_MODE}</td></tr><tr><td>{@link GL14#GL_GENERATE_MIPMAP GENERATE_MIPMAP}</td><td>{@link GL42#GL_IMAGE_FORMAT_COMPATIBILITY_TYPE IMAGE_FORMAT_COMPATIBILITY_TYPE}</td><td>{@link GL42#GL_TEXTURE_IMMUTABLE_FORMAT TEXTURE_IMMUTABLE_FORMAT}</td><td>{@link GL43#GL_TEXTURE_IMMUTABLE_LEVELS TEXTURE_IMMUTABLE_LEVELS}</td></tr><tr><td>{@link GL43#GL_TEXTURE_VIEW_MIN_LEVEL TEXTURE_VIEW_MIN_LEVEL}</td><td>{@link GL43#GL_TEXTURE_VIEW_NUM_LEVELS TEXTURE_VIEW_NUM_LEVELS}</td><td>{@link GL43#GL_TEXTURE_VIEW_MIN_LAYER TEXTURE_VIEW_MIN_LAYER}</td><td>{@link GL43#GL_TEXTURE_VIEW_NUM_LAYERS TEXTURE_VIEW_NUM_LAYERS}</td></tr><tr><td>{@link #GL_TEXTURE_RESIDENT TEXTURE_RESIDENT}</td></tr></table>
     */
    public int glGetTexParameteri(int target, int pname);

    // --- [ glGetTexParameterfv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexParameter">Reference Page</a></p>
     * 
     * Float version of {@link #glGetTexParameteriv GetTexParameteriv}.
     *
     * @param target the texture target
     * @param pname  the parameter to query
     * @param params a scalar or buffer in which to place the returned data
     */
    public void glGetTexParameterfv(int target, int pname, FloatBuffer params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexParameter">Reference Page</a></p>
     * 
     * Float version of {@link #glGetTexParameteriv GetTexParameteriv}.
     *
     * @param target the texture target
     * @param pname  the parameter to query
     */
    public float glGetTexParameterf(int target, int pname);

    // --- [ glHint ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glHint">Reference Page</a></p>
     * 
     * Certain aspects of GL behavior, when there is room for variation, may be controlled with this function. The initial value for all hints is
     * {@link #GL_DONT_CARE DONT_CARE}.
     *
     * @param target the behavior to control. One of:<br><table><tr><td>{@link #GL_PERSPECTIVE_CORRECTION_HINT PERSPECTIVE_CORRECTION_HINT}</td><td>{@link #GL_POINT_SMOOTH_HINT POINT_SMOOTH_HINT}</td><td>{@link #GL_LINE_SMOOTH_HINT LINE_SMOOTH_HINT}</td><td>{@link #GL_POLYGON_SMOOTH_HINT POLYGON_SMOOTH_HINT}</td></tr><tr><td>{@link #GL_FOG_HINT FOG_HINT}</td><td>{@link GL14#GL_GENERATE_MIPMAP_HINT GENERATE_MIPMAP_HINT}</td><td>{@link GL13#GL_TEXTURE_COMPRESSION_HINT TEXTURE_COMPRESSION_HINT}</td><td>{@link GL20#GL_FRAGMENT_SHADER_DERIVATIVE_HINT FRAGMENT_SHADER_DERIVATIVE_HINT}</td></tr></table>
     * @param hint   the behavior hint. One of:<br><table><tr><td>{@link #GL_FASTEST FASTEST}</td><td>{@link #GL_NICEST NICEST}</td><td>{@link #GL_DONT_CARE DONT_CARE}</td></tr></table>
     */
    public void glHint(int target, int hint);

    // --- [ glInterleavedArrays ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Efficiently initializes the six vertex arrays and their enables to one of 14 configurations.
     *
     * @param format  the interleaved array format. One of:<br><table><tr><td>{@link #GL_V2F V2F}</td><td>{@link #GL_V3F V3F}</td><td>{@link #GL_C4UB_V2F C4UB_V2F}</td><td>{@link #GL_C4UB_V3F C4UB_V3F}</td><td>{@link #GL_C3F_V3F C3F_V3F}</td><td>{@link #GL_N3F_V3F N3F_V3F}</td><td>{@link #GL_C4F_N3F_V3F C4F_N3F_V3F}</td><td>{@link #GL_T2F_V3F T2F_V3F}</td></tr><tr><td>{@link #GL_T4F_V4F T4F_V4F}</td><td>{@link #GL_T2F_C4UB_V3F T2F_C4UB_V3F}</td><td>{@link #GL_T2F_C3F_V3F T2F_C3F_V3F}</td><td>{@link #GL_T2F_N3F_V3F T2F_N3F_V3F}</td><td>{@link #GL_T2F_C4F_N3F_V3F T2F_C4F_N3F_V3F}</td><td>{@link #GL_T4F_C4F_N3F_V4F T4F_C4F_N3F_V4F}</td></tr></table>
     * @param stride  the vertex stride in bytes. If specified as zero, then array elements are stored sequentially
     * @param pointer the vertex array data
     */
    //public void glInterleavedArrays(int format, int stride, ByteBuffer pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Efficiently initializes the six vertex arrays and their enables to one of 14 configurations.
     *
     * @param format  the interleaved array format. One of:<br><table><tr><td>{@link #GL_V2F V2F}</td><td>{@link #GL_V3F V3F}</td><td>{@link #GL_C4UB_V2F C4UB_V2F}</td><td>{@link #GL_C4UB_V3F C4UB_V3F}</td><td>{@link #GL_C3F_V3F C3F_V3F}</td><td>{@link #GL_N3F_V3F N3F_V3F}</td><td>{@link #GL_C4F_N3F_V3F C4F_N3F_V3F}</td><td>{@link #GL_T2F_V3F T2F_V3F}</td></tr><tr><td>{@link #GL_T4F_V4F T4F_V4F}</td><td>{@link #GL_T2F_C4UB_V3F T2F_C4UB_V3F}</td><td>{@link #GL_T2F_C3F_V3F T2F_C3F_V3F}</td><td>{@link #GL_T2F_N3F_V3F T2F_N3F_V3F}</td><td>{@link #GL_T2F_C4F_N3F_V3F T2F_C4F_N3F_V3F}</td><td>{@link #GL_T4F_C4F_N3F_V4F T4F_C4F_N3F_V4F}</td></tr></table>
     * @param stride  the vertex stride in bytes. If specified as zero, then array elements are stored sequentially
     * @param pointer the vertex array data
     */
    //public void glInterleavedArrays(int format, int stride, long pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Efficiently initializes the six vertex arrays and their enables to one of 14 configurations.
     *
     * @param format  the interleaved array format. One of:<br><table><tr><td>{@link #GL_V2F V2F}</td><td>{@link #GL_V3F V3F}</td><td>{@link #GL_C4UB_V2F C4UB_V2F}</td><td>{@link #GL_C4UB_V3F C4UB_V3F}</td><td>{@link #GL_C3F_V3F C3F_V3F}</td><td>{@link #GL_N3F_V3F N3F_V3F}</td><td>{@link #GL_C4F_N3F_V3F C4F_N3F_V3F}</td><td>{@link #GL_T2F_V3F T2F_V3F}</td></tr><tr><td>{@link #GL_T4F_V4F T4F_V4F}</td><td>{@link #GL_T2F_C4UB_V3F T2F_C4UB_V3F}</td><td>{@link #GL_T2F_C3F_V3F T2F_C3F_V3F}</td><td>{@link #GL_T2F_N3F_V3F T2F_N3F_V3F}</td><td>{@link #GL_T2F_C4F_N3F_V3F T2F_C4F_N3F_V3F}</td><td>{@link #GL_T4F_C4F_N3F_V4F T4F_C4F_N3F_V4F}</td></tr></table>
     * @param stride  the vertex stride in bytes. If specified as zero, then array elements are stored sequentially
     * @param pointer the vertex array data
     */
    //public void glInterleavedArrays(int format, int stride, ShortBuffer pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Efficiently initializes the six vertex arrays and their enables to one of 14 configurations.
     *
     * @param format  the interleaved array format. One of:<br><table><tr><td>{@link #GL_V2F V2F}</td><td>{@link #GL_V3F V3F}</td><td>{@link #GL_C4UB_V2F C4UB_V2F}</td><td>{@link #GL_C4UB_V3F C4UB_V3F}</td><td>{@link #GL_C3F_V3F C3F_V3F}</td><td>{@link #GL_N3F_V3F N3F_V3F}</td><td>{@link #GL_C4F_N3F_V3F C4F_N3F_V3F}</td><td>{@link #GL_T2F_V3F T2F_V3F}</td></tr><tr><td>{@link #GL_T4F_V4F T4F_V4F}</td><td>{@link #GL_T2F_C4UB_V3F T2F_C4UB_V3F}</td><td>{@link #GL_T2F_C3F_V3F T2F_C3F_V3F}</td><td>{@link #GL_T2F_N3F_V3F T2F_N3F_V3F}</td><td>{@link #GL_T2F_C4F_N3F_V3F T2F_C4F_N3F_V3F}</td><td>{@link #GL_T4F_C4F_N3F_V4F T4F_C4F_N3F_V4F}</td></tr></table>
     * @param stride  the vertex stride in bytes. If specified as zero, then array elements are stored sequentially
     * @param pointer the vertex array data
     */
    //public void glInterleavedArrays(int format, int stride, IntBuffer pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Efficiently initializes the six vertex arrays and their enables to one of 14 configurations.
     *
     * @param format  the interleaved array format. One of:<br><table><tr><td>{@link #GL_V2F V2F}</td><td>{@link #GL_V3F V3F}</td><td>{@link #GL_C4UB_V2F C4UB_V2F}</td><td>{@link #GL_C4UB_V3F C4UB_V3F}</td><td>{@link #GL_C3F_V3F C3F_V3F}</td><td>{@link #GL_N3F_V3F N3F_V3F}</td><td>{@link #GL_C4F_N3F_V3F C4F_N3F_V3F}</td><td>{@link #GL_T2F_V3F T2F_V3F}</td></tr><tr><td>{@link #GL_T4F_V4F T4F_V4F}</td><td>{@link #GL_T2F_C4UB_V3F T2F_C4UB_V3F}</td><td>{@link #GL_T2F_C3F_V3F T2F_C3F_V3F}</td><td>{@link #GL_T2F_N3F_V3F T2F_N3F_V3F}</td><td>{@link #GL_T2F_C4F_N3F_V3F T2F_C4F_N3F_V3F}</td><td>{@link #GL_T4F_C4F_N3F_V4F T4F_C4F_N3F_V4F}</td></tr></table>
     * @param stride  the vertex stride in bytes. If specified as zero, then array elements are stored sequentially
     * @param pointer the vertex array data
     */
    //public void glInterleavedArrays(int format, int stride, FloatBuffer pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Efficiently initializes the six vertex arrays and their enables to one of 14 configurations.
     *
     * @param format  the interleaved array format. One of:<br><table><tr><td>{@link #GL_V2F V2F}</td><td>{@link #GL_V3F V3F}</td><td>{@link #GL_C4UB_V2F C4UB_V2F}</td><td>{@link #GL_C4UB_V3F C4UB_V3F}</td><td>{@link #GL_C3F_V3F C3F_V3F}</td><td>{@link #GL_N3F_V3F N3F_V3F}</td><td>{@link #GL_C4F_N3F_V3F C4F_N3F_V3F}</td><td>{@link #GL_T2F_V3F T2F_V3F}</td></tr><tr><td>{@link #GL_T4F_V4F T4F_V4F}</td><td>{@link #GL_T2F_C4UB_V3F T2F_C4UB_V3F}</td><td>{@link #GL_T2F_C3F_V3F T2F_C3F_V3F}</td><td>{@link #GL_T2F_N3F_V3F T2F_N3F_V3F}</td><td>{@link #GL_T2F_C4F_N3F_V3F T2F_C4F_N3F_V3F}</td><td>{@link #GL_T4F_C4F_N3F_V4F T4F_C4F_N3F_V4F}</td></tr></table>
     * @param stride  the vertex stride in bytes. If specified as zero, then array elements are stored sequentially
     * @param pointer the vertex array data
     */
    //public void glInterleavedArrays(int format, int stride, DoubleBuffer pointer);

    // --- [ glIsEnabled ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glIsEnabled">Reference Page</a></p>
     * 
     * Determines if {@code cap} is currently enabled (as with {@link #glEnable Enable}) or disabled.
     *
     * @param cap the enable state to query
     */
    public boolean glIsEnabled(int cap);

    // --- [ glIsTexture ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glIsTexture">Reference Page</a></p>
     * 
     * Returns true if {@code texture} is the name of a texture object.
     *
     * @param texture the texture name to query
     */
    public boolean glIsTexture(int texture);

    // --- [ glLineWidth ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glLineWidth">Reference Page</a></p>
     * 
     * Sets the width of rasterized line segments. The default width is 1.0.
     *
     * @param width the line width
     */
    public void glLineWidth(float width);

    // --- [ glLogicOp ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glLogicOp">Reference Page</a></p>
     * 
     * Sets the logical framebuffer operation.
     *
     * @param op the operation to set. One of:<br><table><tr><td>{@link #GL_CLEAR CLEAR}</td><td>{@link #GL_AND AND}</td><td>{@link #GL_AND_REVERSE AND_REVERSE}</td><td>{@link #GL_COPY COPY}</td><td>{@link #GL_AND_INVERTED AND_INVERTED}</td><td>{@link #GL_NOOP NOOP}</td><td>{@link #GL_XOR XOR}</td><td>{@link #GL_OR OR}</td><td>{@link #GL_NOR NOR}</td><td>{@link #GL_EQUIV EQUIV}</td><td>{@link #GL_INVERT INVERT}</td><td>{@link #GL_OR_REVERSE OR_REVERSE}</td><td>{@link #GL_COPY_INVERTED COPY_INVERTED}</td></tr><tr><td>{@link #GL_OR_INVERTED OR_INVERTED}</td><td>{@link #GL_NAND NAND}</td><td>{@link #GL_SET SET}</td></tr></table>
     */
    //public void glLogicOp(int op);

    // --- [ glPixelStorei ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPixelStorei">Reference Page</a></p>
     * 
     * Sets the integer value of a pixel store parameter.
     *
     * @param pname the pixel store parameter to set. One of:<br><table><tr><td>{@link #GL_UNPACK_SWAP_BYTES UNPACK_SWAP_BYTES}</td><td>{@link #GL_UNPACK_LSB_FIRST UNPACK_LSB_FIRST}</td><td>{@link #GL_UNPACK_ROW_LENGTH UNPACK_ROW_LENGTH}</td></tr><tr><td>{@link #GL_UNPACK_SKIP_ROWS UNPACK_SKIP_ROWS}</td><td>{@link #GL_UNPACK_SKIP_PIXELS UNPACK_SKIP_PIXELS}</td><td>{@link #GL_UNPACK_ALIGNMENT UNPACK_ALIGNMENT}</td></tr><tr><td>{@link GL12#GL_UNPACK_IMAGE_HEIGHT UNPACK_IMAGE_HEIGHT}</td><td>{@link GL12#GL_UNPACK_SKIP_IMAGES UNPACK_SKIP_IMAGES}</td><td>{@link GL42#GL_UNPACK_COMPRESSED_BLOCK_WIDTH UNPACK_COMPRESSED_BLOCK_WIDTH}</td></tr><tr><td>{@link GL42#GL_UNPACK_COMPRESSED_BLOCK_HEIGHT UNPACK_COMPRESSED_BLOCK_HEIGHT}</td><td>{@link GL42#GL_UNPACK_COMPRESSED_BLOCK_DEPTH UNPACK_COMPRESSED_BLOCK_DEPTH}</td><td>{@link GL42#GL_UNPACK_COMPRESSED_BLOCK_SIZE UNPACK_COMPRESSED_BLOCK_SIZE}</td></tr></table>
     * @param param the parameter value
     */
    public void glPixelStorei(int pname, int param);

    // --- [ glPixelStoref ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPixelStoref">Reference Page</a></p>
     * 
     * Float version of {@link #glPixelStorei PixelStorei}.
     *
     * @param pname the pixel store parameter to set
     * @param param the parameter value
     */
    //public void glPixelStoref(int pname, float param);

    // --- [ glPointSize ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPointSize">Reference Page</a></p>
     * 
     * Controls the rasterization of points if no vertex, tessellation control, tessellation evaluation, or geometry shader is active. The default point size is 1.0.
     *
     * @param size the request size of a point
     */
    //public void glPointSize(float size);

    // --- [ glPolygonMode ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPolygonMode">Reference Page</a></p>
     * 
     * Controls the interpretation of polygons for rasterization.
     * 
     * <p>{@link #GL_FILL FILL} is the default mode of polygon rasterization. Note that these modes affect only the final rasterization of polygons: in particular, a
     * polygon's vertices are lit, and the polygon is clipped and possibly culled before these modes are applied. Polygon antialiasing applies only to the
     * {@link #GL_FILL FILL} state of PolygonMode. For {@link #GL_POINT POINT} or {@link #GL_LINE LINE}, point antialiasing or line segment antialiasing, respectively, apply.</p>
     *
     * @param face the face for which to set the rasterizing method. One of:<br><table><tr><td>{@link #GL_FRONT FRONT}</td><td>{@link #GL_BACK BACK}</td><td>{@link #GL_FRONT_AND_BACK FRONT_AND_BACK}</td></tr></table>
     * @param mode the rasterization mode. One of:<br><table><tr><td>{@link #GL_POINT POINT}</td><td>{@link #GL_LINE LINE}</td><td>{@link #GL_FILL FILL}</td></tr></table>
     */
    //public void glPolygonMode(int face, int mode);

    // --- [ glPolygonOffset ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glPolygonOffset">Reference Page</a></p>
     * 
     * The depth values of all fragments generated by the rasterization of a polygon may be offset by a single value that is computed for that polygon. This
     * function determines that value.
     * 
     * <p>{@code factor} scales the maximum depth slope of the polygon, and {@code units} scales an implementation-dependent constant that relates to the usable
     * resolution of the depth buffer. The resulting values are summed to produce the polygon offset value.</p>
     *
     * @param factor the maximum depth slope factor
     * @param units  the constant scale
     */
    public void glPolygonOffset(float factor, float units);

    // --- [ glReadBuffer ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadBuffer">Reference Page</a></p>
     * 
     * Defines the color buffer from which values are obtained.
     * 
     * <p>Acceptable values for {@code src} depend on whether the GL is using the default framebuffer (i.e., {@link GL30#GL_DRAW_FRAMEBUFFER_BINDING DRAW_FRAMEBUFFER_BINDING} is zero), or
     * a framebuffer object (i.e., {@link GL30#GL_DRAW_FRAMEBUFFER_BINDING DRAW_FRAMEBUFFER_BINDING} is non-zero). In the initial state, the GL is bound to the default framebuffer.</p>
     *
     * @param src the color buffer to read from. One of:<br><table><tr><td>{@link #GL_NONE NONE}</td><td>{@link #GL_FRONT_LEFT FRONT_LEFT}</td><td>{@link #GL_FRONT_RIGHT FRONT_RIGHT}</td><td>{@link #GL_BACK_LEFT BACK_LEFT}</td><td>{@link #GL_BACK_RIGHT BACK_RIGHT}</td><td>{@link #GL_FRONT FRONT}</td><td>{@link #GL_BACK BACK}</td><td>{@link #GL_LEFT LEFT}</td></tr><tr><td>{@link #GL_RIGHT RIGHT}</td><td>{@link #GL_FRONT_AND_BACK FRONT_AND_BACK}</td><td>{@link #GL_AUX0 AUX0}</td><td>{@link #GL_AUX1 AUX1}</td><td>{@link #GL_AUX2 AUX2}</td><td>{@link #GL_AUX3 AUX3}</td><td>{@link GL30#GL_COLOR_ATTACHMENT0 COLOR_ATTACHMENT0}</td><td>GL30.GL_COLOR_ATTACHMENT[1-15]</td></tr></table>
     */
    //public void glReadBuffer(int src);

    // --- [ glReadPixels ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadPixels">Reference Page</a></p>
     * 
     * ReadPixels obtains values from the selected read buffer from each pixel with lower left hand corner at {@code (x + i, y + j)} for {@code 0 <= i < width}
     * and {@code 0 <= j < height}; this pixel is said to be the i<sup>th</sup> pixel in the j<sup>th</sup> row. If any of these pixels lies outside of the
     * window allocated to the current GL context, or outside of the image attached to the currently bound read framebuffer object, then the values obtained
     * for those pixels are undefined. When {@link GL30#GL_READ_FRAMEBUFFER_BINDING READ_FRAMEBUFFER_BINDING} is zero, values are also undefined for individual pixels that are not owned by
     * the current context. Otherwise, {@code ReadPixels} obtains values from the selected buffer, regardless of how those values were placed there.
     *
     * @param x      the left pixel coordinate
     * @param y      the lower pixel coordinate
     * @param width  the number of pixels to read in the x-dimension
     * @param height the number of pixels to read in the y-dimension
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels a buffer in which to place the returned pixel data
     */
    public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadPixels">Reference Page</a></p>
     * 
     * ReadPixels obtains values from the selected read buffer from each pixel with lower left hand corner at {@code (x + i, y + j)} for {@code 0 <= i < width}
     * and {@code 0 <= j < height}; this pixel is said to be the i<sup>th</sup> pixel in the j<sup>th</sup> row. If any of these pixels lies outside of the
     * window allocated to the current GL context, or outside of the image attached to the currently bound read framebuffer object, then the values obtained
     * for those pixels are undefined. When {@link GL30#GL_READ_FRAMEBUFFER_BINDING READ_FRAMEBUFFER_BINDING} is zero, values are also undefined for individual pixels that are not owned by
     * the current context. Otherwise, {@code ReadPixels} obtains values from the selected buffer, regardless of how those values were placed there.
     *
     * @param x      the left pixel coordinate
     * @param y      the lower pixel coordinate
     * @param width  the number of pixels to read in the x-dimension
     * @param height the number of pixels to read in the y-dimension
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels a buffer in which to place the returned pixel data
     */
    //public void glReadPixels(int x, int y, int width, int height, int format, int type, long pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadPixels">Reference Page</a></p>
     * 
     * ReadPixels obtains values from the selected read buffer from each pixel with lower left hand corner at {@code (x + i, y + j)} for {@code 0 <= i < width}
     * and {@code 0 <= j < height}; this pixel is said to be the i<sup>th</sup> pixel in the j<sup>th</sup> row. If any of these pixels lies outside of the
     * window allocated to the current GL context, or outside of the image attached to the currently bound read framebuffer object, then the values obtained
     * for those pixels are undefined. When {@link GL30#GL_READ_FRAMEBUFFER_BINDING READ_FRAMEBUFFER_BINDING} is zero, values are also undefined for individual pixels that are not owned by
     * the current context. Otherwise, {@code ReadPixels} obtains values from the selected buffer, regardless of how those values were placed there.
     *
     * @param x      the left pixel coordinate
     * @param y      the lower pixel coordinate
     * @param width  the number of pixels to read in the x-dimension
     * @param height the number of pixels to read in the y-dimension
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels a buffer in which to place the returned pixel data
     */
    public void glReadPixels(int x, int y, int width, int height, int format, int type, ShortBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadPixels">Reference Page</a></p>
     * 
     * ReadPixels obtains values from the selected read buffer from each pixel with lower left hand corner at {@code (x + i, y + j)} for {@code 0 <= i < width}
     * and {@code 0 <= j < height}; this pixel is said to be the i<sup>th</sup> pixel in the j<sup>th</sup> row. If any of these pixels lies outside of the
     * window allocated to the current GL context, or outside of the image attached to the currently bound read framebuffer object, then the values obtained
     * for those pixels are undefined. When {@link GL30#GL_READ_FRAMEBUFFER_BINDING READ_FRAMEBUFFER_BINDING} is zero, values are also undefined for individual pixels that are not owned by
     * the current context. Otherwise, {@code ReadPixels} obtains values from the selected buffer, regardless of how those values were placed there.
     *
     * @param x      the left pixel coordinate
     * @param y      the lower pixel coordinate
     * @param width  the number of pixels to read in the x-dimension
     * @param height the number of pixels to read in the y-dimension
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels a buffer in which to place the returned pixel data
     */
    public void glReadPixels(int x, int y, int width, int height, int format, int type, IntBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadPixels">Reference Page</a></p>
     * 
     * ReadPixels obtains values from the selected read buffer from each pixel with lower left hand corner at {@code (x + i, y + j)} for {@code 0 <= i < width}
     * and {@code 0 <= j < height}; this pixel is said to be the i<sup>th</sup> pixel in the j<sup>th</sup> row. If any of these pixels lies outside of the
     * window allocated to the current GL context, or outside of the image attached to the currently bound read framebuffer object, then the values obtained
     * for those pixels are undefined. When {@link GL30#GL_READ_FRAMEBUFFER_BINDING READ_FRAMEBUFFER_BINDING} is zero, values are also undefined for individual pixels that are not owned by
     * the current context. Otherwise, {@code ReadPixels} obtains values from the selected buffer, regardless of how those values were placed there.
     *
     * @param x      the left pixel coordinate
     * @param y      the lower pixel coordinate
     * @param width  the number of pixels to read in the x-dimension
     * @param height the number of pixels to read in the y-dimension
     * @param format the pixel format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type   the pixel type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels a buffer in which to place the returned pixel data
     */
    public void glReadPixels(int x, int y, int width, int height, int format, int type, FloatBuffer pixels);

    // --- [ glScissor ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glScissor">Reference Page</a></p>
     * 
     * Defines the scissor rectangle for all viewports. The scissor test is enabled or disabled for all viewports using {@link #glEnable Enable} or {@link #glDisable Disable}
     * with the symbolic constant {@link #GL_SCISSOR_TEST SCISSOR_TEST}. When disabled, it is as if the scissor test always passes. When enabled, if
     * <code>left <= x<sub>w</sub> < left + width</code> and <code>bottom <= y<sub>w</sub> < bottom + height</code> for the scissor rectangle, then the scissor
     * test passes. Otherwise, the test fails and the fragment is discarded.
     *
     * @param x      the left scissor rectangle coordinate
     * @param y      the bottom scissor rectangle coordinate
     * @param width  the scissor rectangle width
     * @param height the scissor rectangle height
     */
    public void glScissor(int x, int y, int width, int height);

    // --- [ glStencilFunc ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glStencilFunc">Reference Page</a></p>
     * 
     * Controls the stencil test.
     * 
     * <p>{@code ref} is an integer reference value that is used in the unsigned stencil comparison. Stencil comparison operations and queries of {@code ref}
     * clamp its value to the range [0, 2<sup>s</sup> &ndash; 1], where s is the number of bits in the stencil buffer attached to the draw framebuffer. The s
     * least significant bits of {@code mask} are bitwise ANDed with both the reference and the stored stencil value, and the resulting masked values are those that
     * participate in the comparison controlled by {@code func}.</p>
     *
     * @param func the stencil comparison function. One of:<br><table><tr><td>{@link #GL_NEVER NEVER}</td><td>{@link #GL_ALWAYS ALWAYS}</td><td>{@link #GL_LESS LESS}</td><td>{@link #GL_LEQUAL LEQUAL}</td><td>{@link #GL_EQUAL EQUAL}</td><td>{@link #GL_GEQUAL GEQUAL}</td><td>{@link #GL_GREATER GREATER}</td><td>{@link #GL_NOTEQUAL NOTEQUAL}</td></tr></table>
     * @param ref  the reference value
     * @param mask the stencil comparison mask
     */
    public void glStencilFunc(int func, int ref, int mask);

    // --- [ glStencilMask ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glStencilMask">Reference Page</a></p>
     * 
     * Masks the writing of particular bits into the stencil plans.
     * 
     * <p>The least significant s bits of {@code mask}, where s is the number of bits in the stencil buffer, specify an integer mask. Where a 1 appears in this
     * mask, the corresponding bit in the stencil buffer is written; where a 0 appears, the bit is not written.</p>
     *
     * @param mask the stencil mask
     */
    public void glStencilMask(int mask);

    // --- [ glStencilOp ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glStencilOp">Reference Page</a></p>
     * 
     * Indicates what happens to the stored stencil value if this or certain subsequent tests fail or pass.
     * 
     * <p>The supported actions are {@link #GL_KEEP KEEP}, {@link #GL_ZERO ZERO}, {@link #GL_REPLACE REPLACE}, {@link #GL_INCR INCR}, {@link #GL_DECR DECR}, {@link #GL_INVERT INVERT},
     * {@link GL14#GL_INCR_WRAP INCR_WRAP} and {@link GL14#GL_DECR_WRAP DECR_WRAP}. These correspond to keeping the current value, setting to zero, replacing with the reference value,
     * incrementing with saturation, decrementing with saturation, bitwise inverting it, incrementing without saturation, and decrementing without saturation.</p>
     * 
     * <p>For purposes of increment and decrement, the stencil bits are considered as an unsigned integer. Incrementing or decrementing with saturation clamps
     * the stencil value at 0 and the maximum representable value. Incrementing or decrementing without saturation will wrap such that incrementing the maximum
     * representable value results in 0, and decrementing 0 results in the maximum representable value.</p>
     *
     * @param sfail  the action to take if the stencil test fails
     * @param dpfail the action to take if the depth buffer test fails
     * @param dppass the action to take if the depth buffer test passes
     */
    public void glStencilOp(int sfail, int dpfail, int dppass);

    // --- [ glTexEnvi ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexEnvi">Reference Page</a></p>
     * 
     * Sets parameters of the texture environment that specifies how texture values are interpreted when texturing a fragment, or sets per-texture-unit
     * filtering parameters.
     *
     * @param target the texture environment target. One of:<br><table><tr><td>{@link #GL_TEXTURE_ENV TEXTURE_ENV}</td><td>{@link GL14#GL_TEXTURE_FILTER_CONTROL TEXTURE_FILTER_CONTROL}</td><td>{@link GL20#GL_POINT_SPRITE POINT_SPRITE}</td></tr></table>
     * @param pname  the parameter to set. One of:<br><table><tr><td>{@link GL20#GL_COORD_REPLACE COORD_REPLACE}</td><td>{@link #GL_TEXTURE_ENV_MODE TEXTURE_ENV_MODE}</td><td>{@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}</td><td>{@link GL13#GL_COMBINE_RGB COMBINE_RGB}</td><td>{@link GL13#GL_COMBINE_ALPHA COMBINE_ALPHA}</td><td>{@link GL15#GL_SRC0_RGB SRC0_RGB}</td></tr><tr><td>{@link GL15#GL_SRC1_RGB SRC1_RGB}</td><td>{@link GL15#GL_SRC2_RGB SRC2_RGB}</td><td>{@link GL15#GL_SRC0_ALPHA SRC0_ALPHA}</td><td>{@link GL15#GL_SRC1_ALPHA SRC1_ALPHA}</td><td>{@link GL15#GL_SRC2_ALPHA SRC2_ALPHA}</td><td>{@link GL13#GL_OPERAND0_RGB OPERAND0_RGB}</td></tr><tr><td>{@link GL13#GL_OPERAND1_RGB OPERAND1_RGB}</td><td>{@link GL13#GL_OPERAND2_RGB OPERAND2_RGB}</td><td>{@link GL13#GL_OPERAND0_ALPHA OPERAND0_ALPHA}</td><td>{@link GL13#GL_OPERAND1_ALPHA OPERAND1_ALPHA}</td><td>{@link GL13#GL_OPERAND2_ALPHA OPERAND2_ALPHA}</td><td>{@link GL13#GL_RGB_SCALE RGB_SCALE}</td></tr><tr><td>{@link #GL_ALPHA_SCALE ALPHA_SCALE}</td></tr></table>
     * @param param  the parameter value. Scalar value or one of:<br><table><tr><td>{@link #GL_REPLACE REPLACE}</td><td>{@link #GL_MODULATE MODULATE}</td><td>{@link #GL_DECAL DECAL}</td><td>{@link #GL_BLEND BLEND}</td><td>{@link #GL_ADD ADD}</td><td>{@link GL13#GL_COMBINE COMBINE}</td><td>{@link GL13#GL_ADD_SIGNED ADD_SIGNED}</td><td>{@link GL13#GL_INTERPOLATE INTERPOLATE}</td></tr><tr><td>{@link GL13#GL_SUBTRACT SUBTRACT}</td><td>{@link GL13#GL_DOT3_RGB DOT3_RGB}</td><td>{@link GL13#GL_DOT3_RGBA DOT3_RGBA}</td><td>{@link #GL_TEXTURE TEXTURE}</td><td>{@link GL13#GL_TEXTURE0 TEXTURE0}</td><td>GL13.GL_TEXTURE[1-31]</td><td>{@link GL13#GL_CONSTANT CONSTANT}</td><td>{@link GL13#GL_PRIMARY_COLOR PRIMARY_COLOR}</td></tr><tr><td>{@link GL13#GL_PREVIOUS PREVIOUS}</td></tr></table>
     */
    //public void glTexEnvi(int target, int pname, int param);

    // --- [ glTexEnviv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexEnv">Reference Page</a></p>
     * 
     * Pointer version of {@link #glTexEnvi TexEnvi}.
     *
     * @param target the texture environment target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_ENV TEXTURE_ENV}</td></tr></table>
     * @param pname  the parameter to set. Must be:<br><table><tr><td>{@link #GL_TEXTURE_ENV_COLOR TEXTURE_ENV_COLOR}</td></tr></table>
     * @param params the parameter value
     */
    //public void glTexEnviv(int target, int pname, IntBuffer params);

    // --- [ glTexEnvf ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexEnvf">Reference Page</a></p>
     * 
     * Float version of {@link #glTexEnvi TexEnvi}.
     *
     * @param target the texture environment target
     * @param pname  the parameter to set
     * @param param  the parameter value
     */
    //public void glTexEnvf(int target, int pname, float param);

    // --- [ glTexEnvfv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexEnv">Reference Page</a></p>
     * 
     * Pointer version of {@link #glTexEnvf TexEnvf}.
     *
     * @param target the texture environment target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_ENV TEXTURE_ENV}</td></tr></table>
     * @param pname  the parameter to set. Must be:<br><table><tr><td>{@link #GL_TEXTURE_ENV_COLOR TEXTURE_ENV_COLOR}</td></tr></table>
     * @param params the parameter value
     */
    //public void glTexEnvfv(int target, int pname, FloatBuffer params);

    // --- [ glTexImage2D ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Specifies a two-dimensional texture image.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_PROXY_TEXTURE_2D PROXY_TEXTURE_2D}</td><td>{@link GL30#GL_PROXY_TEXTURE_1D_ARRAY PROXY_TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_PROXY_TEXTURE_RECTANGLE PROXY_TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_PROXY_TEXTURE_CUBE_MAP PROXY_TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format. One of:<br><table><tr><td>{@link #GL_RED RED}</td><td>{@link GL30#GL_RG RG}</td><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td></tr><tr><td>{@link GL30#GL_R8 R8}</td><td>{@link GL31#GL_R8_SNORM R8_SNORM}</td><td>{@link GL30#GL_R16 R16}</td><td>{@link GL31#GL_R16_SNORM R16_SNORM}</td><td>{@link GL30#GL_RG8 RG8}</td><td>{@link GL31#GL_RG8_SNORM RG8_SNORM}</td></tr><tr><td>{@link GL30#GL_RG16 RG16}</td><td>{@link GL31#GL_RG16_SNORM RG16_SNORM}</td><td>{@link #GL_R3_G3_B2 R3_G3_B2}</td><td>{@link #GL_RGB4 RGB4}</td><td>{@link #GL_RGB5 RGB5}</td><td>{@link GL41#GL_RGB565 RGB565}</td></tr><tr><td>{@link #GL_RGB8 RGB8}</td><td>{@link GL31#GL_RGB8_SNORM RGB8_SNORM}</td><td>{@link #GL_RGB10 RGB10}</td><td>{@link #GL_RGB12 RGB12}</td><td>{@link #GL_RGB16 RGB16}</td><td>{@link GL31#GL_RGB16_SNORM RGB16_SNORM}</td></tr><tr><td>{@link #GL_RGBA2 RGBA2}</td><td>{@link #GL_RGBA4 RGBA4}</td><td>{@link #GL_RGB5_A1 RGB5_A1}</td><td>{@link #GL_RGBA8 RGBA8}</td><td>{@link GL31#GL_RGBA8_SNORM RGBA8_SNORM}</td><td>{@link #GL_RGB10_A2 RGB10_A2}</td></tr><tr><td>{@link GL33#GL_RGB10_A2UI RGB10_A2UI}</td><td>{@link #GL_RGBA12 RGBA12}</td><td>{@link #GL_RGBA16 RGBA16}</td><td>{@link GL31#GL_RGBA16_SNORM RGBA16_SNORM}</td><td>{@link GL21#GL_SRGB8 SRGB8}</td><td>{@link GL21#GL_SRGB8_ALPHA8 SRGB8_ALPHA8}</td></tr><tr><td>{@link GL30#GL_R16F R16F}</td><td>{@link GL30#GL_RG16F RG16F}</td><td>{@link GL30#GL_RGB16F RGB16F}</td><td>{@link GL30#GL_RGBA16F RGBA16F}</td><td>{@link GL30#GL_R32F R32F}</td><td>{@link GL30#GL_RG32F RG32F}</td></tr><tr><td>{@link GL30#GL_RGB32F RGB32F}</td><td>{@link GL30#GL_RGBA32F RGBA32F}</td><td>{@link GL30#GL_R11F_G11F_B10F R11F_G11F_B10F}</td><td>{@link GL30#GL_RGB9_E5 RGB9_E5}</td><td>{@link GL30#GL_R8I R8I}</td><td>{@link GL30#GL_R8UI R8UI}</td></tr><tr><td>{@link GL30#GL_R16I R16I}</td><td>{@link GL30#GL_R16UI R16UI}</td><td>{@link GL30#GL_R32I R32I}</td><td>{@link GL30#GL_R32UI R32UI}</td><td>{@link GL30#GL_RG8I RG8I}</td><td>{@link GL30#GL_RG8UI RG8UI}</td></tr><tr><td>{@link GL30#GL_RG16I RG16I}</td><td>{@link GL30#GL_RG16UI RG16UI}</td><td>{@link GL30#GL_RG32I RG32I}</td><td>{@link GL30#GL_RG32UI RG32UI}</td><td>{@link GL30#GL_RGB8I RGB8I}</td><td>{@link GL30#GL_RGB8UI RGB8UI}</td></tr><tr><td>{@link GL30#GL_RGB16I RGB16I}</td><td>{@link GL30#GL_RGB16UI RGB16UI}</td><td>{@link GL30#GL_RGB32I RGB32I}</td><td>{@link GL30#GL_RGB32UI RGB32UI}</td><td>{@link GL30#GL_RGBA8I RGBA8I}</td><td>{@link GL30#GL_RGBA8UI RGBA8UI}</td></tr><tr><td>{@link GL30#GL_RGBA16I RGBA16I}</td><td>{@link GL30#GL_RGBA16UI RGBA16UI}</td><td>{@link GL30#GL_RGBA32I RGBA32I}</td><td>{@link GL30#GL_RGBA32UI RGBA32UI}</td><td>{@link GL14#GL_DEPTH_COMPONENT16 DEPTH_COMPONENT16}</td><td>{@link GL14#GL_DEPTH_COMPONENT24 DEPTH_COMPONENT24}</td></tr><tr><td>{@link GL14#GL_DEPTH_COMPONENT32 DEPTH_COMPONENT32}</td><td>{@link GL30#GL_DEPTH24_STENCIL8 DEPTH24_STENCIL8}</td><td>{@link GL30#GL_DEPTH_COMPONENT32F DEPTH_COMPONENT32F}</td><td>{@link GL30#GL_DEPTH32F_STENCIL8 DEPTH32F_STENCIL8}</td><td>{@link GL30#GL_COMPRESSED_RED COMPRESSED_RED}</td><td>{@link GL30#GL_COMPRESSED_RG COMPRESSED_RG}</td></tr><tr><td>{@link GL13#GL_COMPRESSED_RGB COMPRESSED_RGB}</td><td>{@link GL13#GL_COMPRESSED_RGBA COMPRESSED_RGBA}</td><td>{@link GL21#GL_COMPRESSED_SRGB COMPRESSED_SRGB}</td><td>{@link GL21#GL_COMPRESSED_SRGB_ALPHA COMPRESSED_SRGB_ALPHA}</td><td>{@link GL30#GL_COMPRESSED_RED_RGTC1 COMPRESSED_RED_RGTC1}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RED_RGTC1 COMPRESSED_SIGNED_RED_RGTC1}</td></tr><tr><td>{@link GL30#GL_COMPRESSED_RG_RGTC2 COMPRESSED_RG_RGTC2}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RG_RGTC2 COMPRESSED_SIGNED_RG_RGTC2}</td><td>{@link GL42#GL_COMPRESSED_RGBA_BPTC_UNORM COMPRESSED_RGBA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM COMPRESSED_SRGB_ALPHA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT COMPRESSED_RGB_BPTC_SIGNED_FLOAT}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_RGB8_ETC2 COMPRESSED_RGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ETC2 COMPRESSED_SRGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGBA8_ETC2_EAC COMPRESSED_RGBA8_ETC2_EAC}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_R11_EAC COMPRESSED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_R11_EAC COMPRESSED_SIGNED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_RG11_EAC COMPRESSED_RG11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_RG11_EAC COMPRESSED_SIGNED_RG11_EAC}</td><td>see {@link EXTTextureCompressionS3TC}</td><td>see {@link EXTTextureCompressionLATC}</td></tr><tr><td>see {@link ATITextureCompression3DC}</td></tr></table>
     * @param width          the texture width
     * @param height         the texture height
     * @param border         the texture border width
     * @param format         the texel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type           the texel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels         the texel data
     */
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ ByteBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Specifies a two-dimensional texture image.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_PROXY_TEXTURE_2D PROXY_TEXTURE_2D}</td><td>{@link GL30#GL_PROXY_TEXTURE_1D_ARRAY PROXY_TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_PROXY_TEXTURE_RECTANGLE PROXY_TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_PROXY_TEXTURE_CUBE_MAP PROXY_TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format. One of:<br><table><tr><td>{@link #GL_RED RED}</td><td>{@link GL30#GL_RG RG}</td><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td></tr><tr><td>{@link GL30#GL_R8 R8}</td><td>{@link GL31#GL_R8_SNORM R8_SNORM}</td><td>{@link GL30#GL_R16 R16}</td><td>{@link GL31#GL_R16_SNORM R16_SNORM}</td><td>{@link GL30#GL_RG8 RG8}</td><td>{@link GL31#GL_RG8_SNORM RG8_SNORM}</td></tr><tr><td>{@link GL30#GL_RG16 RG16}</td><td>{@link GL31#GL_RG16_SNORM RG16_SNORM}</td><td>{@link #GL_R3_G3_B2 R3_G3_B2}</td><td>{@link #GL_RGB4 RGB4}</td><td>{@link #GL_RGB5 RGB5}</td><td>{@link GL41#GL_RGB565 RGB565}</td></tr><tr><td>{@link #GL_RGB8 RGB8}</td><td>{@link GL31#GL_RGB8_SNORM RGB8_SNORM}</td><td>{@link #GL_RGB10 RGB10}</td><td>{@link #GL_RGB12 RGB12}</td><td>{@link #GL_RGB16 RGB16}</td><td>{@link GL31#GL_RGB16_SNORM RGB16_SNORM}</td></tr><tr><td>{@link #GL_RGBA2 RGBA2}</td><td>{@link #GL_RGBA4 RGBA4}</td><td>{@link #GL_RGB5_A1 RGB5_A1}</td><td>{@link #GL_RGBA8 RGBA8}</td><td>{@link GL31#GL_RGBA8_SNORM RGBA8_SNORM}</td><td>{@link #GL_RGB10_A2 RGB10_A2}</td></tr><tr><td>{@link GL33#GL_RGB10_A2UI RGB10_A2UI}</td><td>{@link #GL_RGBA12 RGBA12}</td><td>{@link #GL_RGBA16 RGBA16}</td><td>{@link GL31#GL_RGBA16_SNORM RGBA16_SNORM}</td><td>{@link GL21#GL_SRGB8 SRGB8}</td><td>{@link GL21#GL_SRGB8_ALPHA8 SRGB8_ALPHA8}</td></tr><tr><td>{@link GL30#GL_R16F R16F}</td><td>{@link GL30#GL_RG16F RG16F}</td><td>{@link GL30#GL_RGB16F RGB16F}</td><td>{@link GL30#GL_RGBA16F RGBA16F}</td><td>{@link GL30#GL_R32F R32F}</td><td>{@link GL30#GL_RG32F RG32F}</td></tr><tr><td>{@link GL30#GL_RGB32F RGB32F}</td><td>{@link GL30#GL_RGBA32F RGBA32F}</td><td>{@link GL30#GL_R11F_G11F_B10F R11F_G11F_B10F}</td><td>{@link GL30#GL_RGB9_E5 RGB9_E5}</td><td>{@link GL30#GL_R8I R8I}</td><td>{@link GL30#GL_R8UI R8UI}</td></tr><tr><td>{@link GL30#GL_R16I R16I}</td><td>{@link GL30#GL_R16UI R16UI}</td><td>{@link GL30#GL_R32I R32I}</td><td>{@link GL30#GL_R32UI R32UI}</td><td>{@link GL30#GL_RG8I RG8I}</td><td>{@link GL30#GL_RG8UI RG8UI}</td></tr><tr><td>{@link GL30#GL_RG16I RG16I}</td><td>{@link GL30#GL_RG16UI RG16UI}</td><td>{@link GL30#GL_RG32I RG32I}</td><td>{@link GL30#GL_RG32UI RG32UI}</td><td>{@link GL30#GL_RGB8I RGB8I}</td><td>{@link GL30#GL_RGB8UI RGB8UI}</td></tr><tr><td>{@link GL30#GL_RGB16I RGB16I}</td><td>{@link GL30#GL_RGB16UI RGB16UI}</td><td>{@link GL30#GL_RGB32I RGB32I}</td><td>{@link GL30#GL_RGB32UI RGB32UI}</td><td>{@link GL30#GL_RGBA8I RGBA8I}</td><td>{@link GL30#GL_RGBA8UI RGBA8UI}</td></tr><tr><td>{@link GL30#GL_RGBA16I RGBA16I}</td><td>{@link GL30#GL_RGBA16UI RGBA16UI}</td><td>{@link GL30#GL_RGBA32I RGBA32I}</td><td>{@link GL30#GL_RGBA32UI RGBA32UI}</td><td>{@link GL14#GL_DEPTH_COMPONENT16 DEPTH_COMPONENT16}</td><td>{@link GL14#GL_DEPTH_COMPONENT24 DEPTH_COMPONENT24}</td></tr><tr><td>{@link GL14#GL_DEPTH_COMPONENT32 DEPTH_COMPONENT32}</td><td>{@link GL30#GL_DEPTH24_STENCIL8 DEPTH24_STENCIL8}</td><td>{@link GL30#GL_DEPTH_COMPONENT32F DEPTH_COMPONENT32F}</td><td>{@link GL30#GL_DEPTH32F_STENCIL8 DEPTH32F_STENCIL8}</td><td>{@link GL30#GL_COMPRESSED_RED COMPRESSED_RED}</td><td>{@link GL30#GL_COMPRESSED_RG COMPRESSED_RG}</td></tr><tr><td>{@link GL13#GL_COMPRESSED_RGB COMPRESSED_RGB}</td><td>{@link GL13#GL_COMPRESSED_RGBA COMPRESSED_RGBA}</td><td>{@link GL21#GL_COMPRESSED_SRGB COMPRESSED_SRGB}</td><td>{@link GL21#GL_COMPRESSED_SRGB_ALPHA COMPRESSED_SRGB_ALPHA}</td><td>{@link GL30#GL_COMPRESSED_RED_RGTC1 COMPRESSED_RED_RGTC1}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RED_RGTC1 COMPRESSED_SIGNED_RED_RGTC1}</td></tr><tr><td>{@link GL30#GL_COMPRESSED_RG_RGTC2 COMPRESSED_RG_RGTC2}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RG_RGTC2 COMPRESSED_SIGNED_RG_RGTC2}</td><td>{@link GL42#GL_COMPRESSED_RGBA_BPTC_UNORM COMPRESSED_RGBA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM COMPRESSED_SRGB_ALPHA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT COMPRESSED_RGB_BPTC_SIGNED_FLOAT}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_RGB8_ETC2 COMPRESSED_RGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ETC2 COMPRESSED_SRGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGBA8_ETC2_EAC COMPRESSED_RGBA8_ETC2_EAC}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_R11_EAC COMPRESSED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_R11_EAC COMPRESSED_SIGNED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_RG11_EAC COMPRESSED_RG11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_RG11_EAC COMPRESSED_SIGNED_RG11_EAC}</td><td>see {@link EXTTextureCompressionS3TC}</td><td>see {@link EXTTextureCompressionLATC}</td></tr><tr><td>see {@link ATITextureCompression3DC}</td></tr></table>
     * @param width          the texture width
     * @param height         the texture height
     * @param border         the texture border width
     * @param format         the texel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type           the texel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels         the texel data
     */
    //public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ long pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Specifies a two-dimensional texture image.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_PROXY_TEXTURE_2D PROXY_TEXTURE_2D}</td><td>{@link GL30#GL_PROXY_TEXTURE_1D_ARRAY PROXY_TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_PROXY_TEXTURE_RECTANGLE PROXY_TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_PROXY_TEXTURE_CUBE_MAP PROXY_TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format. One of:<br><table><tr><td>{@link #GL_RED RED}</td><td>{@link GL30#GL_RG RG}</td><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td></tr><tr><td>{@link GL30#GL_R8 R8}</td><td>{@link GL31#GL_R8_SNORM R8_SNORM}</td><td>{@link GL30#GL_R16 R16}</td><td>{@link GL31#GL_R16_SNORM R16_SNORM}</td><td>{@link GL30#GL_RG8 RG8}</td><td>{@link GL31#GL_RG8_SNORM RG8_SNORM}</td></tr><tr><td>{@link GL30#GL_RG16 RG16}</td><td>{@link GL31#GL_RG16_SNORM RG16_SNORM}</td><td>{@link #GL_R3_G3_B2 R3_G3_B2}</td><td>{@link #GL_RGB4 RGB4}</td><td>{@link #GL_RGB5 RGB5}</td><td>{@link GL41#GL_RGB565 RGB565}</td></tr><tr><td>{@link #GL_RGB8 RGB8}</td><td>{@link GL31#GL_RGB8_SNORM RGB8_SNORM}</td><td>{@link #GL_RGB10 RGB10}</td><td>{@link #GL_RGB12 RGB12}</td><td>{@link #GL_RGB16 RGB16}</td><td>{@link GL31#GL_RGB16_SNORM RGB16_SNORM}</td></tr><tr><td>{@link #GL_RGBA2 RGBA2}</td><td>{@link #GL_RGBA4 RGBA4}</td><td>{@link #GL_RGB5_A1 RGB5_A1}</td><td>{@link #GL_RGBA8 RGBA8}</td><td>{@link GL31#GL_RGBA8_SNORM RGBA8_SNORM}</td><td>{@link #GL_RGB10_A2 RGB10_A2}</td></tr><tr><td>{@link GL33#GL_RGB10_A2UI RGB10_A2UI}</td><td>{@link #GL_RGBA12 RGBA12}</td><td>{@link #GL_RGBA16 RGBA16}</td><td>{@link GL31#GL_RGBA16_SNORM RGBA16_SNORM}</td><td>{@link GL21#GL_SRGB8 SRGB8}</td><td>{@link GL21#GL_SRGB8_ALPHA8 SRGB8_ALPHA8}</td></tr><tr><td>{@link GL30#GL_R16F R16F}</td><td>{@link GL30#GL_RG16F RG16F}</td><td>{@link GL30#GL_RGB16F RGB16F}</td><td>{@link GL30#GL_RGBA16F RGBA16F}</td><td>{@link GL30#GL_R32F R32F}</td><td>{@link GL30#GL_RG32F RG32F}</td></tr><tr><td>{@link GL30#GL_RGB32F RGB32F}</td><td>{@link GL30#GL_RGBA32F RGBA32F}</td><td>{@link GL30#GL_R11F_G11F_B10F R11F_G11F_B10F}</td><td>{@link GL30#GL_RGB9_E5 RGB9_E5}</td><td>{@link GL30#GL_R8I R8I}</td><td>{@link GL30#GL_R8UI R8UI}</td></tr><tr><td>{@link GL30#GL_R16I R16I}</td><td>{@link GL30#GL_R16UI R16UI}</td><td>{@link GL30#GL_R32I R32I}</td><td>{@link GL30#GL_R32UI R32UI}</td><td>{@link GL30#GL_RG8I RG8I}</td><td>{@link GL30#GL_RG8UI RG8UI}</td></tr><tr><td>{@link GL30#GL_RG16I RG16I}</td><td>{@link GL30#GL_RG16UI RG16UI}</td><td>{@link GL30#GL_RG32I RG32I}</td><td>{@link GL30#GL_RG32UI RG32UI}</td><td>{@link GL30#GL_RGB8I RGB8I}</td><td>{@link GL30#GL_RGB8UI RGB8UI}</td></tr><tr><td>{@link GL30#GL_RGB16I RGB16I}</td><td>{@link GL30#GL_RGB16UI RGB16UI}</td><td>{@link GL30#GL_RGB32I RGB32I}</td><td>{@link GL30#GL_RGB32UI RGB32UI}</td><td>{@link GL30#GL_RGBA8I RGBA8I}</td><td>{@link GL30#GL_RGBA8UI RGBA8UI}</td></tr><tr><td>{@link GL30#GL_RGBA16I RGBA16I}</td><td>{@link GL30#GL_RGBA16UI RGBA16UI}</td><td>{@link GL30#GL_RGBA32I RGBA32I}</td><td>{@link GL30#GL_RGBA32UI RGBA32UI}</td><td>{@link GL14#GL_DEPTH_COMPONENT16 DEPTH_COMPONENT16}</td><td>{@link GL14#GL_DEPTH_COMPONENT24 DEPTH_COMPONENT24}</td></tr><tr><td>{@link GL14#GL_DEPTH_COMPONENT32 DEPTH_COMPONENT32}</td><td>{@link GL30#GL_DEPTH24_STENCIL8 DEPTH24_STENCIL8}</td><td>{@link GL30#GL_DEPTH_COMPONENT32F DEPTH_COMPONENT32F}</td><td>{@link GL30#GL_DEPTH32F_STENCIL8 DEPTH32F_STENCIL8}</td><td>{@link GL30#GL_COMPRESSED_RED COMPRESSED_RED}</td><td>{@link GL30#GL_COMPRESSED_RG COMPRESSED_RG}</td></tr><tr><td>{@link GL13#GL_COMPRESSED_RGB COMPRESSED_RGB}</td><td>{@link GL13#GL_COMPRESSED_RGBA COMPRESSED_RGBA}</td><td>{@link GL21#GL_COMPRESSED_SRGB COMPRESSED_SRGB}</td><td>{@link GL21#GL_COMPRESSED_SRGB_ALPHA COMPRESSED_SRGB_ALPHA}</td><td>{@link GL30#GL_COMPRESSED_RED_RGTC1 COMPRESSED_RED_RGTC1}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RED_RGTC1 COMPRESSED_SIGNED_RED_RGTC1}</td></tr><tr><td>{@link GL30#GL_COMPRESSED_RG_RGTC2 COMPRESSED_RG_RGTC2}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RG_RGTC2 COMPRESSED_SIGNED_RG_RGTC2}</td><td>{@link GL42#GL_COMPRESSED_RGBA_BPTC_UNORM COMPRESSED_RGBA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM COMPRESSED_SRGB_ALPHA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT COMPRESSED_RGB_BPTC_SIGNED_FLOAT}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_RGB8_ETC2 COMPRESSED_RGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ETC2 COMPRESSED_SRGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGBA8_ETC2_EAC COMPRESSED_RGBA8_ETC2_EAC}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_R11_EAC COMPRESSED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_R11_EAC COMPRESSED_SIGNED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_RG11_EAC COMPRESSED_RG11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_RG11_EAC COMPRESSED_SIGNED_RG11_EAC}</td><td>see {@link EXTTextureCompressionS3TC}</td><td>see {@link EXTTextureCompressionLATC}</td></tr><tr><td>see {@link ATITextureCompression3DC}</td></tr></table>
     * @param width          the texture width
     * @param height         the texture height
     * @param border         the texture border width
     * @param format         the texel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type           the texel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels         the texel data
     */
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ ShortBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Specifies a two-dimensional texture image.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_PROXY_TEXTURE_2D PROXY_TEXTURE_2D}</td><td>{@link GL30#GL_PROXY_TEXTURE_1D_ARRAY PROXY_TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_PROXY_TEXTURE_RECTANGLE PROXY_TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_PROXY_TEXTURE_CUBE_MAP PROXY_TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format. One of:<br><table><tr><td>{@link #GL_RED RED}</td><td>{@link GL30#GL_RG RG}</td><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td></tr><tr><td>{@link GL30#GL_R8 R8}</td><td>{@link GL31#GL_R8_SNORM R8_SNORM}</td><td>{@link GL30#GL_R16 R16}</td><td>{@link GL31#GL_R16_SNORM R16_SNORM}</td><td>{@link GL30#GL_RG8 RG8}</td><td>{@link GL31#GL_RG8_SNORM RG8_SNORM}</td></tr><tr><td>{@link GL30#GL_RG16 RG16}</td><td>{@link GL31#GL_RG16_SNORM RG16_SNORM}</td><td>{@link #GL_R3_G3_B2 R3_G3_B2}</td><td>{@link #GL_RGB4 RGB4}</td><td>{@link #GL_RGB5 RGB5}</td><td>{@link GL41#GL_RGB565 RGB565}</td></tr><tr><td>{@link #GL_RGB8 RGB8}</td><td>{@link GL31#GL_RGB8_SNORM RGB8_SNORM}</td><td>{@link #GL_RGB10 RGB10}</td><td>{@link #GL_RGB12 RGB12}</td><td>{@link #GL_RGB16 RGB16}</td><td>{@link GL31#GL_RGB16_SNORM RGB16_SNORM}</td></tr><tr><td>{@link #GL_RGBA2 RGBA2}</td><td>{@link #GL_RGBA4 RGBA4}</td><td>{@link #GL_RGB5_A1 RGB5_A1}</td><td>{@link #GL_RGBA8 RGBA8}</td><td>{@link GL31#GL_RGBA8_SNORM RGBA8_SNORM}</td><td>{@link #GL_RGB10_A2 RGB10_A2}</td></tr><tr><td>{@link GL33#GL_RGB10_A2UI RGB10_A2UI}</td><td>{@link #GL_RGBA12 RGBA12}</td><td>{@link #GL_RGBA16 RGBA16}</td><td>{@link GL31#GL_RGBA16_SNORM RGBA16_SNORM}</td><td>{@link GL21#GL_SRGB8 SRGB8}</td><td>{@link GL21#GL_SRGB8_ALPHA8 SRGB8_ALPHA8}</td></tr><tr><td>{@link GL30#GL_R16F R16F}</td><td>{@link GL30#GL_RG16F RG16F}</td><td>{@link GL30#GL_RGB16F RGB16F}</td><td>{@link GL30#GL_RGBA16F RGBA16F}</td><td>{@link GL30#GL_R32F R32F}</td><td>{@link GL30#GL_RG32F RG32F}</td></tr><tr><td>{@link GL30#GL_RGB32F RGB32F}</td><td>{@link GL30#GL_RGBA32F RGBA32F}</td><td>{@link GL30#GL_R11F_G11F_B10F R11F_G11F_B10F}</td><td>{@link GL30#GL_RGB9_E5 RGB9_E5}</td><td>{@link GL30#GL_R8I R8I}</td><td>{@link GL30#GL_R8UI R8UI}</td></tr><tr><td>{@link GL30#GL_R16I R16I}</td><td>{@link GL30#GL_R16UI R16UI}</td><td>{@link GL30#GL_R32I R32I}</td><td>{@link GL30#GL_R32UI R32UI}</td><td>{@link GL30#GL_RG8I RG8I}</td><td>{@link GL30#GL_RG8UI RG8UI}</td></tr><tr><td>{@link GL30#GL_RG16I RG16I}</td><td>{@link GL30#GL_RG16UI RG16UI}</td><td>{@link GL30#GL_RG32I RG32I}</td><td>{@link GL30#GL_RG32UI RG32UI}</td><td>{@link GL30#GL_RGB8I RGB8I}</td><td>{@link GL30#GL_RGB8UI RGB8UI}</td></tr><tr><td>{@link GL30#GL_RGB16I RGB16I}</td><td>{@link GL30#GL_RGB16UI RGB16UI}</td><td>{@link GL30#GL_RGB32I RGB32I}</td><td>{@link GL30#GL_RGB32UI RGB32UI}</td><td>{@link GL30#GL_RGBA8I RGBA8I}</td><td>{@link GL30#GL_RGBA8UI RGBA8UI}</td></tr><tr><td>{@link GL30#GL_RGBA16I RGBA16I}</td><td>{@link GL30#GL_RGBA16UI RGBA16UI}</td><td>{@link GL30#GL_RGBA32I RGBA32I}</td><td>{@link GL30#GL_RGBA32UI RGBA32UI}</td><td>{@link GL14#GL_DEPTH_COMPONENT16 DEPTH_COMPONENT16}</td><td>{@link GL14#GL_DEPTH_COMPONENT24 DEPTH_COMPONENT24}</td></tr><tr><td>{@link GL14#GL_DEPTH_COMPONENT32 DEPTH_COMPONENT32}</td><td>{@link GL30#GL_DEPTH24_STENCIL8 DEPTH24_STENCIL8}</td><td>{@link GL30#GL_DEPTH_COMPONENT32F DEPTH_COMPONENT32F}</td><td>{@link GL30#GL_DEPTH32F_STENCIL8 DEPTH32F_STENCIL8}</td><td>{@link GL30#GL_COMPRESSED_RED COMPRESSED_RED}</td><td>{@link GL30#GL_COMPRESSED_RG COMPRESSED_RG}</td></tr><tr><td>{@link GL13#GL_COMPRESSED_RGB COMPRESSED_RGB}</td><td>{@link GL13#GL_COMPRESSED_RGBA COMPRESSED_RGBA}</td><td>{@link GL21#GL_COMPRESSED_SRGB COMPRESSED_SRGB}</td><td>{@link GL21#GL_COMPRESSED_SRGB_ALPHA COMPRESSED_SRGB_ALPHA}</td><td>{@link GL30#GL_COMPRESSED_RED_RGTC1 COMPRESSED_RED_RGTC1}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RED_RGTC1 COMPRESSED_SIGNED_RED_RGTC1}</td></tr><tr><td>{@link GL30#GL_COMPRESSED_RG_RGTC2 COMPRESSED_RG_RGTC2}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RG_RGTC2 COMPRESSED_SIGNED_RG_RGTC2}</td><td>{@link GL42#GL_COMPRESSED_RGBA_BPTC_UNORM COMPRESSED_RGBA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM COMPRESSED_SRGB_ALPHA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT COMPRESSED_RGB_BPTC_SIGNED_FLOAT}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_RGB8_ETC2 COMPRESSED_RGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ETC2 COMPRESSED_SRGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGBA8_ETC2_EAC COMPRESSED_RGBA8_ETC2_EAC}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_R11_EAC COMPRESSED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_R11_EAC COMPRESSED_SIGNED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_RG11_EAC COMPRESSED_RG11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_RG11_EAC COMPRESSED_SIGNED_RG11_EAC}</td><td>see {@link EXTTextureCompressionS3TC}</td><td>see {@link EXTTextureCompressionLATC}</td></tr><tr><td>see {@link ATITextureCompression3DC}</td></tr></table>
     * @param width          the texture width
     * @param height         the texture height
     * @param border         the texture border width
     * @param format         the texel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type           the texel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels         the texel data
     */
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ IntBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Specifies a two-dimensional texture image.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_PROXY_TEXTURE_2D PROXY_TEXTURE_2D}</td><td>{@link GL30#GL_PROXY_TEXTURE_1D_ARRAY PROXY_TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_PROXY_TEXTURE_RECTANGLE PROXY_TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_PROXY_TEXTURE_CUBE_MAP PROXY_TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format. One of:<br><table><tr><td>{@link #GL_RED RED}</td><td>{@link GL30#GL_RG RG}</td><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td></tr><tr><td>{@link GL30#GL_R8 R8}</td><td>{@link GL31#GL_R8_SNORM R8_SNORM}</td><td>{@link GL30#GL_R16 R16}</td><td>{@link GL31#GL_R16_SNORM R16_SNORM}</td><td>{@link GL30#GL_RG8 RG8}</td><td>{@link GL31#GL_RG8_SNORM RG8_SNORM}</td></tr><tr><td>{@link GL30#GL_RG16 RG16}</td><td>{@link GL31#GL_RG16_SNORM RG16_SNORM}</td><td>{@link #GL_R3_G3_B2 R3_G3_B2}</td><td>{@link #GL_RGB4 RGB4}</td><td>{@link #GL_RGB5 RGB5}</td><td>{@link GL41#GL_RGB565 RGB565}</td></tr><tr><td>{@link #GL_RGB8 RGB8}</td><td>{@link GL31#GL_RGB8_SNORM RGB8_SNORM}</td><td>{@link #GL_RGB10 RGB10}</td><td>{@link #GL_RGB12 RGB12}</td><td>{@link #GL_RGB16 RGB16}</td><td>{@link GL31#GL_RGB16_SNORM RGB16_SNORM}</td></tr><tr><td>{@link #GL_RGBA2 RGBA2}</td><td>{@link #GL_RGBA4 RGBA4}</td><td>{@link #GL_RGB5_A1 RGB5_A1}</td><td>{@link #GL_RGBA8 RGBA8}</td><td>{@link GL31#GL_RGBA8_SNORM RGBA8_SNORM}</td><td>{@link #GL_RGB10_A2 RGB10_A2}</td></tr><tr><td>{@link GL33#GL_RGB10_A2UI RGB10_A2UI}</td><td>{@link #GL_RGBA12 RGBA12}</td><td>{@link #GL_RGBA16 RGBA16}</td><td>{@link GL31#GL_RGBA16_SNORM RGBA16_SNORM}</td><td>{@link GL21#GL_SRGB8 SRGB8}</td><td>{@link GL21#GL_SRGB8_ALPHA8 SRGB8_ALPHA8}</td></tr><tr><td>{@link GL30#GL_R16F R16F}</td><td>{@link GL30#GL_RG16F RG16F}</td><td>{@link GL30#GL_RGB16F RGB16F}</td><td>{@link GL30#GL_RGBA16F RGBA16F}</td><td>{@link GL30#GL_R32F R32F}</td><td>{@link GL30#GL_RG32F RG32F}</td></tr><tr><td>{@link GL30#GL_RGB32F RGB32F}</td><td>{@link GL30#GL_RGBA32F RGBA32F}</td><td>{@link GL30#GL_R11F_G11F_B10F R11F_G11F_B10F}</td><td>{@link GL30#GL_RGB9_E5 RGB9_E5}</td><td>{@link GL30#GL_R8I R8I}</td><td>{@link GL30#GL_R8UI R8UI}</td></tr><tr><td>{@link GL30#GL_R16I R16I}</td><td>{@link GL30#GL_R16UI R16UI}</td><td>{@link GL30#GL_R32I R32I}</td><td>{@link GL30#GL_R32UI R32UI}</td><td>{@link GL30#GL_RG8I RG8I}</td><td>{@link GL30#GL_RG8UI RG8UI}</td></tr><tr><td>{@link GL30#GL_RG16I RG16I}</td><td>{@link GL30#GL_RG16UI RG16UI}</td><td>{@link GL30#GL_RG32I RG32I}</td><td>{@link GL30#GL_RG32UI RG32UI}</td><td>{@link GL30#GL_RGB8I RGB8I}</td><td>{@link GL30#GL_RGB8UI RGB8UI}</td></tr><tr><td>{@link GL30#GL_RGB16I RGB16I}</td><td>{@link GL30#GL_RGB16UI RGB16UI}</td><td>{@link GL30#GL_RGB32I RGB32I}</td><td>{@link GL30#GL_RGB32UI RGB32UI}</td><td>{@link GL30#GL_RGBA8I RGBA8I}</td><td>{@link GL30#GL_RGBA8UI RGBA8UI}</td></tr><tr><td>{@link GL30#GL_RGBA16I RGBA16I}</td><td>{@link GL30#GL_RGBA16UI RGBA16UI}</td><td>{@link GL30#GL_RGBA32I RGBA32I}</td><td>{@link GL30#GL_RGBA32UI RGBA32UI}</td><td>{@link GL14#GL_DEPTH_COMPONENT16 DEPTH_COMPONENT16}</td><td>{@link GL14#GL_DEPTH_COMPONENT24 DEPTH_COMPONENT24}</td></tr><tr><td>{@link GL14#GL_DEPTH_COMPONENT32 DEPTH_COMPONENT32}</td><td>{@link GL30#GL_DEPTH24_STENCIL8 DEPTH24_STENCIL8}</td><td>{@link GL30#GL_DEPTH_COMPONENT32F DEPTH_COMPONENT32F}</td><td>{@link GL30#GL_DEPTH32F_STENCIL8 DEPTH32F_STENCIL8}</td><td>{@link GL30#GL_COMPRESSED_RED COMPRESSED_RED}</td><td>{@link GL30#GL_COMPRESSED_RG COMPRESSED_RG}</td></tr><tr><td>{@link GL13#GL_COMPRESSED_RGB COMPRESSED_RGB}</td><td>{@link GL13#GL_COMPRESSED_RGBA COMPRESSED_RGBA}</td><td>{@link GL21#GL_COMPRESSED_SRGB COMPRESSED_SRGB}</td><td>{@link GL21#GL_COMPRESSED_SRGB_ALPHA COMPRESSED_SRGB_ALPHA}</td><td>{@link GL30#GL_COMPRESSED_RED_RGTC1 COMPRESSED_RED_RGTC1}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RED_RGTC1 COMPRESSED_SIGNED_RED_RGTC1}</td></tr><tr><td>{@link GL30#GL_COMPRESSED_RG_RGTC2 COMPRESSED_RG_RGTC2}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RG_RGTC2 COMPRESSED_SIGNED_RG_RGTC2}</td><td>{@link GL42#GL_COMPRESSED_RGBA_BPTC_UNORM COMPRESSED_RGBA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM COMPRESSED_SRGB_ALPHA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT COMPRESSED_RGB_BPTC_SIGNED_FLOAT}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_RGB8_ETC2 COMPRESSED_RGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ETC2 COMPRESSED_SRGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGBA8_ETC2_EAC COMPRESSED_RGBA8_ETC2_EAC}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_R11_EAC COMPRESSED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_R11_EAC COMPRESSED_SIGNED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_RG11_EAC COMPRESSED_RG11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_RG11_EAC COMPRESSED_SIGNED_RG11_EAC}</td><td>see {@link EXTTextureCompressionS3TC}</td><td>see {@link EXTTextureCompressionLATC}</td></tr><tr><td>see {@link ATITextureCompression3DC}</td></tr></table>
     * @param width          the texture width
     * @param height         the texture height
     * @param border         the texture border width
     * @param format         the texel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type           the texel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels         the texel data
     */
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ FloatBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Specifies a two-dimensional texture image.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr><tr><td>{@link #GL_PROXY_TEXTURE_2D PROXY_TEXTURE_2D}</td><td>{@link GL30#GL_PROXY_TEXTURE_1D_ARRAY PROXY_TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_PROXY_TEXTURE_RECTANGLE PROXY_TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_PROXY_TEXTURE_CUBE_MAP PROXY_TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format. One of:<br><table><tr><td>{@link #GL_RED RED}</td><td>{@link GL30#GL_RG RG}</td><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td></tr><tr><td>{@link GL30#GL_R8 R8}</td><td>{@link GL31#GL_R8_SNORM R8_SNORM}</td><td>{@link GL30#GL_R16 R16}</td><td>{@link GL31#GL_R16_SNORM R16_SNORM}</td><td>{@link GL30#GL_RG8 RG8}</td><td>{@link GL31#GL_RG8_SNORM RG8_SNORM}</td></tr><tr><td>{@link GL30#GL_RG16 RG16}</td><td>{@link GL31#GL_RG16_SNORM RG16_SNORM}</td><td>{@link #GL_R3_G3_B2 R3_G3_B2}</td><td>{@link #GL_RGB4 RGB4}</td><td>{@link #GL_RGB5 RGB5}</td><td>{@link GL41#GL_RGB565 RGB565}</td></tr><tr><td>{@link #GL_RGB8 RGB8}</td><td>{@link GL31#GL_RGB8_SNORM RGB8_SNORM}</td><td>{@link #GL_RGB10 RGB10}</td><td>{@link #GL_RGB12 RGB12}</td><td>{@link #GL_RGB16 RGB16}</td><td>{@link GL31#GL_RGB16_SNORM RGB16_SNORM}</td></tr><tr><td>{@link #GL_RGBA2 RGBA2}</td><td>{@link #GL_RGBA4 RGBA4}</td><td>{@link #GL_RGB5_A1 RGB5_A1}</td><td>{@link #GL_RGBA8 RGBA8}</td><td>{@link GL31#GL_RGBA8_SNORM RGBA8_SNORM}</td><td>{@link #GL_RGB10_A2 RGB10_A2}</td></tr><tr><td>{@link GL33#GL_RGB10_A2UI RGB10_A2UI}</td><td>{@link #GL_RGBA12 RGBA12}</td><td>{@link #GL_RGBA16 RGBA16}</td><td>{@link GL31#GL_RGBA16_SNORM RGBA16_SNORM}</td><td>{@link GL21#GL_SRGB8 SRGB8}</td><td>{@link GL21#GL_SRGB8_ALPHA8 SRGB8_ALPHA8}</td></tr><tr><td>{@link GL30#GL_R16F R16F}</td><td>{@link GL30#GL_RG16F RG16F}</td><td>{@link GL30#GL_RGB16F RGB16F}</td><td>{@link GL30#GL_RGBA16F RGBA16F}</td><td>{@link GL30#GL_R32F R32F}</td><td>{@link GL30#GL_RG32F RG32F}</td></tr><tr><td>{@link GL30#GL_RGB32F RGB32F}</td><td>{@link GL30#GL_RGBA32F RGBA32F}</td><td>{@link GL30#GL_R11F_G11F_B10F R11F_G11F_B10F}</td><td>{@link GL30#GL_RGB9_E5 RGB9_E5}</td><td>{@link GL30#GL_R8I R8I}</td><td>{@link GL30#GL_R8UI R8UI}</td></tr><tr><td>{@link GL30#GL_R16I R16I}</td><td>{@link GL30#GL_R16UI R16UI}</td><td>{@link GL30#GL_R32I R32I}</td><td>{@link GL30#GL_R32UI R32UI}</td><td>{@link GL30#GL_RG8I RG8I}</td><td>{@link GL30#GL_RG8UI RG8UI}</td></tr><tr><td>{@link GL30#GL_RG16I RG16I}</td><td>{@link GL30#GL_RG16UI RG16UI}</td><td>{@link GL30#GL_RG32I RG32I}</td><td>{@link GL30#GL_RG32UI RG32UI}</td><td>{@link GL30#GL_RGB8I RGB8I}</td><td>{@link GL30#GL_RGB8UI RGB8UI}</td></tr><tr><td>{@link GL30#GL_RGB16I RGB16I}</td><td>{@link GL30#GL_RGB16UI RGB16UI}</td><td>{@link GL30#GL_RGB32I RGB32I}</td><td>{@link GL30#GL_RGB32UI RGB32UI}</td><td>{@link GL30#GL_RGBA8I RGBA8I}</td><td>{@link GL30#GL_RGBA8UI RGBA8UI}</td></tr><tr><td>{@link GL30#GL_RGBA16I RGBA16I}</td><td>{@link GL30#GL_RGBA16UI RGBA16UI}</td><td>{@link GL30#GL_RGBA32I RGBA32I}</td><td>{@link GL30#GL_RGBA32UI RGBA32UI}</td><td>{@link GL14#GL_DEPTH_COMPONENT16 DEPTH_COMPONENT16}</td><td>{@link GL14#GL_DEPTH_COMPONENT24 DEPTH_COMPONENT24}</td></tr><tr><td>{@link GL14#GL_DEPTH_COMPONENT32 DEPTH_COMPONENT32}</td><td>{@link GL30#GL_DEPTH24_STENCIL8 DEPTH24_STENCIL8}</td><td>{@link GL30#GL_DEPTH_COMPONENT32F DEPTH_COMPONENT32F}</td><td>{@link GL30#GL_DEPTH32F_STENCIL8 DEPTH32F_STENCIL8}</td><td>{@link GL30#GL_COMPRESSED_RED COMPRESSED_RED}</td><td>{@link GL30#GL_COMPRESSED_RG COMPRESSED_RG}</td></tr><tr><td>{@link GL13#GL_COMPRESSED_RGB COMPRESSED_RGB}</td><td>{@link GL13#GL_COMPRESSED_RGBA COMPRESSED_RGBA}</td><td>{@link GL21#GL_COMPRESSED_SRGB COMPRESSED_SRGB}</td><td>{@link GL21#GL_COMPRESSED_SRGB_ALPHA COMPRESSED_SRGB_ALPHA}</td><td>{@link GL30#GL_COMPRESSED_RED_RGTC1 COMPRESSED_RED_RGTC1}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RED_RGTC1 COMPRESSED_SIGNED_RED_RGTC1}</td></tr><tr><td>{@link GL30#GL_COMPRESSED_RG_RGTC2 COMPRESSED_RG_RGTC2}</td><td>{@link GL30#GL_COMPRESSED_SIGNED_RG_RGTC2 COMPRESSED_SIGNED_RG_RGTC2}</td><td>{@link GL42#GL_COMPRESSED_RGBA_BPTC_UNORM COMPRESSED_RGBA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM COMPRESSED_SRGB_ALPHA_BPTC_UNORM}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT COMPRESSED_RGB_BPTC_SIGNED_FLOAT}</td><td>{@link GL42#GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_RGB8_ETC2 COMPRESSED_RGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ETC2 COMPRESSED_SRGB8_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2}</td><td>{@link GL43#GL_COMPRESSED_RGBA8_ETC2_EAC COMPRESSED_RGBA8_ETC2_EAC}</td><td>{@link GL43#GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC COMPRESSED_SRGB8_ALPHA8_ETC2_EAC}</td></tr><tr><td>{@link GL43#GL_COMPRESSED_R11_EAC COMPRESSED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_R11_EAC COMPRESSED_SIGNED_R11_EAC}</td><td>{@link GL43#GL_COMPRESSED_RG11_EAC COMPRESSED_RG11_EAC}</td><td>{@link GL43#GL_COMPRESSED_SIGNED_RG11_EAC COMPRESSED_SIGNED_RG11_EAC}</td><td>see {@link EXTTextureCompressionS3TC}</td><td>see {@link EXTTextureCompressionLATC}</td></tr><tr><td>see {@link ATITextureCompression3DC}</td></tr></table>
     * @param width          the texture width
     * @param height         the texture height
     * @param border         the texture border width
     * @param format         the texel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type           the texel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels         the texel data
     */
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ DoubleBuffer pixels);

    // --- [ glTexImage1D ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexImage2D TexImage2D}}.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_PROXY_TEXTURE_1D PROXY_TEXTURE_1D}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format
     * @param width          the texture width
     * @param border         the texture border width
     * @param format         the texel data format
     * @param type           the texel data type
     * @param pixels         the texel data
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable ByteBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexImage2D TexImage2D}}.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_PROXY_TEXTURE_1D PROXY_TEXTURE_1D}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format
     * @param width          the texture width
     * @param border         the texture border width
     * @param format         the texel data format
     * @param type           the texel data type
     * @param pixels         the texel data
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable long pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexImage2D TexImage2D}}.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_PROXY_TEXTURE_1D PROXY_TEXTURE_1D}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format
     * @param width          the texture width
     * @param border         the texture border width
     * @param format         the texel data format
     * @param type           the texel data type
     * @param pixels         the texel data
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable ShortBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexImage2D TexImage2D}}.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_PROXY_TEXTURE_1D PROXY_TEXTURE_1D}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format
     * @param width          the texture width
     * @param border         the texture border width
     * @param format         the texel data format
     * @param type           the texel data type
     * @param pixels         the texel data
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable IntBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexImage2D TexImage2D}}.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_PROXY_TEXTURE_1D PROXY_TEXTURE_1D}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format
     * @param width          the texture width
     * @param border         the texture border width
     * @param format         the texel data format
     * @param type           the texel data type
     * @param pixels         the texel data
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable FloatBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexImage2D TexImage2D}}.
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_PROXY_TEXTURE_1D PROXY_TEXTURE_1D}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalformat the texture internal format
     * @param width          the texture width
     * @param border         the texture border width
     * @param format         the texel data format
     * @param type           the texel data type
     * @param pixels         the texel data
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable DoubleBuffer pixels);

    // --- [ glCopyTexImage2D ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glCopyTexImage2D">Reference Page</a></p>
     * 
     * Defines a two-dimensional texel array in exactly the manner of {@link #glTexImage2D TexImage2D}, except that the image data are taken from the framebuffer rather
     * than from client memory.
     * 
     * <p>{@code x}, {@code y}, {@code width}, and {@code height} correspond precisely to the corresponding arguments to {@link #glReadPixels ReadPixels}; they specify the
     * image's width and height, and the lower left (x, y) coordinates of the framebuffer region to be copied.</p>
     * 
     * <p>The image is taken from the framebuffer exactly as if these arguments were passed to {@link #glCopyPixels CopyPixels} with argument type set to {@link #GL_COLOR COLOR},
     * {@link #GL_DEPTH DEPTH}, or {@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}, depending on {@code internalformat}. RGBA data is taken from the current color buffer, while depth
     * component and stencil index data are taken from the depth and stencil buffers, respectively.</p>
     * 
     * <p>Subsequent processing is identical to that described for {@link #glTexImage2D TexImage2D}, beginning with clamping of the R, G, B, A, or depth values, and masking
     * of the stencil index values from the resulting pixel groups. Parameters {@code level}, {@code internalformat}, and {@code border} are specified using
     * the same values, with the same meanings, as the corresponding arguments of {@link #glTexImage2D TexImage2D}.</p>
     * 
     * <p>The constraints on width, height, and border are exactly those for the corresponding arguments of {@link #glTexImage2D TexImage2D}.</p>
     *
     * @param target         the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalFormat the texture internal format. See {@link #glTexImage2D TexImage2D} for a list of supported formats.
     * @param x              the left framebuffer pixel coordinate
     * @param y              the lower framebuffer pixel coordinate
     * @param width          the texture width
     * @param height         the texture height
     * @param border         the texture border width
     */
    public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border);

    // --- [ glCopyTexImage1D ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glCopyTexImage1D">Reference Page</a></p>
     * 
     * Defines a one-dimensional texel array in exactly the manner of {@link #glTexImage1D TexImage1D}, except that the image data are taken from the framebuffer rather
     * than from client memory. For the purposes of decoding the texture image, {@code CopyTexImage1D} is equivalent to calling {@link #glCopyTexImage2D CopyTexImage2D}
     * with corresponding arguments and height of 1, except that the height of the image is always 1, regardless of the value of border. level, internalformat,
     * and border are specified using the same values, with the same meanings, as the corresponding arguments of {@link #glTexImage1D TexImage1D}. The constraints on
     * width and border are exactly those of the corresponding arguments of {@link #glTexImage1D TexImage1D}.
     *
     * @param target         the texture target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td></tr></table>
     * @param level          the level-of-detail number
     * @param internalFormat the texture internal format. See {@link #glTexImage2D TexImage2D} for a list of supported formats.
     * @param x              the left framebuffer pixel coordinate
     * @param y              the lower framebuffer pixel coordinate
     * @param width          the texture width
     * @param border         the texture border width
     */
    //public void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border);

    // --- [ glCopyTexSubImage1D ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glCopyTexSubImage1D">Reference Page</a></p>
     * 
     * Respecifies a rectangular subregion of an existing texel array. No change is made to the {@code internalformat}, {@code width} or {@code border}
     * parameters of the specified texel array, nor is any change made to texel values outside the specified subregion. See {@link #glCopyTexImage1D CopyTexImage1D} for more
     * details.
     *
     * @param target  the texture target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td></tr></table>
     * @param level   the level-of-detail number
     * @param xoffset the left texel coordinate of the texture subregion to update
     * @param x       the left framebuffer pixel coordinate
     * @param y       the lower framebuffer pixel coordinate
     * @param width   the texture subregion width
     */
    //public void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width);

    // --- [ glCopyTexSubImage2D ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glCopyTexSubImage2D">Reference Page</a></p>
     * 
     * Respecifies a rectangular subregion of an existing texel array. No change is made to the {@code internalformat}, {@code width}, {@code height},
     * or {@code border} parameters of the specified texel array, nor is any change made to texel values outside the specified subregion. See
     * {@link #glCopyTexImage2D CopyTexImage2D} for more details.
     *
     * @param target  the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level   the level-of-detail number
     * @param xoffset the left texel coordinate of the texture subregion to update
     * @param yoffset the lower texel coordinate of the texture subregion to update
     * @param x       the left framebuffer pixel coordinate
     * @param y       the lower framebuffer pixel coordinate
     * @param width   the texture subregion width
     * @param height  the texture subregion height
     */
    public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);

    // --- [ glTexParameteri ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexParameteri">Reference Page</a></p>
     * 
     * Sets the integer value of a texture parameter, which controls how the texel array is treated when specified or changed, and when applied to a fragment.
     *
     * @param target the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL12#GL_TEXTURE_3D TEXTURE_3D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td></tr><tr><td>{@link GL30#GL_TEXTURE_2D_ARRAY TEXTURE_2D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td><td>{@link GL40#GL_TEXTURE_CUBE_MAP_ARRAY TEXTURE_CUBE_MAP_ARRAY}</td></tr><tr><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE TEXTURE_2D_MULTISAMPLE}</td><td>{@link GL32#GL_TEXTURE_2D_MULTISAMPLE_ARRAY TEXTURE_2D_MULTISAMPLE_ARRAY}</td></tr></table>
     * @param pname  the parameter to set. One of:<br><table><tr><td>{@link GL12#GL_TEXTURE_BASE_LEVEL TEXTURE_BASE_LEVEL}</td><td>{@link #GL_TEXTURE_BORDER_COLOR TEXTURE_BORDER_COLOR}</td><td>{@link GL14#GL_TEXTURE_COMPARE_MODE TEXTURE_COMPARE_MODE}</td><td>{@link GL14#GL_TEXTURE_COMPARE_FUNC TEXTURE_COMPARE_FUNC}</td></tr><tr><td>{@link GL14#GL_TEXTURE_LOD_BIAS TEXTURE_LOD_BIAS}</td><td>{@link #GL_TEXTURE_MAG_FILTER TEXTURE_MAG_FILTER}</td><td>{@link GL12#GL_TEXTURE_MAX_LEVEL TEXTURE_MAX_LEVEL}</td><td>{@link GL12#GL_TEXTURE_MAX_LOD TEXTURE_MAX_LOD}</td></tr><tr><td>{@link #GL_TEXTURE_MIN_FILTER TEXTURE_MIN_FILTER}</td><td>{@link GL12#GL_TEXTURE_MIN_LOD TEXTURE_MIN_LOD}</td><td>{@link #GL_TEXTURE_PRIORITY TEXTURE_PRIORITY}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_R TEXTURE_SWIZZLE_R}</td></tr><tr><td>{@link GL33#GL_TEXTURE_SWIZZLE_G TEXTURE_SWIZZLE_G}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_B TEXTURE_SWIZZLE_B}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_A TEXTURE_SWIZZLE_A}</td><td>{@link GL33#GL_TEXTURE_SWIZZLE_RGBA TEXTURE_SWIZZLE_RGBA}</td></tr><tr><td>{@link #GL_TEXTURE_WRAP_S TEXTURE_WRAP_S}</td><td>{@link #GL_TEXTURE_WRAP_T TEXTURE_WRAP_T}</td><td>{@link GL12#GL_TEXTURE_WRAP_R TEXTURE_WRAP_R}</td><td>{@link GL14#GL_DEPTH_TEXTURE_MODE DEPTH_TEXTURE_MODE}</td></tr><tr><td>{@link GL14#GL_GENERATE_MIPMAP GENERATE_MIPMAP}</td></tr></table>
     * @param param  the parameter value
     */
    public void glTexParameteri(int target, int pname, int param);

    // --- [ glTexParameteriv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexParameter">Reference Page</a></p>
     * 
     * Pointer version of {@link #glTexParameteri TexParameteri}.
     *
     * @param target the texture target
     * @param pname  the parameter to set
     * @param params the parameter value
     */
    public void glTexParameteriv(int target, int pname, IntBuffer params);

    // --- [ glTexParameterf ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexParameterf">Reference Page</a></p>
     * 
     * Float version of {@link #glTexParameteri TexParameteri}.
     *
     * @param target the texture target
     * @param pname  the parameter to set
     * @param param  the parameter value
     */
    public void glTexParameterf(int target, int pname, float param);

    // --- [ glTexParameterfv ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexParameter">Reference Page</a></p>
     * 
     * Pointer version of {@link #glTexParameterf TexParameterf}.
     *
     * @param target the texture target
     * @param pname  the parameter to set
     * @param params the parameter value
     */
    public void glTexParameterfv(int target, int pname, FloatBuffer params);

    // --- [ glTexSubImage1D ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexSubImage2D TexSubImage2D}.
     *
     * @param target  the texture target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param width   the subregion width
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ByteBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexSubImage2D TexSubImage2D}.
     *
     * @param target  the texture target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param width   the subregion width
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, long pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexSubImage2D TexSubImage2D}.
     *
     * @param target  the texture target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param width   the subregion width
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ShortBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexSubImage2D TexSubImage2D}.
     *
     * @param target  the texture target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param width   the subregion width
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, IntBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexSubImage2D TexSubImage2D}.
     *
     * @param target  the texture target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param width   the subregion width
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, FloatBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * One-dimensional version of {@link #glTexSubImage2D TexSubImage2D}.
     *
     * @param target  the texture target. Must be:<br><table><tr><td>{@link #GL_TEXTURE_1D TEXTURE_1D}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param width   the subregion width
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, DoubleBuffer pixels);

    // --- [ glTexSubImage2D ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Respecifies a rectangular subregion of an existing texel array. No change is made to the internalformat, width, height, depth, or border parameters of
     * the specified texel array, nor is any change made to texel values outside the specified subregion.
     *
     * @param target  the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param yoffset the bottom coordinate of the texel subregion
     * @param width   the subregion width
     * @param height  the subregion height
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Respecifies a rectangular subregion of an existing texel array. No change is made to the internalformat, width, height, depth, or border parameters of
     * the specified texel array, nor is any change made to texel values outside the specified subregion.
     *
     * @param target  the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param yoffset the bottom coordinate of the texel subregion
     * @param width   the subregion width
     * @param height  the subregion height
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    //public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Respecifies a rectangular subregion of an existing texel array. No change is made to the internalformat, width, height, depth, or border parameters of
     * the specified texel array, nor is any change made to texel values outside the specified subregion.
     *
     * @param target  the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param yoffset the bottom coordinate of the texel subregion
     * @param width   the subregion width
     * @param height  the subregion height
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ShortBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Respecifies a rectangular subregion of an existing texel array. No change is made to the internalformat, width, height, depth, or border parameters of
     * the specified texel array, nor is any change made to texel values outside the specified subregion.
     *
     * @param target  the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param yoffset the bottom coordinate of the texel subregion
     * @param width   the subregion width
     * @param height  the subregion height
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, IntBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Respecifies a rectangular subregion of an existing texel array. No change is made to the internalformat, width, height, depth, or border parameters of
     * the specified texel array, nor is any change made to texel values outside the specified subregion.
     *
     * @param target  the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param yoffset the bottom coordinate of the texel subregion
     * @param width   the subregion width
     * @param height  the subregion height
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, FloatBuffer pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Respecifies a rectangular subregion of an existing texel array. No change is made to the internalformat, width, height, depth, or border parameters of
     * the specified texel array, nor is any change made to texel values outside the specified subregion.
     *
     * @param target  the texture target. One of:<br><table><tr><td>{@link #GL_TEXTURE_2D TEXTURE_2D}</td><td>{@link GL30#GL_TEXTURE_1D_ARRAY TEXTURE_1D_ARRAY}</td><td>{@link GL31#GL_TEXTURE_RECTANGLE TEXTURE_RECTANGLE}</td><td>{@link GL13#GL_TEXTURE_CUBE_MAP TEXTURE_CUBE_MAP}</td></tr></table>
     * @param level   the level-of-detail-number
     * @param xoffset the left coordinate of the texel subregion
     * @param yoffset the bottom coordinate of the texel subregion
     * @param width   the subregion width
     * @param height  the subregion height
     * @param format  the pixel data format. One of:<br><table><tr><td>{@link #GL_STENCIL_INDEX STENCIL_INDEX}</td><td>{@link #GL_DEPTH_COMPONENT DEPTH_COMPONENT}</td><td>{@link GL30#GL_DEPTH_STENCIL DEPTH_STENCIL}</td><td>{@link #GL_RED RED}</td><td>{@link #GL_GREEN GREEN}</td><td>{@link #GL_BLUE BLUE}</td><td>{@link #GL_ALPHA ALPHA}</td><td>{@link GL30#GL_RG RG}</td></tr><tr><td>{@link #GL_RGB RGB}</td><td>{@link #GL_RGBA RGBA}</td><td>{@link GL12#GL_BGR BGR}</td><td>{@link GL12#GL_BGRA BGRA}</td><td>{@link #GL_LUMINANCE LUMINANCE}</td><td>{@link #GL_LUMINANCE_ALPHA LUMINANCE_ALPHA}</td><td>{@link GL30#GL_RED_INTEGER RED_INTEGER}</td><td>{@link GL30#GL_GREEN_INTEGER GREEN_INTEGER}</td></tr><tr><td>{@link GL30#GL_BLUE_INTEGER BLUE_INTEGER}</td><td>{@link GL30#GL_ALPHA_INTEGER ALPHA_INTEGER}</td><td>{@link GL30#GL_RG_INTEGER RG_INTEGER}</td><td>{@link GL30#GL_RGB_INTEGER RGB_INTEGER}</td><td>{@link GL30#GL_RGBA_INTEGER RGBA_INTEGER}</td><td>{@link GL30#GL_BGR_INTEGER BGR_INTEGER}</td><td>{@link GL30#GL_BGRA_INTEGER BGRA_INTEGER}</td></tr></table>
     * @param type    the pixel data type. One of:<br><table><tr><td>{@link #GL_UNSIGNED_BYTE UNSIGNED_BYTE}</td><td>{@link #GL_BYTE BYTE}</td><td>{@link #GL_UNSIGNED_SHORT UNSIGNED_SHORT}</td><td>{@link #GL_SHORT SHORT}</td></tr><tr><td>{@link #GL_UNSIGNED_INT UNSIGNED_INT}</td><td>{@link #GL_INT INT}</td><td>{@link GL30#GL_HALF_FLOAT HALF_FLOAT}</td><td>{@link #GL_FLOAT FLOAT}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_BYTE_3_3_2 UNSIGNED_BYTE_3_3_2}</td><td>{@link GL12#GL_UNSIGNED_BYTE_2_3_3_REV UNSIGNED_BYTE_2_3_3_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5 UNSIGNED_SHORT_5_6_5}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_6_5_REV UNSIGNED_SHORT_5_6_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4 UNSIGNED_SHORT_4_4_4_4}</td><td>{@link GL12#GL_UNSIGNED_SHORT_4_4_4_4_REV UNSIGNED_SHORT_4_4_4_4_REV}</td><td>{@link GL12#GL_UNSIGNED_SHORT_5_5_5_1 UNSIGNED_SHORT_5_5_5_1}</td><td>{@link GL12#GL_UNSIGNED_SHORT_1_5_5_5_REV UNSIGNED_SHORT_1_5_5_5_REV}</td></tr><tr><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8 UNSIGNED_INT_8_8_8_8}</td><td>{@link GL12#GL_UNSIGNED_INT_8_8_8_8_REV UNSIGNED_INT_8_8_8_8_REV}</td><td>{@link GL12#GL_UNSIGNED_INT_10_10_10_2 UNSIGNED_INT_10_10_10_2}</td><td>{@link GL12#GL_UNSIGNED_INT_2_10_10_10_REV UNSIGNED_INT_2_10_10_10_REV}</td></tr><tr><td>{@link GL30#GL_UNSIGNED_INT_24_8 UNSIGNED_INT_24_8}</td><td>{@link GL30#GL_UNSIGNED_INT_10F_11F_11F_REV UNSIGNED_INT_10F_11F_11F_REV}</td><td>{@link GL30#GL_UNSIGNED_INT_5_9_9_9_REV UNSIGNED_INT_5_9_9_9_REV}</td><td>{@link GL30#GL_FLOAT_32_UNSIGNED_INT_24_8_REV FLOAT_32_UNSIGNED_INT_24_8_REV}</td></tr><tr><td>{@link #GL_BITMAP BITMAP}</td></tr></table>
     * @param pixels  the pixel data
     */
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, DoubleBuffer pixels);

    // --- [ glViewport ] ---

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glViewport">Reference Page</a></p>
     * 
     * Specifies the viewport transformation parameters for all viewports.
     * 
     * <p>The location of the viewport's bottom-left corner, given by {@code (x, y)}, are clamped to be within the implementation-dependent viewport bounds range.
     * The viewport bounds range {@code [min, max]} tuple may be determined by calling {@link #glGetFloatv GetFloatv} with the symbolic
     * constant {@link GL41#GL_VIEWPORT_BOUNDS_RANGE VIEWPORT_BOUNDS_RANGE}. Viewport width and height are clamped to implementation-dependent maximums when specified. The maximum
     * width and height may be found by calling {@link #glGetFloatv GetFloatv} with the symbolic constant {@link #GL_MAX_VIEWPORT_DIMS MAX_VIEWPORT_DIMS}. The
     * maximum viewport dimensions must be greater than or equal to the larger of the visible dimensions of the display being rendered to (if a display
     * exists), and the largest renderbuffer image which can be successfully created and attached to a framebuffer object.</p>
     * 
     * <p>In the initial state, {@code w} and {@code h} for each viewport are set to the width and height, respectively, of the window into which the GL is to do
     * its rendering. If the default framebuffer is bound but no default framebuffer is associated with the GL context, then {@code w} and {@code h} are
     * initially set to zero.</p>
     *
     * @param x the left viewport coordinate
     * @param y the bottom viewport coordinate
     * @param w the viewport width
     * @param h the viewport height
     */
    public void glViewport(int x, int y, int w, int h);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glClipPlane">Reference Page</a></p>
     * 
     * Array version of: {@link #glClipPlane ClipPlane}
     */
    //public void glClipPlane(int plane, double[] equation);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGenTextures">Reference Page</a></p>
     * 
     * Array version of: {@link #glGenTextures GenTextures}
     */
    public void glGenTextures(int[] textures);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glDeleteTextures">Reference Page</a></p>
     * 
     * Array version of: {@link #glDeleteTextures DeleteTextures}
     */
    public void glDeleteTextures(int[] textures);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetClipPlane">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetClipPlane GetClipPlane}
     */
    //public void glGetClipPlane(int plane, double[] equation);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetFloatv">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetFloatv GetFloatv}
     */
    public void glGetFloatv(int pname, float[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetIntegerv">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetIntegerv GetIntegerv}
     */
    public void glGetIntegerv(int pname, int[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetDoublev">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetDoublev GetDoublev}
     */
    //public void glGetDoublev(int pname, double[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexEnv">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexEnviv GetTexEnviv}
     */
    //public void glGetTexEnviv(int env, int pname, int[] data);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexEnv">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexEnvfv GetTexEnvfv}
     */
    //public void glGetTexEnvfv(int env, int pname, float[] data);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexImage GetTexImage}
     */
    //public void glGetTexImage(int tex, int level, int format, int type, short[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexImage GetTexImage}
     */
    //public void glGetTexImage(int tex, int level, int format, int type, int[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexImage GetTexImage}
     */
    //public void glGetTexImage(int tex, int level, int format, int type, float[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexImage">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexImage GetTexImage}
     */
    //public void glGetTexImage(int tex, int level, int format, int type, double[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexLevelParameter">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexLevelParameteriv GetTexLevelParameteriv}
     */
    //public void glGetTexLevelParameteriv(int target, int level, int pname, int[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexLevelParameter">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexLevelParameterfv GetTexLevelParameterfv}
     */
    //public void glGetTexLevelParameterfv(int target, int level, int pname, float[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexParameter">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexParameteriv GetTexParameteriv}
     */
    public void glGetTexParameteriv(int target, int pname, int[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glGetTexParameter">Reference Page</a></p>
     * 
     * Array version of: {@link #glGetTexParameterfv GetTexParameterfv}
     */
    public void glGetTexParameterfv(int target, int pname, float[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Array version of: {@link #glInterleavedArrays InterleavedArrays}
     */
    //public void glInterleavedArrays(int format, int stride, short[] pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Array version of: {@link #glInterleavedArrays InterleavedArrays}
     */
    //public void glInterleavedArrays(int format, int stride, int[] pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Array version of: {@link #glInterleavedArrays InterleavedArrays}
     */
    //public void glInterleavedArrays(int format, int stride, float[] pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glInterleavedArrays">Reference Page</a></p>
     * 
     * Array version of: {@link #glInterleavedArrays InterleavedArrays}
     */
    //public void glInterleavedArrays(int format, int stride, double[] pointer);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadPixels">Reference Page</a></p>
     * 
     * Array version of: {@link #glReadPixels ReadPixels}
     */
    //public void glReadPixels(int x, int y, int width, int height, int format, int type, short[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadPixels">Reference Page</a></p>
     * 
     * Array version of: {@link #glReadPixels ReadPixels}
     */
    //public void glReadPixels(int x, int y, int width, int height, int format, int type, int[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glReadPixels">Reference Page</a></p>
     * 
     * Array version of: {@link #glReadPixels ReadPixels}
     */
    //public void glReadPixels(int x, int y, int width, int height, int format, int type, float[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexEnv">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexEnviv TexEnviv}
     */
    //public void glTexEnviv(int target, int pname, int[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexEnv">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexEnvfv TexEnvfv}
     */
    //public void glTexEnvfv(int target, int pname, float[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexImage2D TexImage2D}
     */
    //public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ short[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexImage2D TexImage2D}
     */
    //public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ int[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexImage2D TexImage2D}
     */
    //public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ float[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage2D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexImage2D TexImage2D}
     */
    //public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, /* @Nullable */ double[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexImage1D TexImage1D}
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable short[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexImage1D TexImage1D}
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable int[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexImage1D TexImage1D}
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable float[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexImage1D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexImage1D TexImage1D}
     */
    //public void glTexImage1D(int target, int level, int internalformat, int width, int border, int format, int type, @Nullable double[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexParameter">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexParameteriv TexParameteriv}
     */
    public void glTexParameteriv(int target, int pname, int[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexParameter">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexParameterfv TexParameterfv}
     */
    public void glTexParameterfv(int target, int pname, float[] params);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexSubImage1D TexSubImage1D}
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, short[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexSubImage1D TexSubImage1D}
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, int[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexSubImage1D TexSubImage1D}
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, float[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage1D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexSubImage1D TexSubImage1D}
     */
    //public void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, double[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexSubImage2D TexSubImage2D}
     */
    //public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, short[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexSubImage2D TexSubImage2D}
     */
    //public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, int[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexSubImage2D TexSubImage2D}
     */
    //public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, float[] pixels);

    /**
     * <p><a target="_blank" href="http://docs.gl/gl4/glTexSubImage2D">Reference Page</a></p>
     * 
     * Array version of: {@link #glTexSubImage2D TexSubImage2D}
     */
    //public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, double[] pixels);

}
