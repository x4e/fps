# lwjgl stuff

Just learning lwjgl!

## Stuff this project has

#### Font Renderer
Bitmap font renderer that supports variably spaced fonts

![Font rendering example](https://i.binclub.dev/kcmecrjw.png)

How it works:
1. Alpha channel only of bitmap texture is loaded into memory
2. Once vao for every character in the bitmap is created, containing two triangles and
texture coordinates of the characters position on the bitmap

#### Cool vao/vbo abstractions
```kotlin
val posBuf = positions.buffer()
val texBuf = textures.buffer()
val idxBuf = indices.buffer()

vao.use {
    posVbo.use {
        posVbo.bindData(posBuf)
        glEnableVertexAttribArray(0)
        posVbo.bindToVao(0, 3, GL_FLOAT)
    }
    
    texVbo.use {
        texVbo.bindData(texBuf)
        glEnableVertexAttribArray(1)
        texVbo.bindToVao(1, 2, GL_FLOAT)
    }
    
    idxVbo.use {
        idxVbo.bindData(idxBuf)
    }
}

memFree(posBuf, texBuf, idxBuf)

// draw

vao.use {
    idxVbo.use {
        texture?.bind()
        glDrawElements(GL_TRIANGLES, numVertices, GL_UNSIGNED_INT, 0)
        texture?.unbind()
    }
}
```

#### A modular entity system (similar to [EntityX](https://github.com/alecthomas/entityx)):
```kotlin
object LocalPlayerEntity: Entity() {
	init {
		injectComponent(PositionedEntity())
		injectComponent(CameraComponent)
		injectComponent(ControllableEntity())
	}
}

shader.use {
    shader["projectionMatrix"] = ProjectionHandler.projection
    shader["texture_sampler"] = 0
    
    // Loop through each entity that has a mesh
    // Entities with multiple mesh components will be iterated for each mesh they have
    Client.world.entities.each { entity: Entity, meshed: MeshedEntity ->
        entity.component { positioned: PositionedEntity ->
            val matrix = ProjectionHandler.getModelMatrix(positioned, viewMatrix)
            shader["worldMatrix"] = matrix
        } `else` {
            shader["worldMatrix"] = Mat4.identity
        }
        meshed.mesh.draw()
    }
}
```
