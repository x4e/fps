package dev.binclub.fps.client.utils.obj

import dev.binclub.fps.client.utils.gl.Mesh
import glm_.parseFloat
import glm_.parseInt
import glm_.vec2.Vec2
import glm_.vec3.Vec3

/**
 * @author cookiedragon234 10/Jul/2020
 */
object ObjLoader {
	private val lineFormatRegex = "\\s+".toRegex()
	
	fun loadMesh(name: String): Mesh {
		val vertices = ArrayList<Vec3>()
		val textures = ArrayList<Vec2>()
		val normals = ArrayList<Vec3>()
		val faces = ArrayList<Face>()
		
		val string = ObjLoader::class.java.getResourceAsStream("/models/$name.obj")
			.readBytes()
			.toString(Charsets.UTF_8)
		
		for (line in string.lineSequence()) {
			val tokens = line.split(lineFormatRegex)
			println(tokens)
			when (tokens[0]) {
				"v" -> {
					vertices.add(Vec3(tokens[1].parseFloat, tokens[2].parseFloat, tokens[3].parseFloat))
				}
				"vt" -> {
					textures.add(Vec2(tokens[1].parseFloat, tokens[2].parseFloat))
				}
				"vn" -> {
					normals.add(Vec3(tokens[1].parseFloat, tokens[2].parseFloat, tokens[3].parseFloat))
				}
				"f" -> {
					faces.add(parseFace(tokens))
				}
			}
		}
		return createMesh(vertices, textures, normals, faces)
	}
	
	private fun createMesh(vertices: List<Vec3>, textures: List<Vec2>, normals: List<Vec3>, faces: List<Face>): Mesh {
		val indices = ArrayList<Int>()
		val posArr = FloatArray(vertices.size * 3)
		for ((i, vertex) in vertices.withIndex()) {
			posArr[i * 3 + 0] = vertex.x
			posArr[i * 3 + 1] = vertex.y
			posArr[i * 3 + 2] = vertex.z
		}
		val textArr = FloatArray(vertices.size * 2)
		val normArr = FloatArray(vertices.size * 3)
		
		println("Faces $faces")
		for (face in faces) {
			for (indexGroup in face.indexes) {
				val pos = indexGroup.pos
				indices.add(pos)
				if (indexGroup.text >= 0) {
					val textCoord = textures[indexGroup.text]
					textArr[pos * 2 + 0] = textCoord.x
					textArr[pos * 2 + 1] = 1 - textCoord.y
				}
				if (indexGroup.norm >= 0) {
					val normCoord = normals[indexGroup.norm]
					normArr[pos * 3 + 0] = normCoord.x
					normArr[pos * 3 + 1] = normCoord.y
					normArr[pos * 3 + 2] = normCoord.z
				}
			}
		}
		return Mesh(posArr, textArr, normArr, indices.toIntArray())
	}
	
	private fun parseFace(tokens: List<String>): Face = Face(tokens.subList(1, tokens.size).map { parseIndex(it) })
	
	@Suppress("ReplaceSizeCheckWithIsNotEmpty")
	private fun parseIndex(index: String): IndexGroup {
		val tokens = index.split('/')
		
		return IndexGroup().also { group ->
			if (tokens.size >= 1) {
				group.pos = tokens[0].parseInt() - 1
			}
			if (tokens.size >= 2) {
				group.text = tokens[1].parseInt() - 1
			}
			if (tokens.size >= 3) {
				group.norm = tokens[2].parseInt() - 1
			}
		}
	}
	
	private data class IndexGroup (var pos: Int = -1, var text: Int = -1, var norm: Int = -1)
	
	private data class Face (val indexes: List<IndexGroup>)
}
