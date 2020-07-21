@file:Suppress("NOTHING_TO_INLINE", "unused", "ConstantConditionIf")

package dev.binclub.fps.client.utils.gl

import dev.binclub.fps.client.Client.DEBUG
import glm_.L
import org.lwjgl.opengl.GL46C
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * @author cookiedragon234 21/Jul/2020
 */

// Num draw calls this pass
var drawCalls: Int = 0
// Num triangles rendered this pass
var triangles: Int = 0

inline fun <T> T.drawCall(): T = this.apply {
	if (DEBUG) {
		drawCalls += 1
	}
}

inline fun <T> T.appendTriangle(): T = appendTriangles(1)
inline fun <T> T.appendTriangles(count: Int): T = this.apply {
	if (DEBUG) {
		triangles += count
	}
}
inline fun <T> T.appendTriangles(count: Int, mode: Int): T
	= appendTriangles(when (mode) {
		GL_TRIANGLES -> count / 3
		GL_POINTS -> count
		else -> error("Unkown mode $mode")
	})

inline fun glEnableVertexAttribArray(
	@NativeType("GLuint") index: Int
) = GL46C.glEnableVertexAttribArray(index)

inline fun glDrawElements(
	@NativeType("GLenum") mode: Int,
	@NativeType("GLsizei") count: Int,
	@NativeType("GLenum") type: Int,
	@NativeType("void const *") indices: Long
) = GL46C.glDrawElements(mode, count, type, indices).drawCall().appendTriangles(count, mode)

inline fun glVertexAttribPointer(
	@NativeType("GLuint") index: Int,
	@NativeType("GLint") size: Int,
	@NativeType("GLenum") type: Int,
	@NativeType("GLboolean") normalized: Boolean,
	@NativeType("GLsizei") stride: Int,
	@NativeType("void const *") pointer: Long
) = GL46C.glVertexAttribPointer(index, size, type, normalized, stride, pointer)

inline fun glVertexAttribPointer(
	@NativeType("GLuint") index: Int,
	@NativeType("GLint") size: Int,
	@NativeType("GLenum") type: Int,
	@NativeType("GLboolean") normalized: Boolean,
	@NativeType("GLsizei") stride: Int,
	@NativeType("void const *")pointer: Int
) = GL46C.glVertexAttribPointer(index, size, type, normalized, stride, pointer.L)

inline fun glActiveTexture(
	@NativeType("GLenum") texture: Int
) = GL46C.glActiveTexture(texture)

inline fun glBindTexture(
	@NativeType("GLenum") target: Int,
	@NativeType("GLuint") texture: Int
) = GL46C.glBindTexture(target, texture)

inline fun glPixelStorei(
	@NativeType("GLenum") pname: Int,
	@NativeType("GLint") param: Int
) = GL46C.glPixelStorei(pname, param)

inline fun glGenTextures() = GL46C.glGenTextures()

// --- [ glTexParameteri ] ---
inline fun glTexParameteri(
	@NativeType("GLenum") target: Int,
	@NativeType("GLenum") pname: Int,
	@NativeType("GLint") param: Int
) = GL46C.glTexParameteri(target, pname, param)

inline fun glTexImage2D(
	@NativeType("GLenum") target: Int,
	@NativeType("GLint") level: Int,
	@NativeType("GLint") internalformat: Int,
	@NativeType("GLsizei") width: Int,
	@NativeType("GLsizei") height: Int,
	@NativeType("GLint") border: Int,
	@NativeType("GLenum") format: Int,
	@NativeType("GLenum") type: Int,
	@NativeType("void const *") pixels: ByteBuffer?
) = GL46C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels)

inline fun glTexImage2D(
	@NativeType("GLenum") target: Int,
	@NativeType("GLint") level: Int,
	@NativeType("GLint") internalformat: Int,
	@NativeType("GLsizei") width: Int,
	@NativeType("GLsizei") height: Int,
	@NativeType("GLint") border: Int,
	@NativeType("GLenum") format: Int,
	@NativeType("GLenum") type: Int,
	@NativeType("void const *") pixels: IntBuffer?
) = GL46C.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels)

inline fun glGenerateMipmap(
	@NativeType("GLenum") target: Int
) = GL46C.glGenerateMipmap(target)

inline fun glGenBuffers(): Int = GL46C.glGenBuffers()

inline fun glBufferData(
	@NativeType("GLenum") target: Int,
	@NativeType("GLsizeiptr") size: Long,
	@NativeType("GLenum") usage: Int
) = GL46C.glBufferData(target, size, usage)

inline fun glBufferData(
	@NativeType("GLenum") target: Int,
	@NativeType("void const *") data: ByteBuffer,
	@NativeType("GLenum") usage: Int
) = GL46C.glBufferData(target, data, usage)

inline fun glBufferData(
	@NativeType("GLenum") target: Int,
	@NativeType("void const *") data: FloatBuffer,
	@NativeType("GLenum") usage: Int
) = GL46C.glBufferData(target, data, usage)

inline fun glBufferData(
	@NativeType("GLenum") target: Int,
	@NativeType("void const *") data: IntBuffer,
	@NativeType("GLenum") usage: Int
) = GL46C.glBufferData(target, data, usage)

inline fun glBindBuffer(
	@org.lwjgl.system.NativeType("GLenum") target: Int,
	@org.lwjgl.system.NativeType("GLuint") buffer: Int
) = GL46C.glBindBuffer(target, buffer)

inline fun glDeleteBuffers(
	@org.lwjgl.system.NativeType("GLuint const *") buffers: IntBuffer
) = GL46C.glDeleteBuffers(buffers)

inline fun glDeleteBuffers(
	@org.lwjgl.system.NativeType("GLuint const *") buffer: Int
) = GL46C.glDeleteBuffers(buffer)


//([/*A-z0-9_ .,`\n\r]+)[\r\n]+(const val [A-z0-9_]+ = [0x]*[A-z0-9]+)[,]*[\r\n]*



/** Accepted by the `internalformat` parameter of TexImage1D, TexImage2D, TexImage3D, CopyTexImage1D, and CopyTexImage2D.  */
const val GL_COMPRESSED_ALPHA = 0x84E9
/** Accepted by the `internalformat` parameter of TexImage1D, TexImage2D, TexImage3D, CopyTexImage1D, and CopyTexImage2D.  */
const val GL_COMPRESSED_LUMINANCE = 0x84EA
/** Accepted by the `internalformat` parameter of TexImage1D, TexImage2D, TexImage3D, CopyTexImage1D, and CopyTexImage2D.  */
const val GL_COMPRESSED_LUMINANCE_ALPHA = 0x84EB
/** Accepted by the `internalformat` parameter of TexImage1D, TexImage2D, TexImage3D, CopyTexImage1D, and CopyTexImage2D.  */
const val GL_COMPRESSED_INTENSITY = 0x84EC
/** Accepted by the `internalformat` parameter of TexImage1D, TexImage2D, TexImage3D, CopyTexImage1D, and CopyTexImage2D.  */
const val GL_COMPRESSED_RGB = 0x84ED
/** Accepted by the `internalformat` parameter of TexImage1D, TexImage2D, TexImage3D, CopyTexImage1D, and CopyTexImage2D.  */
const val GL_COMPRESSED_RGBA = 0x84EE
/** Accepted by the `target` parameter of Hint and the `value` parameter of GetIntegerv, GetBooleanv, GetFloatv, and GetDoublev.  */
const val GL_TEXTURE_COMPRESSION_HINT = 0x84EF
/** Accepted by the `value` parameter of GetTexLevelParameter.  */
const val GL_TEXTURE_COMPRESSED_IMAGE_SIZE = 0x86A0
/** Accepted by the `value` parameter of GetTexLevelParameter.  */
const val GL_TEXTURE_COMPRESSED = 0x86A1
/** Accepted by the `value` parameter of GetIntegerv, GetBooleanv, GetFloatv, and GetDoublev.  */
const val GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2
/** Accepted by the `value` parameter of GetIntegerv, GetBooleanv, GetFloatv, and GetDoublev.  */
const val GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3
/** Accepted by the `param` parameters of TexGend, TexGenf, and TexGeni when `pname` parameter is TEXTURE_GEN_MODE.  */
const val GL_NORMAL_MAP = 0x8511
/** Accepted by the `param` parameters of TexGend, TexGenf, and TexGeni when `pname` parameter is TEXTURE_GEN_MODE.  */
const val GL_REFLECTION_MAP = 0x8512
/**
 * When the `pname` parameter of TexGendv, TexGenfv, and TexGeniv is TEXTURE_GEN_MODE, then the array `params` may also contain NORMAL_MAP
 * or REFLECTION_MAP. Accepted by the `cap` parameter of Enable, Disable, IsEnabled, and by the `pname` parameter of GetBooleanv,
 * GetIntegerv, GetFloatv, and GetDoublev, and by the `target` parameter of BindTexture, GetTexParameterfv, GetTexParameteriv, TexParameterf,
 * TexParameteri, TexParameterfv, and TexParameteriv.
 */
const val GL_TEXTURE_CUBE_MAP = 0x8513
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_TEXTURE_BINDING_CUBE_MAP = 0x8514
/**
 * Accepted by the `target` parameter of GetTexImage, GetTexLevelParameteriv, GetTexLevelParameterfv, TexImage2D, CopyTexImage2D, TexSubImage2D, and
 * CopySubTexImage2D.
 */
const val GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515
/**
 * Accepted by the `target` parameter of GetTexImage, GetTexLevelParameteriv, GetTexLevelParameterfv, TexImage2D, CopyTexImage2D, TexSubImage2D, and
 * CopySubTexImage2D.
 */
const val GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516
/**
 * Accepted by the `target` parameter of GetTexImage, GetTexLevelParameteriv, GetTexLevelParameterfv, TexImage2D, CopyTexImage2D, TexSubImage2D, and
 * CopySubTexImage2D.
 */
const val GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517
/**
 * Accepted by the `target` parameter of GetTexImage, GetTexLevelParameteriv, GetTexLevelParameterfv, TexImage2D, CopyTexImage2D, TexSubImage2D, and
 * CopySubTexImage2D.
 */
const val GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518
/**
 * Accepted by the `target` parameter of GetTexImage, GetTexLevelParameteriv, GetTexLevelParameterfv, TexImage2D, CopyTexImage2D, TexSubImage2D, and
 * CopySubTexImage2D.
 */
const val GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519
/**
 * Accepted by the `target` parameter of GetTexImage, GetTexLevelParameteriv, GetTexLevelParameterfv, TexImage2D, CopyTexImage2D, TexSubImage2D, and
 * CopySubTexImage2D.
 */
const val GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A
/** Accepted by the `target` parameter of GetTexLevelParameteriv, GetTexLevelParameterfv, GetTexParameteriv, and TexImage2D.  */
const val GL_PROXY_TEXTURE_CUBE_MAP = 0x851B
/** Accepted by the `pname` parameter of GetBooleanv, GetDoublev, GetIntegerv, and GetFloatv.  */
const val GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C
/**
 * Accepted by the `cap` parameter of Enable, Disable, and IsEnabled, and by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and
 * GetDoublev.
 */
const val GL_MULTISAMPLE = 0x809D
/**
 * Accepted by the `cap` parameter of Enable, Disable, and IsEnabled, and by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and
 * GetDoublev.
 */
const val GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E
/**
 * Accepted by the `cap` parameter of Enable, Disable, and IsEnabled, and by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and
 * GetDoublev.
 */
const val GL_SAMPLE_ALPHA_TO_ONE = 0x809F
/**
 * Accepted by the `cap` parameter of Enable, Disable, and IsEnabled, and by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and
 * GetDoublev.
 */
const val GL_SAMPLE_COVERAGE = 0x80A0
/** Accepted by the `mask` parameter of PushAttrib.  */
const val GL_MULTISAMPLE_BIT = 0x20000000
/** Accepted by the `pname` parameter of GetBooleanv, GetDoublev, GetIntegerv, and GetFloatv.  */
const val GL_SAMPLE_BUFFERS = 0x80A8
/** Accepted by the `pname` parameter of GetBooleanv, GetDoublev, GetIntegerv, and GetFloatv.  */
const val GL_SAMPLES = 0x80A9
/** Accepted by the `pname` parameter of GetBooleanv, GetDoublev, GetIntegerv, and GetFloatv.  */
const val GL_SAMPLE_COVERAGE_VALUE = 0x80AA
/** Accepted by the `pname` parameter of GetBooleanv, GetDoublev, GetIntegerv, and GetFloatv.  */
const val GL_SAMPLE_COVERAGE_INVERT = 0x80AB
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE0 = 0x84C0
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE1 = 0x84C1
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE2 = 0x84C2
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE3 = 0x84C3
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE4 = 0x84C4
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE5 = 0x84C5
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE6 = 0x84C6
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE7 = 0x84C7
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE8 = 0x84C8
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE9 = 0x84C9
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE10 = 0x84CA
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE11 = 0x84CB
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE12 = 0x84CC
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE13 = 0x84CD
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE14 = 0x84CE
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE15 = 0x84CF
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE16 = 0x84D0
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE17 = 0x84D1
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE18 = 0x84D2
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE19 = 0x84D3
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE20 = 0x84D4
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE21 = 0x84D5
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE22 = 0x84D6
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE23 = 0x84D7
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE24 = 0x84D8
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE25 = 0x84D9
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE26 = 0x84DA
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE27 = 0x84DB
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE28 = 0x84DC
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE29 = 0x84DD
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE30 = 0x84DE
/** Accepted by the `texture` parameter of ActiveTexture and MultiTexCoord.  */
const val GL_TEXTURE31 = 0x84DF
/** Accepted by the `pname` parameter of GetBooleanv, GetDoublev, GetIntegerv, and GetFloatv.  */
const val GL_ACTIVE_TEXTURE = 0x84E0
/** Accepted by the `pname` parameter of GetBooleanv, GetDoublev, GetIntegerv, and GetFloatv.  */
const val GL_CLIENT_ACTIVE_TEXTURE = 0x84E1
/** Accepted by the `pname` parameter of GetBooleanv, GetDoublev, GetIntegerv, and GetFloatv.  */
const val GL_MAX_TEXTURE_UNITS = 0x84E2
/** Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is TEXTURE_ENV_MODE.  */
const val GL_COMBINE = 0x8570
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_COMBINE_RGB = 0x8571
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_COMBINE_ALPHA = 0x8572
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_SOURCE0_RGB = 0x8580
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_SOURCE1_RGB = 0x8581
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_SOURCE2_RGB = 0x8582
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_SOURCE0_ALPHA = 0x8588
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_SOURCE1_ALPHA = 0x8589
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_SOURCE2_ALPHA = 0x858A
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_OPERAND0_RGB = 0x8590
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_OPERAND1_RGB = 0x8591
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_OPERAND2_RGB = 0x8592
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_OPERAND0_ALPHA = 0x8598
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_OPERAND1_ALPHA = 0x8599
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_OPERAND2_ALPHA = 0x859A
/** Accepted by the `pname` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `target` parameter value is TEXTURE_ENV.  */
const val GL_RGB_SCALE = 0x8573
/**
 * Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is COMBINE_RGB or
 * COMBINE_ALPHA.
 */
const val GL_ADD_SIGNED = 0x8574
/**
 * Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is COMBINE_RGB or
 * COMBINE_ALPHA.
 */
const val GL_INTERPOLATE = 0x8575
/**
 * Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is COMBINE_RGB or
 * COMBINE_ALPHA.
 */
const val GL_SUBTRACT = 0x84E7
/**
 * Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is SOURCE0_RGB,
 * SOURCE1_RGB, SOURCE2_RGB, SOURCE0_ALPHA, SOURCE1_ALPHA, or SOURCE2_ALPHA.
 */
const val GL_CONSTANT = 0x8576
/**
 * Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is SOURCE0_RGB,
 * SOURCE1_RGB, SOURCE2_RGB, SOURCE0_ALPHA, SOURCE1_ALPHA, or SOURCE2_ALPHA.
 */
const val GL_PRIMARY_COLOR = 0x8577
/**
 * Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is SOURCE0_RGB,
 * SOURCE1_RGB, SOURCE2_RGB, SOURCE0_ALPHA, SOURCE1_ALPHA, or SOURCE2_ALPHA.
 */
const val GL_PREVIOUS = 0x8578
/** Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is COMBINE_RGB_ARB.  */
const val GL_DOT3_RGB = 0x86AE
/** Accepted by the `params` parameter of TexEnvf, TexEnvi, TexEnvfv, and TexEnviv when the `pname` parameter value is COMBINE_RGB_ARB.  */
const val GL_DOT3_RGBA = 0x86AF
/**
 * Accepted by the `param` parameter of TexParameteri and TexParameterf, and by the `params` parameter of TexParameteriv and TexParameterfv,
 * when their `pname` parameter is TEXTURE_WRAP_S, TEXTURE_WRAP_T, or TEXTURE_WRAP_R.
 */
const val GL_CLAMP_TO_BORDER = 0x812D
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_TRANSPOSE_MODELVIEW_MATRIX = 0x84E3
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_TRANSPOSE_PROJECTION_MATRIX = 0x84E4
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_TRANSPOSE_TEXTURE_MATRIX = 0x84E5
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_TRANSPOSE_COLOR_MATRIX = 0x84E6


/** New token names.  */ const val GL_FOG_COORD_SRC = 0x8450
/** New token names.  */ const val GL_FOG_COORD = 0x8451
/** New token names.  */ const val GL_CURRENT_FOG_COORD = 0x8453
/** New token names.  */ const val GL_FOG_COORD_ARRAY_TYPE = 0x8454
/** New token names.  */ const val GL_FOG_COORD_ARRAY_STRIDE = 0x8455
/** New token names.  */ const val GL_FOG_COORD_ARRAY_POINTER = 0x8456
/** New token names.  */ const val GL_FOG_COORD_ARRAY = 0x8457
/** New token names.  */ const val GL_FOG_COORD_ARRAY_BUFFER_BINDING = 0x889D
/** New token names.  */ const val GL_SRC0_RGB = 0x8580
/** New token names.  */ const val GL_SRC1_RGB = 0x8581
/** New token names.  */ const val GL_SRC2_RGB = 0x8582
/** New token names.  */ const val GL_SRC0_ALPHA = 0x8588
/** New token names.  */ const val GL_SRC1_ALPHA = 0x8589
/** New token names.  */ const val GL_SRC2_ALPHA = 0x858A
/**
 * Accepted by the `target` parameters of BindBuffer, BufferData, BufferSubData, MapBuffer, UnmapBuffer, GetBufferSubData,
 * GetBufferParameteriv, and GetBufferPointerv.
 */
const val GL_ARRAY_BUFFER = 0x8892
/**
 * Accepted by the `target` parameters of BindBuffer, BufferData, BufferSubData, MapBuffer, UnmapBuffer, GetBufferSubData,
 * GetBufferParameteriv, and GetBufferPointerv.
 */
const val GL_ELEMENT_ARRAY_BUFFER = 0x8893
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_ARRAY_BUFFER_BINDING = 0x8894
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_VERTEX_ARRAY_BUFFER_BINDING = 0x8896
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_NORMAL_ARRAY_BUFFER_BINDING = 0x8897
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_COLOR_ARRAY_BUFFER_BINDING = 0x8898
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_INDEX_ARRAY_BUFFER_BINDING = 0x8899
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 0x889A
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_EDGE_FLAG_ARRAY_BUFFER_BINDING = 0x889B
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 0x889C
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING = 0x889D
/** Accepted by the `pname` parameter of GetBooleanv, GetIntegerv, GetFloatv, and GetDoublev.  */
const val GL_WEIGHT_ARRAY_BUFFER_BINDING = 0x889E
/** Accepted by the `pname` parameter of GetVertexAttribiv.  */
const val GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_STREAM_DRAW = 0x88E0
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_STREAM_READ = 0x88E1
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_STREAM_COPY = 0x88E2
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_STATIC_DRAW = 0x88E4
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_STATIC_READ = 0x88E5
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_STATIC_COPY = 0x88E6
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_DYNAMIC_DRAW = 0x88E8
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_DYNAMIC_READ = 0x88E9
/** Accepted by the `usage` parameter of BufferData.  */
const val GL_DYNAMIC_COPY = 0x88EA
/** Accepted by the `access` parameter of MapBuffer.  */
const val GL_READ_ONLY = 0x88B8
/** Accepted by the `access` parameter of MapBuffer.  */
const val GL_WRITE_ONLY = 0x88B9
/** Accepted by the `access` parameter of MapBuffer.  */
const val GL_READ_WRITE = 0x88BA
/** Accepted by the `pname` parameter of GetBufferParameteriv.  */
const val GL_BUFFER_SIZE = 0x8764
/** Accepted by the `pname` parameter of GetBufferParameteriv.  */
const val GL_BUFFER_USAGE = 0x8765
/** Accepted by the `pname` parameter of GetBufferParameteriv.  */
const val GL_BUFFER_ACCESS = 0x88BB
/** Accepted by the `pname` parameter of GetBufferParameteriv.  */
const val GL_BUFFER_MAPPED = 0x88BC
/** Accepted by the `pname` parameter of GetBufferPointerv.  */
const val GL_BUFFER_MAP_POINTER = 0x88BD
/** Accepted by the `target` parameter of BeginQuery, EndQuery, and GetQueryiv.  */
const val GL_SAMPLES_PASSED = 0x8914
/** Accepted by the `pname` parameter of GetQueryiv.  */
const val GL_QUERY_COUNTER_BITS = 0x8864
/** Accepted by the `pname` parameter of GetQueryiv.  */
const val GL_CURRENT_QUERY = 0x8865
/** Accepted by the `pname` parameter of GetQueryObjectiv and GetQueryObjectuiv.  */
const val GL_QUERY_RESULT = 0x8866
/** Accepted by the `pname` parameter of GetQueryObjectiv and GetQueryObjectuiv.  */
const val GL_QUERY_RESULT_AVAILABLE = 0x8867

const val GL_ACCUM = 0x100
const val GL_LOAD = 0x101
const val GL_RETURN = 0x102
const val GL_MULT = 0x103
const val GL_ADD = 0x104

/** AlphaFunction  */ const val GL_NEVER = 0x200
/** AlphaFunction  */ const val GL_LESS = 0x201
/** AlphaFunction  */ const val GL_EQUAL = 0x202
/** AlphaFunction  */ const val GL_LEQUAL = 0x203
/** AlphaFunction  */ const val GL_GREATER = 0x204
/** AlphaFunction  */ const val GL_NOTEQUAL = 0x205
/** AlphaFunction  */ const val GL_GEQUAL = 0x206
/** AlphaFunction  */ const val GL_ALWAYS = 0x207

/** AttribMask  */ const val GL_CURRENT_BIT = 0x1
/** AttribMask  */ const val GL_POINT_BIT = 0x2
/** AttribMask  */ const val GL_LINE_BIT = 0x4
/** AttribMask  */ const val GL_POLYGON_BIT = 0x8
/** AttribMask  */ const val GL_POLYGON_STIPPLE_BIT = 0x10
/** AttribMask  */ const val GL_PIXEL_MODE_BIT = 0x20
/** AttribMask  */ const val GL_LIGHTING_BIT = 0x40
/** AttribMask  */ const val GL_FOG_BIT = 0x80
/** AttribMask  */ const val GL_DEPTH_BUFFER_BIT = 0x100
/** AttribMask  */ const val GL_ACCUM_BUFFER_BIT = 0x200
/** AttribMask  */ const val GL_STENCIL_BUFFER_BIT = 0x400
/** AttribMask  */ const val GL_VIEWPORT_BIT = 0x800
/** AttribMask  */ const val GL_TRANSFORM_BIT = 0x1000
/** AttribMask  */ const val GL_ENABLE_BIT = 0x2000
/** AttribMask  */ const val GL_COLOR_BUFFER_BIT = 0x4000
/** AttribMask  */ const val GL_HINT_BIT = 0x8000
/** AttribMask  */ const val GL_EVAL_BIT = 0x10000
/** AttribMask  */ const val GL_LIST_BIT = 0x20000
/** AttribMask  */ const val GL_TEXTURE_BIT = 0x40000
/** AttribMask  */ const val GL_SCISSOR_BIT = 0x80000
/** AttribMask  */ const val GL_ALL_ATTRIB_BITS = 0xFFFFF

/** BeginMode  */ const val GL_POINTS = 0x0
/** BeginMode  */ const val GL_LINES = 0x1
/** BeginMode  */ const val GL_LINE_LOOP = 0x2
/** BeginMode  */ const val GL_LINE_STRIP = 0x3
/** BeginMode  */ const val GL_TRIANGLES = 0x4
/** BeginMode  */ const val GL_TRIANGLE_STRIP = 0x5
/** BeginMode  */ const val GL_TRIANGLE_FAN = 0x6
/** BeginMode  */ const val GL_QUADS = 0x7
/** BeginMode  */ const val GL_QUAD_STRIP = 0x8
/** BeginMode  */ const val GL_POLYGON = 0x9

/** BlendingFactorDest  */ const val GL_ZERO = 0

/** BlendingFactorDest  */ const val GL_ONE = 1

/** BlendingFactorDest  */ const val GL_SRC_COLOR = 0x300
/** BlendingFactorDest  */ const val GL_ONE_MINUS_SRC_COLOR = 0x301
/** BlendingFactorDest  */ const val GL_SRC_ALPHA = 0x302
/** BlendingFactorDest  */ const val GL_ONE_MINUS_SRC_ALPHA = 0x303
/** BlendingFactorDest  */ const val GL_DST_ALPHA = 0x304
/** BlendingFactorDest  */ const val GL_ONE_MINUS_DST_ALPHA = 0x305

/** BlendingFactorSrc  */ const val GL_DST_COLOR = 0x306
/** BlendingFactorSrc  */ const val GL_ONE_MINUS_DST_COLOR = 0x307
/** BlendingFactorSrc  */ const val GL_SRC_ALPHA_SATURATE = 0x308

/** Boolean  */ const val GL_TRUE = 1

/** Boolean  */ const val GL_FALSE = 0

/** ClipPlaneName  */ const val GL_CLIP_PLANE0 = 0x3000
/** ClipPlaneName  */ const val GL_CLIP_PLANE1 = 0x3001
/** ClipPlaneName  */ const val GL_CLIP_PLANE2 = 0x3002
/** ClipPlaneName  */ const val GL_CLIP_PLANE3 = 0x3003
/** ClipPlaneName  */ const val GL_CLIP_PLANE4 = 0x3004
/** ClipPlaneName  */ const val GL_CLIP_PLANE5 = 0x3005

/** DataType  */ const val GL_BYTE = 0x1400
/** DataType  */ const val GL_UNSIGNED_BYTE = 0x1401
/** DataType  */ const val GL_SHORT = 0x1402
/** DataType  */ const val GL_UNSIGNED_SHORT = 0x1403
/** DataType  */ const val GL_INT = 0x1404
/** DataType  */ const val GL_UNSIGNED_INT = 0x1405
/** DataType  */ const val GL_FLOAT = 0x1406
/** DataType  */ const val GL_2_BYTES = 0x1407
/** DataType  */ const val GL_3_BYTES = 0x1408
/** DataType  */ const val GL_4_BYTES = 0x1409
/** DataType  */ const val GL_DOUBLE = 0x140A

/** DrawBufferMode  */ const val GL_NONE = 0

/** DrawBufferMode  */ const val GL_FRONT_LEFT = 0x400
/** DrawBufferMode  */ const val GL_FRONT_RIGHT = 0x401
/** DrawBufferMode  */ const val GL_BACK_LEFT = 0x402
/** DrawBufferMode  */ const val GL_BACK_RIGHT = 0x403
/** DrawBufferMode  */ const val GL_FRONT = 0x404
/** DrawBufferMode  */ const val GL_BACK = 0x405
/** DrawBufferMode  */ const val GL_LEFT = 0x406
/** DrawBufferMode  */ const val GL_RIGHT = 0x407
/** DrawBufferMode  */ const val GL_FRONT_AND_BACK = 0x408
/** DrawBufferMode  */ const val GL_AUX0 = 0x409
/** DrawBufferMode  */ const val GL_AUX1 = 0x40A
/** DrawBufferMode  */ const val GL_AUX2 = 0x40B
/** DrawBufferMode  */ const val GL_AUX3 = 0x40C

/** ErrorCode  */ const val GL_NO_ERROR = 0

/** ErrorCode  */ const val GL_INVALID_ENUM = 0x500
/** ErrorCode  */ const val GL_INVALID_VALUE = 0x501
/** ErrorCode  */ const val GL_INVALID_OPERATION = 0x502
/** ErrorCode  */ const val GL_STACK_OVERFLOW = 0x503
/** ErrorCode  */ const val GL_STACK_UNDERFLOW = 0x504
/** ErrorCode  */ const val GL_OUT_OF_MEMORY = 0x505

/** FeedBackMode  */ const val GL_2D = 0x600
/** FeedBackMode  */ const val GL_3D = 0x601
/** FeedBackMode  */ const val GL_3D_COLOR = 0x602
/** FeedBackMode  */ const val GL_3D_COLOR_TEXTURE = 0x603
/** FeedBackMode  */ const val GL_4D_COLOR_TEXTURE = 0x604

/** FeedBackToken  */ const val GL_PASS_THROUGH_TOKEN = 0x700
/** FeedBackToken  */ const val GL_POINT_TOKEN = 0x701
/** FeedBackToken  */ const val GL_LINE_TOKEN = 0x702
/** FeedBackToken  */ const val GL_POLYGON_TOKEN = 0x703
/** FeedBackToken  */ const val GL_BITMAP_TOKEN = 0x704
/** FeedBackToken  */ const val GL_DRAW_PIXEL_TOKEN = 0x705
/** FeedBackToken  */ const val GL_COPY_PIXEL_TOKEN = 0x706
/** FeedBackToken  */ const val GL_LINE_RESET_TOKEN = 0x707

/** FogMode  */ const val GL_EXP = 0x800
/** FogMode  */ const val GL_EXP2 = 0x801

/** FrontFaceDirection  */ const val GL_CW = 0x900
/** FrontFaceDirection  */ const val GL_CCW = 0x901

/** GetMapTarget  */ const val GL_COEFF = 0xA00
/** GetMapTarget  */ const val GL_ORDER = 0xA01
/** GetMapTarget  */ const val GL_DOMAIN = 0xA02

/** GetTarget  */ const val GL_CURRENT_COLOR = 0xB00
/** GetTarget  */ const val GL_CURRENT_INDEX = 0xB01
/** GetTarget  */ const val GL_CURRENT_NORMAL = 0xB02
/** GetTarget  */ const val GL_CURRENT_TEXTURE_COORDS = 0xB03
/** GetTarget  */ const val GL_CURRENT_RASTER_COLOR = 0xB04
/** GetTarget  */ const val GL_CURRENT_RASTER_INDEX = 0xB05
/** GetTarget  */ const val GL_CURRENT_RASTER_TEXTURE_COORDS = 0xB06
/** GetTarget  */ const val GL_CURRENT_RASTER_POSITION = 0xB07
/** GetTarget  */ const val GL_CURRENT_RASTER_POSITION_VALID = 0xB08
/** GetTarget  */ const val GL_CURRENT_RASTER_DISTANCE = 0xB09
/** GetTarget  */ const val GL_POINT_SMOOTH = 0xB10
/** GetTarget  */ const val GL_POINT_SIZE = 0xB11
/** GetTarget  */ const val GL_POINT_SIZE_RANGE = 0xB12
/** GetTarget  */ const val GL_POINT_SIZE_GRANULARITY = 0xB13
/** GetTarget  */ const val GL_LINE_SMOOTH = 0xB20
/** GetTarget  */ const val GL_LINE_WIDTH = 0xB21
/** GetTarget  */ const val GL_LINE_WIDTH_RANGE = 0xB22
/** GetTarget  */ const val GL_LINE_WIDTH_GRANULARITY = 0xB23
/** GetTarget  */ const val GL_LINE_STIPPLE = 0xB24
/** GetTarget  */ const val GL_LINE_STIPPLE_PATTERN = 0xB25
/** GetTarget  */ const val GL_LINE_STIPPLE_REPEAT = 0xB26
/** GetTarget  */ const val GL_LIST_MODE = 0xB30
/** GetTarget  */ const val GL_MAX_LIST_NESTING = 0xB31
/** GetTarget  */ const val GL_LIST_BASE = 0xB32
/** GetTarget  */ const val GL_LIST_INDEX = 0xB33
/** GetTarget  */ const val GL_POLYGON_MODE = 0xB40
/** GetTarget  */ const val GL_POLYGON_SMOOTH = 0xB41
/** GetTarget  */ const val GL_POLYGON_STIPPLE = 0xB42
/** GetTarget  */ const val GL_EDGE_FLAG = 0xB43
/** GetTarget  */ const val GL_CULL_FACE = 0xB44
/** GetTarget  */ const val GL_CULL_FACE_MODE = 0xB45
/** GetTarget  */ const val GL_FRONT_FACE = 0xB46
/** GetTarget  */ const val GL_LIGHTING = 0xB50
/** GetTarget  */ const val GL_LIGHT_MODEL_LOCAL_VIEWER = 0xB51
/** GetTarget  */ const val GL_LIGHT_MODEL_TWO_SIDE = 0xB52
/** GetTarget  */ const val GL_LIGHT_MODEL_AMBIENT = 0xB53
/** GetTarget  */ const val GL_SHADE_MODEL = 0xB54
/** GetTarget  */ const val GL_COLOR_MATERIAL_FACE = 0xB55
/** GetTarget  */ const val GL_COLOR_MATERIAL_PARAMETER = 0xB56
/** GetTarget  */ const val GL_COLOR_MATERIAL = 0xB57
/** GetTarget  */ const val GL_FOG = 0xB60
/** GetTarget  */ const val GL_FOG_INDEX = 0xB61
/** GetTarget  */ const val GL_FOG_DENSITY = 0xB62
/** GetTarget  */ const val GL_FOG_START = 0xB63
/** GetTarget  */ const val GL_FOG_END = 0xB64
/** GetTarget  */ const val GL_FOG_MODE = 0xB65
/** GetTarget  */ const val GL_FOG_COLOR = 0xB66
/** GetTarget  */ const val GL_DEPTH_RANGE = 0xB70
/** GetTarget  */ const val GL_DEPTH_TEST = 0xB71
/** GetTarget  */ const val GL_DEPTH_WRITEMASK = 0xB72
/** GetTarget  */ const val GL_DEPTH_CLEAR_VALUE = 0xB73
/** GetTarget  */ const val GL_DEPTH_FUNC = 0xB74
/** GetTarget  */ const val GL_ACCUM_CLEAR_VALUE = 0xB80
/** GetTarget  */ const val GL_STENCIL_TEST = 0xB90
/** GetTarget  */ const val GL_STENCIL_CLEAR_VALUE = 0xB91
/** GetTarget  */ const val GL_STENCIL_FUNC = 0xB92
/** GetTarget  */ const val GL_STENCIL_VALUE_MASK = 0xB93
/** GetTarget  */ const val GL_STENCIL_FAIL = 0xB94
/** GetTarget  */ const val GL_STENCIL_PASS_DEPTH_FAIL = 0xB95
/** GetTarget  */ const val GL_STENCIL_PASS_DEPTH_PASS = 0xB96
/** GetTarget  */ const val GL_STENCIL_REF = 0xB97
/** GetTarget  */ const val GL_STENCIL_WRITEMASK = 0xB98
/** GetTarget  */ const val GL_MATRIX_MODE = 0xBA0
/** GetTarget  */ const val GL_NORMALIZE = 0xBA1
/** GetTarget  */ const val GL_VIEWPORT = 0xBA2
/** GetTarget  */ const val GL_MODELVIEW_STACK_DEPTH = 0xBA3
/** GetTarget  */ const val GL_PROJECTION_STACK_DEPTH = 0xBA4
/** GetTarget  */ const val GL_TEXTURE_STACK_DEPTH = 0xBA5
/** GetTarget  */ const val GL_MODELVIEW_MATRIX = 0xBA6
/** GetTarget  */ const val GL_PROJECTION_MATRIX = 0xBA7
/** GetTarget  */ const val GL_TEXTURE_MATRIX = 0xBA8
/** GetTarget  */ const val GL_ATTRIB_STACK_DEPTH = 0xBB0
/** GetTarget  */ const val GL_CLIENT_ATTRIB_STACK_DEPTH = 0xBB1
/** GetTarget  */ const val GL_ALPHA_TEST = 0xBC0
/** GetTarget  */ const val GL_ALPHA_TEST_FUNC = 0xBC1
/** GetTarget  */ const val GL_ALPHA_TEST_REF = 0xBC2
/** GetTarget  */ const val GL_DITHER = 0xBD0
/** GetTarget  */ const val GL_BLEND_DST = 0xBE0
/** GetTarget  */ const val GL_BLEND_SRC = 0xBE1
/** GetTarget  */ const val GL_BLEND = 0xBE2
/** GetTarget  */ const val GL_LOGIC_OP_MODE = 0xBF0
/** GetTarget  */ const val GL_INDEX_LOGIC_OP = 0xBF1
/** GetTarget  */ const val GL_LOGIC_OP = 0xBF1
/** GetTarget  */ const val GL_COLOR_LOGIC_OP = 0xBF2
/** GetTarget  */ const val GL_AUX_BUFFERS = 0xC00
/** GetTarget  */ const val GL_DRAW_BUFFER = 0xC01
/** GetTarget  */ const val GL_READ_BUFFER = 0xC02
/** GetTarget  */ const val GL_SCISSOR_BOX = 0xC10
/** GetTarget  */ const val GL_SCISSOR_TEST = 0xC11
/** GetTarget  */ const val GL_INDEX_CLEAR_VALUE = 0xC20
/** GetTarget  */ const val GL_INDEX_WRITEMASK = 0xC21
/** GetTarget  */ const val GL_COLOR_CLEAR_VALUE = 0xC22
/** GetTarget  */ const val GL_COLOR_WRITEMASK = 0xC23
/** GetTarget  */ const val GL_INDEX_MODE = 0xC30
/** GetTarget  */ const val GL_RGBA_MODE = 0xC31
/** GetTarget  */ const val GL_DOUBLEBUFFER = 0xC32
/** GetTarget  */ const val GL_STEREO = 0xC33
/** GetTarget  */ const val GL_RENDER_MODE = 0xC40
/** GetTarget  */ const val GL_PERSPECTIVE_CORRECTION_HINT = 0xC50
/** GetTarget  */ const val GL_POINT_SMOOTH_HINT = 0xC51
/** GetTarget  */ const val GL_LINE_SMOOTH_HINT = 0xC52
/** GetTarget  */ const val GL_POLYGON_SMOOTH_HINT = 0xC53
/** GetTarget  */ const val GL_FOG_HINT = 0xC54
/** GetTarget  */ const val GL_TEXTURE_GEN_S = 0xC60
/** GetTarget  */ const val GL_TEXTURE_GEN_T = 0xC61
/** GetTarget  */ const val GL_TEXTURE_GEN_R = 0xC62
/** GetTarget  */ const val GL_TEXTURE_GEN_Q = 0xC63
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_I = 0xC70
/** GetTarget  */ const val GL_PIXEL_MAP_S_TO_S = 0xC71
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_R = 0xC72
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_G = 0xC73
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_B = 0xC74
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_A = 0xC75
/** GetTarget  */ const val GL_PIXEL_MAP_R_TO_R = 0xC76
/** GetTarget  */ const val GL_PIXEL_MAP_G_TO_G = 0xC77
/** GetTarget  */ const val GL_PIXEL_MAP_B_TO_B = 0xC78
/** GetTarget  */ const val GL_PIXEL_MAP_A_TO_A = 0xC79
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_I_SIZE = 0xCB0
/** GetTarget  */ const val GL_PIXEL_MAP_S_TO_S_SIZE = 0xCB1
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_R_SIZE = 0xCB2
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_G_SIZE = 0xCB3
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_B_SIZE = 0xCB4
/** GetTarget  */ const val GL_PIXEL_MAP_I_TO_A_SIZE = 0xCB5
/** GetTarget  */ const val GL_PIXEL_MAP_R_TO_R_SIZE = 0xCB6
/** GetTarget  */ const val GL_PIXEL_MAP_G_TO_G_SIZE = 0xCB7
/** GetTarget  */ const val GL_PIXEL_MAP_B_TO_B_SIZE = 0xCB8
/** GetTarget  */ const val GL_PIXEL_MAP_A_TO_A_SIZE = 0xCB9
/** GetTarget  */ const val GL_UNPACK_SWAP_BYTES = 0xCF0
/** GetTarget  */ const val GL_UNPACK_LSB_FIRST = 0xCF1
/** GetTarget  */ const val GL_UNPACK_ROW_LENGTH = 0xCF2
/** GetTarget  */ const val GL_UNPACK_SKIP_ROWS = 0xCF3
/** GetTarget  */ const val GL_UNPACK_SKIP_PIXELS = 0xCF4
/** GetTarget  */ const val GL_UNPACK_ALIGNMENT = 0xCF5
/** GetTarget  */ const val GL_PACK_SWAP_BYTES = 0xD00
/** GetTarget  */ const val GL_PACK_LSB_FIRST = 0xD01
/** GetTarget  */ const val GL_PACK_ROW_LENGTH = 0xD02
/** GetTarget  */ const val GL_PACK_SKIP_ROWS = 0xD03
/** GetTarget  */ const val GL_PACK_SKIP_PIXELS = 0xD04
/** GetTarget  */ const val GL_PACK_ALIGNMENT = 0xD05
/** GetTarget  */ const val GL_MAP_COLOR = 0xD10
/** GetTarget  */ const val GL_MAP_STENCIL = 0xD11
/** GetTarget  */ const val GL_INDEX_SHIFT = 0xD12
/** GetTarget  */ const val GL_INDEX_OFFSET = 0xD13
/** GetTarget  */ const val GL_RED_SCALE = 0xD14
/** GetTarget  */ const val GL_RED_BIAS = 0xD15
/** GetTarget  */ const val GL_ZOOM_X = 0xD16
/** GetTarget  */ const val GL_ZOOM_Y = 0xD17
/** GetTarget  */ const val GL_GREEN_SCALE = 0xD18
/** GetTarget  */ const val GL_GREEN_BIAS = 0xD19
/** GetTarget  */ const val GL_BLUE_SCALE = 0xD1A
/** GetTarget  */ const val GL_BLUE_BIAS = 0xD1B
/** GetTarget  */ const val GL_ALPHA_SCALE = 0xD1C
/** GetTarget  */ const val GL_ALPHA_BIAS = 0xD1D
/** GetTarget  */ const val GL_DEPTH_SCALE = 0xD1E
/** GetTarget  */ const val GL_DEPTH_BIAS = 0xD1F
/** GetTarget  */ const val GL_MAX_EVAL_ORDER = 0xD30
/** GetTarget  */ const val GL_MAX_LIGHTS = 0xD31
/** GetTarget  */ const val GL_MAX_CLIP_PLANES = 0xD32
/** GetTarget  */ const val GL_MAX_TEXTURE_SIZE = 0xD33
/** GetTarget  */ const val GL_MAX_PIXEL_MAP_TABLE = 0xD34
/** GetTarget  */ const val GL_MAX_ATTRIB_STACK_DEPTH = 0xD35
/** GetTarget  */ const val GL_MAX_MODELVIEW_STACK_DEPTH = 0xD36
/** GetTarget  */ const val GL_MAX_NAME_STACK_DEPTH = 0xD37
/** GetTarget  */ const val GL_MAX_PROJECTION_STACK_DEPTH = 0xD38
/** GetTarget  */ const val GL_MAX_TEXTURE_STACK_DEPTH = 0xD39
/** GetTarget  */ const val GL_MAX_VIEWPORT_DIMS = 0xD3A
/** GetTarget  */ const val GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 0xD3B
/** GetTarget  */ const val GL_SUBPIXEL_BITS = 0xD50
/** GetTarget  */ const val GL_INDEX_BITS = 0xD51
/** GetTarget  */ const val GL_RED_BITS = 0xD52
/** GetTarget  */ const val GL_GREEN_BITS = 0xD53
/** GetTarget  */ const val GL_BLUE_BITS = 0xD54
/** GetTarget  */ const val GL_ALPHA_BITS = 0xD55
/** GetTarget  */ const val GL_DEPTH_BITS = 0xD56
/** GetTarget  */ const val GL_STENCIL_BITS = 0xD57
/** GetTarget  */ const val GL_ACCUM_RED_BITS = 0xD58
/** GetTarget  */ const val GL_ACCUM_GREEN_BITS = 0xD59
/** GetTarget  */ const val GL_ACCUM_BLUE_BITS = 0xD5A
/** GetTarget  */ const val GL_ACCUM_ALPHA_BITS = 0xD5B
/** GetTarget  */ const val GL_NAME_STACK_DEPTH = 0xD70
/** GetTarget  */ const val GL_AUTO_NORMAL = 0xD80
/** GetTarget  */ const val GL_MAP1_COLOR_4 = 0xD90
/** GetTarget  */ const val GL_MAP1_INDEX = 0xD91
/** GetTarget  */ const val GL_MAP1_NORMAL = 0xD92
/** GetTarget  */ const val GL_MAP1_TEXTURE_COORD_1 = 0xD93
/** GetTarget  */ const val GL_MAP1_TEXTURE_COORD_2 = 0xD94
/** GetTarget  */ const val GL_MAP1_TEXTURE_COORD_3 = 0xD95
/** GetTarget  */ const val GL_MAP1_TEXTURE_COORD_4 = 0xD96
/** GetTarget  */ const val GL_MAP1_VERTEX_3 = 0xD97
/** GetTarget  */ const val GL_MAP1_VERTEX_4 = 0xD98
/** GetTarget  */ const val GL_MAP2_COLOR_4 = 0xDB0
/** GetTarget  */ const val GL_MAP2_INDEX = 0xDB1
/** GetTarget  */ const val GL_MAP2_NORMAL = 0xDB2
/** GetTarget  */ const val GL_MAP2_TEXTURE_COORD_1 = 0xDB3
/** GetTarget  */ const val GL_MAP2_TEXTURE_COORD_2 = 0xDB4
/** GetTarget  */ const val GL_MAP2_TEXTURE_COORD_3 = 0xDB5
/** GetTarget  */ const val GL_MAP2_TEXTURE_COORD_4 = 0xDB6
/** GetTarget  */ const val GL_MAP2_VERTEX_3 = 0xDB7
/** GetTarget  */ const val GL_MAP2_VERTEX_4 = 0xDB8
/** GetTarget  */ const val GL_MAP1_GRID_DOMAIN = 0xDD0
/** GetTarget  */ const val GL_MAP1_GRID_SEGMENTS = 0xDD1
/** GetTarget  */ const val GL_MAP2_GRID_DOMAIN = 0xDD2
/** GetTarget  */ const val GL_MAP2_GRID_SEGMENTS = 0xDD3
/** GetTarget  */ const val GL_TEXTURE_1D = 0xDE0
/** GetTarget  */ const val GL_TEXTURE_2D = 0xDE1
/** GetTarget  */ const val GL_FEEDBACK_BUFFER_POINTER = 0xDF0
/** GetTarget  */ const val GL_FEEDBACK_BUFFER_SIZE = 0xDF1
/** GetTarget  */ const val GL_FEEDBACK_BUFFER_TYPE = 0xDF2
/** GetTarget  */ const val GL_SELECTION_BUFFER_POINTER = 0xDF3
/** GetTarget  */ const val GL_SELECTION_BUFFER_SIZE = 0xDF4

/** GetTextureParameter  */ const val GL_TEXTURE_WIDTH = 0x1000
/** GetTextureParameter  */ const val GL_TEXTURE_HEIGHT = 0x1001
/** GetTextureParameter  */ const val GL_TEXTURE_INTERNAL_FORMAT = 0x1003
/** GetTextureParameter  */ const val GL_TEXTURE_COMPONENTS = 0x1003
/** GetTextureParameter  */ const val GL_TEXTURE_BORDER_COLOR = 0x1004
/** GetTextureParameter  */ const val GL_TEXTURE_BORDER = 0x1005

/** HintMode  */ const val GL_DONT_CARE = 0x1100
/** HintMode  */ const val GL_FASTEST = 0x1101
/** HintMode  */ const val GL_NICEST = 0x1102

/** LightName  */ const val GL_LIGHT0 = 0x4000
/** LightName  */ const val GL_LIGHT1 = 0x4001
/** LightName  */ const val GL_LIGHT2 = 0x4002
/** LightName  */ const val GL_LIGHT3 = 0x4003
/** LightName  */ const val GL_LIGHT4 = 0x4004
/** LightName  */ const val GL_LIGHT5 = 0x4005
/** LightName  */ const val GL_LIGHT6 = 0x4006
/** LightName  */ const val GL_LIGHT7 = 0x4007

/** LightParameter  */ const val GL_AMBIENT = 0x1200
/** LightParameter  */ const val GL_DIFFUSE = 0x1201
/** LightParameter  */ const val GL_SPECULAR = 0x1202
/** LightParameter  */ const val GL_POSITION = 0x1203
/** LightParameter  */ const val GL_SPOT_DIRECTION = 0x1204
/** LightParameter  */ const val GL_SPOT_EXPONENT = 0x1205
/** LightParameter  */ const val GL_SPOT_CUTOFF = 0x1206
/** LightParameter  */ const val GL_CONSTANT_ATTENUATION = 0x1207
/** LightParameter  */ const val GL_LINEAR_ATTENUATION = 0x1208
/** LightParameter  */ const val GL_QUADRATIC_ATTENUATION = 0x1209

/** ListMode  */ const val GL_COMPILE = 0x1300
/** ListMode  */ const val GL_COMPILE_AND_EXECUTE = 0x1301

/** LogicOp  */ const val GL_CLEAR = 0x1500
/** LogicOp  */ const val GL_AND = 0x1501
/** LogicOp  */ const val GL_AND_REVERSE = 0x1502
/** LogicOp  */ const val GL_COPY = 0x1503
/** LogicOp  */ const val GL_AND_INVERTED = 0x1504
/** LogicOp  */ const val GL_NOOP = 0x1505
/** LogicOp  */ const val GL_XOR = 0x1506
/** LogicOp  */ const val GL_OR = 0x1507
/** LogicOp  */ const val GL_NOR = 0x1508
/** LogicOp  */ const val GL_EQUIV = 0x1509
/** LogicOp  */ const val GL_INVERT = 0x150A
/** LogicOp  */ const val GL_OR_REVERSE = 0x150B
/** LogicOp  */ const val GL_COPY_INVERTED = 0x150C
/** LogicOp  */ const val GL_OR_INVERTED = 0x150D
/** LogicOp  */ const val GL_NAND = 0x150E
/** LogicOp  */ const val GL_SET = 0x150F

/** MaterialParameter  */ const val GL_EMISSION = 0x1600
/** MaterialParameter  */ const val GL_SHININESS = 0x1601
/** MaterialParameter  */ const val GL_AMBIENT_AND_DIFFUSE = 0x1602
/** MaterialParameter  */ const val GL_COLOR_INDEXES = 0x1603

/** MatrixMode  */ const val GL_MODELVIEW = 0x1700
/** MatrixMode  */ const val GL_PROJECTION = 0x1701
/** MatrixMode  */ const val GL_TEXTURE = 0x1702

/** PixelCopyType  */ const val GL_COLOR = 0x1800
/** PixelCopyType  */ const val GL_DEPTH = 0x1801
/** PixelCopyType  */ const val GL_STENCIL = 0x1802

/** PixelFormat  */ const val GL_COLOR_INDEX = 0x1900
/** PixelFormat  */ const val GL_STENCIL_INDEX = 0x1901
/** PixelFormat  */ const val GL_DEPTH_COMPONENT = 0x1902
/** PixelFormat  */ const val GL_RED = 0x1903
/** PixelFormat  */ const val GL_GREEN = 0x1904
/** PixelFormat  */ const val GL_BLUE = 0x1905
/** PixelFormat  */ const val GL_ALPHA = 0x1906
/** PixelFormat  */ const val GL_RGB = 0x1907
/** PixelFormat  */ const val GL_RGBA = 0x1908
/** PixelFormat  */ const val GL_LUMINANCE = 0x1909
/** PixelFormat  */ const val GL_LUMINANCE_ALPHA = 0x190A

/** PixelType  */ const val GL_BITMAP = 0x1A00

/** PolygonMode  */ const val GL_POINT = 0x1B00
/** PolygonMode  */ const val GL_LINE = 0x1B01
/** PolygonMode  */ const val GL_FILL = 0x1B02

/** RenderingMode  */ const val GL_RENDER = 0x1C00
/** RenderingMode  */ const val GL_FEEDBACK = 0x1C01
/** RenderingMode  */ const val GL_SELECT = 0x1C02

/** ShadingModel  */ const val GL_FLAT = 0x1D00
/** ShadingModel  */ const val GL_SMOOTH = 0x1D01

/** StencilOp  */ const val GL_KEEP = 0x1E00
/** StencilOp  */ const val GL_REPLACE = 0x1E01
/** StencilOp  */ const val GL_INCR = 0x1E02
/** StencilOp  */ const val GL_DECR = 0x1E03

/** StringName  */ const val GL_VENDOR = 0x1F00
/** StringName  */ const val GL_RENDERER = 0x1F01
/** StringName  */ const val GL_VERSION = 0x1F02
/** StringName  */ const val GL_EXTENSIONS = 0x1F03

/** TextureCoordName  */ const val GL_S = 0x2000
/** TextureCoordName  */ const val GL_T = 0x2001
/** TextureCoordName  */ const val GL_R = 0x2002
/** TextureCoordName  */ const val GL_Q = 0x2003

/** TextureEnvMode  */ const val GL_MODULATE = 0x2100
/** TextureEnvMode  */ const val GL_DECAL = 0x2101

/** TextureEnvParameter  */ const val GL_TEXTURE_ENV_MODE = 0x2200
/** TextureEnvParameter  */ const val GL_TEXTURE_ENV_COLOR = 0x2201

/** TextureEnvTarget  */ const val GL_TEXTURE_ENV = 0x2300

/** TextureGenMode  */ const val GL_EYE_LINEAR = 0x2400
/** TextureGenMode  */ const val GL_OBJECT_LINEAR = 0x2401
/** TextureGenMode  */ const val GL_SPHERE_MAP = 0x2402

/** TextureGenParameter  */ const val GL_TEXTURE_GEN_MODE = 0x2500
/** TextureGenParameter  */ const val GL_OBJECT_PLANE = 0x2501
/** TextureGenParameter  */ const val GL_EYE_PLANE = 0x2502

/** TextureMagFilter  */ const val GL_NEAREST = 0x2600
/** TextureMagFilter  */ const val GL_LINEAR = 0x2601

/** TextureMinFilter  */ const val GL_NEAREST_MIPMAP_NEAREST = 0x2700
/** TextureMinFilter  */ const val GL_LINEAR_MIPMAP_NEAREST = 0x2701
/** TextureMinFilter  */ const val GL_NEAREST_MIPMAP_LINEAR = 0x2702
/** TextureMinFilter  */ const val GL_LINEAR_MIPMAP_LINEAR = 0x2703

/** TextureParameterName  */ const val GL_TEXTURE_MAG_FILTER = 0x2800
/** TextureParameterName  */ const val GL_TEXTURE_MIN_FILTER = 0x2801
/** TextureParameterName  */ const val GL_TEXTURE_WRAP_S = 0x2802
/** TextureParameterName  */ const val GL_TEXTURE_WRAP_T = 0x2803

/** TextureWrapMode  */ const val GL_CLAMP = 0x2900
/** TextureWrapMode  */ const val GL_REPEAT = 0x2901

/** ClientAttribMask  */ const val GL_CLIENT_PIXEL_STORE_BIT = 0x1
/** ClientAttribMask  */ const val GL_CLIENT_VERTEX_ARRAY_BIT = 0x2
/** ClientAttribMask  */ const val GL_CLIENT_ALL_ATTRIB_BITS = -0x1

/** polygon_offset  */ const val GL_POLYGON_OFFSET_FACTOR = 0x8038
/** polygon_offset  */ const val GL_POLYGON_OFFSET_UNITS = 0x2A00
/** polygon_offset  */ const val GL_POLYGON_OFFSET_POINT = 0x2A01
/** polygon_offset  */ const val GL_POLYGON_OFFSET_LINE = 0x2A02
/** polygon_offset  */ const val GL_POLYGON_OFFSET_FILL = 0x8037

/** texture  */ const val GL_ALPHA4 = 0x803B
/** texture  */ const val GL_ALPHA8 = 0x803C
/** texture  */ const val GL_ALPHA12 = 0x803D
/** texture  */ const val GL_ALPHA16 = 0x803E
/** texture  */ const val GL_LUMINANCE4 = 0x803F
/** texture  */ const val GL_LUMINANCE8 = 0x8040
/** texture  */ const val GL_LUMINANCE12 = 0x8041
/** texture  */ const val GL_LUMINANCE16 = 0x8042
/** texture  */ const val GL_LUMINANCE4_ALPHA4 = 0x8043
/** texture  */ const val GL_LUMINANCE6_ALPHA2 = 0x8044
/** texture  */ const val GL_LUMINANCE8_ALPHA8 = 0x8045
/** texture  */ const val GL_LUMINANCE12_ALPHA4 = 0x8046
/** texture  */ const val GL_LUMINANCE12_ALPHA12 = 0x8047
/** texture  */ const val GL_LUMINANCE16_ALPHA16 = 0x8048
/** texture  */ const val GL_INTENSITY = 0x8049
/** texture  */ const val GL_INTENSITY4 = 0x804A
/** texture  */ const val GL_INTENSITY8 = 0x804B
/** texture  */ const val GL_INTENSITY12 = 0x804C
/** texture  */ const val GL_INTENSITY16 = 0x804D
/** texture  */ const val GL_R3_G3_B2 = 0x2A10
/** texture  */ const val GL_RGB4 = 0x804F
/** texture  */ const val GL_RGB5 = 0x8050
/** texture  */ const val GL_RGB8 = 0x8051
/** texture  */ const val GL_RGB10 = 0x8052
/** texture  */ const val GL_RGB12 = 0x8053
/** texture  */ const val GL_RGB16 = 0x8054
/** texture  */ const val GL_RGBA2 = 0x8055
/** texture  */ const val GL_RGBA4 = 0x8056
/** texture  */ const val GL_RGB5_A1 = 0x8057
/** texture  */ const val GL_RGBA8 = 0x8058
/** texture  */ const val GL_RGB10_A2 = 0x8059
/** texture  */ const val GL_RGBA12 = 0x805A
/** texture  */ const val GL_RGBA16 = 0x805B
/** texture  */ const val GL_TEXTURE_RED_SIZE = 0x805C
/** texture  */ const val GL_TEXTURE_GREEN_SIZE = 0x805D
/** texture  */ const val GL_TEXTURE_BLUE_SIZE = 0x805E
/** texture  */ const val GL_TEXTURE_ALPHA_SIZE = 0x805F
/** texture  */ const val GL_TEXTURE_LUMINANCE_SIZE = 0x8060
/** texture  */ const val GL_TEXTURE_INTENSITY_SIZE = 0x8061
/** texture  */ const val GL_PROXY_TEXTURE_1D = 0x8063
/** texture  */ const val GL_PROXY_TEXTURE_2D = 0x806

/** texture_object  */ const val GL_TEXTURE_PRIORITY = 0x8066
/** texture_object  */ const val GL_TEXTURE_RESIDENT = 0x8067
/** texture_object  */ const val GL_TEXTURE_BINDING_1D = 0x8068
/** texture_object  */ const val GL_TEXTURE_BINDING_2D = 0x806

/** vertex_array  */ const val GL_VERTEX_ARRAY = 0x8074
/** vertex_array  */ const val GL_NORMAL_ARRAY = 0x8075
/** vertex_array  */ const val GL_COLOR_ARRAY = 0x8076
/** vertex_array  */ const val GL_INDEX_ARRAY = 0x8077
/** vertex_array  */ const val GL_TEXTURE_COORD_ARRAY = 0x8078
/** vertex_array  */ const val GL_EDGE_FLAG_ARRAY = 0x8079
/** vertex_array  */ const val GL_VERTEX_ARRAY_SIZE = 0x807A
/** vertex_array  */ const val GL_VERTEX_ARRAY_TYPE = 0x807B
/** vertex_array  */ const val GL_VERTEX_ARRAY_STRIDE = 0x807C
/** vertex_array  */ const val GL_NORMAL_ARRAY_TYPE = 0x807E
/** vertex_array  */ const val GL_NORMAL_ARRAY_STRIDE = 0x807F
/** vertex_array  */ const val GL_COLOR_ARRAY_SIZE = 0x8081
/** vertex_array  */ const val GL_COLOR_ARRAY_TYPE = 0x8082
/** vertex_array  */ const val GL_COLOR_ARRAY_STRIDE = 0x8083
/** vertex_array  */ const val GL_INDEX_ARRAY_TYPE = 0x8085
/** vertex_array  */ const val GL_INDEX_ARRAY_STRIDE = 0x8086
/** vertex_array  */ const val GL_TEXTURE_COORD_ARRAY_SIZE = 0x8088
/** vertex_array  */ const val GL_TEXTURE_COORD_ARRAY_TYPE = 0x8089
/** vertex_array  */ const val GL_TEXTURE_COORD_ARRAY_STRIDE = 0x808A
/** vertex_array  */ const val GL_EDGE_FLAG_ARRAY_STRIDE = 0x808C
/** vertex_array  */ const val GL_VERTEX_ARRAY_POINTER = 0x808E
/** vertex_array  */ const val GL_NORMAL_ARRAY_POINTER = 0x808F
/** vertex_array  */ const val GL_COLOR_ARRAY_POINTER = 0x8090
/** vertex_array  */ const val GL_INDEX_ARRAY_POINTER = 0x8091
/** vertex_array  */ const val GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092
/** vertex_array  */ const val GL_EDGE_FLAG_ARRAY_POINTER = 0x8093
/** vertex_array  */ const val GL_V2F = 0x2A20
/** vertex_array  */ const val GL_V3F = 0x2A21
/** vertex_array  */ const val GL_C4UB_V2F = 0x2A22
/** vertex_array  */ const val GL_C4UB_V3F = 0x2A23
/** vertex_array  */ const val GL_C3F_V3F = 0x2A24
/** vertex_array  */ const val GL_N3F_V3F = 0x2A25
/** vertex_array  */ const val GL_C4F_N3F_V3F = 0x2A26
/** vertex_array  */ const val GL_T2F_V3F = 0x2A27
/** vertex_array  */ const val GL_T4F_V4F = 0x2A28
/** vertex_array  */ const val GL_T2F_C4UB_V3F = 0x2A29
/** vertex_array  */ const val GL_T2F_C3F_V3F = 0x2A2A
/** vertex_array  */ const val GL_T2F_N3F_V3F = 0x2A2B
/** vertex_array  */ const val GL_T2F_C4F_N3F_V3F = 0x2A2C
/** vertex_array  */ const val GL_T4F_C4F_N3F_V4F = 0x2A2D


@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
annotation class NativeType(val value: String)
