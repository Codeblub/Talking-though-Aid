# Server Module

This server folder holds the crossplay backend data for maps and weapons.

## Map design
- `Urban Assault`: medium-sized three-lane urban arena with high cover, alleyways, and a sniper tower.
- `Desert Compound`: small, close-range courtyard with sand walls and roof cover.
- `Night Street`: a compact neon-lit urban map for close fights, rooftop movement, and fast flank routes.

The server provides map metadata, weapon metadata, and character loadout data to both PC and Android clients.

## Crossplay function
- `CrossplayManager` in `server/src/main/java/com/codeblub/talkingthroughaid/server` selects available maps and weapons.
- It returns the same map list and weapon list to both platforms so PC and Android players share the same match setup.

## Folders
- `server/maps/`: map metadata files for crossplay matches.
- `server/weapons/`: weapon metadata files for the shared arsenal.
