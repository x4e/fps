@file:Suppress("unused", "NOTHING_TO_INLINE")

package dev.binclub.fps.client.utils.al

import org.lwjgl.openal.AL
import org.lwjgl.openal.AL11
import org.lwjgl.openal.ALC
import org.lwjgl.openal.ALC11
import java.nio.IntBuffer

/**
 * @author cookiedragon234 23/Jul/2020
 */
inline fun alCheckError() = Unit.alCheckError()
fun <T> T.alCheckError(): T = this.apply {
	when (val err = AL11.alGetError()) {
		AL_NO_ERROR -> return this
		AL_INVALID_NAME -> error("AL_INVALID_NAME")
		AL_INVALID_ENUM -> error("AL_INVALID_ENUM")
		AL_INVALID_VALUE -> error("AL_INVALID_VALUE")
		AL_INVALID_OPERATION -> error("AL_INVALID_OPERATION")
		AL_OUT_OF_MEMORY -> error("AL_OUT_OF_MEMORY")
		else -> error("Unknown OpenAL err occured (0x${err.toString(16)})")
	}
}

fun alCreateContext(device: Long)
	= ALC11.alcCreateContext(device, null as IntBuffer)

fun alMakeContextCurrent(context: Long)
	= ALC11.alcMakeContextCurrent(context)

fun alCreateCapabilities(device: Long)
	= ALC.createCapabilities(device).let {
		it to AL.createCapabilities(it)
	}

fun alGetDevices()
	= ALC11.alcGetString(0, ALC_DEVICE_SPECIFIER)
		?.split('\u0000')
		//.alCheckError()
		?: error("Couldn't retrieve available devices")

fun alDefaultDevice()
	= ALC11.alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER)
		//.alCheckError()
		?: error("Couldn't retrieve default device")

fun alOpenDevice(device: String)
	= ALC11.alcOpenDevice(device)

fun alCloseDevice(device: Long)
	= ALC11.alcCloseDevice(device)
		.alCheckError()

fun alGetFrequency(device: Long): Int
	= ALC11.alcGetInteger(device, ALC_FREQUENCY)

fun alGetRefreshRate(device: Long): Int
	= ALC11.alcGetInteger(device, ALC_REFRESH)

fun alGetVersionMajor(device: Long): Int
	= ALC11.alcGetInteger(device, ALC_MAJOR_VERSION)

fun alGetVersionMinor(device: Long): Int
	= ALC11.alcGetInteger(device, ALC_MINOR_VERSION)

fun alGetVersionString()// = "${alGetVersionMajor(device)}.${alGetVersionMinor(device)}"
	= AL11.alGetString(AL_VERSION)
		?: error("Couldn't retrieve vendor")

fun alGetVendor()
	= AL11.alGetString(AL_VENDOR)
		?: error("Couldn't retrieve vendor")

fun alGetRenderer()
	= AL11.alGetString(AL_RENDERER)


/** General tokens.  */ const val AL_INVALID = -0x1
/** General tokens.  */ const val AL_NONE = 0x0
/** General tokens.  */ const val AL_FALSE = 0x0
/** General tokens.  */ const val AL_TRUE = 0x1

/** Context creation attributes.  */ const val ALC_FREQUENCY = 0x1007
/** Context creation attributes.  */ const val ALC_REFRESH = 0x1008
/** Context creation attributes.  */ const val ALC_SYNC = 0x1009

/** Error conditions.  */ const val AL_NO_ERROR = 0x0
/** Error conditions.  */ const val AL_INVALID_NAME = 0xA001
/** Error conditions.  */ const val AL_INVALID_ENUM = 0xA002
/** Error conditions.  */ const val AL_INVALID_VALUE = 0xA003
/** Error conditions.  */ const val AL_INVALID_OPERATION = 0xA004
/** Error conditions.  */ const val AL_OUT_OF_MEMORY = 0xA005

/** String queries.  */ const val ALC_DEFAULT_DEVICE_SPECIFIER = 0x1004
/** String queries.  */ const val ALC_DEVICE_SPECIFIER = 0x1005
/** String queries.  */ const val ALC_EXTENSIONS = 0x1006

/** Integer queries.  */ const val ALC_MAJOR_VERSION = 0x1000
/** Integer queries.  */ const val ALC_MINOR_VERSION = 0x1001
/** Integer queries.  */ const val ALC_ATTRIBUTES_SIZE = 0x1002
/** Integer queries.  */ const val ALC_ALL_ATTRIBUTES = 0x1003

/** Numerical queries.  */ const val AL_DOPPLER_FACTOR = 0xC000
/** Numerical queries.  */ const val AL_DISTANCE_MODEL = 0xD000

/** String queries.  */ const val AL_VENDOR = 0xB001
/** String queries.  */ const val AL_VERSION = 0xB002
/** String queries.  */ const val AL_RENDERER = 0xB003
/** String queries.  */ const val AL_EXTENSIONS = 0xB004

/** Distance attenuation models.  */ const val AL_INVERSE_DISTANCE = 0xD001
/** Distance attenuation models.  */ const val AL_INVERSE_DISTANCE_CLAMPED = 0xD002

/** Source types.  */ const val AL_SOURCE_ABSOLUTE = 0x201
/** Source types.  */ const val AL_SOURCE_RELATIVE = 0x202

/** Listener and Source attributes.  */ const val AL_POSITION = 0x1004
/** Listener and Source attributes.  */ const val AL_VELOCITY = 0x1006
/** Listener and Source attributes.  */ const val AL_GAIN = 0x100A

/** Source attributes.  */ const val AL_CONE_INNER_ANGLE = 0x1001
/** Source attributes.  */ const val AL_CONE_OUTER_ANGLE = 0x1002
/** Source attributes.  */ const val AL_PITCH = 0x1003
/** Source attributes.  */ const val AL_DIRECTION = 0x1005
/** Source attributes.  */ const val AL_LOOPING = 0x1007
/** Source attributes.  */ const val AL_BUFFER = 0x1009
/** Source attributes.  */ const val AL_SOURCE_STATE = 0x1010
/** Source attributes.  */ const val AL_CONE_OUTER_GAIN = 0x1022
/** Source attributes.  */ const val AL_SOURCE_TYPE = 0x1027

/** Source state.  */ const val AL_INITIAL = 0x1011
/** Source state.  */ const val AL_PLAYING = 0x1012
/** Source state.  */ const val AL_PAUSED = 0x1013
/** Source state.  */ const val AL_STOPPED = 0x1014

/** Listener attributes.  */ const val AL_ORIENTATION = 0x100F

/** Queue state.  */ const val AL_BUFFERS_QUEUED = 0x1015
/** Queue state.  */ const val AL_BUFFERS_PROCESSED = 0x1016

/** Gain bounds.  */ const val AL_MIN_GAIN = 0x100D
/** Gain bounds.  */ const val AL_MAX_GAIN = 0x100E

/** Distance model attributes,  */ const val AL_REFERENCE_DISTANCE = 0x1020
/** Distance model attributes,  */ const val AL_ROLLOFF_FACTOR = 0x1021
/** Distance model attributes,  */ const val AL_MAX_DISTANCE = 0x1023

/** Buffer attributes,  */ const val AL_FREQUENCY = 0x2001
/** Buffer attributes,  */ const val AL_BITS = 0x2002
/** Buffer attributes,  */ const val AL_CHANNELS = 0x2003
/** Buffer attributes,  */ const val AL_SIZE = 0x2004

/** Buffer formats.  */ const val AL_FORMAT_MONO8 = 0x1100
/** Buffer formats.  */ const val AL_FORMAT_MONO16 = 0x1101
/** Buffer formats.  */ const val AL_FORMAT_STEREO8 = 0x1102
/** Buffer formats.  */ const val AL_FORMAT_STEREO16 = 0x1103

/** Buffer state.  */ const val AL_UNUSED = 0x2010
/** Buffer state.  */ const val AL_PENDING = 0x2011
/** Buffer state.  */ const val AL_PROCESSED = 0x2012
