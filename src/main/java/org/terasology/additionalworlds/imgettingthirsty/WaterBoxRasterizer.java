/*
 * Copyright 2017 MovingBlocks
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
package org.terasology.additionalworlds.imgettingthirsty;

import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

import java.util.Map.Entry;

public class WaterBoxRasterizer implements WorldRasterizer {
    Block glass;
    Block water;

    @Override
    public void initialize() {
        glass = CoreRegistry.get(BlockManager.class).getBlock("CoreBlocks:Glass");
        water = CoreRegistry.get(BlockManager.class).getBlock("AdditionalWorlds:waterSides");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        WaterBoxFacet waterBoxFacet = chunkRegion.getFacet(WaterBoxFacet.class);

        for (Entry<BaseVector3i, WaterBox> entry : waterBoxFacet.getWorldEntries().entrySet()) {
            // create a couple of 3d regions to help iterate through the cube shape, inside and out
            Vector3i centerHousePosition = new Vector3i(entry.getKey());
            int extent = entry.getValue().getExtent();
            centerHousePosition.add(0, extent, 0);
            Region3i walls = Region3i.createFromCenterExtents(centerHousePosition, extent);
            Region3i inside = Region3i.createFromCenterExtents(centerHousePosition, extent - 1);

            // loop through each of the positions in the cube, filling the middle with water
            for (Vector3i newBlockPosition : walls) {
                if (chunkRegion.getRegion().encompasses(newBlockPosition)
                        && !inside.encompasses(newBlockPosition)) {
                    chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), glass);
                }
                else if (chunkRegion.getRegion().encompasses(newBlockPosition)
                        && inside.encompasses(newBlockPosition)) {
                    chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), water);
                }
            }
        }
    }
}
