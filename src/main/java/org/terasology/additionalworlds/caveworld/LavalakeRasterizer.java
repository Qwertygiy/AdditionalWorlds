/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.additionalworlds.caveworld;

import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.ChunkConstants;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

/**
 * Still need this rasterizer because just changing the density does not provide the correct effect with the default perlin generator
 */
public class LavalakeRasterizer implements WorldRasterizer {
    private Block lava;
    private Block air;

    public LavalakeRasterizer() {
    }

    @Override
    public void initialize() {
        lava = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Lava");
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        air = blockManager.getBlock(BlockManager.AIR_ID);
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        LavalakeFacet caveFacet = chunkRegion.getFacet(LavalakeFacet.class);

        for (Vector3i position : ChunkConstants.CHUNK_REGION) {
            if (caveFacet.get(position)) {
                if (chunk.getBlock(position) != air) {
                    chunk.setBlock(position, lava);
                }
            }
        }
    }
}
