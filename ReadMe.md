# lwjgl stuff

Just learning lwjgl!

So far this project includes cool vao/vbo abstractions e.g.:
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

A module entity system (similar to [EntityX](https://github.com/alecthomas/entityx)):
```kotlin
object LocalPlayerEntity: Entity() {
	init {
		injectComponent(PositionedEntity())
		injectComponent(CameraComponent)
		injectComponent(ControllableEntity())
	}
}

// Loop through each meshed entity
Client.world.entities.each { entity: Entity, meshed: MeshedEntity ->
    // If it has a position apply a transform
    entity.component { positioned: PositionedEntity ->
        val matrix = ProjectionHandler.getModelMatrix(positioned, viewMatrix)
        shader.setUniform("worldMatrix", matrix)
    } `else` {
        shader.setUniform("worldMatrix", Mat4.identity)
    }
    // Draw the entities mesh
    meshed.mesh.draw()
}
```
