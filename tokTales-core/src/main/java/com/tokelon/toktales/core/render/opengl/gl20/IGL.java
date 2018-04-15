package com.tokelon.toktales.core.render.opengl.gl20;

/** OGL2 definition of OpenGL CompatWrapper.
 * <p>
 * The OpenGL CompatWrapper consists of IGL interfaces (one for each version),
 * which contain all GL functions that can be used under compatibility with OpenGLES.
 * <p>
 * Note that, while some definitions use larger size types (eg. long, double),
 * these may be casted down in implementations for platforms that do not support them (ex. Android).
 * 
 * @see IGL11
 * @see IGL12
 * @see IGL13
 * @see IGL14
 * @see IGL15
 * @see IGL20
 * @see IGL21
 */
public interface IGL { //IGL2Family

}
