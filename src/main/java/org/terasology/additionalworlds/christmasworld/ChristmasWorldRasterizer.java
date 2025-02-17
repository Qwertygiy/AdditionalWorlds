/*
 * Copyright 2019 MovingBlocks
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
package org.terasology.additionalworlds.christmasworld;

import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

public class ChristmasWorldRasterizer implements WorldRasterizer {
    private Block ice;
    private Block leaf;
    private Block flower;
    @Override
    public void initialize() {
        ice = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Ice");
        leaf = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:RedLeaf");
        flower = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:GlowbellBloom");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        for (Vector3i position : chunkRegion.getRegion()) {
            float surfaceHeight = surfaceHeightFacet.getWorld(position.x, position.z);
            //if (position.y < surfaceHeight) {
            if(position.y<surfaceHeight) {
                if(((int)(position.x))%2==0) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position), ice);
                } else chunk.setBlock(ChunkMath.calcBlockPos(position), leaf);
                java.util.Random rand = new java.util.Random();
                int rr = rand.nextInt(75);
                if (rr >= 74) {
                    chunk.setBlock(ChunkMath.calcBlockPos(position.addY(1)), flower);
                }
            }


        }
    }
}